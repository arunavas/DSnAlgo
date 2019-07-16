package test.arunava.scala.week4

object IntegerTreeTest {
  
  def main(args: Array[String]): Unit = {
    val iTree = EmptyIntTree.insert(20).insert(10).insert(5).insert(11).insert(10).insert(25)
    println(iTree contains(10))
    println(iTree.contains(5))
    println(iTree contains(100))
    println(iTree.contains(11))
    println(iTree contains(20))
    iTree.traverse
  }
  
}