package test.arunava.scala.week2

object currying {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(95); 
  println("Welcome to the Scala worksheet");$skip(73); 

  def doOperations(f: (Int, Int) => Int, x: Int, y: Int): Int = f(x, y);System.out.println("""doOperations: (f: (Int, Int) => Int, x: Int, y: Int)Int""");$skip(65); 

  def sum(x: Int, y: Int) = doOperations((a, b) => a + b, x, y);System.out.println("""sum: (x: Int, y: Int)Int""");$skip(64); 
  def mul(x: Int, y: Int) = doOperations((a, b) => a * b, x, y);System.out.println("""mul: (x: Int, y: Int)Int""");$skip(15); val res$0 = 

  sum(10, 20);System.out.println("""res0: Int = """ + $show(res$0));$skip(14); val res$1 = 
  mul(10, 20);System.out.println("""res1: Int = """ + $show(res$1));$skip(163); 
  
  def doOps(f: (Int, Int) => Int, x: Int, y: Int) = {
  	def iter(acc: Int, y: Int): Int = if(y < x) acc else iter( f(acc, y), y - 1)
  	
  	iter(y, y - 1)
  };System.out.println("""doOps: (f: (Int, Int) => Int, x: Int, y: Int)Int""");$skip(57); 
  
  def sumVals(x: Int, y: Int): Int = doOps(sum, x, y);System.out.println("""sumVals: (x: Int, y: Int)Int""");$skip(54); 
  def mulVals(x: Int, y: Int): Int = doOps(mul, x, y);System.out.println("""mulVals: (x: Int, y: Int)Int""");$skip(20); val res$2 = 
  
  sumVals(1, 10);System.out.println("""res2: Int = """ + $show(res$2));$skip(16); val res$3 = 
  mulVals(1, 5);System.out.println("""res3: Int = """ + $show(res$3));$skip(445); 
  
  /* Here in the above example, the arguements x & y are being used in sumVals & mulVals only just to pass them to the doOps, lets try eliminating them */
  
  /******** CURRYING START ********/
  // tail rec
  def doOperation1(f: (Int, Int) => Int): (Int, Int) => Int = {
  	
  	def subOper(acc: Int, x: Int, y: Int): Int = if (y == x) acc else subOper(f (acc, y), x, y - 1)
  	
  	subOper(1, _, _)
  	/* OR */
  	//subOper(1, _, _)
  	
  };System.out.println("""doOperation1: (f: (Int, Int) => Int)(Int, Int) => Int""");$skip(195); 
  
  // not tail rec
  def doOperation1_(f: (Int, Int) => Int): (Int, Int) => Int = {
  	
  	def subOper(x: Int, y: Int): Int = if (y == x) 1 else f (y, subOper(x, y - 1))
  	
  	subOper
  	
  };System.out.println("""doOperation1_ : (f: (Int, Int) => Int)(Int, Int) => Int""");$skip(37); 
  
  def sumInts = doOperation1(sum);System.out.println("""sumInts: => (Int, Int) => Int""");$skip(34); 
  def mulInts = doOperation1(mul);System.out.println("""mulInts: => (Int, Int) => Int""");$skip(39); 
  
  def sumInts_ = doOperation1_(sum);System.out.println("""sumInts_ : => (Int, Int) => Int""");$skip(36); 
  def mulInts_ = doOperation1_(mul);System.out.println("""mulInts_ : => (Int, Int) => Int""");$skip(20); val res$4 = 
  
  sumInts(1, 10);System.out.println("""res4: Int = """ + $show(res$4));$skip(16); val res$5 = 
  mulInts(1, 5);System.out.println("""res5: Int = """ + $show(res$5));$skip(251); 
  
  /* here sumInts and mulInts are the middle man, lets try avoiding this middle man and directly call doOperation now */
  
  def doOperation2(f: (Int, Int) => Int)(x: Int, y: Int): Int = {
  	if (y < x) 1 else f (y, doOperation2(f)(x, y - 1))
  };System.out.println("""doOperation2: (f: (Int, Int) => Int)(x: Int, y: Int)Int""");$skip(30); val res$6 = 
  
  doOperation2(sum)(1, 10);System.out.println("""res6: Int = """ + $show(res$6));$skip(26); val res$7 = 
  doOperation2(mul)(1, 5);System.out.println("""res7: Int = """ + $show(res$7))}
  
  /******** CURRYING END ********/
  
}
