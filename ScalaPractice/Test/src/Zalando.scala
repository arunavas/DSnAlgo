import scala.annotation.tailrec

object Zalando {
  def main(args: Array[String]): Unit = {
    println("Hello")
    //println(solution1(Array[Int](40, 40, 100, 80, 20), Array[Int](3, 3, 2, 2, 3), 3, 5, 200))
    //println(solution1(Array[Int](60, 80, 40), Array[Int](2, 3, 5), 5, 2, 200))
    println(solution(4, "1B 2C,2D 4D", "2B 2D 3D 4D 4A"))
    println(solution(3, "1A 1B,2C 2C", "1B"))
    println(solution(12, "1A 2A,12A 12A", "12A"))
  }

  def solution(n: Int, s: String, t: String): String = {
    // write your code in Scala 2.12
    val hits = t.split(" ").toList.distinct
    val ships = s.split(",").toList.map { e =>
      val arr = e.split(" ")
      val l = arr(0)
      val r = arr(1)
      List.range(l.substring(0, l.length() - 1).toInt, r.substring(0, l.length() - 1).toInt + 1).flatMap(i => List.range(l.charAt(l.length() - 1), (r.charAt(l.length() - 1) + 1).toChar).map(j => s"$i$j"))
    }
    
    val res = ships.map(cells => {
      val count = cells.count(hits.contains);
      if (count == cells.size) (1, 0) else if (count > 0) (0, 1) else (0, 0)
    }).foldLeft((0, 0)) { case ((sumA, sumB), (a, b)) => (sumA + a, sumB + b) }
    s"${res._1},${res._2}"
  }

  def solution1(a: Array[Int], b: Array[Int], m: Int, x: Int, y: Int): Int = {
    // write your code in Scala 2.12
    @tailrec
    def takePeople(a: Array[Int], b: Array[Int], floors: Set[Int], people: Int, weight: Int): (Array[Int], Array[Int], Set[Int]) = a match {
      case Array() => (Array(), Array(), floors)
      case _ => {
        if ((people + 1) <= x && (weight + a.head) <= y) takePeople(a.tail, b.tail, floors + b.head, people + 1, weight + a.head)
        else (a, b, floors)
      }
    }
    @tailrec
    def aux(a: Array[Int], b: Array[Int], stops: Int): Int = a match {
      case Array() => stops
      case _ => {
        val res = takePeople(a, b, Set(), 0, 0)
        aux(res._1, res._2, stops + res._3.size + 1)
      }
    }
    aux(a, b, 0)
  }
}