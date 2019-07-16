package test.arunava.scala.week4

abstract class IntegerQueue {
  def push(n: Int): IntegerQueue
  def pull: IntegerQueue
  def peek: Int
  def isEmpty: Boolean
  def contains(n: Int): Boolean
  def printAll
}

class EmptyQueue extends IntegerQueue {
  def push(n: Int) = new NonEmptyQueue(n, this)
  def pull = error("Queue Underflow...")
	def peek = error("Empty Queue...")
  def isEmpty = true
  def contains(n: Int) = false
  def printAll = println()
}

class NonEmptyQueue(value: Int, next: IntegerQueue) extends IntegerQueue {
  override def push(n: Int) = {
    if(next.isEmpty) new NonEmptyQueue(value, next.push(n))
    else new NonEmptyQueue(value, next.push(n))
  }
  override def pull = next
  override def peek = value
  override def isEmpty = false
  override def contains(x: Int) = {
    if (value == x) true
    else next.contains(x)
  }
  override def printAll = {
    print(" " + value)
    next.printAll
  }
}