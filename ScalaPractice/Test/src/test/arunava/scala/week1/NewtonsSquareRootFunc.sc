package test.arunava.scala.week1

object NewtonsSquareRootFunc {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def abs(v: Double) = if(v >= 0) v else -v       //> abs: (v: Double)Double
  def square(v: Double) = v * v                   //> square: (v: Double)Double
  
  def findSquare(x: Int): Int = {
  	def iter(guess: Double): Int = {
	  	
	  	def isGoodEnough = abs(square(guess) - x) < 0.001
	  	def improveGuess = (guess + x/guess) / 2
	  	
  		if(isGoodEnough) guess.intValue()
  		else iter(improveGuess)
  	}
  	
  	iter(1)
  }                                               //> findSquare: (x: Int)Int
  
  val x = square(9999)                            //> x  : Double = 9.9980001E7
  findSquare(x.intValue())                        //> res0: Int = 9999
  
  
  
  def loop: Int = loop                            //> loop: => Int
  
  def cbv(x: Int, y: Int): Int = x + x            //> cbv: (x: Int, y: Int)Int
  cbv(10 + 2, 10 + 2)                             //> res1: Int = 24
  
  // cbv(12, 12)
  // 10 + 10
  // 20
  
  def cbn(x: => Int, y: => Int): Int = x + x      //> cbn: (x: => Int, y: => Int)Int
  cbn(10 + 2, 10 + 2)                             //> res2: Int = 24
  
  
  //cbn(x, y)
  //x + y
  //(10 + 2) + y
  //(10 + 2) + (10 + 2)
  //12 + 12
  //24
  
  def cbn1(x:Int, y: Int) = x+x                   //> cbn1: (x: Int, y: Int)Int
  cbn(10, loop)                                   //> res3: Int = 20
  
  def cbv1(x: Int, y: Int) = x + x                //> cbv1: (x: Int, y: Int)Int
  cbv(10, 10 )                                    //> res4: Int = 20
  
  //cbn1(loop, 10)
}