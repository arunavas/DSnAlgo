package test.arunava.scala.week4.generics

import scala.annotation.tailrec

trait Tree[A <: Ordered[A]] {
  
  def insert(n: A): Tree[A] = {
    this match {
      case EmptyTree() => ValueTree[A](n, EmptyTree(), EmptyTree())
      case ValueTree(v, l, r) => {
        if (n < v) ValueTree(v, l.insert(n), r)
        else if (n > v) ValueTree(v, l, r insert n)
        else this
      }
    }
  }
  
  @tailrec
  final def contains(n: A): Boolean = {
    this match {
      case EmptyTree() => false
      case ValueTree(v, l, r) => {
        if (n == v) true
        else if (n < v) l contains n
        else r contains n
      }
    }
  }
  
  def traverse: Unit = {
    this match {
      case EmptyTree() => print("")
      case ValueTree(v, l, r) => {
        l.traverse
        print(" " + v)
        r traverse
      }
    }
  }
  
}

case class EmptyTree[A <: Ordered[A]]() extends Tree[A]
case class ValueTree[A <: Ordered[A]](value: A, left: Tree[A], right: Tree[A]) extends Tree[A]