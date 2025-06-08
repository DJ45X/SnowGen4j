package com.snowflakecsvtool;

import com.snowflakecsvtool.config.EurekaConfig;
import com.snowflakecsvtool.util.SnowflakeIdGenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvIdInjector {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java com.snowflakecsvtool.CsvIdInjector <input_csv_file>");
            System.err.println("Please provide the path to the input CSV file.");
            System.exit(1);
        }

        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || inputFile.isDirectory()) {
            System.err.println("Error: Input file not found or is a directory: " + inputFilePath);
            System.exit(1);
        }

        String outputFilePath = getOutputFilePath(inputFilePath);

        System.out.println("Processing file: " + inputFilePath);

        try {
            EurekaConfig eurekaConfig = new EurekaConfig();
            SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(eurekaConfig);

            List<String> outputLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String headerLine = reader.readLine();
                if (headerLine != null) {
                    System.out.println("DEBUG: Original header read: [" + headerLine + "]"); // Diagnostic print
                    outputLines.add("id," + headerLine);

                    String dataLine;
                    int processedDataRows = 0;
                    boolean firstDataLineProcessed = false; // Flag for first data line diagnostic
                    while ((dataLine = reader.readLine()) != null) {
                        if (!firstDataLineProcessed) {
                            System.out.println("DEBUG: First data line read: [" + dataLine + "]"); // Diagnostic print
                            firstDataLineProcessed = true;
                        }
                        if (!dataLine.trim().isEmpty()) {
                            long snowflakeId = idGenerator.generateId();
                            outputLines.add(snowflakeId + "," + dataLine);
                            processedDataRows++;
                        }
                    }
                    System.out.println("Successfully processed " + processedDataRows + " data rows.");

                } else {
                    System.err.println("Warning: Input file is empty: " + inputFilePath);
                    outputLines.add("id");
                    System.out.println("Successfully processed 0 data rows.");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                for (String outputLine : outputLines) {
                    writer.write(outputLine);
                    writer.newLine();
                }
            }

            // System.out.println("Successfully processed " + (outputLines.size() -1) + " data rows (excluding header)."); // Old message
            System.out.println("Output written to: " + outputFilePath);

        } catch (IOException e) {
            System.err.println("An error occurred during CSV processing:");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static String getOutputFilePath(String inputPath) {
        int dotIndex = inputPath.lastIndexOf('.');
        if (dotIndex == -1) {
            return inputPath + "_processed";
        } else {
            return inputPath.substring(0, dotIndex) + "_processed" + inputPath.substring(dotIndex);
        }
    }
}
