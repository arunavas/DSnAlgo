package test.arunava.scala

object HigherOrderFunction {
  def main(args: Array[String]): Unit = {
    println(doOperation(10, 5, getOperation('+')))
    println(doOperation(10, 5, getOperation('*')))
    println(doOperation(10, 15, _ + _))
  }
  
  def doOperation(x: Int, y: Int, f: (Int, Int) => Int): Int = f(x, y)
  def getOperation(oper: Char): (Int, Int) => Int = oper match {
    case '+' => return (x, y) => x + y
    case '-' => (x, y) => x - y
    case '/' => (x, y) => x / y
    case '*' => (x, y) => x * y
  }
  
}