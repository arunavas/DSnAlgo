package practice

import scala.annotation.tailrec

object Reverse {
  def main(args: Array[String]): Unit = {
    println(reverse(List(1, 2, 3, 4, 5, 6)))
  }

  def reverse[A](list: List[A]): List[A] = {
    @tailrec
    def aux(list: List[A], res: List[A]): List[A] = list match {
      case Nil => res
      case x :: xs => aux(xs, x :: res)
    }

    aux(list, Nil)
  }
}
