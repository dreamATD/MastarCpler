package GeneralDataStructure.MyListClass;

public class MyList<T> {
	ListNode<T> head, tail, curIter;
	int curIdx, size;
	public MyList() {
		head = new ListNode<>();
		tail = new ListNode<>();
		ListNode.link(head, tail);
		size = 0;
	}
	public int size() {
		return size;
	}
	public void add(T data) {
		ListNode<T> nod = new ListNode<>(data, null, null);
		ListNode.link(tail.last, nod);
		ListNode.link(nod, tail);
		if (size == 0) {
			curIter = head.next;
			curIdx = 0;
		}
		++size;
	}
	void moveIterTo(int idx) {
		boolean delta3 = idx >= curIdx;
		while (curIdx != idx) {
			if (delta3) {
				++curIdx;
				curIter = curIter.next;
			} else {
				--curIdx;
				curIter = curIter.last;
			}
		}
	}
	public T get(int idx) {
		int delta1 = idx;
		int delta2 = size - 1 - idx;
		int delta4 = Math.abs(idx - curIdx);
		if (delta4 < delta1 && delta4 < delta2) {
		} else if (delta1 <= delta2) {
			curIter = head.next;
			curIdx = 0;
		} else {
			curIter = tail.last;
			curIdx = size - 1;
		}
		moveIterTo(idx);
		return curIter.data;
	}
}
