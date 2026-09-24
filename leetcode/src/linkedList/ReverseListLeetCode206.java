package linkedList;

public class ReverseListLeetCode206 {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode tmp = null;
        while (curr != null) {
            tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        return prev;
    }


    public ListNode reverseListRecursive(ListNode head) {
        if (head == null) {
            return null;
        } else if (head.next == null) {
            return head;
        } else {
            ListNode nextNode = head.next;
            head.next = null;
            ListNode rest = reverseListRecursive(nextNode);
            nextNode.next = head;
            return rest;
        }
    }
}
