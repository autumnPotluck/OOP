package ru.nsu.govorukhina.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SubstringFinderTest {

    @TempDir
    Path tempDir;

    /**
     * Generates a file larger than the JVM's maximum heap and scans it.
     */
    @Test
    void fileLargerThanJvmHeapIsStillFound() throws IOException {
        long maxHeap = Runtime.getRuntime().maxMemory();
        long fileSize = 2 * maxHeap;
        long blockSize = 1_000_000L;
        Path file = tempDir.resolve("huge.txt");

        long expectedMatches = generateSparseFile(file, "abracadabra", fileSize, blockSize);

        List<Long> found;
        try {
            found = SubstringFinder.find(file.toString(), "abracadabra");
        } catch (OutOfMemoryError oom) {
            fail("find() ran out of memory, it must not load the file into memory. "
                    + "File size was " + fileSize + " bytes, max heap " + maxHeap + " bytes.");
            return;
        }
        assertEquals(expectedMatches, found.size(),
                "file was " + fileSize + " bytes, max heap " + maxHeap + " bytes");
    }

    private static long generateSparseFile(Path file, String pattern,
                                           long fileSize, long blockSize) throws IOException {
        long matches = 0;
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            long written = 0;
            while (written < fileSize) {
                if (fileSize - written < pattern.length()) {
                    for (long i = 0; i < fileSize - written; i++) {
                        w.write('x');
                    }
                    break;
                }
                w.write(pattern);
                written += pattern.length();
                matches++;
                long gap = Math.min(blockSize - pattern.length(), fileSize - written);
                for (long i = 0; i < gap; i++) {
                    w.write('x');
                }
                written += gap;
            }
        }
        return matches;
    }

    private Path writeFile(String content) throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void findsSingleOccurrence() throws IOException {
        Path file = writeFile("hello world");
        assertEquals(List.of(6L), SubstringFinder.find(file.toString(), "world"));
    }

    @Test
    void findsMultipleNonOverlappingOccurrences() throws IOException {
        Path file = writeFile("abcabcabc");
        assertEquals(List.of(0L, 3L, 6L), SubstringFinder.find(file.toString(), "abc"));
    }

    @Test
    void findsOverlappingOccurrences() throws IOException {
        Path file = writeFile("aaaaa");
        assertEquals(List.of(0L, 1L, 2L), SubstringFinder.find(file.toString(), "aaa"));
    }

    @Test
    void findsOverlappingWithPrefixSuffix() throws IOException {
        Path file = writeFile("abababa");
        assertEquals(List.of(0L, 2L, 4L), SubstringFinder.find(file.toString(), "aba"));
    }

    @Test
    void returnsEmptyWhenNotFound() throws IOException {
        Path file = writeFile("hello");
        assertTrue(SubstringFinder.find(file.toString(), "xyz").isEmpty());
    }

    @Test
    void emptyTargetReturnsEmptyList() throws IOException {
        Path file = writeFile("hello");
        assertTrue(SubstringFinder.find(file.toString(), "").isEmpty());
    }

    @Test
    void nullTargetThrows() throws IOException {
        Path file = writeFile("hello");
        String path = file.toString();
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find(path, null));
    }

    @Test
    void targetLongerThanFile() throws IOException {
        Path file = writeFile("hi");
        assertTrue(SubstringFinder.find(file.toString(), "hello").isEmpty());
    }

    @Test
    void matchAtStartAndEnd() throws IOException {
        Path file = writeFile("hello world");
        assertEquals(List.of(0L), SubstringFinder.find(file.toString(), "hello"));
        assertEquals(List.of(6L), SubstringFinder.find(file.toString(), "world"));
    }

    @Test
    void singleCharacterTarget() throws IOException {
        Path file = writeFile("banana");
        assertEquals(List.of(1L, 3L, 5L), SubstringFinder.find(file.toString(), "a"));
    }

    @Test
    void cyrillicCharacters() throws IOException {
        Path file = writeFile("привет мир, привет");
        assertEquals(List.of(0L, 12L), SubstringFinder.find(file.toString(), "привет"));
    }

    @Test
    void multiLineFile() throws IOException {
        Path file = writeFile("line one\nline two\nline three");
        assertEquals(List.of(23L), SubstringFinder.find(file.toString(), "three"));
    }

    @Test
    void emptyFile() throws IOException {
        Path file = writeFile("");
        assertTrue(SubstringFinder.find(file.toString(), "abc").isEmpty());
    }

    @Test
    void fileCrossesManyBufferBoundaries() throws IOException {
        String pattern = "abracadabra";
        int repeats = 5000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeats; i++) {
            sb.append(pattern).append('-');
        }
        Path file = writeFile(sb.toString());
        List<Long> result = SubstringFinder.find(file.toString(), pattern);
        int stride = pattern.length() + 1;
        assertEquals(repeats, result.size());
        assertEquals(0L, result.get(0).longValue());
        assertEquals((long) stride * (repeats - 1), result.get(repeats - 1).longValue());
    }

    @Test
    void patternSpansBufferBoundary() throws IOException {
        int bufferSize = 8192;
        String filler = "x".repeat(bufferSize - 3);
        Path file = writeFile(filler + "abcabc");
        List<Long> result = SubstringFinder.find(file.toString(), "abc");
        assertEquals(List.of((long) bufferSize - 3, (long) bufferSize), result);
    }

    @Test
    void fileDoesNotExist() {
        String missing = tempDir.resolve("missing.txt").toString();
        assertThrows(IOException.class, () -> SubstringFinder.find(missing, "x"));
    }
}