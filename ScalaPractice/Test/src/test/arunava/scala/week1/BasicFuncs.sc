package test.arunava.scala.week1

object BasicFuncs {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def square(x: Int) = x * x                      //> square: (x: Int)Int
  square(4)                                       //> res0: Int = 16
  
  def sum(x: Int, y: Int) = x + y                 //> sum: (x: Int, y: Int)Int
  sum(4, 3)                                       //> res1: Int = 7
  
  square(sum(4, 4))                               //> res2: Int = 64
  sum(square(4), square(4))                       //> res3: Int = 32
  
  def sumation(x: Int): Int = {
  	if(x == 1) x
  	else x + sumation(x - 1);
  }                                               //> sumation: (x: Int)Int
  sumation(10)                                    //> res4: Int = 55
  
  def product(x: Int): Int = {
  	if(x == 1) x
  	else x * product(x - 1)
  }                                               //> product: (x: Int)Int
  product(5)                                      //> res5: Int = 120
  
  def doOperation1(x: Int, f: (Int, Int) => Int): Int = {
  	if(x == 1) x
  	else f (x, doOperation1(x - 1, f))
  }                                               //> doOperation1: (x: Int, f: (Int, Int) => Int)Int
  doOperation1(10, sum)                           //> res6: Int = 55
  doOperation1(5, (x: Int, y: Int) => x * y)      //> res7: Int = 120
  
  def doOperation2(x: Int, s: Int => Int, f: (Int, Int) => Int): Int = {
  	if(x == 1) x
  	else f (s (x), doOperation2(x - 1, s, f))
  }                                               //> doOperation2: (x: Int, s: Int => Int, f: (Int, Int) => Int)Int
  doOperation2(10, x => x, sum)                   //> res8: Int = 55
  doOperation2(10, square, sum)                   //> res9: Int = 385
  doOperation2(5, x => x, (x: Int, y: Int) => x * y)
                                                  //> res10: Int = 120
  doOperation2(10, (x: Int) => x, (x, y) => x + y)//> res11: Int = 55
  doOperation2(10, x => x, (x, y) => x + y)       //> res12: Int = 55
}