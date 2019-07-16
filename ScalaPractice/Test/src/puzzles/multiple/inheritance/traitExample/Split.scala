package puzzles.multiple.inheritance.traitExample

import scala.annotation.tailrec

object Split {
  def main(args: Array[String]): Unit = {
    val list = List.range(1, 11)
    val l = List(1)
    println(list)
    println(splitAt(list, 5))
    /*println(splitAt(Nil, 0))
    println(splitAt(l, 0))
    println(splitAt(l, 1))
    // println(splitAt(l, 2)) // Should throw the error as 2 is outside list range
    println()
    println(splitAtImplicit(list, 5))
    println(splitAtImplicit(Nil, 0))
    println(splitAtImplicit(l, 0))
    println(splitAtImplicit(l, 1))
    println()
    println(span(l)(x => x % 2 == 0))
    println(span(l)(x => x % 2 == 1))
    println(span(list)(x => x % 2 == 0))
    println(span(list)(x => x % 2 == 1))
    println()
    println(doSpanImplicit(list)(x => x % 2 == 1))
    println(doSpanImplicit(list)(x => x % 2 == 0))*/
  }

  def splitAt[T](list: List[T], index: Int): (List[T], List[T]) = {
    if (index < 0 || index > list.length) throw new Error("Cannot split at index not in list range")
    list match {
      case Nil => (Nil, Nil)
      case x :: xs => {
        @tailrec
        def split(p1: List[T], p2: List[T], acc: Int): (List[T], List[T]) = {
          if (acc == index) (p1, p2)
          else split(p1 ::: List(p2.head), p2.tail, acc + 1)
        }
        split(Nil, list, 0)
      }
    }
  }

  @tailrec
  def splitAtImplicit[T](list: List[T], index: Int)(implicit prev: List[T]): (List[T], List[T]) = {
    if (index < 0 || index > list.length) throw new Error("Cannot split at index not in list range")
    list match {
      case Nil => (prev, list)
      case x :: xs => {
        if (index == 0) (prev, list)
        else splitAtImplicit(xs, index - 1)(prev ::: List(x))
      }
    }
  }

  def span[T](list: List[T])(op: T => Boolean): (List[T], List[T]) = {
    @tailrec
    def doSpan(matches: List[T], notMatches: List[T], list: List[T]): (List[T], List[T]) = list match {
      case Nil      => (matches, notMatches)
      case x :: Nil => if (op(x)) (matches :+ x, notMatches) else (matches, notMatches :+ x)
      case x :: xs  => if (op(x)) doSpan(matches :+ x, notMatches, xs) else doSpan(matches, notMatches :+ x, xs)
    }
    doSpan(Nil, Nil, list)
  }

  @tailrec
  def doSpanImplicit[T](list: List[T])(op: T => Boolean)(implicit matches: List[T], notMatches: List[T]): (List[T], List[T]) = list match {
    case Nil      => (matches, notMatches)
    case x :: Nil => if (op(x)) (matches :+ x, notMatches) else (matches, notMatches :+ x)
    case x :: xs  => if (op(x)) doSpanImplicit(xs)(op)(matches :+ x, notMatches) else doSpanImplicit(xs)(op)(matches, notMatches :+ x)
  }

  implicit val m: List[Int] = Nil
}