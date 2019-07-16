package codility

object MaxDiff {
  def main(args: Array[String]): Unit = {
    val A = Array[Int](1, 3, -3)
    println(solution(A))
    println
    val B = Array[Int](4, 3, 2, 5, 1, 1)
    println(solution(B))
  }
  
  def abs(n: Int): Int = if (n < 0) -n else n

  def solution(A: Array[Int]): Int = {
    ((1 until A.length) map (i => {
      val p = A.splitAt(i)
      abs(p._1.max - p._2.max)
    })).max
  }
}