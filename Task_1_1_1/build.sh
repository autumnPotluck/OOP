SRC_MAIN="src/main/java"
OUT_MAIN="out/main"
DOCS="docs"
JAR_NAME="heapsort.jar"
MAIN_CLASS="HeapSort"

echo "==> Cleaning"
rm -rf out docs "$JAR_NAME"
mkdir -p "$OUT_MAIN" "$DOCS"

echo "==> Compiling"
javac -d "$OUT_MAIN" "$SRC_MAIN" -name '*.java'

echo "==> Generating Javadoc"
javadoc -d docs -sourcepath "$SRC_MAIN" -name '*.java')

echo "==> Cooking jar"
jar --create --file "$JAR_NAME" \
    --main-class "$MAIN_CLASS" \
    -C "$OUT_MAIN" .

echo "==> App launch"
java -jar "$JAR_NAME"

echo "==> Benchmark (proving O(n log n))"
java -cp "$OUT_MAIN" Benchmark

echo
echo "==> Done:"
echo "    jar:     $JAR_NAME"
echo "    javadoc: $DOCS/index.html"