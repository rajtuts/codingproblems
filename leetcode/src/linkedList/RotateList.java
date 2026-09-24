package linkedList;

public class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return null;
        int size = 0;
        ListNode curr = head;
        while (curr.next != null) {
            size++;
            curr = curr.next;
        }
        size += 1;
        curr.next = head; // tail -> head
        k = k % size;
        curr = head;
        for (int i = 1; i < size - k; i++) {
            curr = curr.next;
        }
        ListNode newHead = curr.next;
        curr.next = null;
        return newHead;
    }
} // TC: O(n), SC: O(1)
