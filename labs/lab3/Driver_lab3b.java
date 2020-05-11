/**
  * file: Driver_lab3b.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 3
  * due date: Feburary 16, 2020
  * version: 1
  * 
  * This file contains methods for breaking down a plaintext file
  * into bytes, conveerting thier ASCII codes into Hex, and storing them
  * into two dimensional arrays with a buffer.
  *
  */

import java.util.Scanner;

 /**
   * Driver_lab3b
   * 
   * This class takes a plaintext file, encrypts it into
   * hexadecimal representations of their ASCII codes with a buffer,
   * and prints the resulting two dimensional arrays.
   */
class Driver_lab3b {

  /**
    * getHexMatP
    *
    *
    * This function iteratively gets the greatest common denominator
    * from two integers by subtracting them from each other until they are
    * equal to each other.
    * 
    * Parameters:
    * s: The char to be used as a buffer.
    *
    * p: The string message being encrypted.
    * 
    * Return value: ans, the 4x4 two dimensional array
    * containing the encrypted message with buffer.
    */
  public static int[][] getHexMatP(char s, String p) {
    int[][] ans = new int[4][4];
    int pInt;
    int sInt;
    int counter2 = 0;

    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        if(p.length()-counter2 > 0){
          pInt = (int)p.charAt(counter2);
          ans[j][i] = pInt;
          counter2++;
        }

        else{
          sInt = (int)s;
          ans[j][i] = sInt;
          counter2++;
        } 
      }
    }
    return ans;
  }

  /**
    * main
    * This method takes a plaintext file, sends the buffer character and
    * string message to be encrypted to the getHexMatp
    * function and prints the resulting two dimensional array.
    * 
    * Parameters:
    *   plainText: the String that is being converted
    */
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    char s = input.next().charAt(0);
    input.nextLine();
    String p  = input.nextLine();
    int size = p.length();
    int counter = 0;
    
    while(size > 16){
      String tempString = p.substring(counter, counter + 16);
      size = size - 16;
      counter = counter + 16;
      int[][] ans = getHexMatP(s, tempString);
      for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
          String hex = Integer.toHexString(ans[i][j]).toUpperCase();
          System.out.print(hex + " ");
        }
        System.out.println();
      }
      System.out.println();
    }

    if(size > 0 && size <= 16){
      String tempString2 = p.substring(counter, counter + size);
      int[][] ans = getHexMatP(s, tempString2);
      for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
          String hex = Integer.toHexString(ans[i][j]).toUpperCase();
          System.out.print(hex + " ");
        }
        System.out.println();
      }
      if(size == 16){
        System.out.println();
        tempString2 = Character.toString(s);
        ans = getHexMatP(s, tempString2);
        for(int i = 0; i < 4; i++){
          for(int j = 0; j < 4; j++){
            String hex = Integer.toHexString(ans[i][j]).toUpperCase();
            System.out.print(hex + " ");
          }
          System.out.println();
        }
      }
    }
  } 
}
