package leetcode;

import java.util.*;

/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 *
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 *
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 */
public class Solution02 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        List<Double> list = new ArrayList<>(nums1.length + nums2.length);
        for (int i = 0; i < nums1.length; i++) {
            list.add(Double.parseDouble(String.valueOf(nums1[i])));
        }
        for (int i = 0; i < nums2.length; i++) {
            list.add(Double.parseDouble(String.valueOf(nums2[i])));
        }
        Collections.sort(list);

        System.out.println(list);
        if (list.size() % 2 == 0) {
            return (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
    }


    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int count = m + n;
        int[] num3 = new int[count];
        for (int i = 0; i < m; i++) {
            num3[i] = nums1[i];
        }

        for (int i = 0; i < n; i++) {
            num3[m + i] = nums2[i];
        }

        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                int temp = num3[i];
                if (num3[i] > num3[j]) {
                    num3[i] = num3[j];
                    num3[j] = temp;
                }
            }
        }

        int index = count / 2;
        if (count % 2 == 0) {
            return (num3[index] + num3[index - 1]) / 2.0;
        } else {
            return num3[index] / 1.0;
        }
    }


    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m == 0) {
            int index = n / 2;
            if (n % 2 == 0) {
                return (nums2[index - 1] + nums2[index]) / 2.0;
            } else {
                return nums2[index];
            }
        }

        if (n == 0) {
            int index = m / 2;
            if (m % 2 == 0) {
                return (nums1[index - 1] + nums1[index]) / 2.0;
            } else {
                return nums1[index] ;
            }
        }


        int count = m + n;
        int i = 0,j = 0,k = 0;
        int[] nums3 = new int[count];
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                nums3[k++] = nums1[i++];
            } else {
                nums3[k++] = nums2[j++];
            }
        }
        while (i < m) {
            nums3[k++] = nums1[i++];
        }

        while (j < n) {
            nums3[k++] = nums2[j++];
        }

        int index = count / 2;
        if (count % 2 == 0) {
            return (nums3[index] + nums3[index - 1]) / 2.0;
        } else {
            return nums3[index] ;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2};
        int[] b = new int[]{2};
        System.out.println(findMedianSortedArrays3(a, b));
    }
}
