package test.arunava.scala.week5.list

import scala.annotation.tailrec

object ListTask {
  def main(args: Array[String]): Unit = {
    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(numList takeWhile (e => e < 6))
    val squaredList = squareList(numList)
    println(squaredList)
    val squaredListMap = numList map (x => x * x)
    println(squaredListMap)

    val charList = List('a', 'a', 'a', 'b', 'c', 'c', 'a')
    println(pack(charList))
    println(encode(charList))
    println(packDuplicates(charList))
    println(encodeDuplicates(charList))
    
    val nilList = Nil
    println(pack(nilList))
    println(encode(nilList))
    println(packDuplicates(nilList))
    println(encodeDuplicates(nilList))
    
    val matrix = List(List.range(1, 11), List.range(11, 21), List.range(21, 31), List.range(31, 41), List.range(41, 51))
    println(matrix)
    println(flatten(matrix))
    println(flattenFold(matrix))
    println(flattenReduce(matrix))
    
    println(matrix.flatten)
    
    println(printListFold(numList))
    println(reverseListFold(numList))
  }
  
  def printListFold[T](list: List[T]): List[T] = {
    (list foldLeft(Nil: List[T]))((x, y) => x :+ y)
  }
  
  def reverseListFold[T](list: List[T]): List[T] = {
    (list foldLeft(Nil: List[T]))((x, y) => y :: x)
  }
  
  def flatten[T](matrix: List[List[T]]): List[T] = {
    @tailrec
    def flattenMatrix(list: List[T], mat: List[List[T]]): List[T] = mat match {
      case Nil => list
      case x :: xs => flattenMatrix(list ::: x, xs)
    }
    flattenMatrix(Nil, matrix)
  }

  def flattenFold[T](matrix: List[List[T]]): List[T] = {
    (matrix foldLeft(Nil: List[T]))(_ ::: _)
    //In the above line, (_ ::: _) can also be written as {(x, y) => x ::: y}
  }
  
  def flattenReduce[T](matrix: List[List[T]]): List[T] = {
    matrix reduceLeft(_ ::: _)
    //_ ::: _ is the short form of (x, y) => x ::: y
  }

  def encode[T](list: List[T]): List[(T, Int)] = {
    val packedList = pack(list)
    def countLenth(l: List[T]): (T, Int) = (l.head, l.length)
    packedList filterNot (e => e.isEmpty) map (e => countLenth(e))
  }

  def pack[T](list: List[T]): List[List[T]] = {
    if (list.isEmpty) Nil
    else {
      val (matching, rest) = list span (x => x == list.head)
      matching :: pack(rest)
    }
  }

  def packDuplicates[T](list: List[T]): List[List[T]] = {
    if (list.isEmpty) Nil
    else {
      val (matching, rest) = list partition (x => x == list.head)
      matching :: packDuplicates(rest)
    }
  }

  def encodeDuplicates[T](list: List[T]): List[(T, Int)] = packDuplicates(list) filterNot (x => x.isEmpty) map (y => (y.head, y.length))

  def squareList(list: List[Int]): List[Int] = list match {
    case List()  => Nil
    case x :: xs => (x * x) :: squareList(list.tail)
  }
}