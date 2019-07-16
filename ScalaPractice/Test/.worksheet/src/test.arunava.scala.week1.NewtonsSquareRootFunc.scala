package test.arunava.scala.week1

object NewtonsSquareRootFunc {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(108); 
  println("Welcome to the Scala worksheet");$skip(47); 
  
  def abs(v: Double) = if(v >= 0) v else -v;System.out.println("""abs: (v: Double)Double""");$skip(32); 
  def square(v: Double) = v * v;System.out.println("""square: (v: Double)Double""");$skip(272); 
  
  def findSquare(x: Int): Int = {
  	def iter(guess: Double): Int = {
	  	
	  	def isGoodEnough = abs(square(guess) - x) < 0.001
	  	def improveGuess = (guess + x/guess) / 2
	  	
  		if(isGoodEnough) guess.intValue()
  		else iter(improveGuess)
  	}
  	
  	iter(1)
  };System.out.println("""findSquare: (x: Int)Int""");$skip(26); 
  
  val x = square(9999);System.out.println("""x  : Double = """ + $show(x ));$skip(27); val res$0 = 
  findSquare(x.intValue());System.out.println("""res0: Int = """ + $show(res$0));$skip(32); 
  
  
  
  def loop: Int = loop;System.out.println("""loop: => Int""");$skip(42); 
  
  def cbv(x: Int, y: Int): Int = x + x;System.out.println("""cbv: (x: Int, y: Int)Int""");$skip(22); val res$1 = 
  cbv(10 + 2, 10 + 2);System.out.println("""res1: Int = """ + $show(res$1));$skip(89); 
  
  // cbv(12, 12)
  // 10 + 10
  // 20
  
  def cbn(x: => Int, y: => Int): Int = x + x;System.out.println("""cbn: (x: => Int, y: => Int)Int""");$skip(22); val res$2 = 
  cbn(10 + 2, 10 + 2);System.out.println("""res2: Int = """ + $show(res$2));$skip(125); 
  
  
  //cbn(x, y)
  //x + y
  //(10 + 2) + y
  //(10 + 2) + (10 + 2)
  //12 + 12
  //24
  
  def cbn1(x:Int, y: Int) = x+x;System.out.println("""cbn1: (x: Int, y: Int)Int""");$skip(16); val res$3 = 
  cbn(10, loop);System.out.println("""res3: Int = """ + $show(res$3));$skip(38); 
  
  def cbv1(x: Int, y: Int) = x + x;System.out.println("""cbv1: (x: Int, y: Int)Int""");$skip(15); val res$4 = 
  cbv(10, 10 );System.out.println("""res4: Int = """ + $show(res$4))}
  
  //cbn1(loop, 10)
}
