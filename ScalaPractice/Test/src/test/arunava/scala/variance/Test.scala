package test.arunava.scala.variance

object Test {
  def main(args: Array[String]): Unit = {
    val aB = EmptyBasket.add(Apple(5)).add(Apple(10))
    aB.printAll
    println()
    val fB = aB.add(Banana(7))
    fB.printAll
  }
}