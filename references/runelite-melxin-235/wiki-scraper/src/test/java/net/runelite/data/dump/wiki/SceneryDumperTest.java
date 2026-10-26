/*
 * MIT License
 *
 * Copyright (c) 2026 Tyler Covalt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.runelite.data.dump.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SceneryDumperTest
{
	@Test
	void classifiesSeating()
	{
		assertEquals("Seating", SceneryDumper.classifyCategory("Wooden chair", new String[]{"Sit-on"}));
		assertEquals("Seating", SceneryDumper.classifyCategory("Bench", null));
		assertEquals("Seating", SceneryDumper.classifyCategory("Bar stool", null));
		assertEquals("Seating", SceneryDumper.classifyCategory("Grand Throne", null));
	}

	@Test
	void classifiesSurfaces()
	{
		assertEquals("Surfaces", SceneryDumper.classifyCategory("Table", null));
		assertEquals("Surfaces", SceneryDumper.classifyCategory("Shop counter", null));
		assertEquals("Surfaces", SceneryDumper.classifyCategory("Mahogany desk", null));
	}

	@Test
	void classifiesSleeping()
	{
		assertEquals("Sleeping", SceneryDumper.classifyCategory("Large bed", null));
		assertEquals("Sleeping", SceneryDumper.classifyCategory("Hammock", null));
		assertEquals("Sleeping", SceneryDumper.classifyCategory("Camp cot", null));
	}

	@Test
	void classifiesLighting()
	{
		assertEquals("Lighting", SceneryDumper.classifyCategory("Candle", new String[]{"Light"}));
		assertEquals("Lighting", SceneryDumper.classifyCategory("Wall torch", null));
		assertEquals("Lighting", SceneryDumper.classifyCategory("Lantern", null));
		assertEquals("Lighting", SceneryDumper.classifyCategory("Fireplace", null));
	}

	@Test
	void classifiesEntrances()
	{
		assertEquals("Entrances", SceneryDumper.classifyCategory("Door", new String[]{"Open"}));
		assertEquals("Entrances", SceneryDumper.classifyCategory("Large iron gate", null));
		assertEquals("Entrances", SceneryDumper.classifyCategory("Curtain", null));
		assertEquals("Entrances", SceneryDumper.classifyCategory("Trapdoor", null));
	}

	@Test
	void classifiesCrafting()
	{
		assertEquals("Crafting", SceneryDumper.classifyCategory("Anvil", new String[]{"Smith"}));
		assertEquals("Crafting", SceneryDumper.classifyCategory("Furnace", new String[]{"Smelt"}));
		assertEquals("Crafting", SceneryDumper.classifyCategory("Cooking range", new String[]{"Cook"}));
		assertEquals("Crafting", SceneryDumper.classifyCategory("Pottery wheel", null));
		assertEquals("Crafting", SceneryDumper.classifyCategory("Spinning wheel", null));
	}

	@Test
	void classifiesStorage()
	{
		assertEquals("Storage", SceneryDumper.classifyCategory("Crate", new String[]{"Search"}));
		assertEquals("Storage", SceneryDumper.classifyCategory("Closed chest", null));
		assertEquals("Storage", SceneryDumper.classifyCategory("Oak drawers", null));
		assertEquals("Storage", SceneryDumper.classifyCategory("Cupboard", null));
		assertEquals("Storage", SceneryDumper.classifyCategory("Beer barrel", null));
	}
}
