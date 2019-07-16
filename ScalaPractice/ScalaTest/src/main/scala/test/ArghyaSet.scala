package test

/**
  * Created by arunavas on 14/8/17.
  */
object ArghyaSet {

  def main(args: Array[String]): Unit = {
    /*val x = List.range(1, 21)
    println(x)

    val evenList = x.filter(_ % 2 == 0).sum
    val oddList = x.filter(_ % 2 != 0).sum
    println(evenList)
    println(oddList)*/

    println(solution(12))
  }

  def solution(n: Int): List[(Int, Int)] = {
    val pairs = List.range(1, n + 1) flatMap (x => List.range(12, (0 + x) - 1, -1) map (y => (x, y)))
    println(pairs)
    val matches = pairs filter (x => x._1 * x._2 == n)
    println(matches)
    matches
  }

}
