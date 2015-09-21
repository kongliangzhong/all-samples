package cn.klzhong.samples.algorithms;

/**
 * You are given two linked lists representing two non-negative numbers. The digits are * stored in reverse order and each of their nodes contain a single digit. Add the two  * numbers and return it as a linked list.

 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */

public class AddTwoNumber {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

        @Override public String toString() {
            String s = "[" + val;
            ListNode ln = this;
            while (ln.next != null) {
                ln = ln.next;
                s = s + ", " + ln.val;
            }
            s = s + "]";
            return s;
        }
    }

    public void test() {
        System.out.println(addTwoNumbers(new ListNode(5), new ListNode(5)));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean needCarry = false;
        ListNode ln1 = l1;
        ListNode ln2 = l2;
        ListNode result = null;
        ListNode rn = null;
        int pos = 0;
        while(ln1 != null || ln2 != null || needCarry) {
            int v = 0;
            if (ln1 != null) {
                v += ln1.val;
                ln1 = ln1.next;
            }
            if (ln2 != null) {
                v += ln2.val;
                ln2 = ln2.next;
            }
            if (needCarry) v ++;

            if (v < 10) {
                needCarry = false;
            } else {
                needCarry = true;
                v -= 10;
            }

            if (pos == 0) {
                result = new ListNode(v);
                rn = result;
            } else {
                rn.next = new ListNode(v);
                rn = rn.next;
            }

            pos ++;

            //System.out.println(result);
        }

        return result;
    }

}
