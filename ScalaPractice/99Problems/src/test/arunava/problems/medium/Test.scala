package test.arunava.problems.medium

/**
  * Created by arunavas on 24/7/16.
  */
object Test {
  def main(args: Array[String]): Unit = {
    val ftm = new FirstTenMedium

    val listOfList = List(List.range(0, 5), List.range(5, 10))
    val list = List(0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 0, 0, 0)
    val charList = ('a' to 'z').toList
    println("List Of List: %s".format(listOfList))
    println("list: %s".format(list))
    println("char list: %s".format(charList))
    println("1. 7th Problem: Flattened listOfList: %s".format(ftm.flatten(listOfList)))
    println("2. 8th Problem: Eliminate consecutive duplicates from the list: %s".format(ftm.compress(list)))
    println("3. 9th Problem: Pack consecutive duplicates into sublists from the list: %s".format(ftm.pack(list)))
    val encodedList = ftm.encode(list)
    println("4. 11th Problem: Modified Run-length encoding of the list with duplicate elements: %s".format(encodedList))
    println("5. 12th Problem: Decode a Run-length encoded list: %s".format(ftm.decode(encodedList)))
    println("6. 14th Problem: Replicate the elements of the list the given number of times: %s".format(ftm.replicate(list, 3)))
    println("7. 15th Problem: Drop every N'th element from the list: %s".format(ftm.drop(charList, 3)))
    println("8. 17th Problem: Extract a slice from the list: %s".format(ftm.slice(charList, 2, 6)))
    println("9. 18th Problem: Rotate the list N places to the left: %s".format(ftm.rotate(charList, 3)))
    println("9. 18th Problem: Rotate the list N places to the left: %s".format(ftm.rotate(charList, -3)))
  }
}
