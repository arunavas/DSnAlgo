package test.arunava.scala.week6.map

object SentenceToNumber {
  val numberDirectory = Map(1 -> " .", 2 -> "ABC", 3 -> "DEF", 4 -> "GHI", 5 -> "JKL", 6 -> "MNO", 7 -> "PQRS", 8 -> "TUV", 9 -> "WXYZ")
  lazy val charDirectory = numberDirectory flatMap { case (k, v) => v map (_ -> k)}
  
  def getNumberRepresentation(str: String): String = {
    str map (c => charDirectory(c.toUpper).toString()) mkString
  }
  
  def main(args: Array[String]): Unit = {
    println(getNumberRepresentation("Scala is a fun lang."))
  }
}