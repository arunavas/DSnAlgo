package hackerrank

object SubArray {
  def maxLength(a: Array[Int], k: Int): Int = {
    List.range(0, a.length + 1) flatMap ( i =>
      List.range(0, i) map ( j =>
        {
          a.slice(j, i)
        }
      )
    ) filter (_.sum <= k) map(_.length) max
  }

  def main(args: Array[String]): Unit = {
    println(maxLength(Array(1, 2, 3, 4, 5), 10))
  }
  
  def x[A](list: List[A]) = list match {
    case y: List[_] => Nil
    case z => z :: list
  }
}