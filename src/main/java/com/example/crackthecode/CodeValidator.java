/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
/**
 *
 * @author Danielnaor
 */
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.synth.SynthStyle;

public class CodeValidator {

    private List<Clue> clues;
    private Integer[] codeSolvedSoFar;
    ArrayList<Integer[]> allPosibleArrayList;
    
   

    public CodeValidator(List<Clue> clues, Integer[] code, ArrayList<Integer[]> allPosibleArrayList) {
        this.clues = clues;
        if (code != null) {
            this.codeSolvedSoFar = code;

        } else {
            this.codeSolvedSoFar = new Integer[clues.get(0).getCombination().length];
        }
        this.allPosibleArrayList = allPosibleArrayList;

    }

    public static boolean validateCode(Integer[] code, List<Clue> clues) {
        for (Clue clue : clues) {
            Hint hint = clue.getHint();
            Integer[] combination = clue.getCombination();

            if (!isHintMatched(hint, code, combination)) {
                return false;
            }
        }
        return true;
    }

    public static CodeResult getCodeResult(Integer[] possibleCode, Integer[] CombinationFromClue) {

        int countCorrectNumbers = 0;
        int countCorrectNumbersAndCorrectPlacement = 0;
        int countCorrectNumbersAndIncorrectPlacement = 0;

        // Track the numbers from possibleCode that have already been matched
        Set<Integer> matchedNumbers = new HashSet<>();

        for (int i = 0; i < possibleCode.length; i++) {
            for (int j = 0; j < CombinationFromClue.length; j++) {
                if (possibleCode[i] == CombinationFromClue[j]) {
                    if (!matchedNumbers.contains(possibleCode[i])) {
                        countCorrectNumbers++;
                        matchedNumbers.add(possibleCode[i]);
                    }
                    if (i == j) {
                        countCorrectNumbersAndCorrectPlacement++;
                    } else {
                        countCorrectNumbersAndIncorrectPlacement++;
                    }
                }
            }
        }

        return new CodeResult(countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement);
    }

    // a method that will loop trought each hint and compare it to the possible code if the possible code matches the hint and if it does not match the hint it will remove it from the possible code\
    // (matching the hint with the possible code meants that the number of correct numbers and correct placement and the number of correct numbers and incorrect placement are the same as the hint)
    //  Integer[] combinationT = new Integer[]{3,8,4,1};
    public void validateAllPossibleSolutionsWithHints() {

        HashMap<Integer, Integer[]> allPossibleSolutionsHashMap = convertArrayListToHashMap(allPosibleArrayList);

        for (Clue clue : clues) {
            Hint hint = clue.getHint();
            Integer[] combination = clue.getCombination();

            Iterator<Map.Entry<Integer, Integer[]>> iterator = allPossibleSolutionsHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Integer[]> entry = iterator.next();
                Integer[] possibleCode = entry.getValue();

                if (!isHintMatched(hint, possibleCode, combination)) {
                    iterator.remove(); // Remove the entry from the hashmap
                }
            }
        }

        for (Integer key : allPossibleSolutionsHashMap.keySet()) {
            // final validation that the entry from allPossibleSolutionsHashMap matches with all the clues 
            for (Clue clue : clues) {
                Hint hint = clue.getHint();
                Integer[] combination = clue.getCombination();
                Integer[] possibleCode = allPossibleSolutionsHashMap.get(key);
                if (!isHintMatched(hint, possibleCode, combination)) {
                    allPossibleSolutionsHashMap.remove(key);
                    break;
               } else if (isHintMatched(hint, possibleCode, combination)) {

                    continue;
                }

            }
            
        }
        
        if(allPossibleSolutionsHashMap.size() == 1){
            // get the only key from the hashmap
            //Set<Integer> keySet = allPossibleSolutionsHashMap.keySet();

            // Converting key set to an array
            //Integer[] keysArray = keySet.toArray(new Integer[keySet.size()]);
            
            //Integer key = keysArray[0];


            Integer key =  allPossibleSolutionsHashMap.keySet().toArray(new Integer[1])[0];



            System.out.println("The code is: " + Arrays.toString(allPossibleSolutionsHashMap.get(key)));

        }
        else{
            System.out.println("There are multiple codes to solve this and they are: ");
            for (Integer key : allPossibleSolutionsHashMap.keySet()) {
                Integer[] possibleCode = allPossibleSolutionsHashMap.get(key);
                System.out.println(possibleCode);
            }
         
            
                    

        }

    }

    private static boolean isHintMatched(Hint hint, Integer[] possibleCode, Integer[] combination) {
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

    private static HashMap<Integer, Integer[]> convertArrayListToHashMap(ArrayList<Integer[]> arrayList) {

        HashMap<Integer, Integer[]> hashMap = new HashMap<>();

        int key = 0;
        for (Integer[] item : arrayList) {

            hashMap.put(key, item);
            key++;
        }

        return hashMap;
    }
}
