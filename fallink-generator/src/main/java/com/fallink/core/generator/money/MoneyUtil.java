package com.fallink.core.generator.money;

import com.google.common.math.LongMath;
import org.springframework.util.Assert;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtil {
    private static String[] CH = new String[]{"", "", "拾", "佰", "仟", "万", "", "", "", "亿", "", "", "", "兆"};
    private static String[] CHS_NUMBER = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public MoneyUtil() {
    }

    public static boolean hasMoney(Money amount) {
        return amount != null && amount.getCent() > 0L;
    }

    public static String format(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("金额不能为null");
        } else {
            DecimalFormat fmt = new DecimalFormat("##,###,###,###,###.00");
            String result = fmt.format(money.getAmount().doubleValue());
            if (result.indexOf(".") == 0) {
                result = "0" + result;
            }

            return result;
        }
    }

    public static Money round(Money money, Money roundMoney, RoundingMode roundingMode) {
        Assert.notNull(money, "money不能为空");
        Assert.notNull(roundMoney, "roundMoney不能为空");
        Assert.notNull(roundingMode, "roundingMode不能为空");
        long multiple = LongMath.divide(money.getCent(), roundMoney.getCent(), roundingMode);
        long newCent = LongMath.checkedMultiply(multiple, roundMoney.getCent());
        return money.newMoneyWithSameCurrency(newCent);
    }

    public static String getCHSNumber(Money m) {
        if (m == null) {
            throw new IllegalArgumentException("金额不能为null");
        } else if (m.getCent() == 0L) {
            return "零元";
        } else {
            String money = m.getAmount().toString();
            String chs = "";
            String tmp_int = money.substring(0, money.indexOf("."));
            String tmp_down = money.substring(money.indexOf(".") + 1, money.length());
            char[] tmp_int_char = tmp_int.toCharArray();
            String[] tmp_chs = new String[tmp_int_char.length];
            boolean tab = false;

            for (int tmp = 0; tmp < tmp_int_char.length; ++tmp) {
                int var9 = tmp_int_char.length - tmp - 1;
                if (tmp_int_char.length <= 5) {
                    tmp_chs[var9] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[tmp] + ".0")];
                    if (!tmp_chs[var9].equals("零")) {
                        chs = chs + tmp_chs[var9] + CH[tmp_int_char.length - tmp];
                    } else if (!chs.endsWith("零") && var9 != 0) {
                        chs = chs + tmp_chs[var9];
                    } else if (chs.endsWith("零") && var9 == 0) {
                        chs = chs.substring(0, chs.length() - 1);
                    }
                }

                if (tmp_int_char.length > 5 && tmp_int_char.length < 9) {
                    tmp_chs[var9] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[tmp] + ".0")];
                    if (var9 >= 4) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 3];
                            if (var9 == 4) {
                                chs = chs + "万";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 4) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    chs = chs + "万";
                                } else {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }

                    if (var9 < 4) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[tmp_int_char.length - tmp];
                        } else {
                            if (!chs.endsWith("零") && var9 != 0) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (chs.endsWith("零") && var9 == 0) {
                                chs = chs.substring(0, chs.length() - 1);
                            }
                        }
                    }
                }

                if (tmp_int_char.length >= 9 && tmp_int_char.length <= 12) {
                    tmp_chs[var9] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[tmp] + ".0")];
                    if (var9 >= 8 && var9 < 12) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 7];
                            if (var9 == 8) {
                                chs = chs + "亿";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 8) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    chs = chs + "亿";
                                } else {
                                    chs = chs + "亿";
                                }
                            }
                        }
                    }

                    if (var9 >= 4 && var9 < 8) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 3];
                            if (var9 == 4) {
                                chs = chs + "万";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 4) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    if (!chs.endsWith("亿")) {
                                        chs = chs + "万";
                                    }
                                } else if (!chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }

                    if (var9 < 4) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[tmp_int_char.length - tmp];
                        } else {
                            if (!chs.endsWith("零") && var9 != 0) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (chs.endsWith("零") && var9 == 0) {
                                chs = chs.substring(0, chs.length() - 1);
                            }
                        }
                    }
                }

                if (tmp_int_char.length > 12 && tmp_int_char.length <= 16) {
                    tmp_chs[var9] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[tmp] + ".0")];
                    if (var9 >= 12 && var9 < 16) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 11];
                            if (var9 == 12) {
                                chs = chs + "兆";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 12) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    chs = chs + "兆";
                                } else {
                                    chs = chs + "兆";
                                }
                            }
                        }
                    }

                    if (var9 >= 8 && var9 < 12) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 7];
                            if (var9 == 8) {
                                chs = chs + "亿";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 8) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    if (!chs.endsWith("兆")) {
                                        chs = chs + "亿";
                                    }
                                } else if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            }
                        }
                    }

                    if (var9 >= 4 && var9 < 8) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 3];
                            if (var9 == 4) {
                                chs = chs + "万";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 4) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    if (!chs.endsWith("亿") && !chs.endsWith("兆")) {
                                        chs = chs + "万";
                                    }
                                } else if (!chs.endsWith("亿") && !chs.endsWith("兆")) {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }

                    if (var9 < 4) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[tmp_int_char.length - tmp];
                        } else {
                            if (!chs.endsWith("零") && var9 != 0) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (chs.endsWith("零") && var9 == 0) {
                                chs = chs.substring(0, chs.length() - 1);
                            }
                        }
                    }
                }

                if (tmp_int_char.length > 16) {
                    tmp_chs[var9] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[tmp] + ".0")];
                    if (var9 >= 12) {
                        chs = chs + tmp_chs[var9];
                        if (var9 == 12) {
                            chs = chs + "兆";
                        }
                    }

                    if (var9 >= 8 && var9 < 12) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 7];
                            if (var9 == 8) {
                                chs = chs + "亿";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 8) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    if (!chs.endsWith("兆")) {
                                        chs = chs + "亿";
                                    }
                                } else if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            }
                        }
                    }

                    if (var9 >= 4 && var9 < 8) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[var9 - 3];
                            if (var9 == 4) {
                                chs = chs + "万";
                            }
                        } else {
                            if (!chs.endsWith("零")) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (var9 == 4) {
                                if (chs.endsWith("零")) {
                                    chs = chs.substring(0, chs.length() - 1);
                                    if (!chs.endsWith("兆") && !chs.endsWith("亿")) {
                                        chs = chs + "万";
                                    }
                                } else if (!chs.endsWith("兆") && !chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }

                    if (var9 < 4) {
                        if (!tmp_chs[var9].equals("零")) {
                            chs = chs + tmp_chs[var9] + CH[tmp_int_char.length - tmp];
                        } else {
                            if (!chs.endsWith("零") && var9 != 0) {
                                chs = chs + tmp_chs[var9];
                            }

                            if (chs.endsWith("零") && var9 == 0) {
                                chs = chs.substring(0, chs.length() - 1);
                            }
                        }
                    }
                }
            }

            char[] var10 = tmp_down.toCharArray();
            if (var10.length == 1) {
                if (var10[0] != 48) {
                    chs = chs + "元" + CHS_NUMBER[(int) Float.parseFloat(var10[0] + ".0")] + "角整";
                } else {
                    chs = chs + "元整";
                }
            } else if (var10[1] != 48 && var10[0] != 48) {
                if (chs.isEmpty()) {
                    chs = CHS_NUMBER[(int) Float.parseFloat(var10[0] + ".0")] + "角" + CHS_NUMBER[(int) Float.parseFloat(var10[1] + ".0")] + "分";
                } else {
                    chs = chs + "元" + CHS_NUMBER[(int) Float.parseFloat(var10[0] + ".0")] + "角" + CHS_NUMBER[(int) Float.parseFloat(var10[1] + ".0")] + "分";
                }
            } else if (var10[1] != 48 && var10[0] == 48) {
                if (chs.isEmpty()) {
                    chs = CHS_NUMBER[(int) Float.parseFloat(var10[1] + ".0")] + "分";
                } else {
                    chs = chs + "元零" + CHS_NUMBER[(int) Float.parseFloat(var10[1] + ".0")] + "分";
                }
            } else if (var10[1] == 48 && var10[0] != 48) {
                if (chs.isEmpty()) {
                    chs = CHS_NUMBER[(int) Float.parseFloat(var10[0] + ".0")] + "角";
                } else {
                    chs = chs + "元" + CHS_NUMBER[(int) Float.parseFloat(var10[0] + ".0")] + "角";
                }
            } else if (var10[1] == 48 && var10[0] == 48) {
                chs = chs + "元整";
            }

            return chs;
        }
    }

}
