package test.hackerearth.scala

object DualNumber {
  def main(args: Array[String]): Unit = {
    val n = Console.readInt()
    val input: List[List[Long]] = List.range(0, n).map { x => {
      val l = Console.readInt()
      val data = Console.readLine()
      val arr = data.split(" ").toList
      arr map (_.toLong)
    }}
    input foreach (e => println(maxDual(e)))
  }
  
  def isPrime(x: Long): Boolean = x == 2 || (x != 1 && (2l to (Math.sqrt(x).longValue() + 1)).forall { x % _ != 0 })
  
  def maxDual(list: List[Long]): Long = {
    val primes = list filter isPrime
    val l = primes.length
    val res = (0 until l) flatMap (j => (0 to j) map (i => primes(i) * primes(j)))
    if (res.isEmpty) -1
    else res.max
  }
}