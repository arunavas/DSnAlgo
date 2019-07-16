package test.arunava.problems.common

import scala.annotation.tailrec
import test.arunava.problems.easy.FirstTenEasy

class Util[A] {
  lazy val fte = new FirstTenEasy
  
  @tailrec
  final def dropWhile(list: List[A], f: A => Boolean): List[A] = list match {
    case Nil => list
    case x :: xs => if (f(x)) dropWhile(xs, f) else list
  }
  
  final def takeWhile(list: List[A], f: A => Boolean): List[A] = {
    @tailrec
    def aux(lst: List[A], res: List[A]): List[A] = lst match {
      case Nil => res
      case x :: xs => if (f(x)) aux(xs, x :: res) else res
    }
    fte.reverse(aux(list, Nil))
  }
  
  final def repeat(count: Int, element: A): List[A] = {
    List.range(0, count).map { _ => element }
  }
}