/**
  * file: Driver_lab1.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 2
  * due date: Feburary 7, 2020
  * version: 1
  * 
  * This file contains methods for getting the greatest common
  * denominator given two positive long integers.
  *
  */

import java.util.Scanner;

 /**
   * Driver_lab2a
   * 
   * This class takes a plaintext file, finds the greatest common
   * denominator with the euclidAlg method, and prints it.
   */
class Driver_lab2a {

  /**
    * euclidAlg
    *
    *
    * This function iteratively gets the greatest common denominator
    * from two integers by subtracting them from each other until they are
    * equal to each other.
    * 
    * Parameters:
    * a: First long integer given from main method
    *
    * b: Second long integer given from the main method
    * 
    * Return value: The long integer which is the greatest
    * common denominator of the two parameters.
    */
  public static long euclidAlg(long a, long b) {
    while (a != b){ 
      
      if (a > b){       
        a = a - b; 
      }        
      
      else{
        b = b - a;         
      }
    } 
    
    return a; 
  }

  /**
    * main
    * This method takes a plaintext file, sends two longs on each line to the
    *  euclidAlg function and prints the resulting long.
    * 
    * Parameters:
    *   plainText: the String that is being converted
    */
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    while(input.hasNext()){
      String stra = input.next();
      long a = Long.parseLong(stra);
      String strb = input.next();
      long b = Long.parseLong(strb);
      long encrypted = euclidAlg(a, b);
      System.out.println(encrypted);
    }
  } 
} 
