package test.arunava.scala.week7.streams

case class Glass(capacity: Int)(implicit qty: Int) {
  val quantity = qty
  val available = capacity - quantity
  def isFull = available == 0
  def isEmpty: Boolean = quantity == 0
  def fill(qty: Int) = {
    Glass(capacity)(if (qty > available) quantity + available else quantity + qty)
  }
  def empty: Glass = Glass(capacity)(0)
  def empty(qty: Int): Glass = {
    if (qty > quantity) this.empty
    else Glass(capacity)(quantity - qty)
  }
  
  override def toString() = "(" + capacity + ", " + quantity + ")";
}
