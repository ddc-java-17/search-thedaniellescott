package edu.cnm.deepdive;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchTest {

  private static final int HAYSTACK_SIZE = 10_000_000;
  private static final int NUM_NEEDLES = 200;

  private static int[] unsortedHaystack;
  private static int[] sortedHaystack;
  private static int[] needles;
  private static int[] expectedLinearUnsorted;
  private static int[] expectedLinearSorted;
  private static int[] expectedBinarySorted;
  private Search search;

  @BeforeAll
  static void setupHaystacks() {
    RandomGenerator rng = RandomGenerator.getDefault(); // not necessarily the same as new Random()
    unsortedHaystack = IntStream.generate(rng::nextInt)
        .limit(HAYSTACK_SIZE)
        .toArray();
    sortedHaystack = IntStream.of(unsortedHaystack)
        .sorted()
        .toArray();
    needles = IntStream.generate(rng::nextInt)
        .limit(NUM_NEEDLES)
        .toArray();
    expectedBinarySorted = Arrays.stream(needles)
        .map((needle) -> Arrays.binarySearch(sortedHaystack, needle))
        .toArray();
    expectedLinearSorted = Arrays.stream(expectedBinarySorted)
        .map((position) -> (position >= 0) ? position : -1)
        .toArray();
    List<Integer> unsortedHaystackList = Arrays.stream(unsortedHaystack)
        .boxed()
        .toList();
    expectedLinearUnsorted = Arrays.stream(needles)
        .map(unsortedHaystackList::indexOf)
        .toArray();
  }

  @BeforeEach
  void setupSearch() {
    search = new Search();
  }

  @Test
  void linearSearch_unsorted() {
    int[] actual = getActual((needle) -> search.linearSearch(needle, unsortedHaystack));
    assertArrayEquals(expectedLinearUnsorted, actual);
  }

  @Test
  void linearSearch_sorted() {
    int[] actual = getActual((needle) -> search.linearSearch(needle, sortedHaystack));
    assertArrayEquals(expectedLinearSorted, actual);
  }


  @Test
  void binarySearch_alreadySorted() {
    int[] actual = getActual((needle) -> search.binarySearch(needle, sortedHaystack));
    assertArrayEquals(expectedBinarySorted, actual);
  }

  @Test
  void binarySearch_withSorted() {
    int[] sortedHaystack = IntStream.of(unsortedHaystack)
        .sorted()
        .toArray();
    int[] actual = getActual((needle) -> search.binarySearch(needle, sortedHaystack));
  assertArrayEquals(expectedBinarySorted, actual);
}

  private int[] getActual(IntUnaryOperator searchOperator) {
    return Arrays.stream(needles)
        .map(searchOperator)
        .toArray();
  }

}