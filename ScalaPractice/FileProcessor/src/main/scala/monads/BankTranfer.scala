package monads

import scala.collection.mutable

/**
  * Created by arunavas on 29/3/18.
  */
object BankTranfer {
  case class User(uid: String, name: String, pwd: String)
  val bank = mutable.Map[String, Double]()

  def auth: User => Either[String, User] = u => if (u.pwd == "password") Right(u) else Left("User Auth Failed!")
  def hasSufficientBalance: (User, Double) => Either[String, User] = (u, amt) => bank get u.uid match {
    case None => Left("Invalid User!")
    case Some(bb) => if (bb < amt) Left ("Insufficient Funds!") else {
      Right(u)
    }
  }
  def debit: (User, Double) => Either[String, Double] = (u, amt) => {
    bank get u.uid match {
      case None => Left("Invalid User!")
      case Some(bb) =>
        val bal = bb - amt
        bank += u.uid -> bal
        Right(bal)
    }
  }
  def credit: (User, Double) => Either[String, Double] = (u, amt) => {
    bank get u.uid match {
      case None => Left("Invalid User!")
      case Some(bb) =>
        val bal = bb + amt
        bank += u.uid -> bal
        Right(bal)
    }
  }

  def toStringLeft[A](msg: String): Either[String, A] = Left(msg)

  def transfer(u1: User, u2: User, amt: Double): Either[String, (Double, Double)] = {
    auth(u1)
      .fold(toStringLeft[(Double, Double)], ar => hasSufficientBalance(ar, amt)
        .fold(toStringLeft[(Double, Double)], br => debit(br, amt)
          .fold(toStringLeft[(Double, Double)], dBal => credit(u2, amt)
            .fold(toStringLeft[(Double, Double)], cBal => Right((dBal, cBal))))))
  }

  def initBank(members: List[(User, Double)]) = members foreach (r => bank += r._1.uid -> r._2)

  def main(args: Array[String]): Unit = {
    val arunava = User("arunavas", "Arunava Saha", "password")
    val asim = User("asimg", "Asim Ghosh", "password")
    //initializing with some funds
    initBank(List((arunava, 1000.00), (asim, 1000.00)))

    //success scenario - output should be (900.00, 1100.00)
    println(transfer(arunava, asim, 100))
    //failure scenario - output should be "User Auth Failed"
    println(transfer(User(arunava.uid, arunava.name, "wrong_password"), asim, 100))
    //failure scenario - output should be "Insufficient Funds"
    println(transfer(arunava, asim, 1000))
  }
}
