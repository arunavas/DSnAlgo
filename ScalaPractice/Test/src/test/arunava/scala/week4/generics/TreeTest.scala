package test.arunava.scala.week4.generics

object TreeTest {
  def main(args: Array[String]): Unit = {
    val iTree = EmptyTree[OrderedInt]().insert(OrderedInt(10)).insert(OrderedInt(5)).insert(OrderedInt(40)).insert(OrderedInt(50)).insert(OrderedInt(30)).insert(OrderedInt(20))
    println(iTree contains(OrderedInt(10)))
    println(iTree.contains(OrderedInt(5)))
    println(iTree contains(OrderedInt(100)))
    println(iTree.contains(OrderedInt(11)))
    println(iTree contains(OrderedInt(20)))
    iTree.traverse
  }
}

case class OrderedInt(value: Int) extends Ordered[OrderedInt] {
  override def compare(that: OrderedInt) = {
    if (this.value < that.value) -1
    else if (this.value > that.value) 1
    else 0
  }
  
  override def toString() = "" + value
}