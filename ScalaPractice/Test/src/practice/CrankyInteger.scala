package practice

import scala.annotation.tailrec

object CrankyInteger {
  def main(args: Array[String]): Unit = {
    /*val six = sum(6)
    println(six)
    println("======================================================================================")*/
    val twelve = sum(12)
    println(twelve)
  }

  def reverse(num: Long) = {
    val x = Math.pow(10, num.toString().length() - 1).longValue()
    @tailrec
    def rev(value: Long, res: Long, div: Long): Long = {
      if (div == 1) res + value
      else rev(value / 10, res + ((value % 10) * div), div / 10)
    }
    rev(num, 0, x)
  }

  def sum(limit: Int) = {
    val max = Math.pow(10, limit).longValue()
    def isCranky(num: Long) = {
      val rev = reverse(num)
      @tailrec
      def findCranky(mod: Long, div: Long, i: Long): Boolean = {
        if (div == 0) false
        else if (mod * div == rev) {
          println("== " + num + " (" + mod + " * " + div + ") = " + (mod * div))
          true
        } else findCranky(num % i, num / i, i * 10)
      }

      findCranky(0, num, 10)
    }
    @tailrec
    def sumAux(value: Long, result: Long): Long = {
      if (value == max) result
      else if (isCranky(value)) sumAux(value + 1, result + value)
      else sumAux(value + 1, result)
    }
    sumAux(10, 0)
  }
}
