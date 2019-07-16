package monads

import monads.Pure.IO

/**
  * Created by arunavas on 5/4/18.
  */
object IOMonad extends App {
  val x: IO[Unit] = Pure.toScreenM("Monad Monad and Monads")
  Example.run
  Pure.toScreenM("A").run
  val a = Pure.toScreenM("A").flatMap(_ => Pure.toScreenM("B"))
  val b = Pure.toScreenM("A").flatMap(x => Pure.toScreenM("B")).flatMap(_ => Pure.toScreenM("C"))
  val c = Pure.toScreenM("A").flatMap(_ => Pure.toScreenM("B")).flatMap(_ => Pure.toScreenM("C"))
  //a.flatMap(_ => b).flatMap(_ => c).run
  val r: Int = Example.run
  println(r)
}

object Pure {
  sealed trait IO[A] {
    def flatMap[B](f: A => IO[B]): IO[B] = Suspend(() => f(this.run))
    def map[B](f: A => B): IO[B] = Return(() => f(this.run))

    def run: A = this match {
      case Return(r) => r()
      case Suspend(s) => s().run
    }
  }

  case class Return[A](f: () => A) extends IO[A]
  case class Suspend[A](f: () => IO[A]) extends IO[A]

  object IO {
    def point[A](a: => A): IO[A] = Return(() => a)
  }

  def toScreenM(msg: String): IO[Unit] = IO.point(println(msg))
  def toScreenP(msg: String): () => Unit = () => println(msg)
}

object Example {
  val io = for {
    _ <- Pure.toScreenM("Starting work now.")
    x = 1 + 2 + 3
    _ <- Pure.toScreenM("All done. Home time.")
  } yield x
  val ioM = Pure.toScreenM("Starting work now.").map(_ => 1 + 2 + 3).map(x => {
    println("All done. Home time")
    x
  })

  def run = ioM.run
}