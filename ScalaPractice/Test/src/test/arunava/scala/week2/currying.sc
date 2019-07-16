package test.arunava.scala.week2

object currying {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  def doOperations(f: (Int, Int) => Int, x: Int, y: Int): Int = f(x, y)
                                                  //> doOperations: (f: (Int, Int) => Int, x: Int, y: Int)Int

  def sum(x: Int, y: Int) = doOperations((a, b) => a + b, x, y)
                                                  //> sum: (x: Int, y: Int)Int
  def mul(x: Int, y: Int) = doOperations((a, b) => a * b, x, y)
                                                  //> mul: (x: Int, y: Int)Int

  sum(10, 20)                                     //> res0: Int = 30
  mul(10, 20)                                     //> res1: Int = 200
  
  def doOps(f: (Int, Int) => Int, x: Int, y: Int) = {
  	def iter(acc: Int, y: Int): Int = if(y < x) acc else iter( f(acc, y), y - 1)
  	
  	iter(y, y - 1)
  }                                               //> doOps: (f: (Int, Int) => Int, x: Int, y: Int)Int
  
  def sumVals(x: Int, y: Int): Int = doOps(sum, x, y)
                                                  //> sumVals: (x: Int, y: Int)Int
  def mulVals(x: Int, y: Int): Int = doOps(mul, x, y)
                                                  //> mulVals: (x: Int, y: Int)Int
  
  sumVals(1, 10)                                  //> res2: Int = 55
  mulVals(1, 5)                                   //> res3: Int = 120
  
  /* Here in the above example, the arguements x & y are being used in sumVals & mulVals only just to pass them to the doOps, lets try eliminating them */
  
  /******** CURRYING START ********/
  // tail rec
  def doOperation1(f: (Int, Int) => Int): (Int, Int) => Int = {
  	
  	def subOper(acc: Int, x: Int, y: Int): Int = if (y == x) acc else subOper(f (acc, y), x, y - 1)
  	
  	subOper(1, _, _)
  	/* OR */
  	//subOper(1, _, _)
  	
  }                                               //> doOperation1: (f: (Int, Int) => Int)(Int, Int) => Int
  
  // not tail rec
  def doOperation1_(f: (Int, Int) => Int): (Int, Int) => Int = {
  	
  	def subOper(x: Int, y: Int): Int = if (y == x) 1 else f (y, subOper(x, y - 1))
  	
  	subOper
  	
  }                                               //> doOperation1_ : (f: (Int, Int) => Int)(Int, Int) => Int
  
  def sumInts = doOperation1(sum)                 //> sumInts: => (Int, Int) => Int
  def mulInts = doOperation1(mul)                 //> mulInts: => (Int, Int) => Int
  
  def sumInts_ = doOperation1_(sum)               //> sumInts_ : => (Int, Int) => Int
  def mulInts_ = doOperation1_(mul)               //> mulInts_ : => (Int, Int) => Int
  
  sumInts(1, 10)                                  //> res4: Int = 55
  mulInts(1, 5)                                   //> res5: Int = 120
  
  /* here sumInts and mulInts are the middle man, lets try avoiding this middle man and directly call doOperation now */
  
  def doOperation2(f: (Int, Int) => Int)(x: Int, y: Int): Int = {
  	if (y < x) 1 else f (y, doOperation2(f)(x, y - 1))
  }                                               //> doOperation2: (f: (Int, Int) => Int)(x: Int, y: Int)Int
  
  doOperation2(sum)(1, 10)                        //> res6: Int = 56
  doOperation2(mul)(1, 5)                         //> res7: Int = 120
  
  /******** CURRYING END ********/
  
}