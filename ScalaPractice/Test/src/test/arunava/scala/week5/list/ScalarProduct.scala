package test.arunava.scala.week5.list

object ScalarProduct {
  def main(args: Array[String]): Unit = {
    val l1 = (2 until 21 by 2).toList
    val l2 = (1 to 20 by 2).toList
    println(l1)
    println(l2)
    println(scalarProduct1(l1, l2))
    println(scalarProduct2(l1, l2))
  }
  
  def scalarProduct1(l1: List[Int], l2: List[Int]) = ((l1 zip l2) map (p => p._1 * p._2) foldLeft (0))(_ + _)
  def scalarProduct2(l1: List[Int], l2: List[Int]) = (for ((x, y) <- l1 zip l2) yield (x * y)).sum
}