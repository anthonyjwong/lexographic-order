//-----------------------------------------------------------------------------
// Name: AJ Wong
// CruzID: 1652596
// Class: CMPS 101
// Date: 9 Apr 2019
// Desc: Sorts a list of words in lexographic order.
// File Name: Lex.java
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Lex {

  public static void main(String[] args) throws FileNotFoundException, IOException {

    Scanner in = null;
    PrintWriter out = null;
    List L;
    String[] word;
    int n = 0;

    if (args.length != 2) { // Check number of command line arguments
      System.err.println("Usage: Lex <input file> <output file>");
      System.exit(1);
    }

    in = new Scanner(new File(args[0]));
    while (in.hasNextLine()) {  // Counts number of lines in input file
      n++;
      in.nextLine();
    }
    in.close();

    word = new String[n];  // String array with the same length as the number of lines in input file
    in = new Scanner(new File(args[0]));  // Reset scanner for input file
    out = new PrintWriter(new FileWriter(args[1]));

    int i = 0;
    while (in.hasNextLine()) {  // Reads lines into String array
      word[i] = in.nextLine();
      i++;
    }

    L = new List();
    sort(word, L, n - 1);

    L.moveFront();
    while (L.index() >= 0) {
      int x = L.get();
      out.println(word[x]);
      L.moveNext();
    }

    in.close();
    out.close();
  }

  static void sort(String[] A, List L, int n) {
    if (n == 0) {
      L.append(n);
    }
    else if (n == 1) {
      sort(A, L, n - 1);
      L.moveBack();
      if(A[n].compareTo(A[L.get()]) < 0) {
        L.insertBefore(n);
      }
      else {
        L.insertAfter(n);
      }
    }
    else {
      sort(A, L, n - 1);
      L.moveBack();
      while (L.index() > 0 && A[n].compareTo(A[L.get()]) < 0) {
        L.movePrev();
      }
      if (L.index() == 0 && A[n].compareTo(A[L.get()]) < 0) {
        L.insertBefore(n);
      }
      else {
        L.insertAfter(n);
      }
    }
  }

}
