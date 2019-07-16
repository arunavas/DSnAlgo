package test.arunava.scala.week7.streams

import scala.annotation.tailrec

object WaterPouringProblem {
  def main(args: Array[String]): Unit = {
    printSolution(2, (Glass(3), Glass(7)))
    /*printSolution(6, (Glass(4), Glass(9)))
    printSolution(5, (Glass(4), Glass(9)))*/
  }
  
  def printSolution(target: Int, glasses: (Glass, Glass)) = {
    val solution = solve(target, glasses)
    println(glasses + ": " + solution._1 + " -> " + solution._2)
  }

  def solve(target: Int, glasses: (Glass, Glass)): (Int, List[(Glass, Glass)]) = {
    @tailrec
    def doSolve(queue: Stream[Stream[(Glass, Glass)]]): List[(Glass, Glass)] = {
      println(queue.toList)
      /*Thread.sleep(500)*/
      queue match {
        case Stream()      => Nil
        case null #:: tail => doSolve(tail)
        case head #:: tail => head match {
          case Stream()      => doSolve(tail)
          case null #:: t => doSolve(tail)
          case h #:: t => if (h._1.quantity == target || h._2.quantity == target) head.toList else {
            val nextSteps = calculateSteps(head)
            doSolve((nextSteps map (_ #:: head) foldLeft(tail))((l, r) => r #:: l))
          }
        }
      }
    }

    val x = doSolve(calculateSteps(Stream(glasses)) map (Stream(_, glasses)))
    println("x: " + x)
    (x.size - 1, x)
  }

  def calculateSteps(glasses: Stream[(Glass, Glass)]): Stream[(Glass, Glass)] = {
    val head = glasses.head
    val g1 = if (head._1.capacity < head._2.capacity) head._1 else head._2
    val g2 = if (head._1.capacity > head._2.capacity) head._1 else head._2

    if (g1.isFull && g2.isFull) Stream()
    else if (g1.isEmpty && g2.isEmpty) {
      println((g1.fill(g1.capacity), g2), (g1, g2.fill(g2.capacity)))
      Stream((g1.fill(g1.capacity), g2), (g1, g2.fill(g2.capacity))) filterNot (glasses.contains(_))
    }
    else {
      if (g1.isEmpty && !g2.isFull) Stream((g1.fill(g1.capacity), g2)) filterNot (glasses.contains(_))
      else if (g2.isEmpty && !g1.isFull) Stream((g1, g2.fill(g2.capacity))) filterNot (glasses.contains(_))
      else Stream((g1.fill(g2.quantity), g2.empty(g1.available)), (g1.empty(g2.available), g2.fill(g1.quantity))) filterNot (glasses.contains(_))
    }
  }
  
  implicit def x: Int = 0
  
  /*def solve(target: Int, glasses: (Glass, Glass)): Int = {
    @tailrec
    def doSolve(steps: Int, queue: Stream[(Glass, Glass)], generated: List[(Glass, Glass)]): Int = {
      println(steps + " -> " + queue.toList)
      queue match {
        case Stream()      => -1
        case null #:: tail => doSolve(steps + 1, tail, generated)
        case head #:: tail => if (target == head._1.quantity || target == head._2.quantity) steps else {
          val nextSteps = calculateSteps(head, generated)
          doSolve(steps, (tail foldRight (nextSteps))(_ #:: _), (generated foldRight (nextSteps.toList))(_ :: _))
        }
      }
    }

    doSolve(0, calculateSteps(glasses, Nil), Nil)
  }

  def calculateSteps(glasses: (Glass, Glass), generated: List[(Glass, Glass)]): Stream[(Glass, Glass)] = {
    val steps: List[(Glass, Glass)] = Nil
    val g1 = if (glasses._1.capacity < glasses._2.capacity) glasses._1 else glasses._2
    val g2 = if (glasses._1.capacity > glasses._2.capacity) glasses._1 else glasses._2

    if (g1.isFull && g2.isFull) Stream()
    else if (g1.isEmpty && g2.isEmpty)
      Stream((g1.fill(g1.capacity), g2), (g1, g2.fill(g2.capacity)), null) filterNot (generated.contains(_))
    else {
      if (g1.isEmpty && !g2.isFull) Stream((g1.fill(g1.capacity), g2), null)
      else if (g2.isEmpty && !g1.isFull) Stream((g1, g2.fill(g2.capacity)), null)
      else Stream((g1.fill(g2.quantity), g2.empty(g1.available)), (g1.empty(g2.available), g2.fill(g1.quantity)), null) filterNot (generated.contains(_))
    }
  }*/

}