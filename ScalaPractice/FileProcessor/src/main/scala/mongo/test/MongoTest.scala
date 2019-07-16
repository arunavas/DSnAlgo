package mongo.test

import com.mongodb.DBObject
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by arunavas on 23/3/18.
  */
object MongoTest {

  def main(args: Array[String]): Unit = {
    val mc = MongoClient("172.18.17.43", 27017)
    val db = mc("clearing-records")
    val col = db("txn-data")
    println(s"Number of records: ${col.count()}")
    val start = System.currentTimeMillis()
    val r = col.find(MongoDBObject("host" -> "192.168.0.0", "port" -> 9080))
    r.foreach(println)
    val end = System.currentTimeMillis()
    println(s"Taken Time: ${end - start}")
  }

}
