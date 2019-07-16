package test.arunava.scala.week4

abstract class IntegerStack {
  def push(n: Int): IntegerStack = new NonEmptyIntStack(n, this)
  def pop: IntegerStack
  def top: Int
  def isEmpty: Boolean
  def contains(n: Int): Boolean
  def printAll
}

class EmptyIntStack extends IntegerStack {
  def pop = error("Stack Underflow...")
  def top = error("Empty Stack...")
  def isEmpty = true
  def contains(n: Int) = false
  def printAll = println()
}

class NonEmptyIntStack(n: Int, prev: IntegerStack) extends IntegerStack {
  override def pop = prev
  override def top = n
  override def isEmpty = false
  override def contains(x: Int) = {
    if (n == x) true
    else prev.contains(x)
  }
  override def printAll = {
    print(" " + n)
    prev.printAll
  }
}