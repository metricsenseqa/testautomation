package core;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class FileFilter {

	public static void main(String[] args) {
        String directoryPath = "src/main/resources/ExcelSheets";
        File dir = new File(directoryPath);
        
        File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Failed to list files in directory: " + dir);
            return;
        }
        
        Instant twoDaysAgo = Instant.now().minus(2, ChronoUnit.DAYS);
        Arrays.stream(files).filter(file -> {
            long lastModifiedTime = file.lastModified();
            Instant lastModifiedInstant = Instant.ofEpochMilli(lastModifiedTime);
            return lastModifiedInstant.isAfter(twoDaysAgo);
        }).forEach(System.out::println);
    }		
}