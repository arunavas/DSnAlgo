package test.arunava.scala

object ListMerger extends App {
  val list = List.range(1, 10)
  println(list partition (_ == 5))
  println(list span (_ != 10))
  val otherLists = list span (_ == 5)
  val newList = otherLists._1 ::: (15 :: otherLists._2.tail)
  println(newList)
}