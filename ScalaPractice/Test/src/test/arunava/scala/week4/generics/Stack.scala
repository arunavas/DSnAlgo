package test.arunava.scala.week4.generics

trait Stack[A] {
  def push(n: A): Stack[A] = new FilledStack[A](n, this)
  def pop: Stack[A]
  def top: A
  def isEmpty: Boolean
  def contains(n: A): Boolean
  def printAll
}

class EmptyStack[A] extends Stack[A] {
  def pop: Stack[A] = error("EmptyStack.pop")
  def top: A = error("EmptyStack.top")
  def isEmpty: Boolean = true
  def contains(n: A): Boolean = false
  def printAll = Nil
}

class FilledStack[A](val top: A, val pop: Stack[A]) extends Stack[A] {
  def isEmpty = false
  def contains(n: A) = if (top.equals(n)) true else pop contains (n)
  def printAll = {
    print("" + top)
    pop.printAll
  }
}