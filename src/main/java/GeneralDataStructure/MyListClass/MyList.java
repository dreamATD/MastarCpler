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
	public void addFirst(T data) {
		ListNode<T> nod = new ListNode<>(data, null, null);
		ListNode.link(nod, head.next);
		ListNode.link(head, nod);
		if (size == 0) {
			curIter = head.next;
			curIdx = 0;
		}
		++size;
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
	public void removeBefore(int k) {
		moveIterTo(k);
		size = size - k;
		ListNode.link(head, curIter);
		curIdx = 0;
	}
	public void removeAfter(int k) {
		moveIterTo(k);
		size = k + 1;
		ListNode.link(curIter, tail);
	}
	public void remove(int k) {
		moveIterTo(k);
		ListNode.link(curIter.last, curIter.next);
		--size;
		curIter = curIter.last; // For the sake of loop.
		curIdx--;
	}
	public void addAll(MyList<T> other) {
		for (int i = 0; i < other.size(); ++i) {
			add(other.get(i));
		}
	}
	public void insertBefore(int k, T data) {
		moveIterTo(k);

		ListNode<T> tmp = new ListNode<>(data, null, null);
		ListNode.link(curIter.last, tmp);
		ListNode.link(tmp, curIter);
		++curIdx;
		++size;
	}
}
