package test

import scala.collection.mutable

/**
  * Created by arunavas on 26/7/17.
  */
object Test1 {

  val store: mutable.Map[String, (String, Long)] = mutable.Map()

  def set(key: String, value: String): String = {
    store.get(key) match {
      case None    => store += (key -> (value, -1l))
      case Some(v) => store += (key -> (value, v._2))
    }
    "OK"
  }

  def get(key: String): String = {
    val nullValue = "(null)"
    store.get(key) match {
      case None    => nullValue
      case Some(v) =>
        if (v._2 < System.currentTimeMillis() && v._2 >= 0) {
          del(key)
          nullValue
        } else v._1
    }
  }

  def del(key: String): String = {
    store -= key
    "OK"
  }

  def exists(key: String): Int = store.get(key) match {
    case None    => 0
    case Some(_) => 1
  }

  def expire(key: String, ttl: Long): String = {
    store.get(key) match {
      case None    => ()
      case Some(v) => store += (key -> (v._1, System.currentTimeMillis() + ttl * 1000))
    }
    "OK"
  }

  def main(args: Array[String]) {
    val errorString = "ERROR: not enough parameters"
    for (line <- io.Source.stdin.getLines) {
      val arr = line split " "
      if (arr.length < 2) println(errorString)
      else arr(0) match {
        case "SET" => if (arr.length < 3) println(errorString) else println(set(arr(1), arr(2)))
        case "GET" => println(get(arr(1)))
        case "DEL" => println(del(arr(1)))
        case "EXISTS" => println(exists(arr(1)))
        case "EXPIRE" => if (arr.length < 3) println(errorString) else {
          try {
            println(expire(arr(1), arr(2).toLong))
          } catch {
            case e: Exception => println("ERROR: invalid parameters")
          }
        }
        case _ => println("ERROR: invalid command")
      }
    }
  }

  def findNumber(arr: Array[Int], k: Int): String = arr.toList match {
    case Nil => "NO"
    case h :: t => if (h == k) "YES" else findNumber(t.toArray, k)
  }

}
