package codility

import scala.annotation.tailrec

object LongestPassword {

  def main(args: Array[String]): Unit = {
    /*println(solution("a0Ba"))
    println(solution("a0ba"))*/
    val s = "a0Ba123456abcDefg091asdasdjs"
    println(solution(s))
    println(solutionWoAux(s))
    
  }

  def solution(s: String): Int = {
    val numbers = '0' to '9'
    val capitals = 'A' to 'Z'
    @tailrec
    def getValidPwds(pwd: String, result: List[String]): List[String] = {
      if (pwd == null || pwd.length() == 0) result.filter { x => x.filter (capitals.contains(_)).length() > 0 }
      else {
        val valid = pwd.takeWhile (!numbers.contains(_))
        getValidPwds(pwd.replaceFirst(valid, "").dropWhile (numbers.contains(_)), valid :: result)
      }
    }
    val start = System.currentTimeMillis()
    val validPwds = getValidPwds(s, Nil)
    val x = validPwds match {
      case Nil => -1
      case _ => validPwds.map (_.length()).max
    }
    println("Solution Time Taken: %s".format(System.currentTimeMillis() - start))
    x
  }
  
  def solutionWoAux(S: String): Int = {
    val numbers = '0' to '9'
    val capitals = 'A' to 'Z'
    val start = System.currentTimeMillis()
    val x = S.map ( x => if (numbers.contains(x)) '_' else x ).split("_")
      .filter ( x => x.length() > 0 && x.filter (capitals.contains(_)).length() > 0 )
      .map (_.length()).max
    println("Solution1 Time Taken: %s".format(System.currentTimeMillis() - start))
      x
    /*val validPwds = S.map ( x => if (numbers.contains(x)) '_' else x ).split("_")
                    .filter ( x => x.length() > 0 && x.filter (capitals.contains(_)).length() > 0 )
                    .map (_.length())
    println(validPwds.toList)
    validPwds.max*/
  }

}