/**
  * file: Driver_lab3a.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: lab 3
  * due date: Feburary 16, 2020
  * version: 1
  * 
  * This file contains methods for getting the determinant 
  * in modulo m as an integer.
  *
  */

import java.util.Scanner;

 /**
   * Driver_lab3a
   * 
   * This class takes a plaintext file and finds the positive 
   * determinant in a modulo.
   */
class Driver_lab3a {

  /**
    * codeModDet
    *
    *
    * This function gets the determinant of the matrix and 
    * returns it in modulo m.
    * 
    * 
    * Parameters:
    * m: The modulo for which you are performing the functions in.
    *
    * A: The matrix for which you are getting the determinant for.
    * 
    * Return value: The integer for that represents the determinant in modulo m.
    */
  public static int codeModDet(int m, int[][] A) {
    int n = A.length;
    int determinant = 0;

    for (int i = 0; i < n; i++){
      for (int j = 0; j < n; j++){
        while (A[i][j] < 0){
          A[i][j] = A[i][j] + m;
        }
        A[i][j] = A[i][j]%m;
      }
    }




    if (n == 1){ 
      return A[0][0];
    }


    
    for (int i = 0; i < n; i++){

      if (n > 2){
        int column = 0;
        int row = i;


        int oldSize = A.length;
        int newSize = A.length - 1;
        
        int[][] tempMatrix = new int[newSize][oldSize];
        
        for (int l = 0; l < row; l++){
          for (int j = 0; j < oldSize; j++){
            tempMatrix[l][j] = A[l][j];
          }
        }

        
        for (int l = row; l < newSize; l++){
          for (int j = 0; j < oldSize; j++){
            tempMatrix[l][j] = A[l+1][j];
          }
        }

        int[][] newMatrix = new int[newSize][newSize];
        
        for (int l = 0; l < newSize; l++){
          for (int j = 0; j < column; j++){
            newMatrix[l][j] = tempMatrix[l][j];
          }
        }
        
        for (int l = 0; l < newSize; l++){
          for (int j = column; j < newSize; j++){
            newMatrix[l][j] = tempMatrix[l][j+1];
          }
        }
        int[][] B = newMatrix;


        determinant = determinant + A[i][0] * (int)(Math.pow(-1, i)) * codeModDet(m, B);
      }
      
      else if (n == 2){
        determinant = A[0][0] * A[1][1] - A[0][1] * A[1][0];
      }
    }
  
    int ans = determinant%m;

    while (ans < 0){
      ans = ans + m;
    }

    return ans;
  }



  /**
    * main
    * This method takes a plaintext file, sends the modulo
    * and tow dimensional array to codeModDet, and prints the answer.
    * 
    * Parameters:
    *   plainText: the String that contains the modulo, matrix size, and two dimensional array.
    */
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    String mStr = input.next();
    int m = Integer.parseInt(mStr);
    String nStr = input.next();
    int n = Integer.parseInt(nStr);
    int[][] A = new int[n][n];
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        String temp = input.next();
        A[i][j] = Integer.parseInt(temp);
      }
    }
    int ans = codeModDet(m, A);

    System.out.println(ans);
  }
}
