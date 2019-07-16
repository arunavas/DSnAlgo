package monads

import jdk.nashorn.internal.codegen.Label.Stack

/**
  * Created by arunavas on 6/4/18.
  */
trait StateMonad[A, S] {
  def apply(s: S): (A, S)
  def flatMap[B](f: A => StateMonad[B, S]): StateMonad[B, S] = StateMonad { state =>
    val (a, nS) = this(state)
    f(a)(nS)
  }
  def map[B](f: A => B): StateMonad[B, S] = StateMonad { state =>
    val (a, nS) = this(state)
    (f(a), nS)
  }
  def foreach[U](f: S => U): S => Unit = s => {
    f(s)
    ()
  }
}

object StateMonad {
  def apply[A, S](r: S => (A, S)) = new StateMonad[A, S] {
    def apply(s: S) = r(s)
  }
}

object StateMonadTest extends App {

  type Stack[A] = List[A]

  def push[A](a: A): StateMonad[Unit, Stack[A]] = StateMonad(stack => ((), a :: stack))
  def pop[A]: StateMonad[Option[A], Stack[A]] = StateMonad {
    case Nil => (None, Nil)
    case h :: t => (Some(h), t)
  }

  def popPair[A]: StateMonad[(Option[A], Option[A]), Stack[A]] = pop[A].flatMap(opt1 => pop[A].map(opt2 => (opt1, opt2)))

  val stack = List(1, 2, 3)
  val x = push(1).flatMap(_ => push(2)).flatMap(_ => push(3))
  println(popPair(stack))
}