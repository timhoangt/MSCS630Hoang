/**
  * file: Driver_lab5.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 5
  * due date: March 15, 2020
  * version: 1
  * 
  * This file contains method for testing the implementation
  * by calling aesRoundKeys() in AESCipher.java providing valid data.
  *
  */

import java.util.Scanner;

/**
   * Driver_lab5
   * 
   * This class takes in standard input and sends in the hexadecimal key
   * to AES() to recieve AES cipher.
   */
public class Driver_lab5 {

  /**
    * main
    * This method takes in standard input and sends it to AES()
    * in AEScipher.java to receive the cipherText and prints it.
    * 
    * Parameters:
    *   String: the String recieved from the standard input which is the 
    *           128 bit Hexadecimal key.
    *.  String: the String recieved from the standard input which is the 
    *           plaintext to be encrypted.
    */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    String keyHex = scanner.nextLine();
    String pTextHex = scanner.nextLine();
    String cTextHex = AEScipher.AES(pTextHex, keyHex);

    System.out.println(cTextHex);
  }
}
