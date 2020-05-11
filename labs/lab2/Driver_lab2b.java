/**
  * file: Driver_lab1.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 2
  * due date: Feburary 7, 2020
  * version: 1
  * 
  * This file contains methods for getting the greatest common
  * denominator, x and y in the euclidean algorithm
  * given two positive long integers.
  */

import java.util.Scanner;

 /**
   * Driver_lab2b
   * 
   * This class takes a plaintext file, finds the greatest common
   * denominator, x, and y with the euclidAlgExt method, and prints it.
   */
class Driver_lab2b {

  /**
    * euclidAlgExt
    *
    * This function iteratively gets the greatest common denominator, x, and y
    * from two integers by passing b and the remainder of a / b into the same method
    * until there is no remainder. Once there is no remainder, it works backwards
    * swapping the x and y so that x = y and y = x - (x/y) * y.
    * 
    * Parameters:
    * a: First long integer given from main method
    *
    * b: Second long integer given from the main method
    * 
    * Return value: The array of long integers which are the greatest
    * common denominator, x, and y of the two parameters.
    */
  public static long[] euclidAlgExt(long a, long b){
    //If the remainder is 0, a is the gcd, x is 1, and y is 0
    if (b == 0){
      return new long[] { a, 1, 0 };
    }
    
    long[] nums = euclidAlgExt(b, a % b);
    long d = nums[0];
    long x = nums[2];
    long y = nums[1] - (a / b) * nums[2];
    return new long[] { d, x, y };
  }

  /**
    * main
    * This method takes a plaintext file, sends two longs on each line to the
    *  euclidAlgExt function and prints the resulting array.
    * 
    * Parameters:
    * plainText: the String that is being converted
    */
  public static void main(String[] args){ 
    Scanner input = new Scanner(System.in);
    while(input.hasNext()){
      String stra = input.next();
      long a = Long.parseLong(stra);
      String strb = input.next();
      long b = Long.parseLong(strb);
      long[] encrypted = euclidAlgExt(a, b);
      for(int i = 0; i < encrypted.length; i++){
        System.out.print(encrypted[i]);
        System.out.print(" ");
      }
      System.out.println();
    }
  } 
}
