package test.arunava.scala

import scala.annotation.tailrec

object test {
  def main(args: Array[String]): Unit = {
    println(operate(10, 12, (x, y) => x + y))
    println(operate(10, 20, sum))
    println(operate(5, 10, (_ * _)))
    
    println(sumJ(5))
    println(summation(5))
    
    println(sumT(5, 0))
    
  }
  
  @tailrec
  def sumT(n: Int, acc: Int): Int = {
    if(n == 0) acc
    else sumT(n - 1, acc + n)
  }
  
  def summation(n: Int): Int = {
    if (n == 0) return n 
    else n + summation(n - 1)
  }
  // 5 + 4 + 3 + 2 + 1
  
  def sumJ(num: Int): Int = {
    var sum = 0
    var n = num
    while (n > 0) {
      sum = sum + n
      n = n - 1
    }
    sum
  }
  
  def sum(x: Int, y: Int): Int = x + y
  
  def operate(x: Int, y: Int, f: (Int, Int) => Int): Int = {
    f(x, y)
  }
    
}