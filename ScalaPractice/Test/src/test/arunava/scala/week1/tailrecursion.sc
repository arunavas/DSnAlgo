package test.arunava.scala.week1

object tailrecursion {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //tailrec
  def gcd(x: Int, y: Int): Int = if(y == 0) x else gcd(y, x % y)
                                                  //> gcd: (x: Int, y: Int)Int
  gcd(21, 14)                                     //> res0: Int = 7
  gcd(14, 21)                                     //> res1: Int = 7
  gcd(10, 21)                                     //> res2: Int = 1
  
  //!tailrec
  def fact(x: Int):Int = if(x == 1) x else x * fact(x - 1)
                                                  //> fact: (x: Int)Int
  fact(5)                                         //> res3: Int = 120
  
  //tailrec
  def factorial(x: Int):Int = {
  	def iter(a: Int, b: Int, sum: Int): Int = if(b == a) sum else iter(a, b + 1, sum * (b + 1))
  	
  	iter(x, 1, 1)
  }                                               //> factorial: (x: Int)Int
  factorial(5)                                    //> res4: Int = 120
  
  //tailrec
  def tailfactorial(x: Int) = {
  	def loop(acc: Int, n: Int):Int = if(n == 0) acc else loop(acc * n, n - 1)
  	
  	loop(1, x)
  }                                               //> tailfactorial: (x: Int)Int
  
  tailfactorial(5)                                //> res5: Int = 120
}