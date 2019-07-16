package test.arunava.scala.week6.map

object PolyNomial {
  
  def main(args: Array[String]): Unit = {
    val m = Map(0 -> 1.0, 1 -> 2.0, 2 -> 3.0, 3 -> 4.0, 4 -> 5.0)
    val p1 = new Poly(m)
    println(p1(1))
    val p2 = new Poly(-1 -> 0, 1 -> 1.0, 2 -> 2.0, 3 -> 3.0, 5 -> 4.0)
    println()
    println(p1)
    println(p2)
    println(" + " + (p1 + p2))
    println(" ~ " + (p1 ~+ p2))
  }
  
  class Poly(t: Map[Int, Double]) {
    def this(t: (Int, Double)*) = this (t.toMap)
    val term: Map[Int, Double] = t withDefaultValue 0
    
    def +(that: Poly) = new Poly(term ++ (that.term map {case (k, v) => k -> (v + term(k))}))
    def ~+(that: Poly) = new Poly((that.term foldLeft (term)){ case (l, r) => l + (r match {case (k, v) => k -> (v + term(k))})})
    
    def apply(k: Int) = term(k)
    override def toString = term map { case (k, v) => "(" + k + "-" + v + ")"} mkString(" + ")
    
  }
  
}