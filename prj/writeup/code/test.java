/**
  * file: test.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: Project
  * due date: May 10, 2020
  * version: 1
  * 
  * This file contains method for testing the implementation
  * by calling InvNibbleSub(), InvShiftRow(), and InvMicColumn() in AESdecipher.java providing valid data.
  *
  */

import java.io.*;
import java.nio.*;
import java.util.*;
import java.nio.charset.*;
import java.math.*;
import static java.lang.String.format;

/**
  * test
  * 
  * This class pulls initial matrices through the new methods in AESdecipher.java
  * to see if they perform properly or not. It will print out the transformed matrices.
  */
class test {
  
  /**
    * main
    *
    * This functiontpulls initial matrices through the new methods in AESdecipher.java
    * to see if they perform properly or not
    * 
    * There are no parameters or return values as it is sinply for testing functions.
    * 
    */
  public static void main() throws Exception{
    int i;
    int j;

    String[][] testInvNibbleSub = { {"63","EB","9F","A0"}, 
                                    {"C0","2F","93","92"},
                                    {"AB","30","AF","C7"},
                                    {"20","CB","2B","A2"} };

    System.out.println("The original elements of the matrix are");
    for (i = 0; i < 4; i++) { 
        for (j = 0; j < 4; j++) 
            System.out.print(testInvNibbleSub[i][j] + "  "); 
        System.out.println(); 
    }

    String[][] resultInvNibbleSub = AESdecipher.AESInvNibbleSub(testInvNibbleSub);

    System.out.println("The result of InvNibbleSub() is");
    for (i = 0; i < 4; i++) { 
      for (j = 0; j < 4; j++) {
        System.out.print(resultInvNibbleSub[i][j] + "  "); 
      }
      System.out.println(); 
    }

    String[][] testInvShiftRow = { {"63","EB","9F","A0"}, 
                                   {"2F","93","92","C0"},
                                   {"AF","C7","AB","30"},
                                   {"A2","20","CB","2B"} };

    System.out.println("The original elements of the matrix are");
    for (i = 0; i < 4; i++) { 
        for (j = 0; j < 4; j++) 
            System.out.print(testInvShiftRow[i][j] + "  "); 
        System.out.println(); 
    }

    String[][] resultInvShiftRow = AESdecipher.AESInvShiftRow(testInvShiftRow);

    System.out.println("The result of InvShiftRow() is");
    for (i = 0; i < 4; i++) { 
      for (j = 0; j < 4; j++) {
        System.out.print(resultInvShiftRow[i][j] + "  "); 
      }
      System.out.println(); 
    }

    String[][] testInvMixColumn = { {"BA","84","E8","1B"}, 
                                    {"75","A4","8D","40"},
                                    {"F4","8D","06","7D"},
                                    {"7A","32","0E","5D"} };

    System.out.println("The original elements of the matrix are");
    for (i = 0; i < 4; i++) { 
        for (j = 0; j < 4; j++) 
            System.out.print(testInvMixColumn[i][j] + "  "); 
        System.out.println(); 
    }

    String[][] resultInvMixColumn = AESdecipher.AESInvMixColumn(testInvMixColumn);

    System.out.println("The result of InvMixColumn() is");
    for (i = 0; i < 4; i++) { 
      for (j = 0; j < 4; j++) {
        System.out.print(resultInvMixColumn[i][j] + "  "); 
      }
      System.out.println(); 
    }
  }
}
