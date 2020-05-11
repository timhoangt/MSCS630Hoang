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

import java.io.*;
import java.nio.*;
import java.util.*;
import java.nio.charset.*;
import java.math.*;
import static java.lang.String.format;

class Driver_project {
  
  public static void main(String args[]) throws Exception{
    //The number of characters left
    int leftover = 16;
    String[] stringArr = readFile(args[0]);

    //Get the size of array for the minimum and maximum word length
    int size = stringArr.length;
    int lengthMin = stringArr[0].length();
    int lengthMax = stringArr[size-1].length();

    //Maps the array into several different arrays according to word length
    Map<Integer, List<String>> map = new HashMap<>();

    for (int i = 0; i < stringArr.length; i++) {
      List< String> temp = map.getOrDefault(stringArr[i].length(),new ArrayList<>());
      temp.add(stringArr[i]);
      map.put(stringArr[i].length(),temp);
    }

    String pass = englishPass(map, leftover, lengthMin, lengthMax);
    //String pass = fillIn(leftover);
    System.out.println("pass is " + pass);
    String keyHex = toHex(pass);
    System.out.println("keyhex = " + keyHex);
    System.out.println("Write a 16 char message you would like to encrpyt:");
    Scanner scanner = new Scanner(System.in);
    String text = scanner.nextLine();
    String pTextHex = toHex(text);
    System.out.println("hextext = " + pTextHex);

    String encryptedText = AEScipher.AES(pTextHex, keyHex);
    System.out.println("cipher text is " + encryptedText);

    String decryptedText = AESdecipher.AESdecrypt(encryptedText, keyHex);
    System.out.println("deciphered text is " + decryptedText);    
  }

  private static String[] readFile(String args) {
    //Store txt file into array.
    String[] stringArr = {""};
    try {
      BufferedReader in = new BufferedReader(new FileReader(args));
      String str;

      List<String> list = new ArrayList<String>();
      try{
        while((str = in.readLine()) != null){
        list.add(str);
        }
      }
      catch(IOException e) {
        e.printStackTrace();
      }

      stringArr = list.toArray(new String[0]);

      //Sorts array by word size.
      Arrays.sort(stringArr, Comparator.comparingInt(String::length));
    }

    catch (FileNotFoundException ex) {
      System.out.println("No file found by that name!");
      System.exit(0);
    }
    return stringArr;
  }


  private static String englishPass(Map<Integer, List<String>> map, int leftover, int lengthMin, int lengthMax) {
    String pass = "";
    String word = "";
    
    //While we still need characters to fulfill our password length requirements
    while (leftover >= 1) {

      //If the smallest word is smaller than or equal to the amount of characters left
      if (lengthMin <= leftover){
        word = getWord(map, leftover, lengthMin, lengthMax);
      }
      
      //Else if the smallest word is bigger than the amount of character left
      else if (lengthMin > leftover){
        word = fillIn(leftover);
      }

      pass = pass.concat(word);
      leftover = leftover - word.length();

    }
    return pass;
  }

  private static String getWord(Map<Integer, List<String>> map, int leftover, int lengthMin, int lengthMax) {
    //Get array of keys
    Set<Integer> keys = map.keySet();
    Integer[] keysArr = keys.toArray(new Integer[keys.size()]);
    int currentKey = -1;
    int rnd = -1;
    
    //Get random key between the mimum word length and the number of characters left
    while (rnd < 0 || rnd > keysArr.length-1){
      rnd = new Random().nextInt(leftover);
      if(rnd <= 0) {
      rnd = lengthMin - 1;
      }
    }
    
    currentKey = keysArr[rnd];

    //int currentKey = keysArr[rnd];
    leftover = leftover - currentKey;

    //Get list size
    int arraySize = map.get(currentKey).size();
    rnd = new Random().nextInt(arraySize);

    //Get the word
    String word = map.get(currentKey).get(rnd);
    return word;
  }

  private static String fillIn(int leftover) {
    // length is bounded by 256 Character 
    byte[] array = new byte[256]; 
    new Random().nextBytes(array); 

    String randomString = new String(array, Charset.forName("UTF-8")); 

    // Create a StringBuffer to store the result 
    StringBuffer r = new StringBuffer(); 

    // Append first 20 alphanumeric characters 
    // from the generated random String into the result 
    for (int k = 0; k < randomString.length(); k++) { 
      char ch = randomString.charAt(k); 
      if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') 
      || (ch >= '0' && ch <= '9')) && (leftover > 0)) { 
        r.append(ch); 
        leftover--; 
      } 
    } 
    // return the resultant string 
    return r.toString();
  }

  //Converts the String to Hex
  private static String toHex(String pass){
    return String.format("%032x", new BigInteger(1, pass.getBytes(StandardCharsets.UTF_8)));
  }

}
