package test.arunava.scala.week5.list

object ListPrimePairs {
  def main(args: Array[String]): Unit = {
    //println(pairOfPrimes(7))
    println(pairOfPrimesFlatMap(7))
    
    /*val s = "Hello World";
    println(s map (c => "." + c))
    println(s flatMap(c => "." + c))
    println(s map (c => List('.', c)))
    println(s flatMap(c => List('.', c)))
    println(pairOfPrimesFor(7))*/
  }
  
  def isPrime(x: Int): Boolean = {
    List.range(2, x).forall(x % _ != 0)
  }
  
  def pairOfPrimesFor(n: Int): List[(Int, Int)] = for {
    i <- List.range(1, n)
    j <- List.range(1, i)
    if(isPrime(i + j))
  } yield (i, j)
  
  def pairOfPrimesFlatMap(n: Int): List[(Int, Int)] = {
    List.range(1, n)
        .flatMap { x => List.range(1, x).map { y => (x, y) } }
        .filter(p => List.range(2, (p._1 + p._2)) forall ((p._1 + p._2) % _ != 0))
  }
  
  def pairOfPrimes(n: Int): List[(Int, Int)] = {
    /*val x = List.range(1, n)
                .map(i => List.range(1, i).map(j => (j, i)))
                .foldRight(List[(Int, Int)]())((x, y) => x ::: y)
    println(x)
    val y = List.range(1, n)
                .map(i => List.range(1, i).map(j => (j, i)))
                .foldLeft(List[(Int, Int)]())((x, y) => x ::: y)
    println(y)
    val a = List.range(1, n)
                .map(i => List.range(1, i).map(j => (j, i)))
                .reduceLeft((x, y) => x ::: y)
    println(a)
    val b = List.range(1, n)
                .map(i => List.range(1, i).map(j => (j, i)))
                .reduceRight((x, y) => x ::: y)
    println(b)*/
    List.range(1, n)
        .map(i => List.range(1, i).map(j => (j, i)))
        .flatten
        .filter(p => List.range(2, (p._1 + p._2)) forall ((p._1 + p._2) % _ != 0))
  }
}