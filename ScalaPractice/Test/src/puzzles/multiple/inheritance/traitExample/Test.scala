package puzzles.multiple.inheritance.traitExample

object Test {
  def main(args: Array[String]): Unit = {
    val t1: trait1 = new MultipleTraitClass
    val t2: trait2 = new MultipleTraitClass
    t1.implementedMethod
    t1.unImplementedMethod
    t2.implementedMethod
    t2.unImplementedMethod
  }
}