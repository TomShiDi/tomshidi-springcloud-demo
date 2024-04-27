package com.tomshidi.code.test;

/**
 * @author TomShiDi
 * @since 2024/4/27 16:08
 */
public class FuncDemo {

    /**
     * Replace substrings consisting of more than 3 consecutive characters
     * @param str origin string
     * @param replaceStrategy Replacement strategy
     * @return result
     */
    private static String replaceRepeatStr(String str, ReplaceStrategy replaceStrategy) {
        if (str == null || str.length() < 3) {
            return str;
        }
        while (true) {
            int repeatCount = 1;
            int endIndex = -1;
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) == str.charAt(i - 1)) {
                    repeatCount++;
                    if (repeatCount >= 3) {
                        endIndex = i;
                    }
                } else {
                    // the number of character repeats is greater than 3, and the next character starts to be different
                    if (repeatCount >= 3) {
                        endIndex = i;
                        break;
                    } else {
                        // only one or two characters are repeated, need to continue to traverse the string
                        repeatCount = 1;
                    }
                }
            }
            if (endIndex == -1) {
                break;
            }
            // Determines whether the end of the string is still repeated
            if (str.charAt(endIndex) == str.charAt(endIndex - 1)) {
                endIndex = endIndex + 1;
            }
            int beginIndex = endIndex - repeatCount;
            String target = str.substring(beginIndex, endIndex);
            str = replaceStrategy.replace(str, target);
        }
        return str;
    }

    /**
     * stage 1
     * @param str origin string
     * @return remove repeat part
     */
    public static String removeRepeatStr(String str) {
        return replaceRepeatStr(str, (origin, target) -> origin.replace(target, ""));
    }

    /**
     * stage 2
     * @param str origin string
     * @return replace repeat part with a single character that comes before it alphabetically
     */
    public static String replaceByBeforeChar(String str) {
        return replaceRepeatStr(str, (origin, target) -> {
            char firstChar = target.charAt(0);
            String replace;
            if (firstChar == 'a') {
                replace = "";
            } else {
                replace = (char) (target.charAt(0) - 1) + "";
            }
            return origin.replace(target, replace);
        });
    }
}
