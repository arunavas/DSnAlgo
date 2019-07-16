package functional.programming.in.scala.exercises

/**
  * Created by arunavas on 14/8/17.
  */
object Chapter5 {

  def main(args: Array[String]): Unit = {
    val s = Stream(1, 2, 3, 4, 5, 6, 7)
    /*val r = s.toList
    println(s)
    println(r)
    println(s.headOption)
    println(s.take(3).toList)
    println(s.drop(3).toList)*/

    /*println(Stream.ones.take(5).toList == Empty.ones.take(5).toList)
    println(Stream.constant("A").take(5).toList == Empty.constant("A").take(5).toList)
    println(Stream.from(1).takeWhile(_ <= 5).toList == Empty.from(1).takeWhile(_ <= 5).toList)
    println(Stream.fibs.take(7).toList == Empty.fibs.take(7).toList)*/

    /*println(Stream.from(5).map(_ + 1).takeWhileEfficiently(_ <= 5 * 2).toList)
    println((Stream.fibs zipWith s).toList)
    println((s zipAll Stream(1, 2)).toList)
    println((Stream(1, 2) zipAll s).toList)

    println(s startsWith Stream(1, 2))*/

    println(s)

    /*val l = Stream(1, 2, 3, 4)
    println(l.hasSubsequence(Stream(4)))
    println(l.hasSubsequence(Stream(1, 2, 3, 4)))
    println(l.hasSubsequence(Stream(1, 2, 4)))
    println(l.hasSubsequence(Stream(1, 3, 4)))
    println(l.hasSubsequence(Stream(1, 4)))
    println(l.hasSubsequence(Stream(3, 4)))
    println(l.hasSubsequence(Stream(2, 3, 4)))
    println(l.hasSubsequence(Stream(1, 2, 3)))
    println(l.hasSubsequence(Stream(1)))
    println(l.hasSubsequence(Stream(2)))
    println(l.hasSubsequence(Stream(3)))*/

    /*println(Stream.from(1).foldRight(Stream.empty[Int])(Stream.cons(_, _)).takeWhileEfficiently(_ < 5))
    println(Stream.from(1).foldLeft(Stream.empty[Int])((x, y) => Stream.cons(y, x)).takeWhileEfficiently(_ < 5))
    println(Stream.unfold(1)(x => Some(x, x + 1)).takeWhileEfficiently(_ <= 5))*/
    println(s.scanRight(0)(_ + _))
    println(s.tailss.map(_.foldRight(0)(_ + _)))
    println(s.tailss.mkString("\n"))
  }

  sealed trait Stream[+A] {
    override def toString: String = {
      def aux(s: Stream[A]): String = s match {
        case Empty => ""
        case Cons(h, t) if t() == Empty => s"${h()}"
        case Cons(h, t) => s"${h()} -> ${aux(t())}"
      }
      s"(${aux(this)})"
    }

    def mkString(sep: String): String = {
      def aux(s: Stream[A]): String = s match {
        case Empty => ""
        case Cons(h, t) => s"${h()}$sep${aux(t())}"
      }
      aux(this)
    }

    def headOption: Option[A] = this match {
      case Empty => None
      case Cons(h, t) => Some(h())
    }

    def toList: List[A] = this match {
      case Empty => Nil
      case Cons(h, t) => h() :: t().toList
    }

    def lift: Option[Stream[A]] = this match {
      case Empty      => None
      case v @ Cons(h, t) => Some(v)
    }

    def size: Int = {
      def aux(c: Int, s: Stream[A]): Int = s match {
        case Empty => c
        case Cons(_, t) => aux(c + 1, t())
      }
      aux(0, this)
    }

    def reverse: Stream[A] = {
      def aux(s: Stream[A], acc: Stream[A]): Stream[A] = s match {
        case Empty => acc
        case Cons(h, t) => aux(t(), Stream.cons(h(), acc))
      }
      aux(this, Stream.empty[A])
    }

    def take(n: Int): Stream[A] = {
      def go(c: Int, s: Stream[A], acc: Stream[A]): Stream[A] = s match {
        case Cons(h, t) if c > 0 => go(c - 1, t(), Stream.cons(h(), acc))
        case _ => acc
      }
      go(n, this, Stream.empty[A])
    }

    def drop(n: Int): Stream[A] = this match {
      case Empty => Stream.empty[A]
      case Cons(_, t) => if (n > 1) t().drop(n - 1) else t()
    }

    def takeWhile(f: A => Boolean): Stream[A] = {
      def aux(s: Stream[A], acc: Stream[A]): Stream[A] = s match {
        case Cons(h, t) if f(h()) => aux(t(), Stream.cons(h(), acc))
        case _ => acc
      }
      aux(this, Stream.empty[A])
    }

    def dropWhile(f: A => Boolean): Stream[A] = this match {
      case Empty => Stream.empty[A]
      case Cons(h, t) => if (f(h())) t() dropWhile f else t()
    }

    def forAll(f: A => Boolean): Boolean = this match {
      case Empty      => true
      case Cons(h, t) => f(h()) && t().forAll(f)
    }

