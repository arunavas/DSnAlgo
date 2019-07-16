package test.arunava.scala.week4

object IntegerQueueTest {
  def main(args: Array[String]): Unit = {
    val iQueue = new EmptyQueue().push(10).push(30).push(20).push(50).push(40)
    println(iQueue.isEmpty)
    println(iQueue.peek)
    println(iQueue.contains(30))
    println(iQueue.contains(100))
    iQueue.printAll
  }
}