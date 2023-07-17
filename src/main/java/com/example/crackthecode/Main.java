/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.example.crackthecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.System.out;
import java.lang.reflect.Array;
import java.util.*;
import static java.util.Collections.list;
import static java.util.Collections.shuffle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danielnaor
 */
public class Main implements Cloneable, Serializable {

    static File testFile;
    private static String currentFile;    //the save location for the program

    public static void main(String[] args) {
        CreateTestFile();

        //currentFile = getCurrentFileLocation();
        HashMap<Integer, ArrayList<Clue>> testCases;
        DataFactory data = new DataFactory();

        testCases = data.getTestCases();
        for (Integer testCaseNum : testCases.keySet()) {
               System.out.println("testCaseNum:" + testCaseNum);
            ArrayList<Clue> clues = new ArrayList<>();
            ArrayList<Clue> deepCopyOfClues = new ArrayList<>();

            clues = testCases.get(testCaseNum);

            for (Clue clue : clues) {
                Clue shallowCopy = new Clue(clue.getCombination().clone(), (Hint) clue.getHint().clone());

                deepCopyOfClues.add(shallowCopy);
            }
                CodeCracker codeCracker = new CodeCracker(deepCopyOfClues);

                Integer[] code = codeCracker.crackCode();
                boolean foundCode = codeCracker.isFoundCode();

                if (!foundCode) {
// make sure the code is correct by checking it with the clues and the codevalidator
                    CodeValidator codeValidator = new CodeValidator(clues, code, codeCracker.getAllPosibleArrayList());
                    codeValidator.validateAllPossibleSolutionsWithHints();

                } else {

                    // validate that the code found is correct
                    boolean codeIsCorrect = CodeValidator.validateCode(code, clues);

                    if (codeIsCorrect) {
                        System.out.println("The code is: " + Arrays.toString(code));

                    }
                }

                clues.clear();
                deepCopyOfClues.clear();

            }

        

    }   

    ///CreateTestFile();
    //}
    private static void CreateTestFile() {
        ArrayList<ArrayList<Clue>> allTestCases = new ArrayList<>();
        ArrayList<Clue> clues = new ArrayList<>();

        // test case 1:
        clues.add(new Clue(new Integer[]{9, 2, 8, 5}, new Hint("One number is correct but wrong placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 9, 3, 7}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{5, 2, 0, 1}, new Hint("One number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{6, 5, 0, 7}, new Hint("Nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{8, 5, 2, 4}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));

        // add the clues to the allTestCases and clear the clues arraylist
        allTestCases.add(clues);
        clues = new ArrayList<>();

        // test case 2:
        clues.add(new Clue(new Integer[]{3, 6, 8}, new Hint("One number is correct and correctly placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{3, 8, 7}, new Hint("Nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{2, 7, 6}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{4, 7, 1}, new Hint("Two numbers are correct but wrongly placed", 2, 0, 2, false)));

        // add the clues to the allTestCases and clear the clues arraylist
        allTestCases.add(clues);
        clues = new ArrayList<>();

        // test case 3:
        clues.add(new Clue(new Integer[]{3, 4, 2}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{2, 7, 3}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 6, 5}, new Hint("Two numbers are correct and correctly placed", 2, 2, 0, true)));
        clues.add(new Clue(new Integer[]{8, 5, 3}, new Hint("Two numbers are correct but wrongly placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{2, 6, 4}, new Hint("nothing is correct", 0, 0, 0, false)));

        // add the clues to the allTestCases and clear the clues arraylist
        allTestCases.add(clues);
        clues = new ArrayList<>();

        // test case 4:
        clues.add(new Clue(new Integer[]{0, 1, 2}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{2, 3, 0}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 8, 0}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{3, 1, 4}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        allTestCases.add(clues);
        clues = new ArrayList<>();

        // test case 5:
        // 8 9 5 1 Two digits are correct  but wrongly placed
        // 2 1 6 9 One digit is correct and well placed and Another digit right but in wrongly placed
        // 3 6 9 4 One digit right and in the right place. Another digit right but in wrong place.
        // 4 7 2 1 One digit right and in the right place. Another digit right but in wrong place.
        // 1 2 3 7 Three digits are right but all are at wrong place.

        clues.add(new Clue(new Integer[]{8, 9, 5, 1}, new Hint("Two digits are correct  but wrongly placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{2, 1, 6, 9}, new Hint("One digit is correct and well placed and Another digit right but in wrongly placed", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{3, 6, 9, 4}, new Hint("One digit right and in the right place. Another digit right but in wrong place.", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{4, 7, 2, 1}, new Hint("One digit right and in the right place. Another digit right but in wrong place.", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{1, 2, 3, 7}, new Hint("Three digits are right but all are at wrong place.", 3, 0, 3, false)));
        allTestCases.add(clues);
        clues = new ArrayList<>();

        TestFileCreator testFileCreator = new TestFileCreator(allTestCases, "test", "testFile.txt");

    }

    private static String getCurrentFileLocation() {

        try {
            Scanner file = new Scanner(new File("preferences.txt"));
            return file.nextLine();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void writeCurrentFileLocaitonToPreferences() {
        try {
            FileWriter writer = new FileWriter("preferences.txt");
            writer.write(currentFile);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}

// test case 1:
// 9, 2, 8, 5 one number is correct but wrong placed
// 1, 9, 3, 7 two numbers are correct but wrong placed
// 5, 2, 0, 1 one number is correct and well placed
// 6, 5, 0, 7 nothing is correct
// 8, 5, 2, 4 two numbers are correct but wrong placed
// solution: 3, 8 , 4 , 1
// test case 2:
// 3, 6, 8 one number is correct and correctly placed
// 3, 8, 7 nothing is correct
// 2, 7, 6 one number is correct but wrongly placed
// 4, 7, 1 two numbers are correct but wrongly placed
// solution: 1, 6, 4
// test case 3:
// 3, 4, 2 one number is correct but wrongly placed
// 2, 7, 3 one number is correct but wrongly placed
// 1, 6, 5 two numbers are correct and correctly placed
// 8, 5, 3 two numbers are correct but wrongly placed
// 2, 6, 4 nothing is correct
// solution: 1, 3, 5
// test case 4:
// 0, 1, 2 one number is correct and well placed
// 2, 3, 0 one number is correct but wrongly placed
// 1, 8, 0 one number is correct and well placed
// 3, 1, 4 one number is correct but wrongly placed
// solution: 4, 8, 2

// test case 5:
// 8 9 5 1 Two digits are correct  but wrongly placed
// 2 1 6 9 One digit is correct and well placed and Another digit right but in wrongly placed
// 3 6 9 4 One digit right and in the right place. Another digit right but in wrong place.
// 4 7 2 1 One digit right and in the right place. Another digit right but in wrong place.
// 1 2 3 7 Three digits are right but all are at wrong place.



