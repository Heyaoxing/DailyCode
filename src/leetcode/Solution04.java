package leetcode;

public class Solution04 {
    public static int myAtoi(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] nums = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char less = '-';
        char plus = '+';
        char space = ' ';
        char zero = '0';
        char[] c = str.toCharArray();
        int index = 0;
        int mul = 0;
        int change = 0;
        char[] nums1 = new char[c.length];
        for (char value : c) {
            if (value == space && index == 0) {
                continue;
            }

            if (value == less || value == plus) {
                if (mul != 0) {
                    return 0;
                }
                mul = value == less ? -1 : 1;
                continue;
            }

            change = index;
            for (char num : nums) {
                if (zero == value && index == 0) {
                    break;
                } else if (num == value) {
                    nums1[index++] = value;
                    break;
                }
            }
            if (change == index && zero != value) {
                break;
            }
        }

        if (nums1[0] == 0) {
            return 0;
        }

        if (mul == 0) {
            mul = 1;
        }

        if (change > 12) {
            return 0;
        }


        long result = Long.parseLong(String.valueOf(nums1).trim()) * mul;
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }

    public static void main(String[] args) {
        String str = "+0 23";

        System.out.println(myAtoi(str));
    }

}
