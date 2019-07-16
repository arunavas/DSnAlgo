package file.processor

import java.util.concurrent.Executors

import scala.collection.parallel.ParSeq
import scala.collection.parallel.immutable.ParMap
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

/**
  * Created by arunavas on 17/2/18.
  */
object Main {
  //"id,BANK_CODE,SIDE,AMOUNT,STATUS"
  val FILE = "/home/arunavas/MyWorks/Documents/UPI/FileUpload&Verification/blob.csv"

  def main(args: Array[String]): Unit = {
    process(FILE)
  }

  def process(file: String) {
    val startTs = System.currentTimeMillis()
    val lines = Source.fromFile(file).getLines().drop(1)

    /*val r: Seq[Map[(String, String), (Int, Double)]] = lines.grouped(50000).map(_.par.map(l => {
      val split = l split ","
      (split(1), split(2)) -> split(3).toDouble
    }).foldLeft(Map[(String, String), (Int, Double)]())((a, b) => a + (b._1 -> {
      val d = a.getOrElse(b._1, (0, 0.0))
      (d._1 + 1, d._2 + b._2)
    }))).toSeq
    val endTs = System.currentTimeMillis()
    println(s"Processed ${r.size} in ${endTs - startTs} time!")*/

    /*val r: Seq[Map[(String, String), (Int, Double)]] = lines.grouped(50000).map(_.par.foldLeft(Map[(String, String), (Int, Double)]())((a, l) => a + {
      val split = l split ","
      val k = (split(1), split(2))
      val d = a.getOrElse(k, (0, 0.0))
      k -> (d._1 + 1, d._2 + split(3).toDouble)
    })).toSeq
    val midTs = System.currentTimeMillis()
    println(s"Parallel processing Time: ${midTs - startTs} | Size: ${r.size}")
    val result: Map[(String, String), (Int, Double)] = r.reduceLeft((a, b) => a.foldLeft(b)((x, y) => x + (y._1 -> {
      val d = x.getOrElse(y._1, (0, 0.0))
      (d._1 + y._2._1, d._2 + y._2._2)
    })))
    val endTs = System.currentTimeMillis()
    println(s"Processed ${result.size} in ${endTs - startTs} time!")*/

    /*val r = lines.grouped(50000).map(_.par.foldLeft(Map[(String, String), (Int, Double)]())((a, l) => a + {
      val split = l split ","
      val k = (split(1), split(2))
      val d = a.getOrElse(k, (0, 0.0))
      k -> (d._1 + 1, d._2 + split(3).toDouble)
    })).foldLeft(Map[(String, String), (Int, Double)]())((a, b) => a.foldLeft(b)((x, y) => x + (y._1 -> {
      val d = x.getOrElse(y._1, (0, 0.0))
      (d._1 + y._2._1, d._2 + y._2._2)
    })))
    val endTs = System.currentTimeMillis()
    println(s"Processed ${r.size} in ${endTs - startTs} time!")*/

    val result = lines.grouped(50000).map(x => Future{
      //val mS = System.currentTimeMillis()
      val r = x.par.foldLeft(Map[(String, String), (Int, Double)]())((a, l) => a + {
        val split = l split ","
        val k = (split(1), split(2))
        val d = a.getOrElse(k, (0, 0.0))
        k -> (d._1 + 1, d._2 + split(3).toDouble)
      })
      //val mE = System.currentTimeMillis()
      //println(s"Mapped in ${mE - mS} time...")
      r
    }(mapperEc)).foldLeft(Future.successful(Map[(String, String), (Int, Double)]()))((fa, fb) => fb.flatMap(b => fa.map(a => {
      //println(s"Reducing\n\t$a\n\t$b")
      //val rS = System.currentTimeMillis()
      val r = a.foldLeft(b)((x, y) => x + (y._1 -> {
        val d = x.getOrElse(y._1, (0, 0.0))
        (d._1 + y._2._1, d._2 + y._2._2)
      }))
      //val rE = System.currentTimeMillis()
      //println(s"Reduced in ${rE - rS} time...")
      r
    })(reducerEc))(reducerEc))
    result.foreach(r => {
      val endTs = System.currentTimeMillis()
      println(s"Processed ${r.size} in ${endTs - startTs} time!")

    })(ec)

    /*val s = Stream.from(1).take(1000).filter(a => {
      println("Filter")
      a % 2 == 0
    }).map(b => {
      println("Map")
      b * 2
    })
    println(s.toList)

    val ls = List.range(1, 1001).filter(a => {
      println("Filter")
      a % 2 == 0
    }).map(b => {
      println("Map")
      b * 2
    })
    println(ls.toList)*/

    //println(r.mkString("\n"))
  }

  val mapperEc = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(32)

    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {}
  }
  val reducerEc = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(32)

    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {}
  }
  val ec = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(32)

    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {}
  }
}
