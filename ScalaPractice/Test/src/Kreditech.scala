

object Kreditech {
  
  implicit def defaultInt: Int = 1;
  
  def main(args: Array[String]): Unit = {
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
}