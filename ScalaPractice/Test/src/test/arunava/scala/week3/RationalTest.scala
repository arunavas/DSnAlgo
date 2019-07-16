package test.arunava.scala.week3

import java.util.concurrent.atomic.AtomicInteger

object RationalTest {
  



  def main(args: Array[String]): Unit = {
    val r1 = new Rational(10, 2)
    val r2: Rational = new Rational(5)
    
    println("" + r1 + " + " + r2 + " = " + (r1 + r2))
    println("" + r1 + " - " + r2 + " = " + (r1 - r2))
    println("" + r1 + " * " + r2 + " = " + (r1 * r2))
    println("" + r1 + " / " + r2 + " = " + (r1 / r2))
    println()
    println("" + r1 + " < " + r2 + " = " + (r1 < r2))
    println("" + r1 + " > " + r2 + " = " + (r1 > r2))
    println("" + r1 + " == " + r2 + " = " + (r1 == r2))
    println("" + r1 + " != " + r2 + " = " + (r1 != r2))
    println("" + r1 + " <= " + r2 + " = " + (r1 <= r2))
    println("" + r1 + " >= " + r2 + " = " + (r1 >= r2))
    println()
    println("" + r1 + " min " + r2 + " = " + (r1 min r2))
    println("" + r1 + " max " + r2 + " = " + (r1 max r2))
    
    val r3 = new Rational(10, -2)
    
    val a: AtomicInteger = new AtomicInteger(10)
  }
  
}