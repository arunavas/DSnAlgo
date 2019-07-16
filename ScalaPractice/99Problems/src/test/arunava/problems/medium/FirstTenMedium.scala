package test.arunava.problems.medium

import java.util.Collections

import test.arunava.problems.easy.FirstTenEasy

import scala.annotation.tailrec
import test.arunava.problems.common.One
import test.arunava.problems.common.Many
import test.arunava.problems.common.Element
import test.arunava.problems.common.Util
import test.arunava.problems.common.Util

/**
 * Created by arunavas on 24/7/16.
 */
class FirstTenMedium {
  lazy val fte = new FirstTenEasy
  // Flatten a nested list structure. (medium)
  /*
  // Without tailrec
  final def flatten[A](list: List[List[A]]): List[A] = list match {
    case Nil => Nil
    case h :: t => (flatten(t) foldLeft(h))(_ :+ _)
  }*/
  final def flatten[A](list: List[List[A]]): List[A] = {
    @tailrec
    def aux(lst: List[List[A]], flatList: List[A]): List[A] = lst match {
      case Nil    => flatList
      case h :: t => aux(t, (flatList foldRight (h))(_ :: _))
    }
    aux(list, Nil)
  }

  // Eliminate consecutive duplicates of list elements. (medium)
  /*
  //Without telrec
  final def compress[A](list: List[A]): List[A] = list match {
    case Nil => Nil
    case h :: t => h :: compress(list dropWhile(_ == h))
  }*/
  //With dropWhile
  /*final def compress[A](list: List[A]): List[A] = {
    @tailrec
    def doCompress(lst: List[A], compressedList: List[A]): List[A] = lst match {
      case Nil => compressedList
      case h :: t => doCompress(lst dropWhile(_ == h), h :: compressedList)
    }
    util.reverse(doCompress(list, Nil))
  }*/
  //Without dropWhile
  final def compress[A](list: List[A]): List[A] = {
    @tailrec
    def doCompress(lst: List[A], compressedList: List[A]): List[A] = lst match {
      case Nil => compressedList
      case h :: t => t match {
        case Nil      => h :: compressedList
        case th :: tt => if (h == th) doCompress(t, compressedList) else doCompress(t, h :: compressedList)
      }
    }
    doCompress(list, Nil)
  }

  // Pack consecutive duplicates of list elements into sublists. (medium)
  final def pack[A](list: List[A]): List[List[A]] = {
    @tailrec
    def doPack(lst: List[A], packedList: List[List[A]]): List[List[A]] = lst match {
      case Nil    => packedList
      case h :: t => doPack(lst dropWhile (_ == h), lst.takeWhile(_ == h) :: packedList)
    }
    fte.reverse(doPack(list, Nil))
  }

  /**
   * Modified run-length encoding. (easy).
   * Modify the result of the previous problem in such a way that
   *  if an element has no duplicates it is simply copied into the result list.
   *  Only elements with duplicates are transferred as (N E) lists.
   */
  /*def encode[A](list: List[A]): List[Element[A]] = {
    def doEncoding(lst: List[A], res: List[Element[A]]): List[Element[A]] = lst match {
      case Nil => Nil
      case h :: t => t match {
        case Nil => One(h) :: res;
        case th :: tt => res match {
          case Nil => if (h == th) doEncoding(t, Many(1, h) :: res) else doEncoding(t, One(h) :: res)
          case x :: xs => x match {
            case One(x) => if (h == th) doEncoding(t, Many(2, h) :: xs) else doEncoding(t, One(h) :: xs)
            case Many(l, x) => if (h == th) doEncoding(t, if (x == h) Many(l + 1, h) :: xs else Many(1, h) :: res) else doEncoding(t, if (x == h) Many(l + 1, h) :: xs else One(h) :: res)
          }
        }
      }
    }
    util.reverse(doEncoding(list, Nil));
  }*/

  def encode[A](list: List[A]): List[Element[A]] = {
    lazy val util = new Util[A]
    def doEncoding(lst: List[A], res: List[Element[A]]): List[Element[A]] = lst match {
      case Nil => res
      case h :: t => {
        val a = util.takeWhile(lst, (_ == h))
        val b = util.dropWhile(lst, (_ == h))
        val l = fte.length(a)
        if (l == 1) doEncoding(b, One(h) :: res)
        else doEncoding(b, Many(l, h) :: res)
      }
    }
    fte.reverse(doEncoding(list, Nil));
  }

  // Decode a run-length encoded list. (medium)
  final def decode[A](list: List[Element[A]]): List[A] = {
    lazy val util = new Util[A]
    def doDecoding(lst: List[Element[A]], res: List[A]): List[A] = lst match {
      case Nil => res
      case h :: t => h match {
        case One(x)     => doDecoding(t, x :: res)
        case Many(l, x) => doDecoding(t, (util.repeat(l, x) foldRight res)(_ :: _))
      }
    }
    fte.reverse(doDecoding(list, Nil))
  }

  // Replicate the elements of a list a given number of times. (medium)
  final def replicate[A](list: List[A], count: Int): List[A] = {
    lazy val util = new Util[A]
    @tailrec
    def doReplicate(lst: List[A], res: List[A]): List[A] = lst match {
      case Nil    => res
      case h :: t => doReplicate(t, (util.repeat(count, h) foldRight res)(_ :: _))
    }
    fte.reverse(doReplicate(list, Nil))
  }

  // Drop every N'th element from a list. (medium)
  final def drop[A](list: List[A], index: Int): List[A] = {
    @tailrec
    def doDropping(lst: List[A], result: List[A], cntr: Int): List[A] = lst match {
      case Nil    => result
      case h :: t => if(cntr % index == 0) doDropping(t, result, cntr + 1) else doDropping(t, h :: result, cntr + 1)
    }
    fte.reverse(doDropping(list, Nil, 1))
  }
  
  // Extract a slice from a list. (medium)
  final def slice[A](list: List[A], start: Int, end: Int): List[A] = {
    @tailrec
    def doSlicing(lst: List[A], result: List[A], acc: Int): List[A] = lst match {
      case Nil => result
      case h :: t => if (acc > end) result else if (acc >= start && acc <= end) doSlicing(t, h :: result, acc + 1) else doSlicing(t, result, acc + 1)
    }
    fte.reverse(doSlicing(list, Nil, 0))
  }
  
  // Rotate a list N places to the left. (medium)
  final def rotate[A](list: List[A], n: Int): List[A] = {
    @tailrec
    def doRotation(list: List[A], n: Int): List[A] = list match {
      case Nil => list
      case h :: t => if (n == 0) list else doRotation(t :+ h, n - 1)
    }
    if (n < 0) fte.reverse(doRotation(fte.reverse(list), -n))
    else doRotation(list, n)
  }

}
