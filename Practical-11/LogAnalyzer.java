import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path[] files = {
            Paths.get("log1.txt"),
            Paths.get("log2.txt"),
            Paths.get("log3.txt")
        };

        String keyword = "ERROR";
        int totalLines = 0;
        int matchingLines = 0;

        for (Path file : files) {
            List<String> lines = Files.readAllLines(file);
            int fileLines = 0;
            int fileMatches = 0;

            for (String line : lines) {
                totalLines++;
                fileLines++;

                if (line.contains(keyword)) {
                    matchingLines++;
                    fileMatches++;
                }
            }

            BasicFileAttributes attr = Files.readAttributes(
                file, BasicFileAttributes.class
            );

            System.out.println("File: " + file.getFileName());
            System.out.println("Size: " + attr.size() + " bytes");
            System.out.println("Lines: " + fileLines);
            System.out.println("Matching lines: " + fileMatches);
        }

        System.out.println("Total lines: " + totalLines);
        System.out.println("Total matching lines: " + matchingLines);
    }
}