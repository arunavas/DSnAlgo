package test.arunava.scala

object TicTacToe {
  def main(args: Array[String]): Unit = {
    /*val input = io.StdIn.readLine().split("\\.")
    if (input.length < 2) println("Invalid Entry. Entry format: X.Y")
    else {
      val y = input(0).toInt
      val x = input(1).toInt
      printBoard(List.range(0, 3).map(e => List.range(0, 3).map (k => 'X')))
    }*/
    printBoard(List.range(0, 3).map(e => List.range(0, 3).map (k => 'X')))
  }
  
  def printBoard(p: List[List[Char]]): Unit = {
    println(p)
  }
}