package test

/**
  * Created by arunavas on 14/8/17.
  */
object ArghyaTest {

  def main(args: Array[String]): Unit = {
    //odds(10)
    //evens(10)
    /*val result: Int = sumev(10)
    println(result)*/

    /*val x = sum(30, 10)
    println(x)
    println(abs(5))*/

    /*println(round(5.7f))
    println(round(6.1f))

    println(prime(7))
    primes(18)
    println(mulofprimes(18))*/

    drawTriangle(25)

  }

  def drawTriangle(n: Int) = {
    def aux(c: Int): Unit = if (c <= n) {
      (c until n).foreach(_ => print(" "))
      (1 to (c + (c - 1))).foreach(_ => print("*"))
      println()
      aux(c + 1)
    }
    aux(1)
  }

  def abs(x: Int): Int = if (x > 0) x else -x

  def round(x: Float): Float = {
    val n = (x * 100).toInt
    val f = n % 100
    val r = n / 100
    if (f > 50) r + 1 else r
  }

  def prime(n: Int): Boolean = {
    def aux(c: Int): Boolean = if(c == n) true else if(n % c == 0) false else aux(c + 1)
    aux(2)
  }

  def isOdd(n: Int): Boolean = n % 2 != 0
  def odds(n: Int): Unit = if (n == 0) println("END") else {
    if (isOdd(n) == true) println(n)
    odds(n - 1)
  }


  def primes(n: Int): Unit = if (n == 0) println("END") else {
    if(prime(n) == true) println(n)
    primes(n - 1)
  }
  def sumofprimes(n: Int):Int = if(n == 0) 0 else if (prime(n) == true) n + sumofprimes(n-1) else sumofprimes(n-1)
  def evens(n: Int): Unit = if (n == 0) println("END")
  else {
    if (isOdd(n) == false) println(n)
    evens(n - 1)
  }

  def sumOfOdds(n: Int): Int = if (n == 0) 0 else if (isOdd(n) == true) n + sumOfOdds(n - 1) else sumOfOdds(n - 1)

  def sumev(n: Int): Int = if (n == 0) 0
  else {
    val num = if (isOdd(n) == false) n else 0
    num + sumev(n - 1)
  }
def mulofprimes(n: Int): Int = if (n == 0) 0 else if (n == 1) 1 else if (prime(n) == true) n * mulofprimes(n-1) else mulofprimes(n-1)
  def sum(n: Int, d: Int): Int = if (n == d) d
  else {
    val num = if (n % d == 0) n else 0
    num + sum(n - 1, d)
  }
}
