package test.arunava.scala.week5.list

import test.arunava.scala.week5.list.ListFold.foldLeft

// Would not work for empty list
object ListReduce {
  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)
    
    println(List(50, 51).reduceLeft((x, y) => {
      println(x + " " + y + " = " + (x * 10) + " " + ((x * 10) + 51))
      (x * 10) + y
    }))
    println(list.reduceLeft((x, y) => (x * 10) + y))
    println(list reduceLeft((x, y) => x + y))
    // can also be written as
    println(list reduceLeft(_ + _))
    println(list reduceRight(_ * _))
    
    println(reduceLeft(list)(_ + _))
    println(reduceRight(list)(_ * _))
    
    println(reduceLeft(List("a", "b"))(_ + _))
    println(reduceRight(List("a", "b"))(_ + _))
    println(List("a", "b") reduceLeft(_ + _))
    println(List("a", "b") reduceRight(_ + _))
    
    /*
      val eList = List[Int]()
      println(eList reduceLeft(_ + _)) // Would throw error - Nil.reduceLeft
      println(eList reduceRight(_ * _)) // Would throw error - Nil.reduceRight
    */
  }
  
  def reduceLeft[T](list: List[T])(op: (T, T) => T): T = list match {
    case Nil => throw new Error("Nil.reduceLeft")
    case x :: xs => foldLeft(x, xs)(op)
  }
  
  def reduceRight[T](list: List[T])(op: (T, T) => T): T = list match {
    case Nil => throw new Error("Nil.reduceRight")
    case x :: Nil => x
    case x :: xs => op(x, reduceRight(xs)(op))
  }
}