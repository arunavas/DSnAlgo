import scala.annotation.tailrec


object Test extends App {
  
  import Kreditech.defaultInt
  
  def fact(num: Int): Int = if (num == 1) 1 else num * fact(num - 1)
  
  @tailrec
  def factorial(num: Int)(implicit acc: Int): Int = if (num == 1) acc else factorial(num - 1)(acc * num)
  
  def goodFactorial(num: Int): Int = {
    @tailrec
    def aux(num: Int, acc: Int): Int = if (num == 1) acc else aux(num - 1, acc * num)
    
    aux(num, 1)
  }
  
  println(fact(5))
  println(factorial(5))
  println(goodFactorial(5))
  
}