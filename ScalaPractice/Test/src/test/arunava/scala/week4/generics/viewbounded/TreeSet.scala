package test.arunava.scala.week4.generics.viewbounded

trait TreeSet[A] {
  def contains(n: A): Boolean
  def isEmpty: Boolean
  def insert(n: A): TreeSet[A]
  def union(that: TreeSet[A]): TreeSet[A]
  def printAll: Unit
}

class EmptyTreeSet[A <% Ordered[A]] extends TreeSet[A] {
  def contains(n: A) = false
  def isEmpty = true
  def insert(n: A) = new FilledTreeSet(n, new EmptyTreeSet(), new EmptyTreeSet())
  def union(that: TreeSet[A]) = that
  def printAll = print("")
}

class FilledTreeSet[A <% Ordered[A]](value: A, left: TreeSet[A], right: TreeSet[A]) extends TreeSet[A] {
  def contains(n: A) = {
    if (n < value) left contains n
    else if (n > value) right contains n
    else true
  }
  def isEmpty = false
  def insert(n: A) = {
    if (n < value) new FilledTreeSet(value, left insert n, right)
    else if (n > value) new FilledTreeSet(value, left, right insert n)
    else this
  }
  def union(that: TreeSet[A]) = {
    (((left union right) union that) insert value)
  }
  def printAll = {
    left.printAll
    print(" " + value)
    right.printAll
  }
}