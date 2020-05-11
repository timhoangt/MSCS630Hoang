/**
  * file: AESCipher.java
  * author: Timothy Hoang
  * course: MSCS 630
  * assignment: Project
  * due date: May 10, 2020
  * version: 1
  * 
  * This file contains methods for the full
  * AES encryption.
  *
  */

import java.util.ArrayList;


/**
  * AESCipher
  * 
  * This class takes a 128-bit hex key and finds the round keys
  * assiciated with it according to sBox and rCon.
  */
class AEScipher {

  /**
    * This sBox allows you to transform bytes According to Table1 in the lab spanning
    * 0-F in both the columns and rows with the row being the first symbol and the column
    * bring the second symbol.
    */
  private static int[] sBox = new int[] {
    0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
    0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
    0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
    0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
    0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
    0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
    0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
    0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
    0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
    0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
    0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
    0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
    0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
    0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
    0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
    0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
  };

  /**
    * This table allows you to get the i-th round constant going by column from 
    * 1-15.
    */
  private static int[] rCon = new int[] {
    0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
    0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
    0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
    0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
    0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
    0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
    0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
    0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
    0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
    0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
    0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
    0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
    0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
    0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
    0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
    0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
  };

  /**
    * This table reverts the sBox transformation.
    */
  private static int[] reverseSBox = new int[] {
    0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
    0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
    0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
    0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
    0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
    0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
    0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
    0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
    0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
    0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
    0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
    0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
    0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
    0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
    0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
    0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
  };

