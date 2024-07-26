package filehandler;

import java.io.*;
import java.util.*;

public class FileHandler {
    public static List<String[]> readDataFromCSV(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the first line (header)
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] row = line.split(",");
                data.add(row);
            }
            // System.out.println("Data read from CSV file: " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + fileName);
            e.printStackTrace();
        }
        return data;
    }

    public static void writeDataToCSV(String fileName, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            // System.out.println("Data written to CSV file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + fileName);
            e.printStackTrace();
        }
    }

}
