package puzzles.multiple.inheritance.traitExample

trait trait1 {
  def implementedMethod = println("Implemented Method of trait1")
  def unImplementedMethod
}

trait trait2 {
  def implementedMethod = println("Implemented Method of trait2")
  def unImplementedMethod
}

class MultipleTraitClass extends trait1 with trait2 {
  def unImplementedMethod = println("Unimplemented Method implemented in MultipleTraitClass.")
  override def implementedMethod = {
    super.implementedMethod
    println("Implemeted Method overriden in MultipleTraitClass.")
  }
}