  /**
    * aesRoundKeys 
    *
    * This function produces 11 round keys for each element in KeyHex taken from
    * Driver_lab4. It returns roundKeysHex which is an array of the 11 round keys
    * from each each element in the initial key produces.
    * 
    * Parameters:
    * KeyHex: A 128-bit key delivered from Driver_lab4.java that has 11 elements for which to 
    *         make round keys from.
    *
    * Return value: The array of round keys.
    */
  static String[] aesRoundKeys(String KeyHex) {

    String[] roundKeysHex = new String[11];

    ArrayList<Character> values = new ArrayList<>();
    for (char a : KeyHex.toCharArray()) {
      values.add(a);
    }

    String[][] k = new String[4][4];
    for (int i = 0; i < k.length; i++) {
      for (int j = 0; j < k[0].length; j++) {
        k[j][i] = values.remove(0).toString() + values.remove(0).toString();
      }
    }

    String[][] w = new String[4][44];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        w[j][i] = k[j][i];
      }
    }

    for (int j = 4; j < w[0].length; j++) {
      if (j % 4 != 0) {
        for (int index = 0; index < w.length; index++) {
          w[index][j] = XOR(w[index][j - 4], w[index][j - 1]);
        }
      }

      else {
        String rCon = aesRCon(j / 4);

        String[] temp = new String[4];

        for (int index = 0; index < w.length; index++) {
          temp[index] = w[index][j - 1];
        }

        temp = shift(temp);

        for (int index = 0; index < 4; index++) {
          temp[index] = aesSBox(Integer.parseInt(temp[index], 16));
        }

        temp[0] = XOR(rCon, temp[0]);

        for (int index = 0; index < w.length; index++) {
          w[index][j] = XOR(w[index][j - 4], temp[index]);
        }
      }
    }

    int count = 0;
    while (count != 44) {
      String[][] matrix = new String[4][4];
      for (int i = 0; i < w.length; i++) {
        for (int j = 0; j < w.length; j++) {
          matrix[j][i] = w[j][i + count];
        }
      }

      roundKeysHex[count / 4] = generateString(matrix).toUpperCase();
      count += 4;
    }
    return roundKeysHex;
  }

  /**
    * aesSBox
    *
    * This function reads the SBox and allows you to transform the bytes.
    * returns outHex which is the transformed byte
    * 
    * Parameters:
    * inHex: The byte you are transforming
    *
    * Return value: The transformed byte
    */
  private static String aesSBox(int inHex) {
    return Integer.toHexString(sBox[inHex]);
  }

  /**
    * undoSBox
    *
    * This function reads the reverseSBox and allows you to transform the bytes.
    * returns undone SBox transformation.
    * 
    * Parameters:
    * inHex: The byte you are transforming
    *
    * Return value: The transformed byte
    */
  private static String undoSBox(int inHex) {
    return Integer.toHexString(reverseSBox[inHex]);
  }

  /**
    * aesRCon
    *
    * This function gets each round's constant according to rCon.
    * 
    * Parameters:
    * round: the i'th round
    *
    * Return value: The round's constant
    */
  private static String aesRCon(int round) {
    return Integer.toHexString(rCon[round]);
  }

  /**
    * XOR
    *
    * This function performs the XOR operation using the corresponding round constant and returns
    * the new byte string.
    * 
    * Parameters:
    * byteOne: The first byte
    * byteTwo: The second byte
    * 
    * Return value: The exclusive or byte
    */
  private static String XOR(String byteOne, String byteTwo) {
    String byteXOR = Integer.toHexString(Integer.parseInt(byteOne, 16) ^ Integer.parseInt(byteTwo, 16));
    if (byteXOR.length() < 2) {
      return "0" + byteXOR;
    }
    else {
      return byteXOR;
    }
  }

  /**
    * shift
    *
    * This function shifts the array by one.
    * 
    * Parameters:
    * array: the array you are shifting
    *
    * Return value: The array shifted by one.
    */
  private static String[] shift(String[] array) {
    return new String[]{array[1], array[2], array[3], array[0]};
  }

  /**
    * generateString
    *
    * This function turns a 4x4 matrix into a string.
    * 
    * Parameters:
    * matrix: the matrix you are turning into a string
    * 
    * Return value: The string that was that matrix
    */
  private static String generateString(String[][] matrix) {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sb.append(matrix[j][i]);
      }
    }

    return sb.toString();
  }

  /**
    * AESStateXOR
    *
    * This function finds the XOR of the elements for the input matrix.
    * 
    * Parameters:
    * sHex: the matrix of the sting input by the user.
    * keyHex: the matrix key input by the user.
    * 
    * Return value: The matrix of XOR values from the sHex and keyHex
    */
  private static String[][] AESStateXOR(String[][] sHex, String[][] keyHex) {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sHex[i][j] = XOR(sHex[i][j], keyHex[i][j]);
      }
    }

    return sHex;
  }

  /**
    * AESNibbleSub
    *
    * This function finds the corresponding SBox transformations of the input matrix.
    * 
    * Parameters:
    * inStateHex: The matrix whose elements are to be transformed by the SBox.
    * 
    * Return value: The matrix where the elements from the input have been transformed
    * through the SBox.
    */
  private static String[][] AESNibbleSub(String[][] inStateHex) {

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        inStateHex[i][j] = aesSBox(Integer.parseInt(inStateHex[i][j], 16));
      }
    }
    return inStateHex;
  }

  /**
    * AESShiftRow
    *
    * This function transforms the input state matrix into the output state matrix
    * with the shift operation.
    * 
    * Parameters:
    * inStateHex: The matrix whose elements are to be shifted and transformed
    * into the output state matrix.
    * 
    * Return value: The matrix where the elements have been shifted.
    */
  private static String[][] AESShiftRow(String[][] inStateHex) {

    String[][] shiftedHex = new String[4][4];

    for (int index = 0; index < 4; index++) {
      shiftedHex[0][index] = inStateHex[0][index];
      shiftedHex[1][index] = inStateHex[1][(index + 1) % 4];
      shiftedHex[2][index] = inStateHex[2][(index + 2) % 4];
      shiftedHex[3][index] = inStateHex[3][(index + 3) % 4];
    }
    return shiftedHex;
  }

  /**
    * AESMixColumn
    *
    * This function transforms the input state matrix into the output state matrix
    * with the mix column operation using multiplication in the Galois Fields.
    * 
    * Parameters:
    * inStateHex: The matrix whose elements are being transformed with the Mix Column operation.
    * 
    * Return value: The matrix where the elements from the input have been transformed
    * with the Mix Column operation.
    */
  private static String[][] AESMixColumn(String[][] inStateHex) {

    int M[][] = new int[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        M[i][j] = Integer.parseInt(inStateHex[i][j], 16);
      }
    }

    for (int c = 0; c < 4; c++) {
      inStateHex[0][c] = Integer.toHexString(
              galois(0x02, M[0][c]) ^ galois(0x03, M[1][c]) ^ M[2][c] ^ M[3][c]).toUpperCase();
      inStateHex[1][c] = Integer.toHexString(
              M[0][c] ^ galois(0x02, M[1][c]) ^ galois(0x03, M[2][c]) ^ M[3][c]).toUpperCase();
      inStateHex[2][c] = Integer.toHexString(
              M[0][c] ^ M[1][c] ^ galois(0x02, M[2][c]) ^ galois(0x03, M[3][c])).toUpperCase();
      inStateHex[3][c] = Integer.toHexString(
              galois(0x03, M[0][c]) ^ M[1][c] ^ M[2][c] ^ galois(0x02, M[3][c])).toUpperCase();
    }

    for (int i = 0; i < 4; i++){
      for (int j = 0; j < 4; j++) {
        int length = inStateHex[i][j].length();
        if (length < 2) {
          inStateHex[i][j] = "0" + inStateHex[i][j];
        }
        if (length > 2) {
          inStateHex[i][j] = "" + inStateHex[i][j].charAt(length - 2) + inStateHex[i][j].charAt(length - 1);
        }
      }
    }
    return inStateHex;
  }

  /**
    * AES
    *
    * This function performs AES encryption with nibble substitution, row shifting, column mixing,
    * and adding the key until the last round where you no longer mix the columns.
    * 
    * Parameters:
    * pTextHex: The hexadecimal plaintext.
    * kHex: The hexadecimal key.
    * 
    * Return value: The AES ciphertext all in uppercase.
    */
  static String AES(String pTextHex, String kHex) {
    String[] secureKeys = aesRoundKeys(kHex);
    String[][] sHex = generateMatrix(pTextHex, 4, 4);

    String[][] keyHex = generateMatrix(secureKeys[0], 4, 4);
    String[][] outStateHex = AESStateXOR(sHex, keyHex);

    for (int key = 1; key < secureKeys.length - 1; key++) {
      outStateHex = AESNibbleSub(outStateHex);
      outStateHex = AESShiftRow(outStateHex);
      outStateHex = AESMixColumn(outStateHex);
      keyHex = generateMatrix(secureKeys[key], 4, 4);
      outStateHex = AESStateXOR(outStateHex, keyHex);
    }

    outStateHex = AESNibbleSub(outStateHex);
    outStateHex = AESShiftRow(outStateHex);
    keyHex = generateMatrix(secureKeys[10], 4, 4);
    outStateHex = AESStateXOR(outStateHex, keyHex);

    return generateString(outStateHex).toUpperCase();
  }

  /**
    * generateMatrix
    *
    * This function creates a matrix given a string and the dimensions.
    * 
    * Parameters:
    * hexString: The string to be made into a matrix.
    * rows: The integer representing the nubmer of rows in the matrix.
    * columns: The integer representing the nubmer of columns in the matrix.
    * 
    * Return value: The matrix with all the elements in the string.
    */
  private static String[][] generateMatrix(String hexString, int rows, int columns) {

    ArrayList<Character> values = new ArrayList<>();
    for (char a : hexString.toCharArray()) {
      values.add(a);
    }

    String[][] k = new String[rows][columns];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        k[j][i] = values.remove(0).toString() + values.remove(0).toString();
      }
    }

    return k;
  }

  /**
    * galois
    *
    * This function performs multiplication in the Galois Field.
    * 
    * Parameters:
    * a: The first integer to be multiplied.
    * b: The second integer to be multiplied.
    * 
    * Return value: The product of the two integers in the Galois Field.
    */
  private static int galois(int a, int b) {

    int i = 0;
    for (int c = 0; c < 8; c++) {
      if ((b & 1) != 0) {
        i ^= a;
      }

      boolean high = (a & 0x80) != 0;
      a <<= 1;
      if (high) {
        a ^= 0x1b;
      }
      b >>= 1;
    }

    return i;
  }

}
