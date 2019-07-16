package test.arunava.scala.week6.map

import scala.io.Source

/**
 * Given a number (mobile number) return the list of possible sentences
 * sentences - list of words which [maps to] / [falls under] the telephone character mapping pattern
 * For example, for the number 7225247386, 'Scala is fun' is a possible sentence (words matching as per the telephone mapping pattern)
 */
object Mnemonics {
  val dictionary = Source.fromFile("mnemonics.txt")
  val wordList = dictionary.getLines().toList filter (_ forall (_.isLetter))
  val phoneCharacterMap = Map('1' -> " .", '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL", '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")
  lazy val characterNumberMap = phoneCharacterMap flatMap { case (k, v) => v map (_ -> k) }

  def wordToNumber(word: String): String = word.toUpperCase() map characterNumberMap // same as word.toUpperCase() map (characterNumberMap(_)), here _ is inferred
  val numberToWordList: Map[String, List[String]] = (wordList groupBy wordToNumber) withDefaultValue List() // short-hand of wordList groupBy (wordToNumber(_))
  def translate(number: String): Set[List[String]] = {
    if (number.isEmpty()) Set(List())
    else {
      (1 to number.length flatMap (i => (numberToWordList(number take i) flatMap (word => translate(number drop i) map (word :: _))))) toSet
    }
  }
  
  def translateTailRec(number: String): Set[List[String]] = {
    def translateNumber(num: String, curr: List[String], rest: Set[List[String]]): Set[List[String]] = num match {
      case null => rest + curr
      case n => Set()
    }
    
    translateNumber(number, Nil, Set())
  }

  def encode(number: String): Set[List[String]] = {
    if (number.isEmpty()) Set(List())
    else {
      for {
        i <- 1 to number.length
        word <- numberToWordList(number take i)
        rest <- encode(number drop i)
      } yield word :: rest
    }.toSet
  }

  def solveWithMap(number: String) = {
    val startTime = System.currentTimeMillis()
    val solution = translate(number) filterNot (_.isEmpty)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    println("With Map: " + solution.size + " -> " + solution)
  }
  
  def solveWithFor(number: String) = {
    val startTime = System.currentTimeMillis()
    val solution = encode(number)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    println("With For: " + solution.size + " -> " + solution)
  }
  
  def main(args: Array[String]): Unit = {
    val number = "7225247386"
    //val number = "9679631807"
    solveWithMap(number)
    solveWithFor(number)
  }
}