package test.arunava.problems.easy

import scala.annotation.tailrec

/**
  * Created by arunavas on 24/7/16.
  */
class FirstTenEasy {

  // Write a function last : 'a list -> 'a option that returns the last element of a list. (easy)
  @tailrec
  final def last[A](list: List[A]): Option[A] = list match {
    case Nil => None
    case h :: Nil => Some(h)
    case h :: t => last(t)
  }

  // Find the last but one (last and penultimate) elements of a list. (easy)
  @tailrec
  final def lastTwo[A](list: List[A]): (Option[A], Option[A]) = list match {
    case Nil => (None, None)
    case h :: t => t match {
      case Nil => (Some(h), None)
      case th :: Nil => (Some(h), Some(th))
      case th :: tt => lastTwo(t)
    }
  }

  // Find the k'th element of a list. (easy)
  @tailrec
  final def at[A](list: List[A], k: Int): Option[A] = list match {
    case Nil => None
    case h :: t => if (k == 0) Some(h) else at(t, k - 1)
  }

  // Find the number of elements of a list. (easy)
  final def length[A](list: List[A]): Int = {
    @tailrec
    def findLength(lst: List[A], counter: Int): Int = lst match {
      case Nil => counter
      case h :: t => findLength(t, counter + 1)
    }
    findLength(list, 0)
  }

  // Reverse a list. (easy)
  /*
  // Without tailrec
  final def reverse[A](list: List[A]): List[A] = list match {
    case Nil => Nil
    case h :: t => reverse(t) :+ h
  }*/
  final def reverse[A](list: List[A]): List[A] = {
    def doReverse(lst: List[A], rev: List[A]): List[A] = lst match {
      case Nil => rev
      case h :: t => doReverse(t, h :: rev)
    }
    doReverse(list, Nil)
  }

  // Find out whether a list is a palindrome. (easy)
  final def isPalindrome[A](list: List[A]): Boolean = list == reverse(list)

  //Run-length encoding of a list. (easy)
  final def encode[A](list: List[A]): List[(Int, A)] = {
    @tailrec
    def doEncode(lst: List[A], encodedList: List[(Int, A)]): List[(Int, A)] = lst match {
      case Nil => encodedList
      case h :: t => doEncode(lst dropWhile(_ == h), (length(lst takeWhile(_ == h)), h) :: encodedList)
    }
    reverse(doEncode(list, Nil))
  }
  
  //Duplicate the elements of a list. (easy)
  final def duplicate[A](list: List[A]): List[A] = {
    @tailrec
    def doDuplicate(lst: List[A], res: List[A]): List[A] = lst match {
      case Nil => res
      case h :: t => doDuplicate(t, h :: (h :: res))
    }
    reverse(doDuplicate(list, Nil))
  }
  
  //Split a list into two parts; the length of the first part is given. (easy)
  final def split[A](list: List[A], length: Int): (List[A], List[A]) = {
    @tailrec
    def doSplit(p1: List[A], p2: List[A], acc: Int): (List[A], List[A]) = p2 match {
      case Nil => (reverse(p1), p2)
      case h :: t => if (acc >= length) (reverse(p1), p2) else doSplit(h :: p1, t, acc + 1)
    }
    doSplit(Nil, list, 0);
  }
  
  //Remove the K'th element from a list. (easy)
  final def removeAt[A](n: Int, list: List[A]): List[A] = {
    @tailrec
    def remove(prefix: List[A], suffix: List[A], acc: Int): List[A] = suffix match {
      case Nil => reverse(prefix)
      case x :: xs => if (acc == n) (reverse(prefix) foldRight(xs))(_ :: _) else remove(x :: prefix, xs, acc + 1)
    }
    remove(Nil, list, 0)
  }

}
