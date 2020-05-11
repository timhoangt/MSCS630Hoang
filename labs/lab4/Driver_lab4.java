/**
  * file: Driver_lab4.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 4
  * due date: March 1, 2020
  * version: 1
  * 
  * This file contains method for testing the implementation
  * by calling aesRoundKeys() in AESCipher.java providing valid data.
  *
  */

import java.util.Scanner;

/**
   * Driver_lab4
   * 
   * This class takes in standard input and sends in the hexadecimal key
   * to aesRoundKeys() to recieve all of the round keys.
   */
public class Driver_lab4 {

  /**
    * main
    * This method takes in standard input and sends it to aesRoundKeys()
    * in AESCipher.java to receive all of the round keys and prints them.
    * 
    * Parameters:
    *   String: the String recieved from the standard input which is the 
    *           128 bit Hexadecimal key.
    */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    String KeyHex = input.nextLine();

    String[] roundKeysHex = AESCipher.aesRoundKeys(KeyHex);

    for (String roundKey : roundKeysHex)
      System.out.println(roundKey);
  }
}
