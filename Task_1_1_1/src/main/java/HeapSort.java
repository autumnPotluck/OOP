/**
 * Does Heap Sort.
 */
public class HeapSort {
    /**
     * Sorts array in ascending order.
     *
     * @param arr - the array being sorted (can be null or empty)
     */

    static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            siftDown(arr, end, 0);
        }
    }

    /**
     * Sifts an element down, max-heap
     * in subtree with root. The size of heap - heapSize.
     */

    static void siftDown(int[] arr, int heapSize, int root) {
        while (true) {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            int largest = root;

            if (left < heapSize && arr[left] > arr[largest]) {
                largest = left;
            }
            if (right < heapSize && arr[right] > arr[largest]) {
                largest = right;
            }
            if (largest == root) {
                return;
            }
            swap(arr, root, largest);
            root = largest;
        }
    }

    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Demonstration of a working algorithm was there.
     * */
    public static void main(String[] args) {}


}