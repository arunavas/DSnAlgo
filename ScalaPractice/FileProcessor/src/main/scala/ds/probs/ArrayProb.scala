package ds.probs

import scala.annotation.tailrec

/**
  * Created by arunavas on 23/3/18.
  */
object ArrayProb {
  def main(args: Array[String]): Unit = {
    println(s"testIsSubArraySumExists: $testIsSubArraySumExists")
  }

  def testPairWithGivenSum: Boolean = {
    val arr = Array(8, 7, 2, 5, 3, 1)
    val expect = List((7, 3), (8, 2))
    val result = pairWithGivenSum(arr, 10)
    println(s"Output for pairWithGivenSum with param ${arr.mkString(",")} and 10: $result")
    expect == result
  }
  def pairWithGivenSum(arr: Array[Int], sum: Int): List[(Int, Int)] = {
    /* Time Complexity: O(n) */
    @tailrec
    def aux(pos: Int, keys: Map[Int, Int], acc: List[(Int, Int)]): List[(Int, Int)] = if (pos >= arr.length) acc else {
      val num = arr(pos)
      val diff = sum - num
      val occurance = keys.getOrElse(diff, 0)
      if (occurance > 0) aux(pos + 1, keys + (diff -> (occurance - 1)), (diff, num) :: acc) else aux(pos + 1, keys + (num -> 1), acc)
    }

    aux(0, Map.empty[Int, Int], List.empty[(Int, Int)])
  }

  def testIsSubArraySumExists: Boolean = {
    val arr = Array(3, 4, -7, 3, 1, 3, 1, -4, -2, -2)
    val expect = true
    val result = isSubArraySumExists(arr, 0)
    println(s"Output for isSubArraySumExists with param ${arr.mkString(",")} and 0: $result")
    expect == result
  }
  def isSubArraySumExists(arr: Array[Int], sum: Int): Boolean = {
    /* Time Complexity: O(n) */
    @tailrec
    def aux(pos: Int, sums: Set[Int], acc: Int): Boolean = if (pos >= arr.length) false else {
      val num = arr(pos)
      val sumSoFar = acc + num
      if (sums contains sumSoFar) true else aux(pos + 1, sums + sumSoFar, sumSoFar)
    }

    aux(0, Set.empty[Int], 0)
  }

  def testSubArraySumExists: Boolean = {
    val arr = Array(3, 4, -7, 3, 1, 3, 1, -4, -2, -2)
    val expect = List()
    val result = subArraySumExists(arr, 0)
    println(s"Output for subArraySumExists with param ${arr.mkString(",")} and 0: $result")
    expect == result
  }
  def subArraySumExists(arr: Array[Int], sum: Int): List[Array[Int]] = {
    /* Time Complexity: O(n) */
    //@tailrec
    def aux(pos: Int, sums: Set[Int], acc: Int): List[Array[Int]] = if (pos >= arr.length) Nil else {
      /*val num = arr(pos)
      val sumSoFar = acc + num
      if (sums contains sumSoFar) true else aux(pos + 1, sums + sumSoFar, sumSoFar)*/
      Nil
    }

    aux(0, Set.empty[Int], 0)
  }
}
