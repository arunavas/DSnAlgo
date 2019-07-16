package test.arunava.scala

import java.util.Arrays

object QuickSort {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(115); 
  println("Welcome to the Scala worksheet");$skip(234); 
  
  def sort(arr: Array[Int]): Array[Int] = {
  	if(arr.length <= 1) arr
  	else {
  		val pivot = arr(arr.length / 2)
  		Array.concat(sort(arr filter (pivot >)), arr filter (pivot ==), sort(arr filter { x => pivot < x }))
  	}
  };System.out.println("""sort: (arr: Array[Int])Array[Int]""");$skip(33); 
  
  val arr = Array(2, 0, 4, 1);System.out.println("""arr  : Array[Int] = """ + $show(arr ));$skip(38); 
  println(Arrays.toString(sort(arr)));$skip(19); 
  val x = 10.+(10);System.out.println("""x  : Int = """ + $show(x ))}
  
}
