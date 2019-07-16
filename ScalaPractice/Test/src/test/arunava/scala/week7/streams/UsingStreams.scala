package test.arunava.scala.week7.streams

object UsingStreams {
  def main(args: Array[String]): Unit = {
    println(findPrimes(10))
    println(findPrimesAncient(10))
  }
  
  def findPrimesAncient(n: Int): List[Int] = {
    def fromPrime(n: Int): Stream[Int] = {
      if (isPrime(n)) n #:: (fromPrime(n + 1) filter isPrime) else (fromPrime(n + 1) filter isPrime)
    }
    fromPrime(1) take n toList
  }
  
  def findPrimes(n: Int): List[Int] = {
    from(1) filter isPrime take n toList
  }
  
  def isPrime(n: Int): Boolean = {
    List.range(2, n) forall (n % _ != 0)
  }
  
  def from(n: Int): Stream[Int] = {
    n #:: from(n + 1)
  }
}