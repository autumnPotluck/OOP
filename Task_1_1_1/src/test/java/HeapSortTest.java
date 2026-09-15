import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HeapSortTest {

    /** Empty array won't fail. */
    @Test
    void emptyArray() {
        int[] arr = {};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    /** Null array mustn't fail. */
    @Test
    void nullArray() {
        assertDoesNotThrow(() -> HeapSort.sort(null));
    }

    /** One element sorted already. */
    @Test
    void singleElement() {
        int[] arr = {11};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{11}, arr);
    }

    /** Sorted array sorted already. */
    @Test
    void alreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    /** Array in descending order. */
    @Test
    void reverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    /** Array with duplicates. */
    @Test
    void duplicates() {
        int[] arr = {3, 3, 1, 2, 3, 1};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 3}, arr);
    }

    /** Array with negative numbers. */
    @Test
    void negativeNumbers() {
        int[] arr = {-3, 5, -10, 0, 7, -1};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{-10, -3, -1, 0, 5, 7}, arr);
    }

    /** 1000 random arrays checked with Arrays.sort. */
    @Test
    void randomArraysMatchLibrarySort() {
        Random rnd = new Random(1);
        for (int t = 0; t < 1000; t++) {
            int size = rnd.nextInt(200);
            int[] a = new int[size];
            for (int i = 0; i < size; i++) {
                a[i] = rnd.nextInt(1000) - 500;
            }
            int[] expected = a.clone();
            Arrays.sort(expected);

            HeapSort.sort(a);
            assertArrayEquals(expected, a, "Провал на итерации " + t);
        }
    }
}