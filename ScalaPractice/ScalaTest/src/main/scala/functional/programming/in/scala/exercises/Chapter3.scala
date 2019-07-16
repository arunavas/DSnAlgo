package functional.programming.in.scala.exercises

import scala.annotation.tailrec

/**
  * Created by arunavas on 13/8/17.
  */
object Chapter3 {

  def main(args: Array[String]): Unit = {
    val l = List(1, 2, 3, 4)
    /*println(hasSubsequence(l, List(4)))
    println(hasSubsequence(l, List(1, 2, 3, 4)))
    println(hasSubsequence(l, List(1, 2, 4)))
    println(hasSubsequence(l, List(1, 3, 4)))
    println(hasSubsequence(l, List(1, 4)))
    println(hasSubsequence(l, List(3, 4)))
    println(hasSubsequence(l, List(2, 3, 4)))
    println(hasSubsequence(l, List(1, 2, 3)))
    println(hasSubsequence(l, List(1)))
    println(hasSubsequence(l, List(2)))
    println(hasSubsequence(l, List(3)))*/
    val ll = List(l, l)
    println(ll.flatten)
    println(ll.flatMap(_ map (_ * 2)))
    println(flatMap(ll)(_ map (_ * 2)))
  }

  def map[A, B](l: List[A])(f: A => B): List[B] = l.foldRight(List.empty[B])(f(_) :: _)
  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] = {
    def aux(ll: List[A], acc: List[B]): List[B] = ll match {
      case Nil => acc
      case h :: t => aux(t, f(h).foldRight(acc)(_ :: _))
    }
    aux(l, List.empty[B])
  }

  def flatten[A](l: List[A])(implicit f: A => List[A]): List[A] = l flatMap f

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {
    @tailrec
    def aux(l: List[A], s: List[A]): Boolean = l match {
      case Nil => s == Nil
      case h :: t => (s == Nil) || aux(t, if (h == s.head) s.tail else sub)
    }

    aux(sup, sub)
  }

}
