package test.arunava.scala

object Main extends App {
  /*val map = Map("a" -> 1, "ab" -> 2, "bacd" -> 3, "dbacd" -> 3)
  println(map filterKeys (_ matches ".*a.*d"))*/
  val apis = Map(
      "/helplil/.*/create/" -> "SERVICE.CREATE", 
      "/helplil/user/.*/" -> "USER.FETCH", 
      "/helplil/group/.*/.*/" -> "GROUP.CREATE", 
      "/helplil/problem/.*/solve/" -> "Problem Solve")
  
  println(apis filterKeys ("/helplil/user/create/" matches _))
}