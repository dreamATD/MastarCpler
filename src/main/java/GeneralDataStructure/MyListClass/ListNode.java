package GeneralDataStructure.MyListClass;

public class ListNode<T> {
	T data;
	ListNode<T> last, next;
	ListNode() {
		last = next = null;
	}
	ListNode (T d, ListNode<T> pre, ListNode<T> suc) {
		data = d;
		last = pre;
		next = suc;
	}
	public static <Q> void link (ListNode<Q> pre, ListNode<Q> suc) {
		pre.next = suc;
		suc.last = pre;
	}
}
