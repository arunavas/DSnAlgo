package test.arunava.scala.week5.list

object ListMap {
  def main(args: Array[String]): Unit = {
    val list = List.range(1, 11)
    println(list map (x => x * x))
    println(mapFunR(list, (x: Int) => x * x))
    println(mapFunL(list)((x: Int) => x + x))
    
    println(lengthFunL(list))
    println(lengthFunR(list))
  }
  
  def mapFunR[A, B](list: List[A], f: A => B): List[B] = {
    (list foldRight (Nil: List[B]))((x, y) => f(x) :: y)
  }
  
  def mapFunL[A, B](list: List[A])(f: A => B): List[B] = (list foldLeft (Nil: List[B]))((x, y) => x :+ f(y))
  
  def lengthFunL[A](list: List[A]): Int = (list foldLeft 0)((x, y) => x + 1)
  def lengthFunR[T](list: List[T]): Int = (list foldRight 0)((x, y) => y + 1)
}