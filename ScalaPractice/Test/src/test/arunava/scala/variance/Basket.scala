package test.arunava.scala.variance

import scala.annotation.tailrec

trait Basket[+A] {
  def add[T >: A](newValue: T): Basket[T] = new FilledBasket(newValue, this)
  def isEmpty: Boolean
  def pick: A
  @tailrec
  final def printAll: Unit = this match {
    case EmptyBasket => print("")
    case FilledBasket(x, y) => {
      print(" " + x)
      y.printAll
    }
  }
}

case object EmptyBasket extends Basket[Nothing] {
  def isEmpty = true
  def pick = throw new Error("Can't Pick from Empty basket")
}

case class FilledBasket[+A](value: A, rest: Basket[A]) extends Basket[A] {
  def isEmpty = false
  def pick = value
}

trait Fruit {
  val name: String
  val weight: Double
  override def toString = "[" + this.name + " -> " + this.weight + "]"
}

class Apple(val weight: Double) extends Fruit {
  override val name = "Apple"
}
object Apple {
  def apply(x: Double): Apple = new Apple(x)
}

class Banana(val weight: Double) extends Fruit {
  override val name = "Banana"
}
object Banana {
  def apply(y: Double) = new Banana(y)
}