//-----------------------------------------------------------------------------
// Name: AJ Wong
// CruzID: 1652596
// Class: CMPS 101
// Date: 9 Apr 2019
// Desc: Implementation file for List ADT
// File Name: List.java
//-----------------------------------------------------------------------------

import java.io.*;

public class List {

  private Node front;
  private Node back;
  private Node cursor;
  private int cursorIndex;
  private int length;

  private class Node {
    int data;
    Node prev;
    Node next;

    Node(int x) {
      data = x;
      prev = null;
      next = null;
    }
  }

  // Creates a new empty List.
  List() {
    front = null;
    back = null;
    cursor = null;
    cursorIndex = -1;
    length = 0;
  }

  private int find(int index) {
    Node N = front;
    for (int i = 0; i < index; i++) {
      N = N.next;
    }
    return N.data;
  }

  // Returns the number of elements in this List.
  public int length() {
    return length;
  }

  // If cursor is defined, returns the index of the cursor element, otherwise returns -1.
  public int index() {
    return cursorIndex;
  }

  // Returns front element.
  // Pre: length() > 0
  public int front() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: front() called on empty List");
    }
    return front.data;
  }

  // Returns back element.
  // Pre: length() > 0
  public int back() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: back() called on empty List");
    }
    return back.data;
  }

  // Returns cursor element.
  // Pre: length() > 0, index() >= 0
  public int get() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: get() called on empty List");
    }
    if (!(cursorIndex >= 0)) {
      throw new RuntimeException("List Error: get() called on List with undefined cursor");
    }
    return cursor.data;
  }

  // Returns true if and only if this List and L are the same integer sequence.
  // The states of the cursors in the two Lists are not used in determining equality.
  public boolean equals(List L) {
    boolean eq = true;
    Node n1 = front;
    Node n2 = L.front;
    if (this.length != L.length) {
      eq = false;
    }
    while (eq && (n1 != null && n2 != null)) {
      if (n1.data != n2.data) {
        eq = false;
      }
      n1 = n1.next;
      n2 = n2.next;
    }
    return eq;
  }

  // Resets this List to its original empty state.
  public void clear() {
    front = null;
    back = null;
    cursor = null;
    cursorIndex = -1;
    length = 0;
  }

  // If List is non-empty, places the cursor under the front element, otherwise does nothing.
  public void moveFront() {
    if (length > 0) {
      cursor = front;
      cursorIndex = 0;
    }
  }

  // If List is non-empty, places the cursor under the back element, otherwise does nothing.
  public void moveBack() {
    if (length > 0) {
      cursor = back;
      cursorIndex = length - 1;
    }
  }

  // If cursor is defined and not at front, moves cursor one step toward front of this List.
  // If cursor is defined and at front, cursor becomes undefined.
  // If cursor is undefined, does nothing.
  public void movePrev() {
    if (cursorIndex != -1) {  // if cursor is defined
      cursorIndex--;
      cursor = cursor.prev;
    }
  }

  // If cursor is defined and not at back, moves cursor one step toward back of this List.
  // If cursor is defined and at back, cursor becomes undefined.
  // If cursor is undefined, does nothing.
  public void moveNext() {
    if (cursorIndex != -1) {
      if (cursorIndex == length - 1) {  // if cursor is at the back of List
        cursorIndex = -1;
        cursor = null;
      }
      else {
        cursorIndex++;
        cursor = cursor.next;
      }
    }
  }

  // Insert new element into this List.
  // If List is non-empty, insertion takes place before front element.
  public void prepend(int data) {
    if (length > 0) {
      Node N = new Node(data);
      N.next = front;
      front.prev = N;
      front = N;
    }
    else {  // List is empty, so front and back will be the same
      front = new Node(data);
      back = front;
    }
    length++;
    cursorIndex++;
  }

  // Insert new element into this List.
  // If List is non-empty, insertion takes place after back element.
  public void append(int data) {
    if (length > 0) {
      Node N = new Node(data);
      N.prev = back;
      back.next = N;
      back = N;
    }
    else {  // List is empty, so front and back will be the same
      back = new Node(data);
      front = back;
    }
    length++;
  }

  // Insert new element before cursor.
  // Pre: length() > 0, index() >= 0
  public void insertBefore(int data) {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: insertBefore() called on empty List");
    }
    if (!(cursorIndex >= 0)) {
      throw new RuntimeException("List Error: insertBefore() called on List with undefined cursor");
    }
    if (cursorIndex == 0) { // if cursor is at the front of List
      this.prepend(data);
    }
    else {
      Node N = new Node(data);
      N.prev = cursor.prev;
      N.next = cursor;
      cursor.prev.next = N;
      cursor.prev = N;
      length++;
      cursorIndex++;
    }
  }

  // Insert new element after cursor.
  // Pre: length() > 0, index() >= 0
  public void insertAfter(int data) {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: insertAfter() called on empty List");
    }
    if (!(cursorIndex >= 0)) {
      throw new RuntimeException("List Error: insertAfter() called on List with undefined cursor");
    }
    if (cursorIndex == length - 1) { // if cursor is at the back of List
      this.append(data);
    }
    else {
      Node N = new Node(data);
      N.prev = cursor;
      N.next = cursor.next;
      cursor.next.prev = N;
      cursor.next= N;
      length++;
    }
  }

  // Deletes the front element.
  // Pre: length() > 0
  public void deleteFront() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: deleteFront() called on empty List");
    }
    if (length == 1) {
      clear();
    }
    else {
      front = front.next;
      front.prev = null;
      length--;
      cursorIndex--;
    }
  }

  // Deletes the back element.
  // Pre: length() > 0
  public void deleteBack() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: deleteBack() called on empty List");
    }
    if (length == 1) {
      clear();
    }
    else {
      back = back.prev;
      back.next = null;
      length--;
      if (length == cursorIndex) {
        cursor = null;
        cursorIndex = -1;
      }
    }
  }

  // Deletes cursor element, making cursor undefined.
  // Pre: length() > 0, index() >= 0
  public void delete() {
    if (!(length > 0)) {
      throw new RuntimeException("List Error: delete() called on empty List");
    }
    if (!(cursorIndex >= 0)) {
      throw new RuntimeException("List Error: delete() called on List with undefined cursor");
    }
    if (cursorIndex == 0) {
      deleteFront();
    }
    else if (cursorIndex == length - 1) {
      deleteBack();
    }
    else {
      cursor.prev.next = cursor.next;
      cursor.next.prev = cursor.prev;
      length--;
      cursorIndex = -1;
      cursor = null;
    }
  }

  // Overrides Object's toString method.
  // Returns a String representation of this List consisting of a space separated sequence of integers, with front on left.
  public String toString() {
    StringBuffer sb = new StringBuffer();
    Node N;

    for (N = front ; N != null; N = N.next) {
       sb.append(N.data).append(" ");
    }
    return new String(sb);
  }

  // Returns a new List representing the same integer sequence as this List.
  // The cursor in the new List is undefined, regardless of the state of the cursor in this List.
  // This List is unchanged.
  public List copy() {
    List L = new List();
    for (int i = 0; i < length; i++) {
      L.append(this.find(i));
    }
    return L;
  }

  // Returns a new List which is the concatenation of this List followed by L.
  // The cursor in the new List is undefined, regardless of the states of the cursors in this List and L.
  // The states of this List and L are unchanged.
  public List concat(List L) {
    List A = new List();
    for (int i = 0; i < length; i++) {
       A.append(this.find(i));
    }
    for (int i = 0; i < L.length; i++) {
       A.append(L.find(i));
    }
    return A;
  }

}
