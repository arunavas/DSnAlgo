package hackerrank

import scala.annotation.tailrec

object Kreditech {

  def main(args: Array[String]): Unit = {
    //println(totalCellsVisited(7, 4))
    println(getSortedList(Array("Philip II", "Philippe I", "King VII", "King V")).toList)
  }

  def getSortedList(names: Array[String]): Array[String] = {
    val sortedNames = names.map(n => {
      val x = n.split(" ")
      s"${x(0)} ${fromRoman(x(1))}"
    }).toList.sorted
    sortedNames.map(n => {
      val x = n.split(" ")
      s"${x(0)} ${toRoman(x(1).toInt)}"
    }).toArray
  }

  def toRoman(number: Int): String = {
    def toRomanNumerals(number: Int, digits: List[(String, Int)]): String = digits match {
      case Nil    => ""
      case h :: t => h._1 * (number / h._2) + toRomanNumerals(number % h._2, t)
    }
    toRomanNumerals(number, List(("M", 1000), ("CM", 900), ("D", 500), ("CD", 400), ("C", 100), ("XC", 90),
      ("L", 50), ("XL", 40), ("X", 10), ("IX", 9), ("V", 5), ("IV", 4), ("I", 1)))
  }

  def fromRoman(r: String): Int = {
    val arabicNumerals = List("CM" -> 900, "M" -> 1000, "CD" -> 400, "D" -> 500, "XC" -> 90, "C" -> 100,
      "XL" -> 40, "L" -> 50, "IX" -> 9, "X" -> 10, "IV" -> 4, "V" -> 5, "I" -> 1)

    var s = r
    arabicNumerals.foldLeft(0) { (n, t) =>
      {
        val l = s.length; s = s.replaceAll(t._1, ""); val c = (l - s.length) / t._1.length // Get the frequency
        n + (c * t._2) // Add the arabic numerals up  
      }
    }
  }

  def totalCellsVisited(n: Int, m: Int): Int = {
    @tailrec
    def moveNext(x: Int, y: Int, visited: List[(Int, Int)], direction: Int, count: Int): Option[((Int, Int), Int)] = {
      if (count == 5) None
      else {
        if (direction == 0) {
          val next = (x + 1, y)
          if (isWithinGrid(next, (m, n)) && !visited.contains(next))
            Some(next, direction + 1)
          else moveNext(x, y, visited, direction + 1, count + 1)
        } else if (direction == 1) {
          val next = (x, y + 1)
          if (isWithinGrid(next, (m, n)) && !visited.contains(next))
            Some(next, direction + 1)
          else moveNext(x, y, visited, direction + 1, count + 1)
        } else if (direction == 2) {
          val next = (x - 1, y)
          if (isWithinGrid(next, (m, n)) && !visited.contains(next))
            Some(next, direction + 1)
          else moveNext(x, y, visited, direction + 1, count + 1)
        } else {
          val next = (x, y - 1)
          if (isWithinGrid(next, (m, n)) && !visited.contains(next))
            Some(next, 0)
          else moveNext(x, y, visited, 0, count + 1)
        }
      }
    }
    @tailrec
    def calculate(visited: List[(Int, Int)], current: (Int, Int), direction: Int): Int = {
      val next = moveNext(current._1, current._2, visited, direction, 1)
      next match {
        case None              => visited.size
        case Some(((x, y), d)) => calculate((x, y) :: visited, (x, y), d)
      }
    }
    calculate(List((0, 0)), (0, 0), 0)
  }

  def isWithinGrid(c: (Int, Int), g: (Int, Int)): Boolean = c._1 >= 0 && c._1 < g._1 && c._2 >= 0 && c._2 < g._2

  def printGrid(grid: Array[Array[Int]]) = {
    println("====================")
    grid.foreach { x => println(x.mkString(" ")) }
    println("====================")
  }

}