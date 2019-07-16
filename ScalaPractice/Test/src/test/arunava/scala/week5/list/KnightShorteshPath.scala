package test.arunava.scala.week5.list

import scala.annotation.tailrec

object KnightShorteshPath {
  def main(args: Array[String]): Unit = {
    //solution((0, 0))
    solution((2, 2))
    //solution((4, 5))
    //solution((7, 7))
    //solution(107, 107)
  }
  
  def solution(dest: (Int, Int)) = {
    val startTime = System.currentTimeMillis()
    val solution = findKSP(dest)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    println(dest + ": " + solution)
  }

  def findKSP(dest: (Int, Int)): Int = {
    @tailrec
    def getKSP(step: Int, queue: List[(Int, Int)])(implicit generated: Set[(Int, Int)]): Int = queue match {
        case Nil          => -1
        case null :: tail => getKSP(step + 1, tail :+ null)(generated)
        case head :: tail => if (head == dest) step else {
          val nextSteps = generateNextSteps(head)(generated)
          getKSP(step, (tail foldRight (nextSteps))(_ :: _))(generated ++ nextSteps)
        }
    }
    getKSP(0, List((0, 0), null))
  }

  def generateNextSteps(ind: (Int, Int))(visited: Set[(Int, Int)]): List[(Int, Int)] = {
    @tailrec
    def generate(count: Int)(implicit steps: List[(Int, Int)]): List[(Int, Int)] = count match {
      case 0 => generate(count + 1)((ind._1 - 1, ind._2 + 2) :: steps)
      case 1 => generate(count + 1)((ind._1 + 1, ind._2 + 2) :: steps)
      case 2 => generate(count + 1)((ind._1 - 2, ind._2 + 1) :: steps)
      case 3 => generate(count + 1)((ind._1 - 2, ind._2 - 1) :: steps)
      case 4 => generate(count + 1)((ind._1 - 1, ind._2 - 2) :: steps)
      case 5 => generate(count + 1)((ind._1 + 1, ind._2 - 2) :: steps)
      case 6 => generate(count + 1)((ind._1 + 2, ind._2 - 1) :: steps)
      case 7 => generate(count + 1)((ind._1 + 2, ind._2 + 1) :: steps)
      case 8 => (steps filter (isValid(_)))
    }
    def isValid(ind: (Int, Int)): Boolean = ind._1 >= 0 && ind._2 >= 0 && !visited.contains(ind)
    generate(0)
  }

  implicit def s: List[(Int, Int)] = Nil
  implicit def g: Set[(Int, Int)] = Set()
}