package test.arunava.scala.week4

object IntegerStackTest {
  def main(args: Array[String]): Unit = {
    val iStack = new EmptyIntStack().push(10).push(30).push(20).push(50).push(40)
    println(iStack.isEmpty)
    println(iStack.top)
    println(iStack.contains(30))
    println(iStack.contains(100))
    iStack.printAll
  }
}