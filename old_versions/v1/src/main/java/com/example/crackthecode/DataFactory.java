/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Danielnaor
 */
public class DataFactory {

    private HashMap<Integer, ArrayList<Clue>> testCases;
    private HashMap<Integer, Integer[]> solutions;

    public DataFactory() {
        buildTestCases();
    }

    private void buildTestCases() {
        try {

            testCases = new HashMap<>();
            solutions = new HashMap<>();
            Scanner file = new Scanner(new File("test" + File.separator + "testFile.txt"));
            int numberOfTestCases = Integer.parseInt(file.nextLine());

            int testCaseNum = 1;
            boolean endTestCase = true;
            int lenOfCode = 0;

            ArrayList<Clue> clues = new ArrayList<>();

            while (file.hasNextLine()) {

                String lineData = file.nextLine();

                if (lineData.equals("END")) {
                    testCases.put(testCaseNum, clues);
                    lineData = file.nextLine();
                    // the solution is the lineData
                    // remove the brackets

                    if (lineData.charAt(0) == ('[')) {
                        lineData = lineData.substring(1);

                    }
                    if (lineData.charAt(lineData.length() - 1) == (']')) {
                        lineData = lineData.substring(0, lineData.length() - 1);
                    }

                    String[] solutionString = lineData.split(", ");

                    System.out.println("solutionString: " + Arrays.toString(solutionString));

                    Integer[] solution = new Integer[solutionString.length];
                    for (int i = 0; i < solutionString.length; i++) {
                        solution[i] = Integer.parseInt(solutionString[i]);
                    }
                    solutions.put(testCaseNum, solution);

                    testCaseNum++;

                    if (testCaseNum <= numberOfTestCases) {
                        clues = new ArrayList<>();

                        lineData = file.nextLine();
                        endTestCase = true;
                    } else {
                        return;
                    }
                }

                if (endTestCase) {
                    lenOfCode = Integer.parseInt(lineData);
                    lineData = file.nextLine();
                    clues = new ArrayList<>();
                    endTestCase = false;
                }

                Scanner line = new Scanner(lineData);

                line.useDelimiter("\t");

                Integer[] combination = new Integer[lenOfCode];
                for (int i = 0; i < lenOfCode; i++) {
                    try {
                        combination[i] = Integer.parseInt(line.next());

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }

                String message = line.next();

                int correctCount = Integer.parseInt(line.next());
                int countCorrectNumbersAndCorrectPlacement = Integer.parseInt(line.next());
                int countCorrectNumbersAndIncorrectPlacement = Integer.parseInt(line.next());

                String correctlyPlacedString = line.next();

                boolean correctlyPlaced = false;
                if (correctlyPlacedString.toLowerCase().equals("true")) {
                    correctlyPlaced = true;
                }

                Hint currentHint = new Hint(message, correctCount, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement, correctlyPlaced);

                Clue currentClue = new Clue(combination, currentHint);
                clues.add(currentClue);

            }

        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: File not found.");
            ex.printStackTrace();
        }

    }

    public HashMap<Integer, ArrayList<Clue>> getTestCases() {
        return testCases;
    }

    public HashMap<Integer, Integer[]> getSolutions() {
        return solutions;
    }

}
