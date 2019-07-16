package test.arunava.scala

import scala.util.Random

object Generations {
  
  def main(args: Array[String]): Unit = {
    val n = 8//io.StdIn.readInt()
    val g = 9//io.StdIn.readInt()
    val c = 3//io.StdIn.readInt()
    println(s"Calculating Generation for board size $n and generation $g")
    val board = initBoardRandomManner(n);
    println(s"Initialized Board:")
    printBoard(board)
    changeBoardState(board, g, c)
  }
  
  def initBoardRandomManner(n: Int): Array[Array[Int]] = (1 to n).toArray.map(_ => (1 to n).toArray.map(_ => Random.nextInt(10) % 2))
  def changeBoardState(board: Array[Array[Int]], gen: Int, c: Int): Array[Array[Int]] = {
    if (gen == 0) board
    else {
      val currentState = board.map { x => x.clone() }
      val indexes = List.range(0, board.length).flatMap(i => List.range(0, board.length).map(j => (i, j)))
      indexes.foreach(i => {
        val adjacents = calculateAdjacentStates(currentState, i._2, i._1)
        val live = adjacents.count(x => x == 1)
        val dead = adjacents.count(x => x == 0)
        if (live >= c) board(i._1)(i._2) = 1
        else if (dead >= c) board(i._1)(i._2) = 0
      })
      println(s"Board @ !$gen")
      printBoard(board)
      changeBoardState(board, gen - 1, c)
    }
  }
  
  def calculateAdjacentStates(board: Array[Array[Int]], x: Int, y: Int): List[Int] = {
    List((x - 1, y - 1), (x, y - 1), (x + 1, y - 1), (x + 1, y),
                         (x + 1, y + 1), (x, y + 1), (x - 1, y + 1), (x - 1, y))
                         .filter(e => e._1 >= 0 && e._2 >= 0 && e._1 < board.length && e._2 < board.length)
                         .map(e => board(e._2)(e._1))
  }
  
  def printBoard(board: Array[Array[Int]]) = {
    println("====================")
    board.foreach { x => println(x.mkString(" ")) }
    println("====================")
  }
  
}