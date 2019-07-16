package test.arunava.scala

import java.util.Arrays

object QuickSort {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def sort(arr: Array[Int]): Array[Int] = {
  	if(arr.length <= 1) arr
  	else {
  		val pivot = arr(arr.length / 2)
  		Array.concat(sort(arr filter (pivot >)), arr filter (pivot ==), sort(arr filter { x => pivot < x }))
  	}
  }                                               //> sort: (arr: Array[Int])Array[Int]
  
  val arr = Array(2, 0, 4, 1)                     //> arr  : Array[Int] = Array(2, 0, 4, 1)
  println(Arrays.toString(sort(arr)))             //> [0, 1, 2, 4]
  val x = 10.+(10)                                //> x  : Int = 20
  
}