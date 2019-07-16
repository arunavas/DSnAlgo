package functional.programming.in.scala.exercises

import scala.annotation.tailrec

/**
  * Created by arunavas on 27/7/17.
  */
object Chapter2 {

  def main(args: Array[String]): Unit = {
    /*
    println(fibonacciNumberAt(-1)) // Should be -1
    println(fibonacciNumberAt(0)) // Should be 0
    println(fibonacciNumberAt(1)) // Should be 1
    println(fibonacciNumberAt(2)) // Should be 1
    println(fibonacciNumberAt(3)) // Should be 2
    println(fibonacciNumberAt(4)) // Should be 3
    println(fibonacciNumberAt(5)) // Should be 5
    println(fibonacciNumberAt(6)) // Should be 8
    */

    println(isSorted(List.range(0, 19).toArray)(_ < _))
    println(i)

  }

  def fibonacciNumberAt(n: Int): Int = if (n < 0) -1 else {
    @tailrec
    def aux(p: Int, c: Int, step: Int): Int = if (step == 0) p else aux(c, p + c, step - 1)
    aux(0, 1, n)
  }

  def isSorted[A](as: Array[A])(ordering: (A, A) => Boolean): Boolean = {
    @tailrec
    def aux(c: Int): Boolean = if (c == as.length) true else if (ordering(as(c - 1), as(c))) aux(c + 1) else false
    @tailrec
    def a(c: Int): Boolean = (c == as.length) || (ordering(as(c - 1), as(c)) && a(c + 1))
    a(1)
  }

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = b => f(a, b)
  def partial2[A, B, C](a: A, f: (A, B) => C): B => C = {
    def p(b: B) = f(a, b)
    p
  }

  def currying[A, B, C](f: (A, B) => C): A => (B => C) = a => partial1(a, f)
  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    def p(a: A): B => C = {
      def q(b: B): C = f(a, b)
      q
    }
    p
    /* is similar to (using annonymous function)*/
    // (a: A) => (b: B) => f(a, b)
    /* is similar to (using syntactic suger) */
    //a => b => f(a, b)
  }
  def c[A, B, C](f: (A, B) => C): A => (B => C) = a => b => f(a, b)

  def uncurry[A, B, C](f: A => (B => C)): (A, B) => C = (a, b) => f(a)(b)
  def uncurrying[A, B, C](f: A => (B => C)): (A, B) => C = {
    def aux(a: A, b: B): C = f(a)(b)
    aux
  }

  def a(x: Int, y: Int, z: Int) = x + y + z
  def b(x: Int, y: Int): Int => Int = z => a(x, y, z)
  def c(x: Int): Int => (Int => Int) = y => b(x, y)
  def d: Int => Int => Int => Int = v => c(v)
  val f: (Int) => (Int) => Int = c(10)
  val g: (Int) => Int = f(10)
  val h: Int = g(10)
  val i: Int = d(10)(20)(30)

  def compos[A, B, C](f: B => C, g: A => B): A => C = {
    def func(a: A): C = f(g(a))
    func
  }
  def comp[A, B, C](f: B => C, g: A => B): A => C = (a: A) => f(g(a))
  def compose[A, B, C](f: B => C, g: A => B): A => C = a => f(g(a))
  def andThen[A, B, C](g: A => B, f: B => C): A => C = a => f(g(a))
}
