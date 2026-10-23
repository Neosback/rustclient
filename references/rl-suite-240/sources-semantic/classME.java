import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("me")
public class classME {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4489 = 1048576;
   @ObfuscatedSignature(descriptor = "Laac;")
   @ObfuscatedName("al")
   public static classAAC field4486 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final String field4494 = "rw";
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final String field4495 = "main_file_cache.idx";
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final String field4496 = "main_file_cache.idx255";
   @ToRemove(unused = "true")
   @ObfuscatedName("be")
   static final int field4492 = 89;
   @ObfuscatedSignature(descriptor = "Laac;")
   @ObfuscatedName("aj")
   public static classAAC field4487 = null;
   @ObfuscatedSignature(descriptor = "Laac;")
   @ObfuscatedName("aw")
   static classAAC field4488 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4491 = 24;
   @ToRemove(unused = "true")
   @ObfuscatedName("cp")
   static final int field4493 = 110;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field4490 = 2;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V")
   @ObfuscatedName("ao")
   public static void method7582(String var0, String var1, String var2, int var3, int var4) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var0 = var0 + "-" + var1;
      }

      classUP.field6361 = var4 * -552347913;
      MidiRequest.field4635 = var3 * 381969765;

      try {
         Script.field499 = System.getProperty("os.name");
      } catch (Exception var36) {
         Script.field499 = "Unknown";
      }

      classZW.field7282 = Script.field499.toLowerCase();
      ClanChannel.field2952 = null;

      try {
         ClanChannel.field2952 = System.getProperty("jagex.userhome");
      } catch (Exception var35) {
      }

      if (ClanChannel.field2952 == null) {
         try {
            ClanChannel.field2952 = System.getProperty("user.home");
         } catch (Exception var34) {
         }
      }

      if (null != ClanChannel.field2952) {
         ClanChannel.field2952 = ClanChannel.field2952 + "/";
      }

      try {
         if (classZW.field7282.startsWith("win")) {
            if (ClanChannel.field2952 == null) {
               ClanChannel.field2952 = client.method2380("USERPROFILE");
            }
         } else if (null == ClanChannel.field2952) {
            ClanChannel.field2952 = client.method2380("HOME");
         }

         if (null != ClanChannel.field2952) {
            ClanChannel.field2952 = ClanChannel.field2952 + "/";
         }
      } catch (Exception var33) {
      }

      if (null == ClanChannel.field2952) {
         ClanChannel.field2952 = "~/";
      }

      classWQ.field6766 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", ClanChannel.field2952, "/tmp/", ""};
      classCV.field1303 = new String[]{".jagex_cache_" + MidiRequest.field4635 * 1602897005, ".file_store_" + MidiRequest.field4635 * 1697206599};

      label479:
      for (int var5 = 0; var5 < 4; var5++) {
         String var7 = 0 == var5 ? "" : "" + var5;
         classDB.field1377 = new File(ClanChannel.field2952, "jagex_cl_" + var0 + "_" + var2 + var7 + ".dat");
         String var8 = null;
         String var9 = null;
         boolean var10 = false;
         if (classDB.field1377.exists()) {
            classAAR var11 = null;

            try {
               var11 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var12 = new classXY((int)var11.method251(-153393736));

               while (702114061 * var12.field6953 < var12.field6952.length) {
                  int var13 = var11.method255(var12.field6952, var12.field6953 * 702114061, var12.field6952.length - 2077077400 * var12.field6953, -1397369950);
                  if (var13 == -1) {
                     throw new IOException();
                  }

                  var12.field6953 += -1673129772 * var13;
               }

               var12.field6953 = 0;
               int var61 = var12.method13095(-346779531);
               if (var61 < 1 || var61 > 3) {
                  throw new IOException("" + var61);
               }

               int var14 = 0;
               if (var61 > 1) {
                  var14 = var12.method13095(-346779531);
               }

               if (var61 <= 2) {
                  var8 = var12.method13134(1165669181);
                  if (1 == var14) {
                     var9 = var12.method13134(1628145426);
                  }
               } else {
                  var8 = classXY.method13137(var12, 1081461655);
                  if (var14 == 1) {
                     var9 = classXY.method13137(var12, 755786984);
                  }
               }
            } catch (Exception var39) {
               var39.printStackTrace();
            } finally {
               try {
                  if (null != var11) {
                     var11.method242((byte)1);
                  }
               } catch (IOException var31) {
               }
            }

            if (null != var8) {
               File var56 = new File(var8);
               if (!var56.exists()) {
                  var8 = null;
               }
            }

            if (null != var8) {
               File var57 = new File(var8, "test.dat");
               if (!classBB.method788(var57, true, -1102901767)) {
                  var8 = null;
               }
            }
         }

         if (var8 == null && 0 == var5) {
            label452:
            for (int var53 = 0; var53 < classCV.field1303.length; var53++) {
               for (int var58 = 0; var58 < classWQ.field6766.length; var58++) {
                  File var62 = new File(classWQ.field6766[var58] + classCV.field1303[var53] + File.separatorChar + var0 + File.separatorChar);
                  if (var62.exists() && classBB.method788(new File(var62, "test.dat"), true, -1576606522)) {
                     var8 = var62.toString();
                     var10 = true;
                     break label452;
                  }
               }
            }
         }

         if (var8 == null) {
            var8 = ClanChannel.field2952
               + File.separatorChar
               + "jagexcache"
               + var7
               + File.separatorChar
               + var0
               + File.separatorChar
               + var2
               + File.separatorChar;
            var10 = true;
         }

         if (var9 != null) {
            File var54 = new File(var9);
            File var59 = new File(var8);

            try {
               File[] var63 = var54.listFiles();
               File[] var65 = var63;

               for (int var15 = 0; var15 < var65.length; var15++) {
                  File var16 = var65[var15];
                  File var17 = new File(var59, var16.getName());
                  boolean var18 = var16.renameTo(var17);
                  if (!var18) {
                     throw new IOException();
                  }
               }
            } catch (Exception var38) {
               var38.printStackTrace();
            }

            var10 = true;
         }

         if (var10) {
            File var55 = new File(var8);
            Object var60 = null;

            try {
               classAAR var64 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var66 = new classXY(500);
               classXY.method13029(var66, 3, (byte)-60);
               classXY.method13029(var66, null != var60 ? 1 : 0, (byte)-101);
               classXY.method13064(var66, var55.getPath(), -910745300);
               if (var60 != null) {
                  classXY.method13064(var66, var60.getPath(), -910745300);
               }

               var64.method238(var66.field6952, 0, 689286479 * var66.field6953, -900673116);
               var64.method242((byte)1);
            } catch (IOException var32) {
               var32.printStackTrace();
            }
         }

         File var6 = new File(var8);
         classSL.field5973 = var6;
         if (!classSL.field5973.exists()) {
            classSL.field5973.mkdirs();
         }

         File[] var44 = classSL.field5973.listFiles();
         if (var44 != null) {
            File[] var47 = var44;

            for (int var50 = 0; var50 < var47.length; var50++) {
               File var52 = var47[var50];
               if (!classBB.method788(var52, false, -583133266)) {
                  continue label479;
               }
            }
         }
         break;
      }

      File var41 = classSL.field5973;
      classMT.field4584 = var41;
      if (!classMT.field4584.exists()) {
         throw new RuntimeException("");
      } else {
         classMT.field4578 = true;

         try {
            File var42 = new File(ClanChannel.field2952, "random.dat");
            if (var42.exists()) {
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            } else {
               label403:
               for (int var45 = 0; var45 < classCV.field1303.length; var45++) {
                  for (int var48 = 0; var48 < classWQ.field6766.length; var48++) {
                     File var51 = new File(classWQ.field6766[var48] + classCV.field1303[var45] + File.separatorChar + "random.dat");
                     if (var51.exists()) {
                        field4488 = new classAAC(new classAAR(var51, "rw", 25L), 24, 0);
                        break label403;
                     }
                  }
               }
            }

            if (field4488 == null) {
               RandomAccessFile var46 = new RandomAccessFile(var42, "rw");
               int var49 = var46.read();
               var46.seek(0L);
               var46.write(var49);
               var46.seek(0L);
               var46.close();
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            }
         } catch (IOException var37) {
         }

         field4486 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.dat2", -654385736), "rw", 1048576000L), -1796892074, 0);
         field4487 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx255", -1088429810), "rw", 1048576L), 1530390350, 0);
         GraphicsObject.field477 = new classAAC[classUP.field6361 * -1127736048];

         for (int var43 = 0; var43 < -646722873 * classUP.field6361; var43++) {
            GraphicsObject.field477[var43] = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx" + var43, 140195232), "rw", 1048576L), 6000, 0);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("at")
   public static void method7596(classXY var0, int var1) {
      if (field4488 != null) {
         try {
            field4488.method131(0L);
            field4488.method148(var0.field6952, var1, 24, -1298094667);
         } catch (Exception var3) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V")
   @ObfuscatedName("ac")
   public static void method7583(String var0, String var1, String var2, int var3, int var4) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var0 = var0 + "-" + var1;
      }

      classUP.field6361 = var4 * -552347913;
      MidiRequest.field4635 = var3 * 381969765;

      try {
         Script.field499 = System.getProperty("os.name");
      } catch (Exception var36) {
         Script.field499 = "Unknown";
      }

      classZW.field7282 = Script.field499.toLowerCase();
      ClanChannel.field2952 = null;

      try {
         ClanChannel.field2952 = System.getProperty("jagex.userhome");
      } catch (Exception var35) {
      }

      if (ClanChannel.field2952 == null) {
         try {
            ClanChannel.field2952 = System.getProperty("user.home");
         } catch (Exception var34) {
         }
      }

      if (null != ClanChannel.field2952) {
         ClanChannel.field2952 = ClanChannel.field2952 + "/";
      }

      try {
         if (classZW.field7282.startsWith("win")) {
            if (ClanChannel.field2952 == null) {
               ClanChannel.field2952 = client.method2380("USERPROFILE");
            }
         } else if (null == ClanChannel.field2952) {
            ClanChannel.field2952 = client.method2380("HOME");
         }

         if (null != ClanChannel.field2952) {
            ClanChannel.field2952 = ClanChannel.field2952 + "/";
         }
      } catch (Exception var33) {
      }

      if (null == ClanChannel.field2952) {
         ClanChannel.field2952 = "~/";
      }

      classWQ.field6766 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", ClanChannel.field2952, "/tmp/", ""};
      classCV.field1303 = new String[]{".jagex_cache_" + MidiRequest.field4635 * 1602897005, ".file_store_" + MidiRequest.field4635 * 1602897005};

      label479:
      for (int var5 = 0; var5 < 4; var5++) {
         String var7 = 0 == var5 ? "" : "" + var5;
         classDB.field1377 = new File(ClanChannel.field2952, "jagex_cl_" + var0 + "_" + var2 + var7 + ".dat");
         String var8 = null;
         String var9 = null;
         boolean var10 = false;
         if (classDB.field1377.exists()) {
            classAAR var11 = null;

            try {
               var11 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var12 = new classXY((int)var11.method251(-153393736));

               while (702114061 * var12.field6953 < var12.field6952.length) {
                  int var13 = var11.method255(var12.field6952, var12.field6953 * 702114061, var12.field6952.length - 702114061 * var12.field6953, -1397369950);
                  if (var13 == -1) {
                     throw new IOException();
                  }

                  var12.field6953 += -1095856699 * var13;
               }

               var12.field6953 = 0;
               int var61 = var12.method13095(-346779531);
               if (var61 < 1 || var61 > 3) {
                  throw new IOException("" + var61);
               }

               int var14 = 0;
               if (var61 > 1) {
                  var14 = var12.method13095(-346779531);
               }

               if (var61 <= 2) {
                  var8 = var12.method13134(1867167769);
                  if (1 == var14) {
                     var9 = var12.method13134(1315705290);
                  }
               } else {
                  var8 = classXY.method13137(var12, 1500461334);
                  if (var14 == 1) {
                     var9 = classXY.method13137(var12, -484778587);
                  }
               }
            } catch (Exception var39) {
               var39.printStackTrace();
            } finally {
               try {
                  if (null != var11) {
                     var11.method242((byte)1);
                  }
               } catch (IOException var31) {
               }
            }

            if (null != var8) {
               File var56 = new File(var8);
               if (!var56.exists()) {
                  var8 = null;
               }
            }

            if (null != var8) {
               File var57 = new File(var8, "test.dat");
               if (!classBB.method788(var57, true, 525752549)) {
                  var8 = null;
               }
            }
         }

         if (var8 == null && 0 == var5) {
            label452:
            for (int var53 = 0; var53 < classCV.field1303.length; var53++) {
               for (int var58 = 0; var58 < classWQ.field6766.length; var58++) {
                  File var62 = new File(classWQ.field6766[var58] + classCV.field1303[var53] + File.separatorChar + var0 + File.separatorChar);
                  if (var62.exists() && classBB.method788(new File(var62, "test.dat"), true, 1313050237)) {
                     var8 = var62.toString();
                     var10 = true;
                     break label452;
                  }
               }
            }
         }

         if (var8 == null) {
            var8 = ClanChannel.field2952
               + File.separatorChar
               + "jagexcache"
               + var7
               + File.separatorChar
               + var0
               + File.separatorChar
               + var2
               + File.separatorChar;
            var10 = true;
         }

         if (var9 != null) {
            File var54 = new File(var9);
            File var59 = new File(var8);

            try {
               File[] var63 = var54.listFiles();
               File[] var65 = var63;

               for (int var15 = 0; var15 < var65.length; var15++) {
                  File var16 = var65[var15];
                  File var17 = new File(var59, var16.getName());
                  boolean var18 = var16.renameTo(var17);
                  if (!var18) {
                     throw new IOException();
                  }
               }
            } catch (Exception var38) {
               var38.printStackTrace();
            }

            var10 = true;
         }

         if (var10) {
            File var55 = new File(var8);
            Object var60 = null;

            try {
               classAAR var64 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var66 = new classXY(500);
               classXY.method13029(var66, 3, (byte)-103);
               classXY.method13029(var66, null != var60 ? 1 : 0, (byte)-57);
               classXY.method13064(var66, var55.getPath(), -910745300);
               if (var60 != null) {
                  classXY.method13064(var66, var60.getPath(), -910745300);
               }

               var64.method238(var66.field6952, 0, 702114061 * var66.field6953, -612629987);
               var64.method242((byte)1);
            } catch (IOException var32) {
               var32.printStackTrace();
            }
         }

         File var6 = new File(var8);
         classSL.field5973 = var6;
         if (!classSL.field5973.exists()) {
            classSL.field5973.mkdirs();
         }

         File[] var44 = classSL.field5973.listFiles();
         if (var44 != null) {
            File[] var47 = var44;

            for (int var50 = 0; var50 < var47.length; var50++) {
               File var52 = var47[var50];
               if (!classBB.method788(var52, false, -309035398)) {
                  continue label479;
               }
            }
         }
         break;
      }

      File var41 = classSL.field5973;
      classMT.field4584 = var41;
      if (!classMT.field4584.exists()) {
         throw new RuntimeException("");
      } else {
         classMT.field4578 = true;

         try {
            File var42 = new File(ClanChannel.field2952, "random.dat");
            if (var42.exists()) {
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            } else {
               label403:
               for (int var45 = 0; var45 < classCV.field1303.length; var45++) {
                  for (int var48 = 0; var48 < classWQ.field6766.length; var48++) {
                     File var51 = new File(classWQ.field6766[var48] + classCV.field1303[var45] + File.separatorChar + "random.dat");
                     if (var51.exists()) {
                        field4488 = new classAAC(new classAAR(var51, "rw", 25L), 24, 0);
                        break label403;
                     }
                  }
               }
            }

            if (field4488 == null) {
               RandomAccessFile var46 = new RandomAccessFile(var42, "rw");
               int var49 = var46.read();
               var46.seek(0L);
               var46.write(var49);
               var46.seek(0L);
               var46.close();
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            }
         } catch (IOException var37) {
         }

         field4486 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.dat2", 140772158), "rw", 1048576000L), 5200, 0);
         field4487 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx255", -493268328), "rw", 1048576L), 6000, 0);
         GraphicsObject.field477 = new classAAC[classUP.field6361 * -646722873];

         for (int var43 = 0; var43 < -646722873 * classUP.field6361; var43++) {
            GraphicsObject.field477[var43] = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx" + var43, 25814695), "rw", 1048576L), 6000, 0);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V")
   @ObfuscatedName("aa")
   public static void method7584(String var0, String var1, String var2, int var3, int var4) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var0 = var0 + "-" + var1;
      }

      classUP.field6361 = var4 * -552347913;
      MidiRequest.field4635 = var3 * 381969765;

      try {
         Script.field499 = System.getProperty("os.name");
      } catch (Exception var36) {
         Script.field499 = "Unknown";
      }

      classZW.field7282 = Script.field499.toLowerCase();
      ClanChannel.field2952 = null;

      try {
         ClanChannel.field2952 = System.getProperty("jagex.userhome");
      } catch (Exception var35) {
      }

      if (ClanChannel.field2952 == null) {
         try {
            ClanChannel.field2952 = System.getProperty("user.home");
         } catch (Exception var34) {
         }
      }

      if (null != ClanChannel.field2952) {
         ClanChannel.field2952 = ClanChannel.field2952 + "/";
      }

      try {
         if (classZW.field7282.startsWith("win")) {
            if (ClanChannel.field2952 == null) {
               ClanChannel.field2952 = client.method2380("USERPROFILE");
            }
         } else if (null == ClanChannel.field2952) {
            ClanChannel.field2952 = client.method2380("HOME");
         }

         if (null != ClanChannel.field2952) {
            ClanChannel.field2952 = ClanChannel.field2952 + "/";
         }
      } catch (Exception var33) {
      }

      if (null == ClanChannel.field2952) {
         ClanChannel.field2952 = "~/";
      }

      classWQ.field6766 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", ClanChannel.field2952, "/tmp/", ""};
      classCV.field1303 = new String[]{".jagex_cache_" + MidiRequest.field4635 * 1555860074, ".file_store_" + MidiRequest.field4635 * -1613496402};

      label479:
      for (int var5 = 0; var5 < 4; var5++) {
         String var7 = 0 == var5 ? "" : "" + var5;
         classDB.field1377 = new File(ClanChannel.field2952, "jagex_cl_" + var0 + "_" + var2 + var7 + ".dat");
         String var8 = null;
         String var9 = null;
         boolean var10 = false;
         if (classDB.field1377.exists()) {
            classAAR var11 = null;

            try {
               var11 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var12 = new classXY((int)var11.method251(-153393736));

               while (-33347134 * var12.field6953 < var12.field6952.length) {
                  int var13 = var11.method255(var12.field6952, var12.field6953 * 430135352, var12.field6952.length - 1255560002 * var12.field6953, -1397369950);
                  if (var13 == -1) {
                     throw new IOException();
                  }

                  var12.field6953 += -1095856699 * var13;
               }

               var12.field6953 = 0;
               int var61 = var12.method13095(-346779531);
               if (var61 < 1 || var61 > 3) {
                  throw new IOException("" + var61);
               }

               int var14 = 0;
               if (var61 > 1) {
                  var14 = var12.method13095(-346779531);
               }

               if (var61 <= 2) {
                  var8 = var12.method13134(-1060298622);
                  if (1 == var14) {
                     var9 = var12.method13134(-1047636549);
                  }
               } else {
                  var8 = classXY.method13137(var12, -53657240);
                  if (var14 == 1) {
                     var9 = classXY.method13137(var12, 1584755635);
                  }
               }
            } catch (Exception var39) {
               var39.printStackTrace();
            } finally {
               try {
                  if (null != var11) {
                     var11.method242((byte)1);
                  }
               } catch (IOException var31) {
               }
            }

            if (null != var8) {
               File var56 = new File(var8);
               if (!var56.exists()) {
                  var8 = null;
               }
            }

            if (null != var8) {
               File var57 = new File(var8, "test.dat");
               if (!classBB.method788(var57, true, 1345804784)) {
                  var8 = null;
               }
            }
         }

         if (var8 == null && 0 == var5) {
            label452:
            for (int var53 = 0; var53 < classCV.field1303.length; var53++) {
               for (int var58 = 0; var58 < classWQ.field6766.length; var58++) {
                  File var62 = new File(classWQ.field6766[var58] + classCV.field1303[var53] + File.separatorChar + var0 + File.separatorChar);
                  if (var62.exists() && classBB.method788(new File(var62, "test.dat"), true, 913942607)) {
                     var8 = var62.toString();
                     var10 = true;
                     break label452;
                  }
               }
            }
         }

         if (var8 == null) {
            var8 = ClanChannel.field2952
               + File.separatorChar
               + "jagexcache"
               + var7
               + File.separatorChar
               + var0
               + File.separatorChar
               + var2
               + File.separatorChar;
            var10 = true;
         }

         if (var9 != null) {
            File var54 = new File(var9);
            File var59 = new File(var8);

            try {
               File[] var63 = var54.listFiles();
               File[] var65 = var63;

               for (int var15 = 0; var15 < var65.length; var15++) {
                  File var16 = var65[var15];
                  File var17 = new File(var59, var16.getName());
                  boolean var18 = var16.renameTo(var17);
                  if (!var18) {
                     throw new IOException();
                  }
               }
            } catch (Exception var38) {
               var38.printStackTrace();
            }

            var10 = true;
         }

         if (var10) {
            File var55 = new File(var8);
            Object var60 = null;

            try {
               classAAR var64 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var66 = new classXY(-1871609904);
               classXY.method13029(var66, 3, (byte)-62);
               classXY.method13029(var66, null != var60 ? 1 : 0, (byte)-128);
               classXY.method13064(var66, var55.getPath(), -910745300);
               if (var60 != null) {
                  classXY.method13064(var66, var60.getPath(), -910745300);
               }

               var64.method238(var66.field6952, 0, 702114061 * var66.field6953, -1391552243);
               var64.method242((byte)1);
            } catch (IOException var32) {
               var32.printStackTrace();
            }
         }

         File var6 = new File(var8);
         classSL.field5973 = var6;
         if (!classSL.field5973.exists()) {
            classSL.field5973.mkdirs();
         }

         File[] var44 = classSL.field5973.listFiles();
         if (var44 != null) {
            File[] var47 = var44;

            for (int var50 = 0; var50 < var47.length; var50++) {
               File var52 = var47[var50];
               if (!classBB.method788(var52, false, -713999436)) {
                  continue label479;
               }
            }
         }
         break;
      }

      File var41 = classSL.field5973;
      classMT.field4584 = var41;
      if (!classMT.field4584.exists()) {
         throw new RuntimeException("");
      } else {
         classMT.field4578 = true;

         try {
            File var42 = new File(ClanChannel.field2952, "random.dat");
            if (var42.exists()) {
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            } else {
               label403:
               for (int var45 = 0; var45 < classCV.field1303.length; var45++) {
                  for (int var48 = 0; var48 < classWQ.field6766.length; var48++) {
                     File var51 = new File(classWQ.field6766[var48] + classCV.field1303[var45] + File.separatorChar + "random.dat");
                     if (var51.exists()) {
                        field4488 = new classAAC(new classAAR(var51, "rw", 25L), 24, 0);
                        break label403;
                     }
                  }
               }
            }

            if (field4488 == null) {
               RandomAccessFile var46 = new RandomAccessFile(var42, "rw");
               int var49 = var46.read();
               var46.seek(0L);
               var46.write(var49);
               var46.seek(0L);
               var46.close();
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            }
         } catch (IOException var37) {
         }

         field4486 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.dat2", -503731367), "rw", 1048576000L), 585330237, 0);
         field4487 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx255", -339682670), "rw", 1048576L), 6000, 0);
         GraphicsObject.field477 = new classAAC[classUP.field6361 * -646722873];

         for (int var43 = 0; var43 < -646722873 * classUP.field6361; var43++) {
            GraphicsObject.field477[var43] = new classAAC(
               new classAAR(classBJ.method1118("main_file_cache.idx" + var43, -742991889), "rw", 1048576L), 2029690893, 0
            );
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   public static void method7600() {
      try {
         classAAC.method125(field4486, -220896546);

         for (int var0 = 0; var0 < -646722873 * classUP.field6361; var0++) {
            classAAC.method125(GraphicsObject.field477[var0], 890084153);
         }

         classAAC.method125(field4487, 442594466);
         classAAC.method125(field4488, 99744384);
      } catch (Exception var1) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/io/File;Z)Z")
   @ObfuscatedName("al")
   static boolean method7586(File var0, boolean var1) {
      try {
         RandomAccessFile var2 = new RandomAccessFile(var0, "rw");
         int var3 = var2.read();
         var2.seek(0L);
         var2.write(var3);
         var2.seek(0L);
         var2.close();
         if (var1) {
            var0.delete();
         }

         return true;
      } catch (Exception var4) {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/io/File;Z)Z")
   @ObfuscatedName("aj")
   static boolean method7587(File var0, boolean var1) {
      try {
         RandomAccessFile var2 = new RandomAccessFile(var0, "rw");
         int var3 = var2.read();
         var2.seek(0L);
         var2.write(var3);
         var2.seek(0L);
         var2.close();
         if (var1) {
            var0.delete();
         }

         return true;
      } catch (Exception var4) {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/io/File;Z)Z")
   @ObfuscatedName("ay")
   static boolean method7588(File var0, boolean var1) {
      try {
         RandomAccessFile var2 = new RandomAccessFile(var0, "rw");
         int var3 = var2.read();
         var2.seek(0L);
         var2.write(var3);
         var2.seek(0L);
         var2.close();
         if (var1) {
            var0.delete();
         }

         return true;
      } catch (Exception var4) {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/io/File;Z)Z")
   @ObfuscatedName("aq")
   static boolean method7589(File var0, boolean var1) {
      try {
         RandomAccessFile var2 = new RandomAccessFile(var0, "rw");
         int var3 = var2.read();
         var2.seek(0L);
         var2.write(var3);
         var2.seek(0L);
         var2.close();
         if (var1) {
            var0.delete();
         }

         return true;
      } catch (Exception var4) {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Z)Laar;")
   @ObfuscatedName("ad")
   public static classAAR method7590(String var0, String var1, boolean var2) {
      File var3 = new File(classSL.field5973, "preferences" + var0 + ".dat");
      if (var3.exists()) {
         try {
            return new classAAR(var3, "rw", 10000L);
         } catch (IOException var9) {
         }
      }

      String var4 = "";
      if (33 == -471465215 * MidiRequest.field4635) {
         var4 = "_rc";
      } else if (MidiRequest.field4635 * -1646067518 == 2142874692) {
         var4 = "_wip";
      }

      File var5 = new File(ClanChannel.field2952, "jagex_" + var1 + "_preferences" + var0 + var4 + ".dat");
      if (!var2 && var5.exists()) {
         try {
            return new classAAR(var5, "rw", 10000L);
         } catch (IOException var8) {
         }
      }

      try {
         return new classAAR(var3, "rw", 10000L);
      } catch (IOException var7) {
         throw new RuntimeException();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V")
   @ObfuscatedName("ax")
   public static void method7585(String var0, String var1, String var2, int var3, int var4) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var0 = var0 + "-" + var1;
      }

      classUP.field6361 = var4 * -552347913;
      MidiRequest.field4635 = var3 * 381969765;

      try {
         Script.field499 = System.getProperty("os.name");
      } catch (Exception var36) {
         Script.field499 = "Unknown";
      }

      classZW.field7282 = Script.field499.toLowerCase();
      ClanChannel.field2952 = null;

      try {
         ClanChannel.field2952 = System.getProperty("jagex.userhome");
      } catch (Exception var35) {
      }

      if (ClanChannel.field2952 == null) {
         try {
            ClanChannel.field2952 = System.getProperty("user.home");
         } catch (Exception var34) {
         }
      }

      if (null != ClanChannel.field2952) {
         ClanChannel.field2952 = ClanChannel.field2952 + "/";
      }

      try {
         if (classZW.field7282.startsWith("win")) {
            if (ClanChannel.field2952 == null) {
               ClanChannel.field2952 = client.method2380("USERPROFILE");
            }
         } else if (null == ClanChannel.field2952) {
            ClanChannel.field2952 = client.method2380("HOME");
         }

         if (null != ClanChannel.field2952) {
            ClanChannel.field2952 = ClanChannel.field2952 + "/";
         }
      } catch (Exception var33) {
      }

      if (null == ClanChannel.field2952) {
         ClanChannel.field2952 = "~/";
      }

      classWQ.field6766 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", ClanChannel.field2952, "/tmp/", ""};
      classCV.field1303 = new String[]{".jagex_cache_" + MidiRequest.field4635 * 1602897005, ".file_store_" + MidiRequest.field4635 * 1213339710};

      label479:
      for (int var5 = 0; var5 < 4; var5++) {
         String var7 = 0 == var5 ? "" : "" + var5;
         classDB.field1377 = new File(ClanChannel.field2952, "jagex_cl_" + var0 + "_" + var2 + var7 + ".dat");
         String var8 = null;
         String var9 = null;
         boolean var10 = false;
         if (classDB.field1377.exists()) {
            classAAR var11 = null;

            try {
               var11 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var12 = new classXY((int)var11.method251(-153393736));

               while (-1068916252 * var12.field6953 < var12.field6952.length) {
                  int var13 = var11.method255(
                     var12.field6952, var12.field6953 * -298238560, var12.field6952.length - -1267189305 * var12.field6953, -1397369950
                  );
                  if (var13 == -1) {
                     throw new IOException();
                  }

                  var12.field6953 += -944789764 * var13;
               }

               var12.field6953 = 0;
               int var61 = var12.method13095(-346779531);
               if (var61 < 1 || var61 > 3) {
                  throw new IOException("" + var61);
               }

               int var14 = 0;
               if (var61 > 1) {
                  var14 = var12.method13095(-346779531);
               }

               if (var61 <= 2) {
                  var8 = var12.method13134(225797223);
                  if (1 == var14) {
                     var9 = var12.method13134(-1528834759);
                  }
               } else {
                  var8 = classXY.method13137(var12, 264384070);
                  if (var14 == 1) {
                     var9 = classXY.method13137(var12, 1274257698);
                  }
               }
            } catch (Exception var39) {
               var39.printStackTrace();
            } finally {
               try {
                  if (null != var11) {
                     var11.method242((byte)1);
                  }
               } catch (IOException var31) {
               }
            }

            if (null != var8) {
               File var56 = new File(var8);
               if (!var56.exists()) {
                  var8 = null;
               }
            }

            if (null != var8) {
               File var57 = new File(var8, "test.dat");
               if (!classBB.method788(var57, true, -1098388039)) {
                  var8 = null;
               }
            }
         }

         if (var8 == null && 0 == var5) {
            label452:
            for (int var53 = 0; var53 < classCV.field1303.length; var53++) {
               for (int var58 = 0; var58 < classWQ.field6766.length; var58++) {
                  File var62 = new File(classWQ.field6766[var58] + classCV.field1303[var53] + File.separatorChar + var0 + File.separatorChar);
                  if (var62.exists() && classBB.method788(new File(var62, "test.dat"), true, -1414983483)) {
                     var8 = var62.toString();
                     var10 = true;
                     break label452;
                  }
               }
            }
         }

         if (var8 == null) {
            var8 = ClanChannel.field2952
               + File.separatorChar
               + "jagexcache"
               + var7
               + File.separatorChar
               + var0
               + File.separatorChar
               + var2
               + File.separatorChar;
            var10 = true;
         }

         if (var9 != null) {
            File var54 = new File(var9);
            File var59 = new File(var8);

            try {
               File[] var63 = var54.listFiles();
               File[] var65 = var63;

               for (int var15 = 0; var15 < var65.length; var15++) {
                  File var16 = var65[var15];
                  File var17 = new File(var59, var16.getName());
                  boolean var18 = var16.renameTo(var17);
                  if (!var18) {
                     throw new IOException();
                  }
               }
            } catch (Exception var38) {
               var38.printStackTrace();
            }

            var10 = true;
         }

         if (var10) {
            File var55 = new File(var8);
            Object var60 = null;

            try {
               classAAR var64 = new classAAR(classDB.field1377, "rw", 10000L);
               classXY var66 = new classXY(500);
               classXY.method13029(var66, 3, (byte)-85);
               classXY.method13029(var66, null != var60 ? 1 : 0, (byte)-103);
               classXY.method13064(var66, var55.getPath(), -910745300);
               if (var60 != null) {
                  classXY.method13064(var66, var60.getPath(), -910745300);
               }

               var64.method238(var66.field6952, 0, -1564525175 * var66.field6953, -1186495551);
               var64.method242((byte)1);
            } catch (IOException var32) {
               var32.printStackTrace();
            }
         }

         File var6 = new File(var8);
         classSL.field5973 = var6;
         if (!classSL.field5973.exists()) {
            classSL.field5973.mkdirs();
         }

         File[] var44 = classSL.field5973.listFiles();
         if (var44 != null) {
            File[] var47 = var44;

            for (int var50 = 0; var50 < var47.length; var50++) {
               File var52 = var47[var50];
               if (!classBB.method788(var52, false, 1460538998)) {
                  continue label479;
               }
            }
         }
         break;
      }

      File var41 = classSL.field5973;
      classMT.field4584 = var41;
      if (!classMT.field4584.exists()) {
         throw new RuntimeException("");
      } else {
         classMT.field4578 = true;

         try {
            File var42 = new File(ClanChannel.field2952, "random.dat");
            if (var42.exists()) {
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            } else {
               label403:
               for (int var45 = 0; var45 < classCV.field1303.length; var45++) {
                  for (int var48 = 0; var48 < classWQ.field6766.length; var48++) {
                     File var51 = new File(classWQ.field6766[var48] + classCV.field1303[var45] + File.separatorChar + "random.dat");
                     if (var51.exists()) {
                        field4488 = new classAAC(new classAAR(var51, "rw", 25L), 24, 0);
                        break label403;
                     }
                  }
               }
            }

            if (field4488 == null) {
               RandomAccessFile var46 = new RandomAccessFile(var42, "rw");
               int var49 = var46.read();
               var46.seek(0L);
               var46.write(var49);
               var46.seek(0L);
               var46.close();
               field4488 = new classAAC(new classAAR(var42, "rw", 25L), 24, 0);
            }
         } catch (IOException var37) {
         }

         field4486 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.dat2", 423830089), "rw", 1048576000L), 5200, 0);
         field4487 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx255", -91468902), "rw", 1048576L), -334375416, 0);
         GraphicsObject.field477 = new classAAC[classUP.field6361 * -646722873];

         for (int var43 = 0; var43 < 1070940093 * classUP.field6361; var43++) {
            GraphicsObject.field477[var43] = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx" + var43, -589539822), "rw", 1048576L), 6000, 0);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("au")
   public static byte[] method7592() {
      byte[] var0 = new byte[24];

      try {
         field4488.method131(0L);
         classAAC.method140(field4488, var0, -1230669865);
         int var1 = 0;

         while (var1 < 24 && 0 == var0[var1]) {
            var1++;
         }

         if (var1 >= 24) {
            throw new IOException();
         }
      } catch (Exception var3) {
         for (int var2 = 0; var2 < 24; var2++) {
            var0[var2] = -1;
         }
      }

      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ai")
   public static byte[] method7593() {
      byte[] var0 = new byte[24];

      try {
         field4488.method131(0L);
         classAAC.method140(field4488, var0, -434238545);
         int var1 = 0;

         while (var1 < 24 && 0 == var0[var1]) {
            var1++;
         }

         if (var1 >= 24) {
            throw new IOException();
         }
      } catch (Exception var3) {
         for (int var2 = 0; var2 < 24; var2++) {
            var0[var2] = -1;
         }
      }

      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ar")
   public static byte[] method7594() {
      byte[] var0 = new byte[24];

      try {
         field4488.method131(0L);
         classAAC.method140(field4488, var0, -906904590);
         int var1 = 0;

         while (var1 < 24 && 0 == var0[var1]) {
            var1++;
         }

         if (var1 >= 24) {
            throw new IOException();
         }
      } catch (Exception var3) {
         for (int var2 = 0; var2 < 24; var2++) {
            var0[var2] = -1;
         }
      }

      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("aw")
   public static byte[] method7595() {
      byte[] var0 = new byte[24];

      try {
         field4488.method131(0L);
         classAAC.method140(field4488, var0, -1837703969);
         int var1 = 0;

         while (var1 < 24 && 0 == var0[var1]) {
            var1++;
         }

         if (var1 >= 24) {
            throw new IOException();
         }
      } catch (Exception var3) {
         for (int var2 = 0; var2 < 24; var2++) {
            var0[var2] = -1;
         }
      }

      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ak")
   public static void method7597(classXY var0, int var1) {
      if (field4488 != null) {
         try {
            field4488.method131(0L);
            field4488.method148(var0.field6952, var1, 24, -1927671615);
         } catch (Exception var3) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("av")
   public static void method7598(classXY var0, int var1) {
      if (field4488 != null) {
         try {
            field4488.method131(0L);
            field4488.method148(var0.field6952, var1, 24, 578153911);
         } catch (Exception var3) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Z)Laar;")
   @ObfuscatedName("ap")
   public static classAAR method7591(String var0, String var1, boolean var2) {
      File var3 = new File(classSL.field5973, "preferences" + var0 + ".dat");
      if (var3.exists()) {
         try {
            return new classAAR(var3, "rw", 10000L);
         } catch (IOException var9) {
         }
      }

      String var4 = "";
      if (33 == 2015166692 * MidiRequest.field4635) {
         var4 = "_rc";
      } else if (MidiRequest.field4635 * -1462858247 == 2019241592) {
         var4 = "_wip";
      }

      File var5 = new File(ClanChannel.field2952, "jagex_" + var1 + "_preferences" + var0 + var4 + ".dat");
      if (!var2 && var5.exists()) {
         try {
            return new classAAR(var5, "rw", 10000L);
         } catch (IOException var8) {
         }
      }

      try {
         return new classAAR(var3, "rw", 10000L);
      } catch (IOException var7) {
         throw new RuntimeException();
      }
   }

   classME() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("an")
   public static void method7599(classXY var0, int var1) {
      if (field4488 != null) {
         try {
            field4488.method131(0L);
            field4488.method148(var0.field6952, var1, 24, 841578820);
         } catch (Exception var3) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public static void method7601() {
      try {
         classAAC.method125(field4486, -1599004065);

         for (int var0 = 0; var0 < 75685140 * classUP.field6361; var0++) {
            classAAC.method125(GraphicsObject.field477[var0], 95779239);
         }

         classAAC.method125(field4487, -138619742);
         classAAC.method125(field4488, -95478724);
      } catch (Exception var1) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   public static void method7602() {
      try {
         classAAC.method125(field4486, 173897938);

         for (int var0 = 0; var0 < -646722873 * classUP.field6361; var0++) {
            classAAC.method125(GraphicsObject.field477[var0], -1428403396);
         }

         classAAC.method125(field4487, -1464860951);
         classAAC.method125(field4488, 1015118938);
      } catch (Exception var1) {
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("aw")
   static int method7604(int var0, Script var1, boolean var2, byte var3) {
      try {
         Widget var10000;
         if (var2) {
            if (var3 == 12) {
               throw new IllegalStateException();
            }

            var10000 = classSZ.field6108;
         } else {
            var10000 = TileItem.field1556;
         }

         Widget var4 = var10000;
         if (1500 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.field4279 * 744286069;
            return 1;
         } else if (1501 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1570844829 * var4.field4255;
            return 1;
         } else if (1502 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1115597881 * var4.field4247;
            return 1;
         } else if (1503 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 293773697 * var4.field4257;
            return 1;
         } else if (1504 == var0) {
            if (var3 == 12) {
               throw new IllegalStateException();
            } else {
               int[] var9 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (var4.field4287) {
                  if (var3 == 12) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var9[var10001] = var10002;
               return 1;
            }
         } else if (var0 == 1505) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1502647899 * var4.field4340;
            return 1;
         } else if (var0 == 1506) {
            if (var3 == 12) {
               throw new IllegalStateException();
            } else {
               int var5 = var4.field4340 * -1502647899;
               if (var5 == -1) {
                  if (var3 == 12) {
                     throw new IllegalStateException();
                  }

                  classVV var6 = classWK.field6689;

                  for (WidgetNode var7 = (WidgetNode)var6.field6611.method13423(); null != var7; var7 = (WidgetNode)var6.field6611.method13427()) {
                     if (var3 == 12) {
                        throw new IllegalStateException();
                     }

                     if (var4.field4327 * -1163656551 >> 16 == var7.field5275 * 2023647411) {
                        var5 = (int)var7.field6550;
                        break;
                     }
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var5;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "me.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ac")
   static String method7603(String var0, int var1) {
      try {
         if (var0.startsWith("\"")) {
            if (var1 == 2131153158) {
               throw new IllegalStateException();
            }

            if (var0.length() == 1) {
               if (var1 == 2131153158) {
                  throw new IllegalStateException();
               }

               var0 = "";
            } else if (var0.endsWith("\"") && !var0.endsWith("\\\"")) {
               if (var1 == 2131153158) {
                  throw new IllegalStateException();
               }

               var0 = var0.substring(1, var0.length() - 1);
            }
         }

         return var0.replaceAll("\\\\\"", "\"");
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "me.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIII)Luz;")
   @ObfuscatedName("cx")
   static classUZ method7605(WorldView var0, int var1, int var2, int var3, int var4) {
      try {
         int var5 = var1 * 128;
         int var6 = 128 * var2;
         if (classSG.method10467(var0, var3, var1, var2, 1598583345)) {
            if (var4 >= 702114061) {
               throw new IllegalStateException();
            }

            var3++;
         }

         classUZ var7 = classIK.method6261(var5, var0.field1686[var3][var1][var2], var6, (byte)6);
         classUZ var8 = classIK.method6261(128 + var5, var0.field1686[var3][1 + var1][var2], var6, (byte)28);
         classUZ var9 = classIK.method6261(128 + var5, var0.field1686[var3][var1 + 1][1 + var2], 128 + var6, (byte)67);
         classUZ var10 = classIK.method6261(var5, var0.field1686[var3][var1][var2 + 1], var6 + 128, (byte)52);
         if (var7.field6423 == var8.field6423) {
            if (var4 >= 702114061) {
               throw new IllegalStateException();
            }

            if (var7.field6423 == var9.field6423) {
               if (var4 >= 702114061) {
                  throw new IllegalStateException();
               }

               if (var10.field6423 == var7.field6423) {
                  if (var4 >= 702114061) {
                     throw new IllegalStateException();
                  }

                  var7.method11797(1742134569);
                  var8.method11797(1700571816);
                  var9.method11797(1852589905);
                  var10.method11797(2107937044);
                  return classUZ.field6431;
               }
            }
         }

         classUZ var11 = classWN.method12676(var8, var7, (byte)1);
         classUZ var12 = classWN.method12676(var10, var7, (byte)1);
         classUZ var13 = classIZ.method6384(var11, var12, -1071508276);
         var11.method11797(1923966934);
         var12.method11797(1770747509);
         classUZ var14 = classWN.method12676(var8, var9, (byte)1);
         classUZ var15 = classWN.method12676(var10, var9, (byte)1);
         classUZ var16 = classIZ.method6384(var15, var14, 1684977021);
         var14.method11797(2079647756);
         var15.method11797(1687363155);
         var7.method11797(2120986997);
         var8.method11797(2052194492);
         var9.method11797(1836979318);
         var10.method11797(1905017989);
         classUZ var18 = WorldMapData.method5868(var13, (byte)-15);
         var18.method11822(var16, -664943379);
         var13.method11797(1694843595);
         var16.method11797(1887302824);
         classUZ var20 = classHZ.method6171(16777216);
         float var21 = 1.0F / var18.method11844(-1808401369);
         var20.field6425 = var18.field6425 * var21;
         var20.field6423 = var21 * var18.field6423;
         var20.field6424 = var21 * var18.field6424;
         var18.method11797(1932680200);
         return var20;
      } catch (RuntimeException var22) {
         throw classEG.method3899(var22, "me.cx(" + ')');
      }
   }
}
