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
        WriteSolutions writeSolutions = new WriteSolutions();
        currentFile = getCurrentFileLocation();
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
                // print the hints
                for (Clue clue : deepCopyOfClues) {
                    System.out.println(clue);
                }



                Integer[] code = codeCracker.crackCode();
                boolean foundCode = codeCracker.isFoundCode();

                System.out.println("Code: " + Arrays.toString(code));



                if (!foundCode) {
// make sure the code is correct by checking it with the clues and the codevalidator
                 

                

                                  




                    CodeValidator codeValidator = new CodeValidator(clues, code, codeCracker.getAllPosibleArrayList());
                    codeValidator.validateAllPossibleSolutionsWithHints();

                   writeSolutions.addSolution(codeValidator.getFinalCode());


                } else {

                    // validate that the code found is correct
                    boolean codeIsCorrect = CodeValidator.validateCode(code, clues);

                    if (codeIsCorrect) {
                        System.out.println("The code is: " + Arrays.toString(code));
                                           writeSolutions.addSolution(code);


                    }
                }

                clues.clear();
                deepCopyOfClues.clear();

            }
        
            
        


        
       

        writeSolutions.writeSolutionsToFile();

        // valide that all solutions are correct (for testing purposes)
        boolean testFailed = false;
        
       
        for (int testCaseNum = 1; testCaseNum <= data.getSolutions().size(); testCaseNum++) {
            
            
            Integer[] solutionGot = writeSolutions.getSolutions().get(testCaseNum);
            Integer[] solutionGiven = data.getSolutions().get(testCaseNum);
            
            if(Arrays.equals(solutionGot, solutionGiven)){
                System.out.println("Passed test "+testCaseNum + " solution is: " + Arrays.toString(solutionGot));
            } else{
                System.out.println("Failed test "+testCaseNum);
                System.out.println("solutionGot: " + Arrays.toString(solutionGot));
                System.out.println("solutionGiven: " + Arrays.toString(solutionGiven));

                testFailed = true;
            }
        }

        if(testFailed){
            System.out.println("Program failed");
        } 



    }   

    

   
    private static void CreateTestFile() {
        ArrayList<ArrayList<Clue>> allTestCases = new ArrayList<>();
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Integer[]> Solutions = new ArrayList<>();
        /*
        // test case 1:
        clues.add(new Clue(new Integer[]{9, 2, 8, 5}, new Hint("One number is correct but wrong placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 9, 3, 7}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{5, 2, 0, 1}, new Hint("One number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{6, 5, 0, 7}, new Hint("Nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{8, 5, 2, 4}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));

        // add the clues to the allTestCases and clear the clues arraylist

        allTestCases.add(clues);
        Solutions.add(new Integer[]{3, 8, 4, 1});

        clues = new ArrayList<>();

        // test case 2:
        clues.add(new Clue(new Integer[]{3, 6, 8}, new Hint("One number is correct and correctly placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{3, 8, 7}, new Hint("Nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{2, 7, 6}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{4, 7, 1}, new Hint("Two numbers are correct but wrongly placed", 2, 0, 2, false)));

        // add the clues to the allTestCases and clear the clues arraylist
        allTestCases.add(clues);
        Solutions.add(new Integer[]{1, 6, 4});
        clues = new ArrayList<>();

        // test case 3:
        clues.add(new Clue(new Integer[]{3, 4, 2}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{2, 7, 3}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 6, 5}, new Hint("Two numbers are correct and correctly placed", 2, 2, 0, true)));
        clues.add(new Clue(new Integer[]{8, 5, 3}, new Hint("Two numbers are correct but wrongly placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{2, 6, 4}, new Hint("nothing is correct", 0, 0, 0, false)));

        // add the clues to the allTestCases and clear the clues arraylist
        allTestCases.add(clues);
        Solutions.add(new Integer[]{1, 3, 5});
        clues = new ArrayList<>();

        // test case 4:
        clues.add(new Clue(new Integer[]{0, 1, 2}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{2, 3, 0}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 8, 0}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{3, 1, 4}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        allTestCases.add(clues);
        Solutions.add(new Integer[]{4, 8, 2});
        clues = new ArrayList<>();


        // test case 5:
        clues.add(new Clue(new Integer[]{8, 9, 5, 1}, new Hint("Two digits are correct but wrongly placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{2, 1, 6, 9}, new Hint("One digit is correct and well placed and Another digit right but in wrongly placed", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{3, 6, 9, 4}, new Hint("One digit right and in the right place. Another digit right but in wrong place.", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{4, 7, 2, 1}, new Hint("One digit right and in the right place. Another digit right but in wrong place.", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{1, 2, 3, 7}, new Hint("Three digits are right but all are at wrong place.", 3, 0, 3, false)));
        allTestCases.add(clues);
        Solutions.add(new Integer[]{3, 7, 1, 9});
        clues = new ArrayList<>();

       // test case 6:
        clues.add(new Clue(new Integer[]{2, 3, 4}, new Hint("Two Numbers are correct. One well placed and other wrongly placed.", 2, 1, 1, false)));
        clues.add(new Clue(new Integer[]{5, 6, 7}, new Hint("Nothing is Correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{5, 6, 4}, new Hint("One Number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{4, 0, 1}, new Hint("Two Numbers are correct but wrongly placed.", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{1, 2, 4}, new Hint("Two Numbers are correct. One well placed and other wrongly placed.", 2, 1, 1, false)));
        allTestCases.add(clues);
        Solutions.add(new Integer[]{3, 1, 4});
        clues = new ArrayList<>();
*/ 
/*
        // test case 7:

        clues.add(new Clue(new Integer[]{5, 6, 7}, new Hint("One Number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{4, 3, 5}, new Hint("Nothing is Correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{0, 6, 7}, new Hint("Two Numbers are correct and well placed", 2, 2, 0, true)));
        clues.add(new Clue(new Integer[]{1, 0, 2}, new Hint("One Number is correct and correctly placed.", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{7, 2, 1}, new Hint("One number is correct but wrongly placed", 1, 0, 1, false)));
        allTestCases.add(clues);
        Solutions.add(new Integer[]{0, 0, 7});
        clues = new ArrayList<>();
 */
 /*
        // test case 8:
      clues.add(new Clue(new Integer[]{0, 1, 2, 3}, new Hint("One number is correct and well placed", 1, 1, 0, true)));
       clues.add(new Clue(new Integer[]{1, 0, 2, 3}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
      clues.add(new Clue(new Integer[]{2, 1, 0, 3}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{1, 2, 3, 4}, new Hint("nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{1, 2, 3, 7}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
       
   
        
        
        
        allTestCases.add(clues);
        // solution: 0, 0 , 0 , 7

        Solutions.add(new Integer[]{0, 0, 0, 7});
 
         clues = new ArrayList<>();
     //   */
///*
         // test case 9:
        clues.add(new Clue(new Integer[]{0, 1, 2, 3}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{1, 0, 2, 3}, new Hint("one number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{1, 3, 2, 0}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 7, 2, 3}, new Hint("one number is correct but wrongly placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{7, 4, 5, 0}, new Hint("two numbers are correct but wrongly placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{1, 4, 7, 7}, new Hint("two numbers are correct and well placed", 2, 2, 0, true)));
        allTestCases.add(clues);
        // solution: 0, 0 , 7 , 7
        Solutions.add(new Integer[]{0, 0, 7, 7});
        clues = new ArrayList<>();
//*/

        


        // convert ArrayList<Integer[]> Solutions to Integer[][] Solutions
        Integer[][] SolutionsArray = new Integer[Solutions.size()][];
        for (int i = 0; i < SolutionsArray.length; i++) {
            SolutionsArray[i] = Solutions.get(i);
        }
        

        TestFileCreator testFileCreator = new TestFileCreator(allTestCases, "test", "testFile.txt", SolutionsArray);

    }

    private static String getCurrentFileLocation() {

        try {
            Scanner fileScanner = new Scanner(new File("preferences.txt"));

            if(fileScanner.hasNextLine()){
            String filePath =  fileScanner.nextLine();
            File file = new File(filePath);
            if(file.exists()){
                return filePath;
            } else {
               file.createNewFile();
                return filePath;
             }
            } else {
                FileWriter writer = new FileWriter("preferences.txt");
                writer.write("test" + File.separator + "testFile.txt");
                writer.close();
                return "test" + File.separator + "testFile.txt";
                
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
// solution: 3, 7, 1, 9



// test case 6:
// 2,3,4 Two Numbers are correct. One well placed and other wrongly placed.
// 5,6,7 Nothing is Correct
// 5,6,4 One Number is correct and well placed
// 4,0,1 Two Numbers are correct but wrongly placed.
// 1,2,4 Two Numbers are correct. One well placed and other wrongly placed.
// solution: 3, 1, 4 

// test case 7:
// 5,6,7 One Number is correct and well placed
// 4,3,5 Nothing is Correct
// 0,6,7 Two Numbers are correct and well placed
// 1,0,2 One Number is correct and correctly placed.
// 7,2,1 One number is correct but wrongly placed 
// solution: 0, 0, 7

// test case 8:
// 0 1 2 3 One number is correct and well placed
// 1 0 2 3 one number is correct and well placed
// 2 1 0 3 one number is correct and well placed
// 1 2 3 4 nothing is correct
// 1 2 3 7 one number is correct and well placed
// solution: 0, 0, 0, 7

// test case 9:
// 0 1 2 3 One number is correct and well placed
// 1 0 2 3 one number is correct and well placed
// 1 3 2 0 one number is correct but wrongly placed
// 1 7 2 3 one number is correct but wrongly placed
// 7 4 5 0 two numbers are correct but wrongly placed
// 5 4 7 7 two numbers are correct and well placed
// solution: 0, 0, 7, 7