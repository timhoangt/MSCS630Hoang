/**
  * file: Driver_project.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: Project
  * due date: May 10, 2020
  * version: 1
  * 
  * This file contains methods for reading a Lexicon, creating a readable password,
  * taking an input message from the user, enciphering the text with the password,
  * and deciphering the cipher text with the password.
  *
  */

import java.io.*;
import java.nio.*;
import java.util.*;
import java.nio.charset.*;
import java.math.*;
import static java.lang.String.format;

/**
  * Driver_project
  * 
  * This class reads a Lexicon, creates a readable password,
  * takes an input message from the user, enciphers the text with the password,
  * and deciphers the cipher text with the password.
  */
class Driver_project {
  
  /**
    * main
    *
    * This function reads a Lexicon, creates a readable password,
    * takes an input message from the user, enciphers the text with the password,
    * and deciphers the cipher text with the password.
    * 
    * Parameters:
    * args: The title of the Lexicon
    * text: The 16 chracter message you would like to encrypt
    *
    * Return values: 
    * none
    *
    * Printed values:
    * pass: The randomly genereate password made from the Lexicon and possibly random characters
    * keyHex: The pass in Hex
    * hexText: The text you input in Hex
    * pTextHex: The enciphered hexText
    * decryptedText: The deciphered pTextHex
    */
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

  /**
    * readFile
    *
    * This function reads a Lexicon, stores it in an array, 
    * and creates a list of that array sorted by word size
    * 
    * Parameters:
    * args: The title of the Lexicon
    *
    * Return value: 
    * stringArr: The array of all of the words in the Lexicon
    */
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

  /**
    * englishPass
    *
    * This function populates the passwrod with random words from the Lexicon
    * and fills in the rest if need be.
    * 
    * Parameters:
    * map: the map of words arranged by word size
    * leftover: the size that the password needs to be
    * lengthMin: the size of the smallest word in the map
    * lengthMax: the size of the largst word in the map
    *
    * Return value: 
    * pass: the password created from the Lexicon
    */
  private static String englishPass(Map<Integer, List<String>> map, int leftover, int lengthMin, int lengthMax) {
    String pass = "";
    String word = "";
    
    //While we still need characters to fulfill our password length requirements
    while (leftover >= 1) {

      //If the smallest word is smaller than or equal to the amount of characters left
      if (lengthMin <= leftover){
        word = getWord(map, leftover, lengthMin, lengthMax);
      }
      
      //Else if the smallest word is bigger than the amount of character left put in random characters
      else if (lengthMin > leftover){
        word = fillIn(leftover);
      }

      //Add it to the password and compute the characters needed
      pass = pass.concat(word);
      leftover = leftover - word.length();

    }
    return pass;
  }

  /**
    * getWord
    *
    * This function chooses a random word taken from the Lexicon given the amount of 
    * characters needed and sends it to englishPass to be added to the password
    * 
    * Parameters:
    * map: the map of words arranged by word size
    * leftover: the size that the password needs to be
    * lengthMin: the size of the smallest word in the map
    * lengthMax: the size of the largest word in the map
    *
    * Return value: 
    * word: the word taken from the Lexicon to be added to the password
    */
  private static String getWord(Map<Integer, List<String>> map, int leftover, int lengthMin, int lengthMax) {
    //Get array of keys
    Set<Integer> keys = map.keySet();
    Integer[] keysArr = keys.toArray(new Integer[keys.size()]);
    int currentKey = -1;
    int rnd = -1;
    
    //Get random key between the minimum word length and the number of characters left
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

  /**
    * fillIn
    *
    * This function creates a random string of letters and numbers to be added to the password
    * 
    * Parameters:
    * leftover: the size that the password needs to be complete
    *
    * Return value: A random string of letters and numbers to be added to the password
    */
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

  /**
    * toHex
    *
    * This function transforms characters in UTF_8 to Hex values
    * 
    * Parameters:
    * pass: the password in UTF_8
    *
    * Return value: The pass in Hex
    */
  private static String toHex(String pass){
    return String.format("%032x", new BigInteger(1, pass.getBytes(StandardCharsets.UTF_8)));
  }

}
