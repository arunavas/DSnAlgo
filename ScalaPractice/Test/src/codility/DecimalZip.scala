package codility

object DecimalZip {
  def main(args: Array[String]): Unit = {
    println(solution(123456, 10))
    println(solution(10, 123456))
    println(solution(123, 321456))
    println(solution(10, -321))
  }
  
  def abs(n: Int): Int = if (n < 0) -n else n

  def solution(A: Int, B: Int): Int = {
    val a = abs(A).toString()
    val b = abs(B).toString()
    val c = (a zip b).flatMap { x => x._1 + "" + x._2 }.mkString + (if (a.length() > b.length()) a.substring(b.length()) else b.substring(a.length()))
    if (c.length() > 9) -1
    else {
      val i = Integer.parseInt(c)
      if (i > 100000000) -1
      else i
    }
  }
}