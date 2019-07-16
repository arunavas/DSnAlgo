package functional.programming.in.scala.exercises

/**
  * Created by arunavas on 5/9/17.
  */
object Chapter10 {

  trait Monoid[A] {
    def op(a: A, b: A): A
    def zero: A
  }

  class StringMonoid extends Monoid[String] {
    override def op(a: String, b: String): String = a + b

    override def zero: String = ""
  }

  class ListMonoid[A] extends Monoid[List[A]] {
    override def op(a: List[A], b: List[A]): List[A] = b.foldLeft(a)((x, y) => y :: x)

    override def zero: List[A] = Nil
  }

  class IntAddition extends Monoid[Int] {
    override def op(a: Int, b: Int): Int = a + b

    override def zero: Int = 0
  }

  class IntMultiplication extends Monoid[Int] {
    override def op(a: Int, b: Int): Int = a * b

    override def zero: Int = 1
  }

  class BooleanOr extends Monoid[Boolean] {
    override def op(a: Boolean, b: Boolean): Boolean = a || b

    override def zero: Boolean = false
  }

  class BooleanAnd extends Monoid[Boolean] {
    override def op(a: Boolean, b: Boolean): Boolean = a && b

    override def zero: Boolean = true
  }

  class OptionMonoid[A] extends Monoid[Option[A]] {
    override def op(a: Option[A], b: Option[A]): Option[A] = a orElse b

    override def zero: Option[A] = None
  }

  class EndoMonoid[A] extends Monoid[A => A] {
    override def op(a: A => A, b: A => A): (A) => A = a andThen b

    override def zero: A => A = A => A
  }

  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    override def op(a: A, b: A): A = m.op(b, a)

    override def zero: A = m.zero
  }

  def concatenate[A](l: List[A], m: Monoid[A]): A = l.foldLeft(m.zero)(m.op)

  def foldMap[A, B](l: List[A], m: Monoid[B])(f: A => B): B = l.foldLeft(m.zero)((x, y) => m.op(x, f(y)))

  def foldRight[A, B](l: List[A])(z: B)(f: (A, B) => B): B = foldMap(l, new EndoMonoid[B])(f.curried)(z)

  def foldLeft[A, B](l: List[A])(z: B)(f: (B, A) => B): B = foldMap(l, dual(new EndoMonoid[B]))(a => b => f(b, a))(z)

  def foldMapV[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = if (v.length == 1) m.op(f(v(0)), m.zero) else {
    val (l, r) = v.splitAt(v.length / 2)
    m.op(foldMapV(l, m)(f), foldMapV(r, m)(f))
  }

  def productMonoid[A, B](a: Monoid[A], b: Monoid[B]): Monoid[(A, B)] = new Monoid[(A, B)] {
    override def op(x: (A, B), y: (A, B)): (A, B) = (a.op(x._1, y._1), b.op(x._2, y._2))

    override def zero: (A, B) = (a.zero, b.zero)
  }

  def main(args: Array[String]): Unit = {
    val sm = new StringMonoid
    println(s"String Monad: ${sm.op("Hello", sm.op(" ", "World")) == sm.op(sm.op("Hello", " "), "World")}")
    println(List("A", "B", "C").foldLeft(sm.zero)(sm.op))
    println(List("A", "B", "C").foldRight(sm.zero)(sm.op))
    println(concatenate(List("A", "B", "C"), sm))
    println(foldMap(List(1, 2, 3), sm)(_.toString))
    println(foldMapV(IndexedSeq(1, 2, 3), sm)(_.toString))
  }

}
