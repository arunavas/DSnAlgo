package test.arunava.scala.week4

import scala.annotation.tailrec

trait IntegerTree {
  
  def traverse {
    this match {
      case EmptyIntTree => print("")
      case Element(v, l, r) => {
        l.traverse
        print(" " + v)
        r.traverse
      }
    }
  }
  
  @tailrec
  final def contains(n: Int): Boolean = {
    this match {
      case EmptyIntTree => false
      case Element(v, l, r) => {
        if (n < v) l contains(n)
        else if (n > v) r contains(n)
        else true
      }
    }
  }
  
  def insert(n: Int): IntegerTree = {
    this match {
      case EmptyIntTree => Element(n, EmptyIntTree, EmptyIntTree)
      case Element(v, l, r) => {
        if (n < v) Element(v, l.insert(n), r)
        else Element(v, l, r.insert(n))
        //else this
      }
    }
  }
  
}

case object EmptyIntTree extends IntegerTree
case class Element(value: Int, left: IntegerTree, right: IntegerTree) extends IntegerTree