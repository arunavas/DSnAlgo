package test.arunava.scala

object IntStack {

  def main(args: Array[String]): Unit = {
    val s = EmptyStack.push(20).push(9).push(11)
    println(s.top)
    s.traverse
  }

  trait IntegerStack {
    def push(value: Int): IntegerStack
    def pop: IntegerStack
    def top: Int
    def isEmpty: Boolean
    def contains(value: Int): Boolean
    def traverse: Unit = this match {
      case EmptyStack => println("Nil")
      case FilledStack(v) => {
        print(v + ",")
        this.pop.traverse
      }
    }
  }

  case object EmptyStack extends IntegerStack {
    def push(value: Int) = FilledStack(value)
    def pop = throw new Exception("Stack is empty")
    def top = throw new Exception("Stack is empty")
    def isEmpty: Boolean = true
    def contains(value: Int): Boolean = false
  }

  case class FilledStack(value: Int)(implicit tail: IntegerStack) extends IntegerStack {
    def push(value: Int) = FilledStack(value)(this)
    def pop = this.tail
    def top = this.value
    def isEmpty: Boolean = false
    def contains(value: Int): Boolean = if (this.value == value) true else tail.contains(value)
  }

  implicit val emptyStack: IntegerStack = EmptyStack;

}