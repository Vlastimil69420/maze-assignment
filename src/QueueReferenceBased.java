/**
 * Maze Assignment - QueueReferenceBased
 * Reference-based (linked) implementation of the ADT Queue, the linked
 * version of the Lab4 QueueArrayBased. It implements the same
 * QueueInterface and uses the same Node class as the Lab3 stack.
 * QueueArrayBased can hold at most 50 items, this one has no limit,
 * so the solver works for any maze size.
 * The nodes form a circular list: lastNode is the back of the queue and
 * lastNode.getNext() is the front, so one reference reaches both ends.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class QueueReferenceBased implements QueueInterface {
  private Node lastNode;

  public QueueReferenceBased() {
    lastNode = null;
  }  // end default constructor

  // queue operations:
  public boolean isEmpty() {
    return lastNode == null;
  }  // end isEmpty

  public void dequeueAll() {
    lastNode = null;
  }  // end dequeueAll

  public void enqueue(Object newItem) {
    Node newNode = new Node(newItem);
    if (isEmpty()) {
      // only node, so it points to itself
      newNode.setNext(newNode);
    }
    else {
      // new back node points to the front
      newNode.setNext(lastNode.getNext());
      lastNode.setNext(newNode);
    }  // end if
    lastNode = newNode;
  }  // end enqueue

  public Object dequeue() throws QueueException {
    if (!isEmpty()) {
      // queue is not empty; remove front
      Node firstNode = lastNode.getNext();
      if (firstNode == lastNode) {
        lastNode = null;  // it was the only item
      }
      else {
        lastNode.setNext(firstNode.getNext());
      }  // end if
      return firstNode.getItem();
    }
    else {
      throw new QueueException("QueueException on dequeue: "+
                              "Queue empty");
    }  // end if
  }  // end dequeue

  public Object peek() throws QueueException {
    if (!isEmpty()) {
      // queue is not empty; retrieve front
      return lastNode.getNext().getItem();
    }
    else {
      throw new QueueException("Queue exception on peek: " +
                              "Queue empty");
    }  // end if
  }  // end peek

} // end QueueReferenceBased
