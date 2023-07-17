/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/**
 *
 * @author Danielnaor
 */
import java.util.List;

import javax.swing.plaf.synth.SynthStyle;

public class CodeValidator {

    private List<Clue> clues;
    private Integer[] codeSolvedSoFar;
    ArrayList<Integer[]> allPosibleArrayList;

    public CodeValidator(List<Clue> clues, Integer[] code, ArrayList<Integer[]> allPosibleArrayList) {
        this.clues = clues;
        if (code != null) {
            this.codeSolvedSoFar = code;

        }
        else{ 
            this.codeSolvedSoFar = new Integer[clues.get(0).getCombination().length];
        }
        this.allPosibleArrayList = allPosibleArrayList;

        // print the clues
        /*System.out.println("Clues: ");
        for (Clue clue : clues) {
            System.out.println(clue);
        }
*/
        // print the code
        System.out.println("Code valid: " + Arrays.toString(codeSolvedSoFar));

        // print the possible codes
        System.out.println("All possible: ");
        
       

    }

    public static CodeResult getCodeResult(Integer[] possibleCode, Integer[] originalCombination) {
        
        int countCorrectNumbers = 0;
        int countCorrectNumbersAndCorrectPlacement = 0;
        int countCorrectNumbersAndIncorrectPlacement = 0;

        for (int i = 0; i < possibleCode.length; i++) {
            for (int j = 0; j < originalCombination.length; j++) {
                if (possibleCode[i] == originalCombination[j]) {
                    countCorrectNumbers++;
                    if (i == j) {
                        countCorrectNumbersAndCorrectPlacement++;
                    } else {
                        countCorrectNumbersAndIncorrectPlacement++;
                    }
                   // originalCombination[j] = -1; // Mark the digit as used to avoid counting it again
                    break; // Move to the next digit in the possibleCode array
                }

            }

        }
        return new CodeResult(countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement);
    }

    // a method that will loop trought each hint and compare it to the possible code if the possible code matches the hint and if it does not match the hint it will remove it from the possible code\
    // (matching the hint with the possible code meants that the number of correct numbers and correct placement and the number of correct numbers and incorrect placement are the same as the hint)
    
    public void validateHints() {
         Integer[] combinationT = new Integer[]{3,8,4,1};

        allPosibleArrayList.add(combinationT);

        System.out.println("validateHints: ");
        for (Clue clue : clues) {
            Hint hint = clue.getHint();
            Integer[] combination = clue.getCombination();
           /*  
            System.out.println("hint: " + hint);
            System.out.println("combination: " + Arrays.toString(combination));
            System.out.println("combinationT: " + Arrays.toString(combinationT));
            System.out.println("matched: " + isHintMatched(hint, combination, combinationT));
            */
            if(!isHintMatched(hint, combination, combinationT)){
                System.out.println("no Solution");
                return;

            }   

        }

        System.out.println("Solution: " + Arrays.toString(combinationT));







       /*  System.out.println("Possible Codes: ");
        for (Integer[] possibleCode : allPosibleArrayList) {
            System.out.println(Arrays.toString(possibleCode));
        }

        */




    }
    
    private boolean isHintMatched(Hint hint, Integer[] possibleCode, Integer[] combination) {
        // Compare the hint with the possible code and combination
        // Return true if the hint matches the possible code and combination, false otherwise
        // Implement the comparison logic based on the requirements of your game
        // For example:
        // return hint.getCorrectCount() == calculateCorrectCount(possibleCode, combination)
        //        && hint.isCorrectlyPlaced() == isCorrectlyPlaced(possibleCode, combination);
        
        CodeResult codeResult = getCodeResult(possibleCode, combination);

        int countCorrectNumbers = codeResult.getCountCorrectNumbers();
        int countCorrectNumbersHint = hint.getCorrectCount();

        int countCorrectNumbersAndCorrectPlacement = codeResult.getCountCorrectNumbersAndCorrectPlacement();
        int countCorrectNumbersAndCorrectPlacementHint = hint.getCountCorrectNumbersAndCorrectPlacement();

        int countCorrectNumbersAndIncorrectPlacement = codeResult.getCountCorrectNumbersAndIncorrectPlacement();
        int countCorrectNumbersAndIncorrectPlacementHint = hint.getCountCorrectNumbersAndIncorrectPlacement();


        

        return countCorrectNumbers == countCorrectNumbersHint
                && countCorrectNumbersAndCorrectPlacement == countCorrectNumbersAndCorrectPlacementHint
                && countCorrectNumbersAndIncorrectPlacement == countCorrectNumbersAndIncorrectPlacementHint;
    }
    

    

    // now we will just make the final elimation by checking which possible code matches with all of the digits of Code (instance variable) that are not null
    public void filterPossibleCodes() {

        // loop through all the possible codes
        for (int i = 0; i < allPosibleArrayList.size(); i++) {
            // Get the possible code from the possible codes array list
            Integer[] possibleCode = allPosibleArrayList.get(i);
            // loop through all the digits of the code (instance variable) that are not null
            for (int j = 0; j < codeSolvedSoFar.length; j++) {
                if (codeSolvedSoFar[j] != null) {
                    // if the possible code does not match the digit of the code (instance variable) that is not null remove the possible code from the possible codes array list
                    if (possibleCode[j] != codeSolvedSoFar[j]) {
                        allPosibleArrayList.remove(i);
                        // Decrement i so that the next possible code will be checked in the next iteration
                        i--;
                        break;
                    }
                }
            }
        }

    }
}
