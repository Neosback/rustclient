//! Jagex desktop wire ciphers. These are compatibility primitives, not general crypto APIs.
use std::num::Wrapping as W;
use zeroize::Zeroize;

/// Stateful ISAAC word stream, seeded explicitly; never used for entropy.
pub struct Isaac {
    memory: [u32; 256],
    results: [u32; 256],
    a: u32,
    b: u32,
    c: u32,
    remaining: usize,
}
impl Isaac {
    #[must_use]
    pub fn new(seed: [u32; 4]) -> Self {
        let mut this = Self {
            memory: [0; 256],
            results: [0; 256],
            a: 0,
            b: 0,
            c: 0,
            remaining: 256,
        };
        this.results[..4].copy_from_slice(&seed);
        let mut v = [W(0x9e37_79b9); 8];
        for _ in 0..4 {
            mix(&mut v);
        }
        for pass in 0..2 {
            for i in (0..256).step_by(8) {
                for (j, value) in v.iter_mut().enumerate() {
                    *value += W(if pass == 0 {
                        this.results[i + j]
                    } else {
                        this.memory[i + j]
                    });
                }
                mix(&mut v);
                for (j, value) in v.iter().enumerate() {
                    this.memory[i + j] = value.0;
                }
            }
        }
        this.generate();
        this
    }
    fn generate(&mut self) {
        self.c = self.c.wrapping_add(1);
        self.b = self.b.wrapping_add(self.c);
        for i in 0..256 {
            let x = self.memory[i];
            self.a ^= match i % 4 {
                0 => self.a << 13,
                1 => self.a >> 6,
                2 => self.a << 2,
                _ => self.a >> 16,
            };
            self.a = self.a.wrapping_add(self.memory[(i + 128) & 255]);
            let y = self.memory[((x >> 2) & 255) as usize]
                .wrapping_add(self.a)
                .wrapping_add(self.b);
            self.memory[i] = y;
            self.b = self.memory[((y >> 10) & 255) as usize].wrapping_add(x);
            self.results[i] = self.b;
        }
    }
    pub fn next_word(&mut self) -> u32 {
        if self.remaining == 0 {
            self.generate();
            self.remaining = 256;
        }
        self.remaining -= 1;
        self.results[self.remaining]
    }
}
impl Drop for Isaac {
    fn drop(&mut self) {
        self.memory.zeroize();
        self.results.zeroize();
        self.a.zeroize();
        self.b.zeroize();
        self.c.zeroize();
    }
}
fn mix(v: &mut [W<u32>; 8]) {
    v[0] ^= v[1] << 11;
    v[3] += v[0];
    v[1] += v[2];
    v[1] ^= v[2] >> 2;
    v[4] += v[1];
    v[2] += v[3];
    v[2] ^= v[3] << 8;
    v[5] += v[2];
    v[3] += v[4];
    v[3] ^= v[4] >> 16;
    v[6] += v[3];
    v[4] += v[5];
    v[4] ^= v[5] << 10;
    v[7] += v[4];
    v[5] += v[6];
    v[5] ^= v[6] >> 4;
    v[0] += v[5];
    v[6] += v[7];
    v[6] ^= v[7] << 8;
    v[1] += v[6];
    v[7] += v[0];
    v[7] ^= v[0] >> 9;
    v[2] += v[7];
    v[0] += v[1];
}

/// Encrypt complete eight-byte blocks, retaining the protocol's unencrypted tail.
pub(crate) fn xtea(bytes: &mut [u8], key: [u32; 4]) {
    let k = key.map(W);
    for block in bytes.as_chunks_mut::<8>().0 {
        let mut a = W(u32::from_be_bytes(
            block[..4].try_into().expect("four-byte block"),
        ));
        let mut b = W(u32::from_be_bytes(
            block[4..].try_into().expect("four-byte block"),
        ));
        let mut sum = W(0_u32);
        for _ in 0..32 {
            a += (((b << 4) ^ (b >> 5)) + b) ^ (sum + k[(sum.0 & 3) as usize]);
            sum += W(0x9e37_79b9);
            b += (((a << 4) ^ (a >> 5)) + a) ^ (sum + k[((sum.0 >> 11) & 3) as usize]);
        }
        block[..4].copy_from_slice(&a.0.to_be_bytes());
        block[4..].copy_from_slice(&b.0.to_be_bytes());
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use sha2::{Digest, Sha256};
    #[test]
    fn isaac_matches_rsprot_across_two_complete_generations() {
        for (seed, expected) in [
            (
                [0; 4],
                "c5748c77f4ac6a5b062e2a309dd92b3660dacadfe89b891ced2d9ce4f1d86367",
            ),
            (
                [1, 2, 3, 4],
                "46563c2a08f673a83a571b3f4e10aea3026031168b01d8e4b35cfd3b89822a6a",
            ),
        ] {
            let mut cipher = Isaac::new(seed);
            let mut hash = Sha256::new();
            for _ in 0..512 {
                hash.update(cipher.next_word().to_be_bytes());
            }
            assert_eq!(format!("{:x}", hash.finalize()), expected);
        }
    }
    #[test]
    fn xtea_standard_zero_vector_and_unencrypted_tail() {
        let mut bytes = [0; 11];
        bytes[8..].copy_from_slice(&[1, 2, 3]);
        xtea(&mut bytes, [0; 4]);
        assert_eq!(
            &bytes[..8],
            &[0xde, 0xe9, 0xd4, 0xd8, 0xf7, 0x13, 0x1e, 0xd9]
        );
        assert_eq!(&bytes[8..], &[1, 2, 3]);
    }
}
