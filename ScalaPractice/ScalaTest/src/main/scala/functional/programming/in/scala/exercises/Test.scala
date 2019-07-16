package functional.programming.in.scala.exercises

/**
  * Created by arunavas on 9/8/17.
  */
object Test {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5)

    println(sum(list))
    println(doOperation(list, 0)(_ + _))
    println(doOperation(list, 1)(_ * _))
  }

  def doOperation(list: List[Int], baseValue: Int)(f: (Int, Int) => Int): Int = list match {
    case Nil => baseValue
    case h :: t => f(h, doOperation(t, baseValue)(f))
  }

  def sum(list: List[Int]): Int = list match {
    case Nil => 0
    case h :: t => h + sum(t)
  }

  def mul(list: List[Int]): Int = list match {
    case Nil => 1
    case h :: t => h * sum(t)
  }

}
