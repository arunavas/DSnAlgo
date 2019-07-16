package monads

/**
  * Created by arunavas on 26/3/18.
  */
object MonadTest {

  def double(a: Int): Int = a + a
  def div(a: Int, b: Int): Option[Int] = if (b > 0) Some(a / b) else None

  def main(args: Array[String]): Unit = {
    println(div(2, 1).map(double))
  }
}
