package test.arunava.scala.week3

class Rational(val n: Int, val d: Int) {
  require(d > 0, "Denominator must be greater than zero")
  def this(n: Int) = this(n, 1)
  
  private def gcd(x: Int, y: Int): Int = if(y == 0) x else gcd(y, x % y)
  private val g = gcd(n, d)
  
  private val numer = n / g
  private val denum = d / g
  
  private def flip = new Rational(this.denum, this.numer)
  
  def unary_- : Rational = new Rational(-this.numer, this.denum)
  
  def + (that: Rational) = new Rational((this.numer * that.denum) + (this.denum * that.numer), this.denum * that.denum)
  def - (that: Rational) = this + (-that)
  def * (that: Rational) = new Rational(this.numer * that.numer, this.denum * that.denum)
  def / (that: Rational) = this * that.flip
  
  def < (that: Rational) = (this.numer * this.denum) < (that.numer * that.denum)
  def > (that: Rational) = that < this
  def == (that: Rational) = (this.numer * this.denum) == (that.numer * that.denum)
  def != (that: Rational) = !(this == that)
  def <= (that: Rational) = (this < that) || (this == that)
  def >= (that: Rational) = that <= this
  
  def min(that: Rational) = if (this < that) this else that
  def max(that: Rational) = if (this < that) that else this
  
  override def toString = this.numer + "/" + this.denum
  
}