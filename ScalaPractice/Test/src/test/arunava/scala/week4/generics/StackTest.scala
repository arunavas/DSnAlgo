package test.arunava.scala.week4.generics

object StackTest {
  def main(args: Array[String]): Unit = {
    println("------------Int------------")
    val iStack = new EmptyStack[Int]().push(10).push(30).push(20).push(50).push(40)
    println(iStack.isEmpty)
    println(iStack.top)
    println(iStack.contains(30))
    println(iStack.contains(100))
    iStack.printAll
    
    println("\n------------String------------")
    val sStack = new EmptyStack[String]().push("A").push("B").push("C").push("D").push("E")
    sStack.printAll
  }
}