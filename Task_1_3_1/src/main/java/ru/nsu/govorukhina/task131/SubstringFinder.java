package ru.nsu.govorukhina.task131;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The substring finder finds all occurrences of a substring in strings using KMP.
 */
public class SubstringFinder {

    private static final int BUFFER_SIZE = 8192;
    /**
     * Finds all starting indices of the target string inside the given file.
     *
     * @return ordered list of starting indices; empty when there is no match or target is empty
     * @throws IOException if the file cannot be opened or read
     * @throws IllegalArgumentException if the target is null
     */

    public static List<Long> find(String fileName, String target) throws IOException {
        if (target == null) {
            throw new IllegalArgumentException("target must not be null");
        }
        List<Long> result = new ArrayList<>();
        if (target.isEmpty()) {
            return result;
        }

        int[] prefix = buildPrefixFunction(target);

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(fileName), StandardCharsets.UTF_8)) {
            char[] buffer = new char[BUFFER_SIZE];
            int cnt = 0;
            long pos = 0;
            int readChars = reader.read(buffer);

            while (readChars != -1) {
                for (int i = 0; i < readChars; ++i) {
                    char currChar = buffer[i];

                    while (cnt > 0 && target.charAt(cnt) != currChar) {
                        cnt = prefix[cnt - 1];
                    }
                    if (target.charAt(cnt) == (char) currChar) {
                        cnt++;
                    }
                    if (cnt == target.length()) {
                        result.add(pos - target.length() + 1);
                        cnt = prefix[cnt - 1];
                    }
                    pos++;
                }
                readChars = reader.read(buffer);
            }
        }

        return result;
    }

    private static int[] buildPrefixFunction(String target) {
        int start = 0;
        int[] prefix = new int[target.length()];
        for (int i = 1; i < target.length(); ++i) {
            while (start > 0 && target.charAt(start) != target.charAt(i)) {
                start = prefix[start - 1];
            }
            if (target.charAt(start) == target.charAt(i)) {
                start++;
            }
            prefix[i] = start;
        }
        return prefix;
    }
}
