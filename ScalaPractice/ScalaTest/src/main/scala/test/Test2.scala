package test

/**
  * Created by arunavas on 26/7/17.
  */
object Test2 {

  def main(args: Array[String]): Unit = {
    println("Test1")

    val list = List.fill(10)(1)
    println(list)
    println(list.reduce((a1, a2) => a1 + a2))
    println(list.reduceLeft((a1, a2) => a1 + a2))
    println(list.reduceRight((a1, a2) => a1 + a2))
  }

}
