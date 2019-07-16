package test.hackerearth.scala

object MatchGame {
  def result(a: List[Int], b: List[Int]): (Int, Int) = 
    a zip b map { case (_a: Int, _b: Int) => if (_a > _b) (1, 0) else if (_a < _b) (0, 1) else (0, 0) } reduceLeft((x, y) => (x._1 + y._1, x._2 + y._2))
  
  def main(args: Array[String]): Unit = {
    val a = List(0, 1, 2, 4)
    val b = List(0, 2, 1, 3)
    println(result(a, b))
  }
}