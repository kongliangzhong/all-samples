package cn.klzhong.samples.algorithms;

/**
 * Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.
 * For example:
 * Given "aacecaaa", return "aaacecaaa".
 * Given "abcd", return "dcbabcd".
 * see: https://leetcode.com/problems/shortest-palindrome/
 */
public class Palindrome {

    public void test() {
        System.out.println(shortestPalindrome(""));
    }

    public String shortestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        int len = s.length();
        byte[] bs = s.getBytes();
        int needAdd = len - 1;

        for (int i = len/2; i > 0; i --) {
            int a = addCount(bs, i, len);
            if (a > 0) {
                needAdd = a;
                break;
            }
        }
        if (needAdd > len) needAdd = len - 1;

        return reverse(s.substring(len - needAdd)) + s;
    }

    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private boolean compareReverse(byte[] bts, int begin1, int end1, int begin2, int end2) {
        for (int i = begin1, j = end2 - 1; i < end1 && j >= begin2; i++, j--) {
            if (bts[i] != bts[j]) return false;
        }
        return true;
    }

    private int addCount(byte[] s, int i, int len) {
        if (2 * i > len) return -1;
        if (compareReverse(s, 0, i, i, 2*i)) return len - 2*i;

        else {
            if (2 * i + 1 > len) return -1;
            if (compareReverse(s, 0, i, i + 1, 2 * i + 1)) return len - (2*i + 1);
            else return -1;
        }
    }

}
