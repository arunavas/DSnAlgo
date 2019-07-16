package test.arunava.scala.week5.list

import scala.annotation.tailrec

object ListSortTask {
  def main(args: Array[String]): Unit = {
    val list = 9::8::7::6::5::1::2::3::4::0::Nil
    val sortedListSimple = iSortSimple(list)
    println(sortedListSimple)
    val sortedListCase = iSortCase(list)
    println(sortedListCase)
    val mergeAscendingSortedList = mSort[Int]((x, y) => x < y)(list)
    println(mergeAscendingSortedList)
    val mergeDescendingSortedList = mSort[Int]((x, y) => x > y)(list)
    println(mergeDescendingSortedList)
    val fruits = List("apple", "orange", "lemon", "pineapple", "banana")
    val sortedFruits = mSort((x: String, y: String) => x.compareTo(y) < 0)(fruits)
    //^--- if the comparison pattern would have been the last one then the type for x & y would have been optional as the compiler would get the information about the type by it's first arguement - fruits
    println(sortedFruits)
    val mergeSortedListPair = mSortByPair(list)
    println(mergeSortedListPair)
    val mergeSortImplicit = mSortByImplicit(list)
    println(mergeSortImplicit)
    val sortedFruitsImplicit = mSortByImplicit(fruits)
    println(sortedFruitsImplicit)
    
    
    println(lengthSimple(list))
    println(lengthTailRec(list))
    
    println(list)
    println(list take 5)
    println(list drop 5)
    println(drop(list, 5))
  }
  
  //if (n == 0 || isEmpty) this else tail.drop(n-1)
  //List(9, 8, 7, 6, 5, 1, 2, 3, 4, 0)
  //List(9, 8, 7, 6, 5)
  //List(1, 2, 3, 4, 0)
  
  def drop(list: List[Int], n: Int): List[Int] =
    if (n == 0 || list.isEmpty) list
    else {
      drop(list.tail, n-1)
    }
  
  def lengthSimple(list: List[Int]): Int = list match {
    case List() => 0
    case x :: xs => 1 + lengthSimple(xs)
  }
  
  def lengthTailRec(list: List[Int]): Int = {
    @tailrec
    def len(list: List[Int], l: Int): Int = list match {
      case List() => l
      case x :: xs => len(xs, l + 1)
    }
    len(list, 0)
  }
  
  /***** Insertion Sort *****/
  def iSortSimple(list: List[Int]): List[Int] = {
    if (list.isEmpty) Nil
    else insertSimple(list.head, iSortSimple(list.tail))
  }
  
  def insertSimple(v: Int, list: List[Int]): List[Int] = {
    if (list.isEmpty) List(v)
    else if (v <= list.head) v :: list
    else list.head :: insertSimple(v, list.tail)
  }
  
  def iSortCase(list: List[Int]): List[Int] = list match {
    case List() => List()
    case x :: xs => insertCase(x, iSortCase(xs))
  }
  
  def insertCase(v: Int, list: List[Int]): List[Int] = list match {
    case List() => List(v)
    case head :: tail => if (v < head) v :: list else head :: insertCase(v, tail)
  }
  
  /***** Merge Sort *****/
  def mSort[T](comparison: (T, T) => Boolean)(list: List[T]): List[T] = {
    
    def merge(p1: List[T], p2: List[T]): List[T] = {
      if (p1.isEmpty) p2
      else if (p2.isEmpty) p1
      else if (comparison(p1.head, p2.head)) p1.head :: merge(p1.tail, p2)
      else p2.head :: merge(p2.tail, p1)
    }
    
    val pivot: Int = list.length / 2
    if (pivot == 0) list
    else merge(mSort(comparison)(list.take(pivot)), mSort(comparison)(list.drop(pivot)))
  }
  
  def mSortByPair(list: List[Int]): List[Int] = list match {
    case x :: Nil => list
    case x :: xs => {
      def merge(p1: List[Int], p2: List[Int]): List[Int] = (p1, p2) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs, y :: ys) => {
          if (x < y) x :: merge(xs, p2)
          else y :: merge(ys, p1)
        }
      }
      val (p1, p2) = list splitAt(list.length / 2)
      merge(mSortByPair(p1), mSortByPair(p2))
    }
  }
  
  //Writing the ordering(comparison) before the value parameter would give error as it would not be able to determine the type at that point of time
  def mSortByImplicit[T](list: List[T])(implicit ordering: Ordering[T]): List[T] = {
    
    def merge(p1: List[T], p2: List[T]): List[T] = {
      if (p1.isEmpty) p2
      else if (p2.isEmpty) p1
      else if (ordering.lt(p1.head, p2.head)) p1.head :: merge(p1.tail, p2)
      else p2.head :: merge(p2.tail, p1)
    }
    
    val pivot: Int = list.length / 2
    if (pivot == 0) list
    else merge(mSortByImplicit(list.take(pivot)), mSortByImplicit(list.drop(pivot)))
  }
}