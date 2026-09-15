/**
 * Точка входа для демонстрации пирамидальной сортировки.
 */
public class Main {
    /**
     * Запускает демонстрацию.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        int[] data = {4, 1, 9, 3, 7, 8, 2, 6, 4, 0};
        System.out.print("Before: ");
        printArray(data);
        HeapSort.sort(data);
        System.out.print("After: ");
        printArray(data);
    }

    private static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arr[i]);
        }
        sb.append("]");
        System.out.println(sb);
    }
}