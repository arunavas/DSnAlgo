package test.arunava.scala.week4.generics.viewbounded

object TreeSetTest {
  def main(args: Array[String]): Unit = {
    val iTreeSet1 = new EmptyTreeSet[Int]().insert(10)
    val iTreeSet2 = new EmptyTreeSet[Int]().insert(20)
    val uiTreeSet = iTreeSet1 union(iTreeSet2)
    uiTreeSet.printAll
  }
}