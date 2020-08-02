package leetcode;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 */
public class Solution03 {
    public static String longestPalindrome(String s) {
        int slen = s.length();
        if (slen < 2) {
            return s;
        }

        StringBuffer sb = new StringBuffer(s).reverse();
        if (s.equals(sb.toString())) {
            return s;
        }

        String temp = "";
        int count = 0;
        for (int i = 0; i < slen - 1; i++) {
            for (int j = i + 1; j < slen; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    String str1 = s.substring(i, j + 1);
                    StringBuffer str2 = new StringBuffer(str1).reverse();
                    int length = str1.length();
                    if (str1.equals(str2.toString()) && count < length) {
                        count = length;
                        temp = str1;
                    }
                }

            }
        }
        if (count == 0) {
            return s.charAt(0) + "";
        }
        return temp;
    }

    public static String longestPalindrome01(String s) {
        int slen = s.length();
        if (slen < 2) {
            return s;
        }

        int[] index = new int[2];
        char[] a = s.toCharArray();
        for (int i = 0; i < slen; i++) {
            //遍历AAA的情况
            int left = i;
            int right = i;
            while (right < slen - 1 && a[i] == a[right + 1]) {
                right++;
            }
            //遍历ABA的情况
            while (left > 0 && right < slen- 1 && a[left - 1] == a[right + 1]) {
                left--;
                right++;
            }

            if (right - left > index[1] - index[0]) {
                index[0] = left;
                index[1] = right;
            }
        }
        return s.substring(index[0], index[1] + 1);
    }

    public static void main(String[] args) {
        String s ="aabaa";
        System.out.println(longestPalindrome01(s));
    }
}
