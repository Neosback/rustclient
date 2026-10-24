import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rk")
public class classRK {
   @ObfuscatedName("ae")
   static final int field5680 = "text_pronoun(".indexOf(40) * -1785437779;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final String field5681 = "text_pronoun(";
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final String field5682 = "pronoun";

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;I)V")
   @ObfuscatedName("av")
   static void method10051(String var0, StringBuilder var1, classQI var2, int var3) {
      String[] var4 = classKD.method6502(var0, (byte)-96);
      int var5 = -1;
      if (var4[1].equals("pronoun")) {
         var5 = var3;
      } else if (var4[1].startsWith("int")) {
         var5 = var2.method9611(var4[1], 1311794176);
      } else {
         Boolean var6 = var2.method9625(var4[1], -1587208222);
         if (null != var6) {
            var5 = var6 ? 1 : 0;
         }
      }

      String var7;
      if (var5 >= 0 && var5 < var4.length - 2) {
         var7 = var4[var5 + 2];
      } else {
         var7 = var4[var4.length - 1];
      }

      var1.append(classLA.method6700(var7, var3, new StringBuilder(var7.length()), var2, -142891623));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;)V")
   @ObfuscatedName("ar")
   static void method10046(String var0, StringBuilder var1, classQI var2) {
      String[] var3 = var0.split("=");
      if (1 == var3.length) {
         var3 = var0.split(":");
         long var4 = var2.method9616(var3[0], (byte)-1);
         String var6 = Long.toString(var4);
         var1.append(var6);
      } else {
         var2.method9599(var3[0], var3[1], 2006027330);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("al")
   public static String method10038(String var0, int var1, StringBuilder var2, classQI var3) {
      if (null == var0) {
         return null;
      } else if (var0.isEmpty()) {
         return var0;
      } else {
         String var4;
         try {
            var4 = classLA.method6700(var0, var1, var2, var3, 608218769);
         } finally {
            var2.setLength(0);
            var3.method9628(1452371636);
         }

         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)Ljava/lang/String;")
   @ObfuscatedName("bp")
   static String method10059(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while (var3 < var0.length()) {
         char var4 = var0.charAt(var3);
         if ('<' == var4) {
            int var5 = var0.indexOf(62, var3 + 1);
            if (-1 != var5) {
               var2.append(var0, var3, 1 + var5);
               var3 = 1 + var5;
               continue;
            }
         }

         if (var4 == '@') {
            int var6 = var0.indexOf(64, var3 + 1);
            if (var6 != -1) {
               var2.append(var0, var3, 1 + var6);
               var3 = 1 + var6;
               continue;
            }
         }

         var2.append(var1 ? Character.toLowerCase(var4) : Character.toUpperCase(var4));
         var3++;
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("ay")
   static String method10040(String var0, int var1, StringBuilder var2, classQI var3) {
      while (true) {
         int var5 = classXQ.method12931(var0, 2063673619);
         if (var5 == -1) {
            if (var2.length() == 0) {
               return var0;
            }

            if (!var0.isEmpty()) {
               var2.append(var0);
            }
            break;
         }

         String var6 = var0.substring(0, var5);
         var0 = var0.substring(1 + var5);
         if (!var6.isEmpty()) {
            var2.append(var6);
         }

         int var7;
         if (var0.startsWith("cond")) {
            int var8 = var0.indexOf(44);
            var8 = var0.indexOf(44, var8 + 1);
            if (var8 == -1) {
               var7 = var8;
            } else {
               int var10 = 1 + var8;
               int var11 = var10;
               int var12 = 1;

               int var9;
               while (true) {
                  if (var12 <= 0) {
                     var9 = var11 - 1;
                     break;
                  }

                  int var14 = var0.indexOf(60, var11);

                  while (-1 != var14 && var14 > 0 && var0.charAt(var14 - 1) == '\\') {
                     var14 = var0.indexOf(60, var14 + 1);
                  }

                  int var16 = var0.indexOf(62, var11);

                  while (var16 != -1 && var16 > 0 && var0.charAt(var16 - 1) == '\\') {
                     var16 = var0.indexOf(62, 1 + var16);
                  }

                  if (-1 == var16) {
                     var9 = -1;
                     break;
                  }

                  if (var14 > -1 && var14 < var16) {
                     var11 = 1 + var14;
                     var12++;
                  } else {
                     var11 = var16 + 1;
                     var12--;
                  }
               }

               var7 = var9;
            }
         } else {
            int var23 = 0;
            int var25 = 1;

            int var21;
            while (true) {
               if (var25 <= 0) {
                  var21 = var23 - 1;
                  break;
               }

               int var28 = var0.indexOf(60, var23);

               while (-1 != var28 && var28 > 0 && var0.charAt(var28 - 1) == '\\') {
                  var28 = var0.indexOf(60, 1 + var28);
               }

               int var37 = var0.indexOf(62, var23);

               while (-1 != var37 && var37 > 0 && var0.charAt(var37 - 1) == '\\') {
                  var37 = var0.indexOf(62, 1 + var37);
               }

               if (-1 == var37) {
                  var21 = -1;
                  break;
               }

               if (var28 > -1 && var28 < var37) {
                  var23 = 1 + var28;
                  var25++;
               } else {
                  var23 = var37 + 1;
                  var25--;
               }
            }

            var7 = var21;
         }

         if (var7 == -1) {
            break;
         }

         String var22 = var0.substring(0, var7);
         var0 = var0.substring(var7 + 1);
         if (AbstractFont.method9(var22)) {
            var2.append("<").append(var22).append(">");
         } else {
            classRX var24;
            if (var22.startsWith("int")) {
               var24 = classRX.field5869;
            } else if (var22.startsWith("long")) {
               var24 = classRX.field5873;
            } else if (var22.startsWith("str")) {
               var24 = classRX.field5870;
            } else if (var22.startsWith("bool")) {
               var24 = classRX.field5872;
            } else if (var22.startsWith("switch")) {
               var24 = classRX.field5871;
            } else if (var22.startsWith("cond")) {
               var24 = classRX.field5868;
            } else {
               var24 = classRX.field5867;
            }

            if (classRX.field5867 != var24) {
               switch (var24.field5866 * 2061130823) {
                  case 0:
                     String[] var27 = var22.split("=");
                     if (1 == var27.length) {
                        Boolean var31 = var3.method9625(var22, 14157852);
                        if (null == var31) {
                           var2.append(-1);
                        } else {
                           var2.append(var31 ? 1 : 0);
                        }
                     } else {
                        var3.method9602(var27[0], var27[1], (short)7132);
                     }
                  case 1:
                  default:
                     break;
                  case 2:
                     classFC.method4612(var22, var2, var3, var1, 2073705266);
                     break;
                  case 3:
                     classML.method7639(var22, var2, var3, var1, -954748775);
                     break;
                  case 4:
                     NPC.method3508(var22, var2, var3, -1761067385);
                     break;
                  case 5:
                     String[] var26 = classKD.method6502(var22, (byte)-49);
                     if (4 != var26.length) {
                        break;
                     }

                     Boolean var30 = null;
                     var26[1] = var26[1].trim();
                     if (var26[1].startsWith("bool")) {
                        String[] var32 = var26[1].split("[<>=!]+");
                        if (1 == var32.length) {
                           var30 = var3.method9625(var26[1], -2145350426);
                        } else {
                           Boolean var39 = var3.method9625(var32[0].trim(), -1020389401);
                           Boolean var41 = var3.method9625(var32[1].trim(), -1908112425);
                           String var44 = var26[1].substring(var32[0].length());
                           var44 = var44.substring(0, var44.length() - var32[1].length());
                           if (var44.equals("=")) {
                              var30 = var39 == var41;
                           } else if (var44.equals("!")) {
                              var30 = var39 != var41;
                           }
                        }
                     } else if (var26[1].startsWith("!")) {
                        Boolean var33 = var3.method9625(var26[1].substring(1), -910961122);
                        if (var33 != null) {
                           var30 = !var33;
                        }
                     } else if (var26[1].startsWith("str")) {
                        String var34 = var3.method9624(var26[1], 2145269203);
                        var30 = null != var34 && !var34.isEmpty();
                     } else {
                        String[] var35 = var26[1].split("[<>=!]+");
                        int var40 = var3.method9611(var35[0].trim(), 957143906);
                        String var46 = var35[1].trim();
                        int var42;
                        if (var46.startsWith("int")) {
                           var42 = var3.method9611(var46, 1760484538);
                        } else {
                           try {
                              var42 = classJF.method6402(var46, -1378007593);
                           } catch (Exception var18) {
                              var42 = -1;
                           }
                        }

                        String var17 = var26[1].substring(var35[0].length());
                        var17 = var17.substring(0, var17.length() - var35[1].length());
                        if (var17.equals("=")) {
                           var30 = var42 == var40;
                        } else if (var17.equals("!")) {
                           var30 = var40 != var42;
                        } else if (var17.equals("<")) {
                           var30 = var40 < var42;
                        } else if (var17.equals("<=")) {
                           var30 = var40 <= var42;
                        } else if (var17.equals(">")) {
                           var30 = var40 > var42;
                        } else if (var17.equals(">=")) {
                           var30 = var40 >= var42;
                        }
                     }

                     if (var30 == null) {
                        var30 = false;
                     }

                     String var36 = var30 ? var26[2] : var26[3];
                     var2.append(classLA.method6700(var36, var1, new StringBuilder(var36.length()), var3, -1129505875));
                     break;
                  case 6:
                     classKS.method6599(var22, var2, var3, (byte)96);
               }
            } else if (!var22.startsWith("text_pronoun(")) {
               var2.append("<").append(var22).append(">");
            } else {
               String var29;
               if (var1 >= 0 && var1 <= 2) {
                  int var38 = var22.indexOf(40);
                  if (field5680 * -610948571 != var38) {
                     var29 = "";
                  } else {
                     int var15 = var22.lastIndexOf(41);
                     if (var15 != var22.length() - 1) {
                        var29 = "";
                     } else {
                        String var13 = var22.substring(var38 + 1, var15);
                        String[] var43 = classKD.method6502(var13, (byte)-78);
                        if (var43.length != 3) {
                        }

                        var29 = var43[var1];
                     }
                  }
               } else {
                  var29 = "";
               }

               var2.append(var29);
            }
         }
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("aq")
   static String method10041(String var0, int var1, StringBuilder var2, classQI var3) {
      while (true) {
         int var5 = classXQ.method12931(var0, 2072825575);
         if (var5 == -1) {
            if (var2.length() == 0) {
               return var0;
            }

            if (!var0.isEmpty()) {
               var2.append(var0);
            }
            break;
         }

         String var6 = var0.substring(0, var5);
         var0 = var0.substring(1 + var5);
         if (!var6.isEmpty()) {
            var2.append(var6);
         }

         int var7;
         if (var0.startsWith("cond")) {
            int var8 = var0.indexOf(44);
            var8 = var0.indexOf(44, var8 + 1);
            if (var8 == -1) {
               var7 = var8;
            } else {
               int var10 = 1 + var8;
               int var11 = var10;
               int var12 = 1;

               int var9;
               while (true) {
                  if (var12 <= 0) {
                     var9 = var11 - 1;
                     break;
                  }

                  int var14 = var0.indexOf(60, var11);

                  while (-1 != var14 && var14 > 0 && var0.charAt(var14 - 1) == '\\') {
                     var14 = var0.indexOf(60, var14 + 1);
                  }

                  int var16 = var0.indexOf(62, var11);

                  while (var16 != -1 && var16 > 0 && var0.charAt(var16 - 1) == '\\') {
                     var16 = var0.indexOf(62, 1 + var16);
                  }

                  if (-1 == var16) {
                     var9 = -1;
                     break;
                  }

                  if (var14 > -1 && var14 < var16) {
                     var11 = 1 + var14;
                     var12++;
                  } else {
                     var11 = var16 + 1;
                     var12--;
                  }
               }

               var7 = var9;
            }
         } else {
            int var23 = 0;
            int var25 = 1;

            int var21;
            while (true) {
               if (var25 <= 0) {
                  var21 = var23 - 1;
                  break;
               }

               int var28 = var0.indexOf(60, var23);

               while (-1 != var28 && var28 > 0 && var0.charAt(var28 - 1) == '\\') {
                  var28 = var0.indexOf(60, 1 + var28);
               }

               int var37 = var0.indexOf(62, var23);

               while (-1 != var37 && var37 > 0 && var0.charAt(var37 - 1) == '\\') {
                  var37 = var0.indexOf(62, 1 + var37);
               }

               if (-1 == var37) {
                  var21 = -1;
                  break;
               }

               if (var28 > -1 && var28 < var37) {
                  var23 = 1 + var28;
                  var25++;
               } else {
                  var23 = var37 + 1;
                  var25--;
               }
            }

            var7 = var21;
         }

         if (var7 == -1) {
            break;
         }

         String var22 = var0.substring(0, var7);
         var0 = var0.substring(var7 + 1);
         if (AbstractFont.method9(var22)) {
            var2.append("<").append(var22).append(">");
         } else {
            classRX var24;
            if (var22.startsWith("int")) {
               var24 = classRX.field5869;
            } else if (var22.startsWith("long")) {
               var24 = classRX.field5873;
            } else if (var22.startsWith("str")) {
               var24 = classRX.field5870;
            } else if (var22.startsWith("bool")) {
               var24 = classRX.field5872;
            } else if (var22.startsWith("switch")) {
               var24 = classRX.field5871;
            } else if (var22.startsWith("cond")) {
               var24 = classRX.field5868;
            } else {
               var24 = classRX.field5867;
            }

            if (classRX.field5867 != var24) {
               switch (var24.field5866 * 2061130823) {
                  case 0:
                     String[] var27 = var22.split("=");
                     if (1 == var27.length) {
                        Boolean var31 = var3.method9625(var22, -2081878070);
                        if (null == var31) {
                           var2.append(-1);
                        } else {
                           var2.append(var31 ? 1 : 0);
                        }
                     } else {
                        var3.method9602(var27[0], var27[1], (short)-11125);
                     }
                  case 1:
                  default:
                     break;
                  case 2:
                     classFC.method4612(var22, var2, var3, var1, 2073705266);
                     break;
                  case 3:
                     classML.method7639(var22, var2, var3, var1, 1890700615);
                     break;
                  case 4:
                     NPC.method3508(var22, var2, var3, -576677387);
                     break;
                  case 5:
                     String[] var26 = classKD.method6502(var22, (byte)-25);
                     if (4 != var26.length) {
                        break;
                     }

                     Boolean var30 = null;
                     var26[1] = var26[1].trim();
                     if (var26[1].startsWith("bool")) {
                        String[] var32 = var26[1].split("[<>=!]+");
                        if (1 == var32.length) {
                           var30 = var3.method9625(var26[1], -433390117);
                        } else {
                           Boolean var39 = var3.method9625(var32[0].trim(), 427578153);
                           Boolean var41 = var3.method9625(var32[1].trim(), -2018142193);
                           String var44 = var26[1].substring(var32[0].length());
                           var44 = var44.substring(0, var44.length() - var32[1].length());
                           if (var44.equals("=")) {
                              var30 = var39 == var41;
                           } else if (var44.equals("!")) {
                              var30 = var39 != var41;
                           }
                        }
                     } else if (var26[1].startsWith("!")) {
                        Boolean var33 = var3.method9625(var26[1].substring(1), -434629231);
                        if (var33 != null) {
                           var30 = !var33;
                        }
                     } else if (var26[1].startsWith("str")) {
                        String var34 = var3.method9624(var26[1], 2145269203);
                        var30 = null != var34 && !var34.isEmpty();
                     } else {
                        String[] var35 = var26[1].split("[<>=!]+");
                        int var40 = var3.method9611(var35[0].trim(), 1513043218);
                        String var46 = var35[1].trim();
                        int var42;
                        if (var46.startsWith("int")) {
                           var42 = var3.method9611(var46, 1604250799);
                        } else {
                           try {
                              var42 = classJF.method6402(var46, -251012098);
                           } catch (Exception var18) {
                              var42 = -1;
                           }
                        }

                        String var17 = var26[1].substring(var35[0].length());
                        var17 = var17.substring(0, var17.length() - var35[1].length());
                        if (var17.equals("=")) {
                           var30 = var42 == var40;
                        } else if (var17.equals("!")) {
                           var30 = var40 != var42;
                        } else if (var17.equals("<")) {
                           var30 = var40 < var42;
                        } else if (var17.equals("<=")) {
                           var30 = var40 <= var42;
                        } else if (var17.equals(">")) {
                           var30 = var40 > var42;
                        } else if (var17.equals(">=")) {
                           var30 = var40 >= var42;
                        }
                     }

                     if (var30 == null) {
                        var30 = false;
                     }

                     String var36 = var30 ? var26[2] : var26[3];
                     var2.append(classLA.method6700(var36, var1, new StringBuilder(var36.length()), var3, -435803661));
                     break;
                  case 6:
                     classKS.method6599(var22, var2, var3, (byte)119);
               }
            } else if (!var22.startsWith("text_pronoun(")) {
               var2.append("<").append(var22).append(">");
            } else {
               String var29;
               if (var1 >= 0 && var1 <= 2) {
                  int var38 = var22.indexOf(40);
                  if (field5680 * -610948571 != var38) {
                     var29 = "";
                  } else {
                     int var15 = var22.lastIndexOf(41);
                     if (var15 != var22.length() - 1) {
                        var29 = "";
                     } else {
                        String var13 = var22.substring(var38 + 1, var15);
                        String[] var43 = classKD.method6502(var13, (byte)-44);
                        if (var43.length != 3) {
                        }

                        var29 = var43[var1];
                     }
                  }
               } else {
                  var29 = "";
               }

               var2.append(var29);
            }
         }
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("ad")
   static String method10042(String var0, int var1, StringBuilder var2, classQI var3) {
      while (true) {
         int var5 = classXQ.method12931(var0, 2059896527);
         if (var5 == -1) {
            if (var2.length() == 0) {
               return var0;
            }

            if (!var0.isEmpty()) {
               var2.append(var0);
            }
            break;
         }

         String var6 = var0.substring(0, var5);
         var0 = var0.substring(1 + var5);
         if (!var6.isEmpty()) {
            var2.append(var6);
         }

         int var7;
         if (var0.startsWith("cond")) {
            int var8 = var0.indexOf(44);
            var8 = var0.indexOf(44, var8 + 1);
            if (var8 == -1) {
               var7 = var8;
            } else {
               int var10 = 1 + var8;
               int var11 = var10;
               int var12 = 1;

               int var9;
               while (true) {
                  if (var12 <= 0) {
                     var9 = var11 - 1;
                     break;
                  }

                  int var14 = var0.indexOf(60, var11);

                  while (-1 != var14 && var14 > 0 && var0.charAt(var14 - 1) == '\\') {
                     var14 = var0.indexOf(60, var14 + 1);
                  }

                  int var16 = var0.indexOf(62, var11);

                  while (var16 != -1 && var16 > 0 && var0.charAt(var16 - 1) == '\\') {
                     var16 = var0.indexOf(62, 1 + var16);
                  }

                  if (-1 == var16) {
                     var9 = -1;
                     break;
                  }

                  if (var14 > -1 && var14 < var16) {
                     var11 = 1 + var14;
                     var12++;
                  } else {
                     var11 = var16 + 1;
                     var12--;
                  }
               }

               var7 = var9;
            }
         } else {
            int var23 = 0;
            int var25 = 1;

            int var21;
            while (true) {
               if (var25 <= 0) {
                  var21 = var23 - 1;
                  break;
               }

               int var28 = var0.indexOf(60, var23);

               while (-1 != var28 && var28 > 0 && var0.charAt(var28 - 1) == '\\') {
                  var28 = var0.indexOf(60, 1 + var28);
               }

               int var37 = var0.indexOf(62, var23);

               while (-1 != var37 && var37 > 0 && var0.charAt(var37 - 1) == '\\') {
                  var37 = var0.indexOf(62, 1 + var37);
               }

               if (-1 == var37) {
                  var21 = -1;
                  break;
               }

               if (var28 > -1 && var28 < var37) {
                  var23 = 1 + var28;
                  var25++;
               } else {
                  var23 = var37 + 1;
                  var25--;
               }
            }

            var7 = var21;
         }

         if (var7 == -1) {
            break;
         }

         String var22 = var0.substring(0, var7);
         var0 = var0.substring(var7 + 1);
         if (AbstractFont.method9(var22)) {
            var2.append("<").append(var22).append(">");
         } else {
            classRX var24;
            if (var22.startsWith("int")) {
               var24 = classRX.field5869;
            } else if (var22.startsWith("long")) {
               var24 = classRX.field5873;
            } else if (var22.startsWith("str")) {
               var24 = classRX.field5870;
            } else if (var22.startsWith("bool")) {
               var24 = classRX.field5872;
            } else if (var22.startsWith("switch")) {
               var24 = classRX.field5871;
            } else if (var22.startsWith("cond")) {
               var24 = classRX.field5868;
            } else {
               var24 = classRX.field5867;
            }

            if (classRX.field5867 != var24) {
               switch (var24.field5866 * 2061130823) {
                  case 0:
                     String[] var27 = var22.split("=");
                     if (1 == var27.length) {
                        Boolean var31 = var3.method9625(var22, -288484494);
                        if (null == var31) {
                           var2.append(-1);
                        } else {
                           var2.append(var31 ? 1 : 0);
                        }
                     } else {
                        var3.method9602(var27[0], var27[1], (short)7056);
                     }
                  case 1:
                  default:
                     break;
                  case 2:
                     classFC.method4612(var22, var2, var3, var1, 2073705266);
                     break;
                  case 3:
                     classML.method7639(var22, var2, var3, var1, -1041280499);
                     break;
                  case 4:
                     NPC.method3508(var22, var2, var3, -1396788117);
                     break;
                  case 5:
                     String[] var26 = classKD.method6502(var22, (byte)-66);
                     if (4 != var26.length) {
                        break;
                     }

                     Boolean var30 = null;
                     var26[1] = var26[1].trim();
                     if (var26[1].startsWith("bool")) {
                        String[] var32 = var26[1].split("[<>=!]+");
                        if (1 == var32.length) {
                           var30 = var3.method9625(var26[1], -101859330);
                        } else {
                           Boolean var39 = var3.method9625(var32[0].trim(), 179814960);
                           Boolean var41 = var3.method9625(var32[1].trim(), -597422591);
                           String var44 = var26[1].substring(var32[0].length());
                           var44 = var44.substring(0, var44.length() - var32[1].length());
                           if (var44.equals("=")) {
                              var30 = var39 == var41;
                           } else if (var44.equals("!")) {
                              var30 = var39 != var41;
                           }
                        }
                     } else if (var26[1].startsWith("!")) {
                        Boolean var33 = var3.method9625(var26[1].substring(1), -1547649220);
                        if (var33 != null) {
                           var30 = !var33;
                        }
                     } else if (var26[1].startsWith("str")) {
                        String var34 = var3.method9624(var26[1], 2145269203);
                        var30 = null != var34 && !var34.isEmpty();
                     } else {
                        String[] var35 = var26[1].split("[<>=!]+");
                        int var40 = var3.method9611(var35[0].trim(), 1108578484);
                        String var46 = var35[1].trim();
                        int var42;
                        if (var46.startsWith("int")) {
                           var42 = var3.method9611(var46, 1594149748);
                        } else {
                           try {
                              var42 = classJF.method6402(var46, 673756811);
                           } catch (Exception var18) {
                              var42 = -1;
                           }
                        }

                        String var17 = var26[1].substring(var35[0].length());
                        var17 = var17.substring(0, var17.length() - var35[1].length());
                        if (var17.equals("=")) {
                           var30 = var42 == var40;
                        } else if (var17.equals("!")) {
                           var30 = var40 != var42;
                        } else if (var17.equals("<")) {
                           var30 = var40 < var42;
                        } else if (var17.equals("<=")) {
                           var30 = var40 <= var42;
                        } else if (var17.equals(">")) {
                           var30 = var40 > var42;
                        } else if (var17.equals(">=")) {
                           var30 = var40 >= var42;
                        }
                     }

                     if (var30 == null) {
                        var30 = false;
                     }

                     String var36 = var30 ? var26[2] : var26[3];
                     var2.append(classLA.method6700(var36, var1, new StringBuilder(var36.length()), var3, -295006337));
                     break;
                  case 6:
                     classKS.method6599(var22, var2, var3, (byte)3);
               }
            } else if (!var22.startsWith("text_pronoun(")) {
               var2.append("<").append(var22).append(">");
            } else {
               String var29;
               if (var1 >= 0 && var1 <= 2) {
                  int var38 = var22.indexOf(40);
                  if (field5680 * -610948571 != var38) {
                     var29 = "";
                  } else {
                     int var15 = var22.lastIndexOf(41);
                     if (var15 != var22.length() - 1) {
                        var29 = "";
                     } else {
                        String var13 = var22.substring(var38 + 1, var15);
                        String[] var43 = classKD.method6502(var13, (byte)-112);
                        if (var43.length != 3) {
                        }

                        var29 = var43[var1];
                     }
                  }
               } else {
                  var29 = "";
               }

               var2.append(var29);
            }
         }
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("aj")
   static String method10043(String var0, int var1, StringBuilder var2, classQI var3) {
      while (true) {
         int var5 = classXQ.method12931(var0, 2050179255);
         if (var5 == -1) {
            if (var2.length() == 0) {
               return var0;
            }

            if (!var0.isEmpty()) {
               var2.append(var0);
            }
            break;
         }

         String var6 = var0.substring(0, var5);
         var0 = var0.substring(1 + var5);
         if (!var6.isEmpty()) {
            var2.append(var6);
         }

         int var7;
         if (var0.startsWith("cond")) {
            int var8 = var0.indexOf(44);
            var8 = var0.indexOf(44, var8 + 1);
            if (var8 == -1) {
               var7 = var8;
            } else {
               int var10 = 1 + var8;
               int var11 = var10;
               int var12 = 1;

               int var9;
               while (true) {
                  if (var12 <= 0) {
                     var9 = var11 - 1;
                     break;
                  }

                  int var14 = var0.indexOf(60, var11);

                  while (-1 != var14 && var14 > 0 && var0.charAt(var14 - 1) == 563632120) {
                     var14 = var0.indexOf(-1704255558, var14 + 1);
                  }

                  int var16 = var0.indexOf(1601271636, var11);

                  while (var16 != -1 && var16 > 0 && var0.charAt(var16 - 1) == 15441006) {
                     var16 = var0.indexOf(-1766917441, 1 + var16);
                  }

                  if (-1 == var16) {
                     var9 = -1;
                     break;
                  }

                  if (var14 > -1 && var14 < var16) {
                     var11 = 1 + var14;
                     var12++;
                  } else {
                     var11 = var16 + 1;
                     var12--;
                  }
               }

               var7 = var9;
            }
         } else {
            int var23 = 0;
            int var25 = 1;

            int var21;
            while (true) {
               if (var25 <= 0) {
                  var21 = var23 - 1;
                  break;
               }

               int var28 = var0.indexOf(167337295, var23);

               while (-1 != var28 && var28 > 0 && var0.charAt(var28 - 1) == -1145830296) {
                  var28 = var0.indexOf(-520548499, 1 + var28);
               }

               int var37 = var0.indexOf(1705890630, var23);

               while (-1 != var37 && var37 > 0 && var0.charAt(var37 - 1) == 683291992) {
                  var37 = var0.indexOf(347086447, 1 + var37);
               }

               if (-1 == var37) {
                  var21 = -1;
                  break;
               }

               if (var28 > -1 && var28 < var37) {
                  var23 = 1 + var28;
                  var25++;
               } else {
                  var23 = var37 + 1;
                  var25--;
               }
            }

            var7 = var21;
         }

         if (var7 == -1) {
            break;
         }

         String var22 = var0.substring(0, var7);
         var0 = var0.substring(var7 + 1);
         if (AbstractFont.method9(var22)) {
            var2.append("<").append(var22).append(">");
         } else {
            classRX var24;
            if (var22.startsWith("int")) {
               var24 = classRX.field5869;
            } else if (var22.startsWith("long")) {
               var24 = classRX.field5873;
            } else if (var22.startsWith("str")) {
               var24 = classRX.field5870;
            } else if (var22.startsWith("bool")) {
               var24 = classRX.field5872;
            } else if (var22.startsWith("switch")) {
               var24 = classRX.field5871;
            } else if (var22.startsWith("cond")) {
               var24 = classRX.field5868;
            } else {
               var24 = classRX.field5867;
            }

            if (classRX.field5867 != var24) {
               switch (var24.field5866 * 2061130823) {
                  case 0:
                     String[] var27 = var22.split("=");
                     if (1 == var27.length) {
                        Boolean var31 = var3.method9625(var22, -1635023525);
                        if (null == var31) {
                           var2.append(-1);
                        } else {
                           var2.append(var31 ? 1 : 0);
                        }
                     } else {
                        var3.method9602(var27[0], var27[1], (short)-16981);
                     }
                  case 1:
                  default:
                     break;
                  case 2:
                     classFC.method4612(var22, var2, var3, var1, 2073705266);
                     break;
                  case 3:
                     classML.method7639(var22, var2, var3, var1, 1832563724);
                     break;
                  case 4:
                     NPC.method3508(var22, var2, var3, -1720562356);
                     break;
                  case 5:
                     String[] var26 = classKD.method6502(var22, (byte)-59);
                     if (4 != var26.length) {
                        break;
                     }

                     Boolean var30 = null;
                     var26[1] = var26[1].trim();
                     if (var26[1].startsWith("bool")) {
                        String[] var32 = var26[1].split("[<>=!]+");
                        if (1 == var32.length) {
                           var30 = var3.method9625(var26[1], -706309475);
                        } else {
                           Boolean var39 = var3.method9625(var32[0].trim(), -1949530102);
                           Boolean var41 = var3.method9625(var32[1].trim(), -1362249317);
                           String var44 = var26[1].substring(var32[0].length());
                           var44 = var44.substring(0, var44.length() - var32[1].length());
                           if (var44.equals("=")) {
                              var30 = var39 == var41;
                           } else if (var44.equals("!")) {
                              var30 = var39 != var41;
                           }
                        }
                     } else if (var26[1].startsWith("!")) {
                        Boolean var33 = var3.method9625(var26[1].substring(1), 292420019);
                        if (var33 != null) {
                           var30 = !var33;
                        }
                     } else if (var26[1].startsWith("str")) {
                        String var34 = var3.method9624(var26[1], 2145269203);
                        var30 = null != var34 && !var34.isEmpty();
                     } else {
                        String[] var35 = var26[1].split("[<>=!]+");
                        int var40 = var3.method9611(var35[0].trim(), 1070542082);
                        String var46 = var35[1].trim();
                        int var42;
                        if (var46.startsWith("int")) {
                           var42 = var3.method9611(var46, 1899357064);
                        } else {
                           try {
                              var42 = classJF.method6402(var46, 913619070);
                           } catch (Exception var18) {
                              var42 = -1;
                           }
                        }

                        String var17 = var26[1].substring(var35[0].length());
                        var17 = var17.substring(0, var17.length() - var35[1].length());
                        if (var17.equals("=")) {
                           var30 = var42 == var40;
                        } else if (var17.equals("!")) {
                           var30 = var40 != var42;
                        } else if (var17.equals("<")) {
                           var30 = var40 < var42;
                        } else if (var17.equals("<=")) {
                           var30 = var40 <= var42;
                        } else if (var17.equals(">")) {
                           var30 = var40 > var42;
                        } else if (var17.equals(">=")) {
                           var30 = var40 >= var42;
                        }
                     }

                     if (var30 == null) {
                        var30 = false;
                     }

                     String var36 = var30 ? var26[2] : var26[3];
                     var2.append(classLA.method6700(var36, var1, new StringBuilder(var36.length()), var3, 69171744));
                     break;
                  case 6:
                     classKS.method6599(var22, var2, var3, (byte)58);
               }
            } else if (!var22.startsWith("text_pronoun(")) {
               var2.append("<").append(var22).append(">");
            } else {
               String var29;
               if (var1 >= 0 && var1 <= 2) {
                  int var38 = var22.indexOf(259723577);
                  if (field5680 * -1266666403 != var38) {
                     var29 = "";
                  } else {
                     int var15 = var22.lastIndexOf(41);
                     if (var15 != var22.length() - 1) {
                        var29 = "";
                     } else {
                        String var13 = var22.substring(var38 + 1, var15);
                        String[] var43 = classKD.method6502(var13, (byte)-79);
                        if (var43.length != 3) {
                        }

                        var29 = var43[var1];
                     }
                  }
               } else {
                  var29 = "";
               }

               var2.append(var29);
            }
         }
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;I)V")
   @ObfuscatedName("au")
   static void method10044(String var0, StringBuilder var1, classQI var2, int var3) {
      String[] var4 = var0.split("=");
      if (var4.length == 1) {
         var4 = var0.split(":");
         int var5 = var2.method9611(var4[0], 1851763160);
         String var6 = Integer.toString(var5);
         if (var4.length > 1) {
            String var7 = var4[1].toLowerCase();
            classRZ var8 = classAI.method363(classXO.field6890, 1883191353);
            if (var7.equals("spacer")) {
               var6 = classXW.method13010(var5, 0, true, true, var8, (byte)2);
            } else if (var7.equals("percent")) {
               var6 = classXW.method13010(var5, 0, false, true, var8, (byte)-98);
               var6 = var6 + "%";
            } else if (var7.equals("date")) {
               var6 = classGQ.method5636(var5, (byte)19);
            } else if (var7.startsWith("spacer_")) {
               classRA var32 = Projectile.method1213(var7.substring("spacer_".length()), -1330660453);
               var6 = classXW.method13010(var5, 618506607 * var32.field5629, true, var32.field5628, var8, (byte)-70);
            } else if (var7.startsWith("decimal_")) {
               classRA var31 = Projectile.method1213(var7.substring("decimal_".length()), 907865414);
               var6 = classXW.method13010(var5, 618506607 * var31.field5629, false, var31.field5628, var8, (byte)-4);
            } else if (var7.startsWith("scale_")) {
               String var33 = var7.substring("scale_".length());
               boolean var34 = true;
               if (var33.endsWith("_z")) {
                  var34 = false;
                  var33 = var33.substring(0, var33.length() - "_z".length());
               }

               String[] var35 = var33.split("_");
               int var37 = Integer.parseInt(var35[0]);
               int var39 = var35.length > 1 ? Integer.parseInt(var35[1]) : 0;
               classRL var30 = new classRL(var37, var39, var34);
               var6 = classIG.method6249(var5, var30.field5683 * -527595341, 232299444 * var30.field5685, var30.field5684, var8, 2036758291);
            } else if (var7.startsWith("percent_")) {
               classRA var29 = Projectile.method1213(var7.substring("percent_".length()), -110816709);
               var6 = classYY.method13818(var5, var29.field5629 * -766218934, var29.field5628, var8, (byte)16);
            } else if (var7.startsWith("zeropad_")) {
               int var28 = Integer.parseInt(var7.substring("zeropad_".length()));
               if (var5 >= 0) {
                  var6 = String.format("%0" + var28 + "d", var5);
               }
            } else if (var7.startsWith("time_")) {
               String var10 = var7.substring("time_".length());
               String[] var12 = var10.split("_");
               classRM var13 = new classRM();
               var13.field5806 = var12[0];
               var13.field5805 = var12[1];
               classRM var11;
               if (var13.field5805.equals("clk")) {
                  var11 = var13;
               } else {
                  String var14 = var12[2];
                  if (var14.equals("dt")) {
                     var13.field5804 = "d";
                     var13.field5808 = true;
                  } else if (var14.equals("ht")) {
                     var13.field5804 = "h";
                     var13.field5808 = true;
                  } else {
                     var13.field5804 = var14;
                  }

                  var13.field5809 = var12.length > 3 && var12[3].equals("ms");
                  var11 = var13;
               }

               classQO var36 = classMT.method7735((byte)67);
               String var16 = var11.field5806;
               long var38;
               if ("c".equals(var16)) {
                  var38 = 600L * var5;
               } else if ("s".equals(var16)) {
                  var38 = 1000L * var5;
               } else if ("m".equals(var16)) {
                  var38 = 60000L * var5;
               } else {
                  var38 = var5;
               }

               String var9;
               label160: {
                  if (var11.field5805.equals("w")) {
                     if ((var11.field5806.equals("s") || var11.field5806.equals("c")) && var38 < 2000L) {
                        var9 = "a moment";
                        break label160;
                     }

                     if (var11.field5806.equals("m") && var38 <= 60000L) {
                        var9 = "a minute";
                        break label160;
                     }
                  }

                  if (var11.field5805.equals("clk")) {
                     long var20 = var38 / 60000L % 1440L;
                     if (var20 < 0L) {
                        var20 += 1440L;
                     }

                     long var22 = var20 / 60L;
                     long var24 = var20 % 60L;
                     String var19 = classGK.method5516(var22) + ":" + classGK.method5516(var24);
                     var9 = var19;
                  } else {
                     classRY var40 = classDE.method3000(var38, var11.field5804, -765485579);
                     if (var11.field5805.equals("n")) {
                        var9 = classIR.method6353(var40, var11.field5804, var11.field5809, (byte)86);
                     } else if (var11.field5805.equals("w")) {
                        var9 = classQO.method9736(var36, var40, var11.field5804, false, var11.field5808, 329825058);
                     } else if (var11.field5805.equals("wm")) {
                        var9 = classQO.method9736(var36, var40, var11.field5804, true, var11.field5808, 329825058);
                     } else {
                        var9 = Long.toString(var5);
                     }
                  }
               }

               var6 = var9;
            }
         }

         var1.append(var6);
      } else if (var4[1].equals("pronoun")) {
         var2.method9596(var4[0], var3, 1711641727);
      } else {
         var2.method9595(var4[0], var4[1], 372964854);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;)V")
   @ObfuscatedName("ai")
   static void method10047(String var0, StringBuilder var1, classQI var2) {
      String[] var3 = var0.split("=");
      if (1 == var3.length) {
         var3 = var0.split(":");
         long var4 = var2.method9616(var3[0], (byte)-1);
         String var6 = Long.toString(var4);
         var1.append(var6);
      } else {
         var2.method9599(var3[0], var3[1], 1530066106);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)Ljava/lang/String;")
   @ObfuscatedName("bm")
   static String method10060(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while (var3 < var0.length()) {
         char var4 = var0.charAt(var3);
         if ('<' == var4) {
            int var5 = var0.indexOf(62, var3 + 1);
            if (-1 != var5) {
               var2.append(var0, var3, 1 + var5);
               var3 = 1 + var5;
               continue;
            }
         }

         if (var4 == '@') {
            int var6 = var0.indexOf(64, var3 + 1);
            if (var6 != -1) {
               var2.append(var0, var3, 1 + var6);
               var3 = 1 + var6;
               continue;
            }
         }

         var2.append(var1 ? Character.toLowerCase(var4) : Character.toUpperCase(var4));
         var3++;
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;)V")
   @ObfuscatedName("aw")
   static void method10048(String var0, StringBuilder var1, classQI var2) {
      int var3 = var0.indexOf(-1899564038);
      if (-1 == var3) {
         String[] var4 = var0.split(":");
         String var5 = var2.method9624(var4[0], 2145269203);
         if (null != var5 && var4.length > 1) {
            String var6 = var4[1].toLowerCase();
            if (var6.equals("lower")) {
               var5 = classPD.method8879(var5, true, 172682057);
            } else if (var6.equals("upper")) {
               var5 = classPD.method8879(var5, false, 1342345680);
            }
         }

         var1.append(var5);
      } else {
         String var7 = var0.substring(0, var3);
         String var8 = var0.substring(1 + var3);
         var8 = classME.method7603(var8, -1557408374);
         var2.method9607(var7, var8, -1418396012);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;)V")
   @ObfuscatedName("ak")
   static void method10049(String var0, StringBuilder var1, classQI var2) {
      int var3 = var0.indexOf(61);
      if (-1 == var3) {
         String[] var4 = var0.split(":");
         String var5 = var2.method9624(var4[0], 2145269203);
         if (null != var5 && var4.length > 1) {
            String var6 = var4[1].toLowerCase();
            if (var6.equals("lower")) {
               var5 = classPD.method8879(var5, true, -1710796061);
            } else if (var6.equals("upper")) {
               var5 = classPD.method8879(var5, false, 1209961959);
            }
         }

         var1.append(var5);
      } else {
         String var7 = var0.substring(0, var3);
         String var8 = var0.substring(1 + var3);
         var8 = classME.method7603(var8, -1735623312);
         var2.method9607(var7, var8, 38203528);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;I)V")
   @ObfuscatedName("ap")
   static void method10045(String var0, StringBuilder var1, classQI var2, int var3) {
      String[] var4 = var0.split("=");
      if (var4.length == 1) {
         var4 = var0.split(":");
         int var5 = var2.method9611(var4[0], 1324868884);
         String var6 = Integer.toString(var5);
         if (var4.length > 1) {
            String var7 = var4[1].toLowerCase();
            classRZ var8 = classAI.method363(classXO.field6890, 1883191353);
            if (var7.equals("spacer")) {
               var6 = classXW.method13010(var5, 0, true, true, var8, (byte)-7);
            } else if (var7.equals("percent")) {
               var6 = classXW.method13010(var5, 0, false, true, var8, (byte)61);
               var6 = var6 + "%";
            } else if (var7.equals("date")) {
               var6 = classGQ.method5636(var5, (byte)19);
            } else if (var7.startsWith("spacer_")) {
               classRA var32 = Projectile.method1213(var7.substring("spacer_".length()), 1089735267);
               var6 = classXW.method13010(var5, 1910078687 * var32.field5629, true, var32.field5628, var8, (byte)-51);
            } else if (var7.startsWith("decimal_")) {
               classRA var31 = Projectile.method1213(var7.substring("decimal_".length()), -1412412396);
               var6 = classXW.method13010(var5, -881365419 * var31.field5629, false, var31.field5628, var8, (byte)-13);
            } else if (var7.startsWith("scale_")) {
               String var33 = var7.substring("scale_".length());
               boolean var34 = true;
               if (var33.endsWith("_z")) {
                  var34 = false;
                  var33 = var33.substring(0, var33.length() - "_z".length());
               }

               String[] var35 = var33.split("_");
               int var37 = Integer.parseInt(var35[0]);
               int var39 = var35.length > 1 ? Integer.parseInt(var35[1]) : 0;
               classRL var30 = new classRL(var37, var39, var34);
               var6 = classIG.method6249(var5, var30.field5683 * -527595341, -212446981 * var30.field5685, var30.field5684, var8, 1275168984);
            } else if (var7.startsWith("percent_")) {
               classRA var29 = Projectile.method1213(var7.substring("percent_".length()), -767720142);
               var6 = classYY.method13818(var5, var29.field5629 * 618506607, var29.field5628, var8, (byte)111);
            } else if (var7.startsWith("zeropad_")) {
               int var28 = Integer.parseInt(var7.substring("zeropad_".length()));
               if (var5 >= 0) {
                  var6 = String.format("%0" + var28 + "d", var5);
               }
            } else if (var7.startsWith("time_")) {
               String var10 = var7.substring("time_".length());
               String[] var12 = var10.split("_");
               classRM var13 = new classRM();
               var13.field5806 = var12[0];
               var13.field5805 = var12[1];
               classRM var11;
               if (var13.field5805.equals("clk")) {
                  var11 = var13;
               } else {
                  String var14 = var12[2];
                  if (var14.equals("dt")) {
                     var13.field5804 = "d";
                     var13.field5808 = true;
                  } else if (var14.equals("ht")) {
                     var13.field5804 = "h";
                     var13.field5808 = true;
                  } else {
                     var13.field5804 = var14;
                  }

                  var13.field5809 = var12.length > 3 && var12[3].equals("ms");
                  var11 = var13;
               }

               classQO var36 = classMT.method7735((byte)-95);
               String var16 = var11.field5806;
               long var38;
               if ("c".equals(var16)) {
                  var38 = 600L * var5;
               } else if ("s".equals(var16)) {
                  var38 = 1000L * var5;
               } else if ("m".equals(var16)) {
                  var38 = 60000L * var5;
               } else {
                  var38 = var5;
               }

               String var9;
               label169: {
                  if (var11.field5805.equals("w")) {
                     if ((var11.field5806.equals("s") || var11.field5806.equals("c")) && var38 < 2000L) {
                        var9 = "a moment";
                        break label169;
                     }

                     if (var11.field5806.equals("m") && var38 <= 60000L) {
                        var9 = "a minute";
                        break label169;
                     }
                  }

                  if (var11.field5805.equals("clk")) {
                     long var20 = var38 / 60000L % 1440L;
                     if (var20 < 0L) {
                        var20 += 1440L;
                     }

                     long var22 = var20 / 60L;
                     long var24 = var20 % 60L;
                     String var19 = classGK.method5516(var22) + ":" + classGK.method5516(var24);
                     var9 = var19;
                  } else {
                     classRY var40 = classDE.method3000(var38, var11.field5804, -765485579);
                     if (var11.field5805.equals("n")) {
                        var9 = classIR.method6353(var40, var11.field5804, var11.field5809, (byte)33);
                     } else if (var11.field5805.equals("w")) {
                        var9 = classQO.method9736(var36, var40, var11.field5804, false, var11.field5808, 329825058);
                     } else if (var11.field5805.equals("wm")) {
                        var9 = classQO.method9736(var36, var40, var11.field5804, true, var11.field5808, 329825058);
                     } else {
                        var9 = Long.toString(var5);
                     }
                  }
               }

               var6 = var9;
            }
         }

         var1.append(var6);
      } else if (var4[1].equals("pronoun")) {
         var2.method9596(var4[0], var3, 1711641727);
      } else {
         var2.method9595(var4[0], var4[1], -853488155);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;I)V")
   @ObfuscatedName("at")
   static void method10052(String var0, StringBuilder var1, classQI var2, int var3) {
      String[] var4 = classKD.method6502(var0, (byte)-46);
      int var5 = -1;
      if (var4[1].equals("pronoun")) {
         var5 = var3;
      } else if (var4[1].startsWith("int")) {
         var5 = var2.method9611(var4[1], 1600915152);
      } else {
         Boolean var6 = var2.method9625(var4[1], -1008088227);
         if (null != var6) {
            var5 = var6 ? 1 : 0;
         }
      }

      String var7;
      if (var5 >= 0 && var5 < var4.length - 2) {
         var7 = var4[var5 + 2];
      } else {
         var7 = var4[var4.length - 1];
      }

      var1.append(classLA.method6700(var7, var3, new StringBuilder(var7.length()), var2, 905094636));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)[Ljava/lang/String;")
   @ObfuscatedName("an")
   static String[] method10053(String var0) {
      String[] var1 = var0.split("(?<!\\\\),", -1);

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = classME.method7603(var1[var2], 813554298);
         String var6 = var1[var2];
         String var5 = var6.replaceAll("\\\\,", ",");
         var1[var2] = var5;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)[Ljava/lang/String;")
   @ObfuscatedName("am")
   static String[] method10054(String var0) {
      String[] var1 = var0.split("(?<!\\\\),", -1);

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = classME.method7603(var1[var2], 1355710099);
         String var6 = var1[var2];
         String var5 = var6.replaceAll("\\\\,", ",");
         var1[var2] = var5;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bx")
   static String method10057(String var0) {
      if (var0.startsWith("\"")) {
         if (var0.length() == 1) {
            var0 = "";
         } else if (var0.endsWith("\"") && !var0.endsWith("\\\"")) {
            var0 = var0.substring(1, var0.length() - 1);
         }
      }

      return var0.replaceAll("\\\\\"", "\"");
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)[Ljava/lang/String;")
   @ObfuscatedName("bn")
   static String[] method10055(String var0) {
      String[] var1 = var0.split("(?<!\\\\),", -1);

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = classME.method7603(var1[var2], 908266470);
         String var6 = var1[var2];
         String var5 = var6.replaceAll("\\\\,", ",");
         var1[var2] = var5;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)[Ljava/lang/String;")
   @ObfuscatedName("ah")
   static String[] method10056(String var0) {
      String[] var1 = var0.split("(?<!\\\\),", -1);

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = classME.method7603(var1[var2], -687786476);
         String var6 = var1[var2];
         String var5 = var6.replaceAll("\\\\,", ",");
         var1[var2] = var5;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bc")
   static String method10058(String var0) {
      if (var0.startsWith("\"")) {
         if (var0.length() == 1) {
            var0 = "";
         } else if (var0.endsWith("\"") && !var0.endsWith("\\\"")) {
            var0 = var0.substring(1, var0.length() - 1);
         }
      }

      return var0.replaceAll("\\\\\"", "\"");
   }

   classRK() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)Ljava/lang/String;")
   @ObfuscatedName("br")
   static String method10061(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while (var3 < var0.length()) {
         char var4 = var0.charAt(var3);
         if ('<' == var4) {
            int var5 = var0.indexOf(62, var3 + 1);
            if (-1 != var5) {
               var2.append(var0, var3, 1 + var5);
               var3 = 1 + var5;
               continue;
            }
         }

         if (var4 == 690954892) {
            int var6 = var0.indexOf(-2137805220, var3 + 1);
            if (var6 != -1) {
               var2.append(var0, var3, 1 + var6);
               var3 = 1 + var6;
               continue;
            }
         }

         var2.append(var1 ? Character.toLowerCase(var4) : Character.toUpperCase(var4));
         var3++;
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;)Ljava/lang/String;")
   @ObfuscatedName("ao")
   public static String method10039(String var0, int var1, StringBuilder var2, classQI var3) {
      if (null == var0) {
         return null;
      } else if (var0.isEmpty()) {
         return var0;
      } else {
         String var4;
         try {
            var4 = classLA.method6700(var0, var1, var2, var3, -1491411530);
         } finally {
            var2.setLength(0);
            var3.method9628(1452371636);
         }

         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)Ljava/lang/String;")
   @ObfuscatedName("bt")
   static String method10062(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while (var3 < var0.length()) {
         char var4 = var0.charAt(var3);
         if ('<' == var4) {
            int var5 = var0.indexOf(62, var3 + 1);
            if (-1 != var5) {
               var2.append(var0, var3, 1 + var5);
               var3 = 1 + var5;
               continue;
            }
         }

         if (var4 == '@') {
            int var6 = var0.indexOf(64, var3 + 1);
            if (var6 != -1) {
               var2.append(var0, var3, 1 + var6);
               var3 = 1 + var6;
               continue;
            }
         }

         var2.append(var1 ? Character.toLowerCase(var4) : Character.toUpperCase(var4));
         var3++;
      }

      return var2.toString();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;I)Lte;")
   @ObfuscatedName("au")
   public static classTE method10050(String var0, String var1, int var2) {
      try {
         if (null != var1) {
            if (var2 <= 377918100) {
               throw new IllegalStateException();
            }

            if (!var1.isEmpty()) {
               int var3 = var1.length();
               int var4 = 1;
               int var5 = 0;

               while ((var5 = var0.indexOf(var1, var5)) != -1) {
                  var4++;
                  var5 += var3;
               }

               classTE var6 = new classTE(classYY.field7111, null, var4, var4);
               int var7 = 0;
               var5 = 0;

               for (var4 = 0; (var5 = var0.indexOf(var1, var5)) != -1; var7 = var5) {
                  if (var2 <= 377918100) {
                     throw new IllegalStateException();
                  }

                  var6.method10796(-931273909)[var4++] = var0.substring(var7, var5);
                  var5 += var3;
               }

               var6.method10796(1938092317)[var4] = var0.substring(var7);
               return var6;
            }

            if (var2 <= 377918100) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "rk.au(" + ')');
      }
   }
}
