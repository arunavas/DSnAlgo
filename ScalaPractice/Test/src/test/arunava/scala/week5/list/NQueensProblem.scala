package test.arunava.scala.week5.list

import scala.annotation.tailrec

object NQueensProblem {

  val empty = ". "
  val queen = "Y "

  def main(args: Array[String]): Unit = {
    val n = 16
    solveWithMapTailRec(n)
    //solveWithMap(n)
    /*solveWithFor(n)*/
  }

  def solveWithMapTailRec(n: Int) {
    val startTime = System.currentTimeMillis()
    val solution = solveNQueenProblemTailRecAll(n)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    //representVisually(solution)
    println("With Map && TailRec: " + solution.length + " -> " + solution)
    //solution take 1 map (printSolution(_))
  }

  def solveWithMap(n: Int) = {
    val startTime = System.currentTimeMillis()
    val solution = solveNQueenProblemMap(n)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    //representVisually(solution)
    println("With Map: " + solution.size + " -> " + solution)
    //solution take 1 map (printSolution(_))
  }

  def solveWithFor(n: Int) = {
    val startTime = System.currentTimeMillis()
    val solution = solveNQueenProblemFor(n)
    val endTime = System.currentTimeMillis()
    println("Time Taken: " + ((endTime - startTime) / 1000) + " seconds, " + ((endTime - startTime) % 1000))
    //representVisually(solution)
    println("With For: " + solution.size + " -> " + solution)
    solution take 1 map (printSolution(_))
  }

  def solveNQueenProblemTailRec(n: Int) = {
    @tailrec
    def placeQueens(curr: List[Int], rest: List[List[Int]]): List[Int] = {
      /*println(curr + " -> " + rest)*/
      if (curr.length == n) curr
      else getNextSafeIndices(curr) match {
        case Nil => if (rest.isEmpty) Nil else placeQueens(rest.head, rest.tail)
        case x :: xs => placeQueens(x :: curr, (xs map (_ :: curr) foldRight (rest))(_ :: _))
      }
    }
    def getNextSafeIndices(path: List[Int]): List[Int] = {
      path match {
        case Nil     => Nil
        case x :: xs => List.range(0, n) filter (c => !path.contains(c) && isSafe(c, path))
      }
    }

    List.range(0, n) map (c => placeQueens(List(c), Nil)) filterNot (_.isEmpty)
  }
  
  def solveNQueenProblemTailRecAll(n: Int) = {
    @tailrec
    def placeQueens(curr: List[Int], rest: List[List[Int]])(result: List[List[Int]]): List[List[Int]] = {
      //println(curr + " -> " + rest + " => " + result)
      //result withFilter(!_.isEmpty) foreach(println(_))
      /*if(!result.isEmpty) result
      else */(getNextSafeIndices(curr) foldRight (rest))(_ :: _) match {
        case Nil => result
        case x :: xs => {
          if (x.length == n && rest.isEmpty) x :: result
          else if (x.length == n) placeQueens(xs.head, xs.tail)(x :: result)
          else placeQueens(x, xs)(result)
        }
      }
    }
    def getNextSafeIndices(path: List[Int]): List[List[Int]] = {
      path match {
        case Nil     => Nil
        case x :: xs => List.range(0, n) filter (c => !path.contains(c) && isSafe(c, path)) map (_ :: path)
      }
    }

    List.range(0, n) flatMap (c => placeQueens(List(c), Nil)(Nil))
  }

  def solveNQueenProblemMap(n: Int): Set[List[Int]] = {
    def placeQueens(q: Int): Set[List[Int]] = {
      if (q == 0) Set(List())
      else placeQueens(q - 1).flatMap(e => List.range(0, n).filter(c => !e.contains(c) && isSafe(c, e)) map (_ :: e))
    }
    placeQueens(n)
  }

  def solveNQueenProblemFor(n: Int): Set[List[Int]] = {
    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else for (q <- placeQueens(k - 1); c <- List.range(0, n) if (!q.contains(c) && isSafe(c, q))) yield c :: q
    }
    placeQueens(n)
  }

  def isSafe(col: Int, queens: List[Int]): Boolean = {
    val row = queens.length
    val queensWithAxis = row - 1 to 0 by -1 zip queens
    queensWithAxis forall {
      case (r, c) => Math.abs(col - c) != row - r
    }
  }

  def representVisually(solution: Set[List[Int]]) = {
    solution.foreach {
      list =>
        list.foreach {
          i => println(Vector.fill(list.length)(empty).updated(i, queen).mkString)
        }
        println()
    }
  }

  def printSolution(solution: List[Int]) = {
    val line = for {
      c <- solution
    } yield Vector.fill(solution.length)(empty).updated(c, queen).mkString
    println(line mkString ("\n"))
    println()
  }

}