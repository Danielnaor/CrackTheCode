/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class TestFileCreator {

    private ArrayList<ArrayList<Clue>> allTestCases;
    private String filePath;
    private String fileName;

    public TestFileCreator(ArrayList<ArrayList<Clue>> allTestCases, String filePath, String fileName, Integer[][] Solutions) {
        this.allTestCases = allTestCases;
        this.filePath = filePath;
        this.fileName = fileName;
        try {
            if (filePath.contains("//")) {
                filePath = filePath.replace("//", File.separator);
            }

            if (filePath.contains("\\")) {
                filePath = filePath.replace("\\", File.separator);
            }

            if (filePath.contains("/")) {
                filePath = filePath.replace("/", File.separator);
            }

            if (!filePath.endsWith(File.separator)) {
                filePath += File.separator;
            }

            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

           System.out.println("filePath: " + filePath); 
         //   String[] filePathParts = filePath.split(File.separator);
           String[] filePathParts = filePath.split(Pattern.quote(File.separator));

         String currentPath = "";
            for (String pathPart : filePathParts) {
                currentPath += pathPart + File.separator;
                File currentFile = new File(currentPath);
                if (!currentFile.exists()) {
                    currentFile.mkdir();
                }
            }

            if (!currentPath.equals(filePath)) {
                System.out.println("ERROR: File path is not valid.");
            }

            File file = new File(filePath + File.separator + fileName);

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            boolean fileExists = file.exists();

            System.out.println("File exists: " + fileExists);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(allTestCases.size() + "\n");
            int testCaseNum = 1;
            for (ArrayList<Clue> testCase : allTestCases) {
                int lengthOfCode = testCase.get(0).getCombination().length;
                fileWriter.write(lengthOfCode + "\n");

                for (Clue clue : testCase) {
                    Integer[] combination = clue.getCombination();
                    for (int i = 0; i < combination.length; i++) {
                        fileWriter.write(combination[i] + "\t");
                    }
                    Hint hint = clue.getHint();
                    fileWriter.write(hint.getMessage() + "\t");
                    fileWriter.write(hint.getCorrectCount() + "\t");
                    fileWriter.write(hint.getCountCorrectNumbersAndCorrectPlacement() + "\t");
                    fileWriter.write(hint.getCountCorrectNumbersAndIncorrectPlacement() + "\t");
                    fileWriter.write(hint.isCorrectlyPlaced() + "\n");
                }
                
                fileWriter.write("END\n"); // Indicate the end of a test case

                System.out.println("testCaseNum t: " + testCaseNum);
                
                if(testCaseNum!=allTestCases.size()){
                    Integer[] solution = Solutions[testCaseNum-1];
                    fileWriter.write(Arrays.toString(solution) + "\n");
                } 
                else{
                    Integer[] solution = Solutions[testCaseNum-1];
                    fileWriter.write(Arrays.toString(solution));
                }
                testCaseNum++;

            }

            fileWriter.close();
            System.out.println("Test file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
