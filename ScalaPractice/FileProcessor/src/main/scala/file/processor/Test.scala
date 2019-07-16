package file.processor

import scala.annotation.tailrec
import scala.collection.immutable.NumericRange

/**
  * Created by arunavas on 19/2/18.
  */
object Test {

  def main(args: Array[String]): Unit = {
    val max = 999999
    //println(primeCount(max))
    /*val r = Stream.from(1).take(999999).grouped(10000).map(_.par.filter(isPrime)).foldLeft(0)((a, _) => a + 1)
    println(r)*/
    /*val x = 3 - 2 + 1
    println(x)*/
    val x = 4294967296l
    val s = stream(255).takeWhile(_ <= x)
    val count = s.count(n => {
      if (n % 100000 == 0) {
        println(n)
      }
      n.toBinaryString.count(_ == '1') >= 8
    })
    println("Size is: " + count)
  }

  def stream(i: Long = 1): Stream[Long] = i #:: stream(i + 1)

  def primeCount(max: Int): Int = {
    @tailrec
    def aux(n: Int, acc: Int): Int = if (n == 1) acc else aux(n - 1, if (isPrime(n)) acc + 1 else acc)
    aux(max, 0)
  }

  def isPrime(n: Int): Boolean = {
    @tailrec
    def aux(c: Int): Boolean = if (c == 1) true else n % c != 0 && aux(c - 1)

    n % 2 != 0 && (n <= 2 || aux(n / 2))
  }

}
