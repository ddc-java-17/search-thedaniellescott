package edu.cnm.deepdive;

public class Search {

  public int linearSearch(int needle, int[] haystack) {
    int position = -1;
    for (int i = 0; i < haystack.length; i++) {
      if (haystack[i] == needle) {
        position = i;
        break;
      }
    }
    return position;
  }

  public int binarySearch(int needle, int[] haystack) {
    return binarySearch(needle, haystack, 0, haystack.length);
  }

  private int binarySearch(int needle, int[] haystack, int startIndex, int endIndex) {
    int position;
    if (endIndex > startIndex) {
      int midIndex = (startIndex + endIndex) / 2;
      int midValue = haystack[midIndex];
      if (midValue == needle) {
        position = midIndex;
      } else if (midValue > needle) {
        position = binarySearch(needle, haystack,startIndex, midIndex);
      } else {
        position = binarySearch(needle, haystack, midIndex + 1, endIndex);
      }
    } else {
      position = - startIndex - 1;
    }
    return position;
  }

}
