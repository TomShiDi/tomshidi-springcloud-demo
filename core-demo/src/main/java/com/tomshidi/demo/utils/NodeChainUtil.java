package com.tomshidi.demo.utils;

/**
 * @author tomshidi
 * @since 2024/4/11 11:19
 */
public class NodeChainUtil {

    /**
     * 根据数组元素顺序构建链表
     *
     * @param values 数组元素
     * @return 链表首节点
     */
    public static ListNode generate(int... values) {
        ListNode head = new ListNode(values[0]);
        ListNode node = head;
        for (int i = 1; i < values.length; i++) {
            node.next = new ListNode(values[i]);
            node = node.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = generate(-1,5,3,4,0);
        ListNode newHead = sortList(head);
        System.out.println();
    }

    public static ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }
        // 归并区间为单节点时结束递归
        if (head.next == null) {
            return head;
        }
        ListNode slow = head;
        // 如果fast = head，在区间只有两个节点时，出现无限递归
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        ListNode fakeHead = new ListNode();
        ListNode t = fakeHead;
        while (left != null && right != null) {
            if (left.val > right.val) {
                t.next = right;
                right = right.next;
            } else {
                t.next = left;
                left = left.next;
            }
            t = t.next;
        }
        t.next = left == null ? right : left;
        return fakeHead.next;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
