package test.arunava.scala.graph

import scala.annotation.tailrec

case class DiGraph[A](nodes: List[(A, List[A])]) {
  def addVertex(v: A): DiGraph[A] = DiGraph((v, Nil) :: nodes)
  def removeVertex(v: A): DiGraph[A] = new DiGraph(nodes filter (_._1 != v) map { case (x, y) => (x, y filter (_ != v)) })
  def vertex(v: A): Option[(A, List[A])] = {
    @tailrec
    def vertexAux(list: List[(A, List[A])]): Option[(A, List[A])] = list match {
      case Nil     => None
      case x :: xs => if (x._1 == v) Some(x) else vertexAux(xs)
    }
    vertexAux(nodes)
  }
  def edges(v: A): List[A] = {
    vertex(v) match {
      case None    => Nil
      case Some(x) => x._2
    }
  }
  def addEdge(v1: A, v2: A): DiGraph[A] = DiGraph[A](nodes map { case (v, e) => if (v == v1) (v, v2 :: e) else (v, e) })
  def removeEdge(v1: A, v2: A): DiGraph[A] = DiGraph[A](nodes map { case (v, e) => if (v == v1) (v, e filter (_ != v2)) else (v, e) })

  def isEmpty: Boolean = nodes.isEmpty
  def containsVertex(v: A): Boolean = nodes exists (_._1 == v)
  def containsEdge(v1: A, v2: A): Boolean = edges(v1) exists (_ == v2)
  def vertexCount: Int = nodes.size
  def edgeCount: Int = nodes map (_._2.size) sum

  def dfsAll = {
    @tailrec
    def dfsAux(list: List[A], visited: List[A]): Unit = list match {
      case Nil => println("|")
      case x :: xs => if (!visited.contains(x)) {
        print(x + " -> ")
        val adj = edges(x) filterNot (visited contains)
        dfsAux(adj.foldRight(list)(_ :: _), x :: visited)
      } else dfsAux(xs, visited)
    }
    dfsAux(nodes map (_._1), Nil)
  }

  def dfs(v: A) = {
    @tailrec
    def dfsAux(v: A, list: List[A], visited: List[A]): Unit = {
      print(" => " + v)
      val adj = edges(v) filterNot (visited contains)
      val next = (adj foldRight list)(_ :: _)
      next match {
        case Nil     => println()
        case x :: xs => dfsAux(x, xs, x :: visited)
      }
    }
    dfsAux(v, Nil, List(v))
  }

  def bfs(v: A) = {
    def bfsAux(v: A, list: List[A], visited: List[A]): Unit = {
      print(" => " + v)
      val adj = edges(v) filterNot (visited contains)
      val next = (list foldRight adj)(_ :: _)
      next match {
        case Nil     => println()
        case x :: xs => bfsAux(x, xs, x :: visited)
      }
    }
    bfsAux(v, Nil, List(v))
  }

  override def toString(): String = nodes map {
    case (v, e) => v + "\t=> " + e
  } mkString ("\n")

}