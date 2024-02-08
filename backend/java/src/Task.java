package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    public record File(
        int id,
        String name,
        List<String> categories,
        int parent,
        int size
    ) {}

    /**
     * Task 1
     */
    public static List<String> leafFiles(List<File> files) {
        List<String> leafNodes = new ArrayList<String>();
        HashMap<String, Integer> parentMap = new HashMap<String, Integer>();
        for (File file : files) {
            if (!parentMap.containsValue(file.parent) && file.parent != -1) {
                parentMap.put(file.name, file.parent);
            }
        }

        for (File file: files) {
            if (!parentMap.containsValue(file.id)) {
                leafNodes.add(file.name);
            }
        }
        return leafNodes;
    }

    /**
     * Task 2
     */
    public static List<String> kLargestCategories(List<File> files, int k) {
        HashMap<String,Integer> categoriesCount = new HashMap<String, Integer>();
        for (File file: files) {
            for (String category: file.categories) {
                if (categoriesCount.containsKey(category)) {
                    // Incrementing count of category in the hashmap
                    categoriesCount.put(category, categoriesCount.get(category) + 1);
                } else {
                    categoriesCount.put(category, 1);
                }
            }
        }

        // Convert the HashMap to a List
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(categoriesCount.entrySet());

        Comparator<Map.Entry<String, Integer>> customComparator = (category1, category2) -> {
            int valueComparison = category2.getValue().compareTo(category1.getValue());
            if (valueComparison != 0) {
                return valueComparison;
            } else {
                // If values are the same, compare keys alphabetically
                return category1.getKey().compareTo(category2.getKey()); 
            }
        };

        Collections.sort(entryList, customComparator);

        List<String> sortedCategories = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedCategories.add(entry.getKey());
        }

        if (sortedCategories.size() > k) {
            sortedCategories.subList(k, sortedCategories.size()).clear();
        }

        return sortedCategories;

    }

    /**
     * Task 3
     */
    public static int largestFileSize(List<File> files) {
        if (files.size() == 0) {
            return 0;
        }

        return 1;
    }

    public static void main(String[] args) {
        List<File> testFiles = List.of(
            new File(1, "Document.txt", List.of("Documents"), 3, 1024),
            new File(2, "Image.jpg", List.of("Media", "Photos"), 34, 2048),
            new File(3, "Folder", List.of("Folder"), -1, 0),
            new File(5, "Spreadsheet.xlsx", List.of("Documents", "Excel"), 3, 4096),
            new File(8, "Backup.zip", List.of("Backup"), 233, 8192),
            new File(13, "Presentation.pptx", List.of("Documents", "Presentation"), 3, 3072),
            new File(21, "Video.mp4", List.of("Media", "Videos"), 34, 6144),
            new File(34, "Folder2", List.of("Folder"), 3, 0),
            new File(55, "Code.py", List.of("Programming"), -1, 1536),
            new File(89, "Audio.mp3", List.of("Media", "Audio"), 34, 2560),
            new File(144, "Spreadsheet2.xlsx", List.of("Documents", "Excel"), 3, 2048),
            new File(233, "Folder3", List.of("Folder"), -1, 4096)
        );
        
        List<String> leafFiles = leafFiles(testFiles);
        leafFiles.sort(null);

        assert leafFiles.equals(List.of(
            "Audio.mp3",
            "Backup.zip",
            "Code.py",
            "Document.txt",
            "Image.jpg",
            "Presentation.pptx",
            "Spreadsheet.xlsx",
            "Spreadsheet2.xlsx",
            "Video.mp4"
        ));

        assert kLargestCategories(testFiles, 3).equals(List.of(
            "Documents", "Folder", "Media"
        ));


        assert largestFileSize(testFiles) == 20992;
    }
}