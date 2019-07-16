package practice

object SSETest {
  def main(args: Array[String]): Unit = {
    //println(List.range(0, 10).flatMap ( x => List.range(0, 10).map ( y => (x, y) ).filter(z => z._1 != z._2).filter ( z => (z._1 * 10 + z._2 * 3) == (z._2 * 10 + z._1 * 3) ) ))
    println(doSum(10, 20))
    println(curriedSum(x => x)(10, 20))
  }

  def sum(f: Int => Int): (Int, Int) => Int = {
    def aux(a: Int, b: Int): Int = f(a) + f(b)
    aux
  }
  def doSum = sum(x => x)

  def curriedSum(f: Int => Int)(a: Int, b: Int): Int = f(a) + f(b)

}
