import java.util.Vector;

public class Queue {

	Vector queue;

	Queue() {
		queue = new Vector();
	}

	public void enqueue(State x) {
		queue.addElement(x);
	}

	public State dequeue() {
		State tempState = (State)queue.firstElement();
		queue.remove(0);
		return tempState;
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
