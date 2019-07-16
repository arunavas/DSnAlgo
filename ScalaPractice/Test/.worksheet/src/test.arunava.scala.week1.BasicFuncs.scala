package test.arunava.scala.week1

object BasicFuncs {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); 
  println("Welcome to the Scala worksheet");$skip(32); 
  
  def square(x: Int) = x * x;System.out.println("""square: (x: Int)Int""");$skip(12); val res$0 = 
  square(4);System.out.println("""res0: Int = """ + $show(res$0));$skip(37); 
  
  def sum(x: Int, y: Int) = x + y;System.out.println("""sum: (x: Int, y: Int)Int""");$skip(12); val res$1 = 
  sum(4, 3);System.out.println("""res1: Int = """ + $show(res$1));$skip(23); val res$2 = 
  
  square(sum(4, 4));System.out.println("""res2: Int = """ + $show(res$2));$skip(28); val res$3 = 
  sum(square(4), square(4));System.out.println("""res3: Int = """ + $show(res$3));$skip(84); 
  
  def sumation(x: Int): Int = {
  	if(x == 1) x
  	else x + sumation(x - 1);
  };System.out.println("""sumation: (x: Int)Int""");$skip(15); val res$4 = 
  sumation(10);System.out.println("""res4: Int = """ + $show(res$4));$skip(81); 
  
  def product(x: Int): Int = {
  	if(x == 1) x
  	else x * product(x - 1)
  };System.out.println("""product: (x: Int)Int""");$skip(13); val res$5 = 
  product(5);System.out.println("""res5: Int = """ + $show(res$5));$skip(119); 
  
  def doOperation1(x: Int, f: (Int, Int) => Int): Int = {
  	if(x == 1) x
  	else f (x, doOperation1(x - 1, f))
  };System.out.println("""doOperation1: (x: Int, f: (Int, Int) => Int)Int""");$skip(24); val res$6 = 
  doOperation1(10, sum);System.out.println("""res6: Int = """ + $show(res$6));$skip(45); val res$7 = 
  doOperation1(5, (x: Int, y: Int) => x * y);System.out.println("""res7: Int = """ + $show(res$7));$skip(141); 
  
  def doOperation2(x: Int, s: Int => Int, f: (Int, Int) => Int): Int = {
  	if(x == 1) x
  	else f (s (x), doOperation2(x - 1, s, f))
  };System.out.println("""doOperation2: (x: Int, s: Int => Int, f: (Int, Int) => Int)Int""");$skip(32); val res$8 = 
  doOperation2(10, x => x, sum);System.out.println("""res8: Int = """ + $show(res$8));$skip(32); val res$9 = 
  doOperation2(10, square, sum);System.out.println("""res9: Int = """ + $show(res$9));$skip(53); val res$10 = 
  doOperation2(5, x => x, (x: Int, y: Int) => x * y);System.out.println("""res10: Int = """ + $show(res$10));$skip(51); val res$11 = 
  doOperation2(10, (x: Int) => x, (x, y) => x + y);System.out.println("""res11: Int = """ + $show(res$11));$skip(44); val res$12 = 
  doOperation2(10, x => x, (x, y) => x + y);System.out.println("""res12: Int = """ + $show(res$12))}
}
