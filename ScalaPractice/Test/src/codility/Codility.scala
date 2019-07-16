package com.helplil.problemhelp

import scala.collection.immutable.IndexedSeq

/**
  * Created by arunavas on 28/1/17.
  */
object Codility extends App {

  println("Hello World!")

  def solution(a: Int, b: Int): Int = {
    List.range(a, b + 1).count(x => (x == 1) || List.range(2, (x / 2) + 1).exists(i => i * i == x))
  }
  //println(solution(-100, 17))

  val numbers: List[Char] = List('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  def flattenString(s: String): String = {
    def doFlatten(x: List[Char], result: String): String = x match {
      case Nil => result
      case l @ h :: t =>
        if (numbers.contains(h)) {
          val s: (List[Char], List[Char]) = l.span(numbers.contains)
          val n = s._1.map(_.asDigit).reduceLeft((i, j) => (i * 10) + j)
          doFlatten(s._2, s"$result${List.range(1, n + 1).map(i => "_").mkString}")
        } else doFlatten(t, s"$result$h")
    }
    doFlatten(s.toList, "")
  }
  def solution(s: String, t: String): Boolean = {
    val a = flattenString(s)
    val b = flattenString(t)
    (a.length == b.length) && ((a zip b) forall(x => x._1 == '_' || x._2 == '_' || x._1 == x._2))
  }

  println(solution("a2Le", "2pLe"))

  /*val l: List[Char] = List('2', '3', 'j', '4', '5', 'x')
  val n = l.span(numbers.contains)._1.map(_.asDigit).reduceLeft((i, j) => (i * 10) + j)
  println(n + " " + List.range(1, n).map(k => "_").mkString("|"))

  val x = "a2Le"
  val z = "2pLe"
  val y = "a1g3m2"
  val a = flattenString(x)
  val b = flattenString(z)
  println(a)
  println(b)
  println(flattenString(y))
  val ab: IndexedSeq[(Char, Char)] = a zip b
  println(ab)*/

}
