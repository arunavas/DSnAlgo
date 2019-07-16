package test.arunava.scala.week4.generics

trait ConsList[T] {
  def head: T
  def isEmpty: Boolean
  def tail: ConsList[T]
  def printAll
}

case class Cons[T](val head: T, val tail: ConsList[T]) extends ConsList[T] {
  override def isEmpty = false
  override def printAll = {
    print(" " + head)
    tail.printAll
  }
}

case class Nil[T]() extends ConsList[T] {
  def isEmpty = true
  def head: Nothing = error("Nil.head")
  def tail: Nothing = error("Nil.tail")
  def printAll = print("")
}