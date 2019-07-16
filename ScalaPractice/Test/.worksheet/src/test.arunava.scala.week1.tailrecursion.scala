package test.arunava.scala.week1

object tailrecursion {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(100); 
  println("Welcome to the Scala worksheet");$skip(80); 
  
  //tailrec
  def gcd(x: Int, y: Int): Int = if(y == 0) x else gcd(y, x % y);System.out.println("""gcd: (x: Int, y: Int)Int""");$skip(14); val res$0 = 
  gcd(21, 14);System.out.println("""res0: Int = """ + $show(res$0));$skip(14); val res$1 = 
  gcd(14, 21);System.out.println("""res1: Int = """ + $show(res$1));$skip(14); val res$2 = 
  gcd(10, 21);System.out.println("""res2: Int = """ + $show(res$2));$skip(75); 
  
  //!tailrec
  def fact(x: Int):Int = if(x == 1) x else x * fact(x - 1);System.out.println("""fact: (x: Int)Int""");$skip(10); val res$3 = 
  fact(5);System.out.println("""res3: Int = """ + $show(res$3));$skip(167); 
  
  //tailrec
  def factorial(x: Int):Int = {
  	def iter(a: Int, b: Int, sum: Int): Int = if(b == a) sum else iter(a, b + 1, sum * (b + 1))
  	
  	iter(x, 1, 1)
  };System.out.println("""factorial: (x: Int)Int""");$skip(15); val res$4 = 
  factorial(5);System.out.println("""res4: Int = """ + $show(res$4));$skip(146); 
  
  //tailrec
  def tailfactorial(x: Int) = {
  	def loop(acc: Int, n: Int):Int = if(n == 0) acc else loop(acc * n, n - 1)
  	
  	loop(1, x)
  };System.out.println("""tailfactorial: (x: Int)Int""");$skip(22); val res$5 = 
  
  tailfactorial(5);System.out.println("""res5: Int = """ + $show(res$5))}
}