    def forAny(f: A => Boolean): Boolean = this match {
      case Empty      => false
      case Cons(h, t) => f(h()) || t().forAny(f)
    }

    def tails: Stream[Stream[A]] = Stream.unfold(this) {
      case Empty => None
      case s @ Cons(h, t) => Some(s, t())
    }

    def foldRight[B](v: B)(f: (A, => B) => B): B = this match {
      case Cons(h, t) => f(h(), t().foldRight(v)(f))
      case _ => v
    }
    def foldLeft[B](v: => B)(f: (B, => A) => B): B = this match {
      case Cons(h, t) => f(v, h())
      case _ => v
    }
    def scanRight1[B](v: B)(f: (A, => B) => B): Stream[B] = tails map (_.foldRight(v)(f(_, _)))
    def scanRight2[B](v: B)(f: (A, => B) => B): Stream[B] = Stream.unfold(this) {
      case Empty => None
      case st @ Cons(_, t) => Some((st.foldRight(v)(f), t()))
    }

    def scanRight[B](v: B)(f: (A, => B) => B): Stream[B] = foldRight((v, Stream(v)))((a, b) => {
      lazy val b2 = b
      val r = f(a, b2._1)
      (r, Stream.cons(r, b2._2))
    })._2
    def tailss: Stream[Stream[A]] = foldRight((Stream.empty[A], Stream(Stream.empty[A])))((a, b) => {
      lazy val b2 = b
      val r = Stream.cons(a, b2._1)
      (r, Stream.cons(r, b2._2))
    })._2

    def inits: Stream[Stream[A]] = Stream.unfold(this.reverse) {
      case Empty => None
      case s @ Cons(h, t) => Some(s.reverse, t())
    }

    def zipWith[B](x: Stream[B]): Stream[(A, B)] = Stream.unfold((this, x)) {
      case (Cons(ah, at), Cons(bh, bt)) => Some((ah(), bh()), (at(), bt()))
      case _ => None
    }

    def zipAll[B](x: Stream[B]): Stream[(Option[A], Option[B])] = Stream.unfold((this.lift, x.lift)) {
      case (Some(Cons(ah, at)), Some(Cons(bh, bt))) => Some((Some(ah()), Some(bh())), (at().lift, bt().lift))
      case (Some(Cons(ah, at)), None)               => Some((Some(ah()), None), (at().lift, None))
      case (None, Some(Cons(bh, bt)))               => Some((None, Some(bh())), (None, bt().lift))
      case _                                        => None
    }

    def map[B](f: A => B): Stream[B] = Stream.unfold(this) {
      case Empty => None
      case Cons(h, t) => Some(f(h()), t())
    }

    def startsWith[B](that: Stream[B]): Boolean = (this takeEfficiently that.size zipAll that) forAll(x => x._1 == x._2)

    def hasSubsequence[B](that: Stream[B]): Boolean = this.tails.forAny(_ startsWith that)

    def takeEfficiently(n: Int): Stream[A] = Stream.unfold((n, this)) {
      case (x, Cons(h, t)) if x > 0 => Some(h(), (x - 1, t()))
      case _ => None
    }

    def takeWhileEfficiently(f: A => Boolean): Stream[A] = Stream.unfold((f, this)) {
      case (fn, Cons(h, t)) if f(h()) => Some(h(), (fn, t()))
      case _ => None
    }

    def ones: Stream[Int] = Stream.unfold(1)(x => Some(1, 1))
    def constant[T](a: T): Stream[T] = Stream.unfold(a)(x => Some(a, a))
    def from(n: Int): Stream[Int] = from(n, 1)
    def from(n: Int, s: Int): Stream[Int] = Stream.unfold(n)(x => Some(x, x + s))
    def fibs: Stream[Int] = {
      def aux(p: Int, c: Int): Stream[Int] = Stream.unfold((p, c))(x => Some(x._1, (x._2, x._1 + x._2)))
      aux(0, 1)
    }
  }
  case object Empty extends Stream[Nothing]
  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {
    def empty[A]: Stream[A] = Empty
    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def apply[A](as: A*): Stream[A] = {
      if (as.isEmpty) empty[A] else cons(as.head, apply(as.tail: _*))
    }

    def ones: Stream[Int] = cons(1, ones)
    def constant[A](a: A): Stream[A] = cons(a, constant(a))

    def from(n: Int): Stream[Int] = from(n, 1)
    def from(n: Int, s: Int): Stream[Int] = {
      def aux(c: Int): Stream[Int] = cons(n + c, aux(c + s))
      aux(0)
    }

    def fibs: Stream[Int] = {
      def aux(p: Int, c: Int): Stream[Int] = cons(p, aux(c, p + c))
      aux(0, 1)
    }

    def unfold[V, S](z: S)(f: S => Option[(V, S)]): Stream[V] = f(z) match {
      case Some(x) => Stream.cons(x._1, unfold(x._2)(f))
      case None => Empty
    }
  }

}
