package test.arunava.problems.common

sealed trait Element[T]
case class One[T](value: T) extends Element[T] {
  override def toString() = "One: %s".format(value)
}
case class Many[T](count: Int, value: T) extends Element[T] {
  override def toString() = "Many: (%s, %s)".format(count, value)
}