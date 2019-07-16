package test.arunava.scala.week4.generics

import scala.annotation.tailrec

object ConsListTest {
  def main(args: Array[String]): Unit = {
    val x: ConsList[Int] = Cons(10, Cons(20, Cons(30, Nil())))
    
    x.printAll
    println()
    println("Head: " + x.head)
    println("tail.Head: " + x.tail.head)
    println("tail.tail.Head: " + x.tail.tail.head)
    /*println(x.tail.tail.tail.head)*/ // Will throw an exception - Nil.head - Tried to access head of a Nil as the last one is Nil
    
    println("---------TASK---------")
    val e0 = nth(x, 0)
    println (e0.head)
    val e1 = nth(x, 1)
    println (e1.head)
    val e2 = nth(x, 2)
    println (e2.head)
    val e3 = nth(x, 3)
    println (e3.head)
    
  }
  
  @tailrec
  def nth[T] (list: ConsList[T], n: Int): ConsList[T] = {
    list match {
      case Nil() => throw new IndexOutOfBoundsException("Index out of range")
      case Cons(v, t) => {
        if (n == 0) list
        else nth(t, n - 1)
      }
    }
  }
}