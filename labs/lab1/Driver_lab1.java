/**
  * file: Driver_lab1.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 1
  * due date: Feburary 2, 2020
  * version: 1
  * 
  * This file contains the methods for encrypting plaintext
  * into A0Z25.
  */

import java.util.Scanner;

 /**
   * Driver_lab1
   * 
   * This class takes a plaintext file, encrypts it
   * with the A0Z25 method, and prints the encrypted text.
   */
class Driver_lab1{

  /**
     * str2int
     *
     * This function converts the string into an array of 
     * integers according to the character.
     * 
     * Parameters:
     *   plainText: the String that it being converted
     * 
     * Return value: the Integer array encrypted which is
     * computed by taking the ASCII character code and moving
     * the value
     */
  public static int[] str2int(String plainText){
    //Converting to uppercase makes the job easier since there are less numbers to worry about
    plainText = plainText.toUpperCase();
    char[] chars = plainText.toCharArray();
    int[] encrypted = new int[plainText.length()*2];

    for(int i = 0; i < plainText.length(); i++){
      if ((int)chars[i] > 64){
        encrypted[i] = (int)chars[i] - 65;
      }
      else if ((int)chars[i] == 32){
        encrypted[i] = 26;
      }
    }

    return encrypted;
  }

  /**
     * main
     *
     * This method takes a plaintext file, sends to to the
     * str2int function and prints the resulting array.
     * 
     * Parameters:
     *   plainText: the String that is being converted
     */
  public static void main(String[] args){ 
    Scanner input = new Scanner(System.in);
    while (input.hasNext()){
      String text = input.nextLine();
      int[] encrypted = str2int(text);

      for(int i = 0; i < text.length(); i++){
        System.out.print(encrypted[i]);
        System.out.print(" ");
      }
      System.out.println();

    }
  } 
} 
