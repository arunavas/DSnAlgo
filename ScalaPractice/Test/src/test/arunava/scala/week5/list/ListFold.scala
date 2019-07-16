package test.arunava.scala.week5.list

import scala.annotation.tailrec

//Works for Empty List as well
object ListFold {
  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)
    println((list foldLeft 0)((x, y) => x + y))
    // can also be written as
    println((list foldLeft 0)(_ + _))
    println((list foldLeft 1)(_ * _))
    
    println((list foldRight 0)(_ + _))
    println((list foldRight 1)(_ * _))
    
    println(foldLeft(0, list)(_ + _))
    println(foldRight(0, list)(_ + _))
    
    println(foldLeft(1, list)(_ * _))
    println(foldRight(1, list)(_ * _))
    
    val eList = List[Int]()
    println((eList foldLeft 0)(_ + _))
    println((eList foldRight 1)(_ * _))
    
    println(foldLeft("", List("a", "b"))(_ + _))
    println(foldRight("", List("a", "b"))((x, y) => x + y))
    println((List("a", "b") foldLeft "")(_ + _))
    println((List("a", "b") foldRight "")(_ + _))
  }
  
  @tailrec
  def foldLeft[T](acc: T, list: List[T])(op: (T, T) => T): T = list match {
    case Nil => acc
    case x :: xs => foldLeft(op(acc, x), xs)(op)
  }
  
  def foldRight[T](acc: T, list: List[T])(op: (T, T) => T): T = list match {
    case Nil => acc
    case x :: xs => op(acc, foldRight(x, xs)(op))
  }
}