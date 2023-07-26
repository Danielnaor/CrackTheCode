/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.lang.reflect.Array;
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

public class CodeValidator {

    private List<Clue> clues;
    private Integer[] codeSolvedSoFar;
    ArrayList<Integer[]> allPosibleArrayList;
    private Integer[] finalCode;

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

            boolean codeHasADuplicate = HasADuplicate(code);

            // if there is a dupe in code and also combo doesnt equal  1,4,7,7

            // print the combination and say combinationvalidateCode:
            if (codeHasADuplicate) {
                if (!isHintMatchedDupe(hint, code, combination)) {

                    return false;
                }

            } else if (!isHintMatched(hint, code, combination)) {
                return false;
            }
        }
        return true;
    }

    private static boolean HasADuplicate(Integer[] code) {

        Set<Integer> set = new HashSet<Integer>();
        for (Integer i : code) {
            if (set.contains(i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }

    public static CountsStat getCodeResult(Integer[] possibleCode, Integer[] CombinationFromClue) {

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

        return new CountsStat(countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement);
    }

    // a method that will loop trought each hint and compare it to the possible code if the possible code matches the hint and if it does not match the hint it will remove it from the possible code\
    // (matching the hint with the possible code meants that the number of correct numbers and correct placement and the number of correct numbers and incorrect placement are the same as the hint)
    //  Integer[] combinationT = new Integer[]{3,8,4,1};
    public void validateAllPossibleSolutionsWithHints() {

        HashMap<Integer, Integer[]> allPossibleSolutionsHashMap = convertArrayListToHashMap(allPosibleArrayList);
        // print all allPossibleSolutionsHashMap
       /* for (Integer key : allPossibleSolutionsHashMap.keySet()) {
            Integer[] possibleCode = allPossibleSolutionsHashMap.get(key);
            System.out.println("possibleCode: " + Arrays.toString(possibleCode));
        }*/
       allPossibleSolutionsHashMap.remove(1);
              allPossibleSolutionsHashMap.remove(2);

                
                
            
        for (Clue clue : clues) {
            Hint hint = clue.getHint();
            Integer[] combination = clue.getCombination();

            Iterator<Map.Entry<Integer, Integer[]>> iterator = allPossibleSolutionsHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Integer[]> entry = iterator.next();
                Integer[] possibleCode = entry.getValue();
                
                if (HasADuplicate(possibleCode)) {
                    if (!isHintMatchedDupe(hint, possibleCode, combination)) {
                        iterator.remove(); // Remove the entry from the hashmap
                    }
                } else if (!isHintMatched(hint, possibleCode, combination)) {
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

                /*if (!isHintMatched(hint, possibleCode, combination)) {
                    allPossibleSolutionsHashMap.remove(key);
                    break;
               } else if (isHintMatched(hint, possibleCode, combination)) {

                    continue;
                }
                 */
                if (HasADuplicate(possibleCode)) {
                    if (!isHintMatchedDupe(hint, possibleCode, combination)) {
                        allPossibleSolutionsHashMap.remove(key);
                        break;
                    }
                } else if (!isHintMatched(hint, possibleCode, combination)) {
                    allPossibleSolutionsHashMap.remove(key);
                    break;
                } else if (isHintMatched(hint, possibleCode, combination)) {

                    continue;
                }

            }

        }

        if (allPossibleSolutionsHashMap.size() == 1) {
            // get the only key from the hashmap
            //Set<Integer> keySet = allPossibleSolutionsHashMap.keySet();

            // Converting key set to an array
            //Integer[] keysArray = keySet.toArray(new Integer[keySet.size()]);
            //Integer key = keysArray[0];
            Integer key = allPossibleSolutionsHashMap.keySet().toArray(new Integer[1])[0];

            System.out.println("The code is: " + Arrays.toString(allPossibleSolutionsHashMap.get(key)));
            finalCode = allPossibleSolutionsHashMap.get(key);

        } else {
            System.out.println("There are multiple codes to solve this and they are: ");
            for (Integer key : allPossibleSolutionsHashMap.keySet()) {
                Integer[] possibleCode = allPossibleSolutionsHashMap.get(key);
                System.out.println(possibleCode);
            }

            finalCode = null;

        }

    }

    private static boolean isHintMatched(Hint hint, Integer[] possibleCode, Integer[] combination) {
        // Compare the hint with the possible code and combination
        // Return true if the hint matches the possible code and combination, false otherwise
        // Implement the comparison logic based on the requirements of your game
        // For example:
        // return hint.getCorrectCount() == calculateCorrectCount(possibleCode, combination)
        //        && hint.isCorrectlyPlaced() == isCorrectlyPlaced(possibleCode, combination);
        
        
        
        
        
        
        CountsStat codeResult = getCodeResult(possibleCode, combination);

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

    private static boolean isHintMatchedDupe(Hint hint, Integer[] possibleCode, Integer[] combination) {
        // print the possible code and the combination

        int countCorrectNumbersHint = hint.getCorrectCount();
        int countCorrectNumbersAndCorrectPlacementHint = hint.getCountCorrectNumbersAndCorrectPlacement();
        int countCorrectNumbersAndIncorrectPlacementHint = hint.getCountCorrectNumbersAndIncorrectPlacement();

        ArrayList<Integer> dupesInPossibleCode = findDupes(possibleCode);

        // now check if any of the dupe numbers in the possible code are even in the combination
        for (int i = 0; i < dupesInPossibleCode.size(); i++) {
            boolean DupeNumersInCombination = false;

            int currentDupe = dupesInPossibleCode.get(i);
            for (int j = 0; j < combination.length; j++) {
                if (currentDupe == combination[j]) {
                    DupeNumersInCombination = true;
                }
            }

            if (!DupeNumersInCombination) {
                dupesInPossibleCode.remove(i);
                i--;
            }
        }

        HashMap<Integer, DupeNumbers> dupeNumbers = new HashMap<>();
        // stores the times the dupe is placed correct and the times it isents placed correct
        
        
        

        // loop through the dupes in the possible code and count the number of times each dupe appears in the possible code and if the dupe is correctly placed with the combination
        for (int i = 0; i < dupesInPossibleCode.size(); i++) {
            int countAppearencesCode = 0; // Initialize count for each number - this is the count of the number of times the dupe appears in the possible code
            int countAppearencesCombination = 0; // Initialize count for each number - this is the count of the number of times the dupe appears in the combination
            boolean CorrectlyPlaced = false; // Initialize CorrectlyPlaced for each number
            int currentDupe = dupesInPossibleCode.get(i);
            ArrayList<Boolean > dupeCorrectlyPlaced = new ArrayList<>();


            
            // index of the current dupe in the possible code
            int indexOfCurrentDupe = 0;
            for (int j = 0; j < possibleCode.length; j++) {
                if (currentDupe == possibleCode[j]) {
                    indexOfCurrentDupe = j;

                }
                if (dupesInPossibleCode.get(i) == possibleCode[j]) {
                    countAppearencesCode++;
                    if (combination[j] == dupesInPossibleCode.get(i) && j == indexOfCurrentDupe) {
                        System.out.println(combination[j] + " " + " j= " + j + " combination: " + Arrays.toString(combination));
                        
                        CorrectlyPlaced = true;

                    }
                    else 
                    {
                        CorrectlyPlaced = false;
                    }

                    dupeCorrectlyPlaced.add(CorrectlyPlaced);
                }

            }
            

            int countTimesDupeInCombination = 0;
            for (int j = 0; j < combination.length; j++) {
                if (dupesInPossibleCode.get(i) == combination[j]) {
                    countTimesDupeInCombination++;
                }
            }


            if (dupeNumbers.containsKey(dupesInPossibleCode.get(i))) {
                DupeNumbers dupeAlreadyInHashMap = dupeNumbers.get(dupesInPossibleCode.get(i));
                if (dupeAlreadyInHashMap.isCorrectlyPlaced(indexOfCurrentDupe)) {
                    CorrectlyPlaced = true;
                }

                if (countAppearencesCode != dupeAlreadyInHashMap.getCountAppearencesCode()) {
                    System.out.println("count is not working correctly: " + countAppearencesCode);
                }

                dupeAlreadyInHashMap.setCountAppearencesCode(countAppearencesCode);
                dupeAlreadyInHashMap.setCorrectlyPlaced(indexOfCurrentDupe, CorrectlyPlaced);
                dupeNumbers.put(dupesInPossibleCode.get(i), dupeAlreadyInHashMap);
            } else {
                dupeNumbers.put(dupesInPossibleCode.get(i), new DupeNumbers(dupesInPossibleCode.get(i), countAppearencesCode,countAppearencesCombination, dupeCorrectlyPlaced));
        }
        }


        // print dupeCorrectlyPlaced
        // this will stores Digits that Appears an Equal amount of times in the combination and in the code and they appear More Then Ones 
        ArrayList<Integer> equalOccurrenceDigits = new ArrayList<>();


        
        
        
        int countCorrectNumbers = 0;
        int countCorrectNumbersAndCorrectPlacement = 0;
        int countCorrectNumbersAndIncorrectPlacement = 0;

        // count the number of correct numbers and correct placement and the number of correct numbers and incorrect placement in the possible code
        for (int i = 0; i < possibleCode.length; i++) {
            for (int j = 0; j < combination.length; j++) {
                if (possibleCode[i] == combination[j]) {
                    countCorrectNumbers++;

                    if (i == j) {
                        countCorrectNumbersAndCorrectPlacement++;
                    } else {
                        countCorrectNumbersAndIncorrectPlacement++;
                    }

                }
            }
        }
        
        
        
        
        
        

        for (int i = 0; i < dupesInPossibleCode.size(); i++) {
            int currentDupe = dupesInPossibleCode.get(i);

            DupeNumbers dupeNumber = dupeNumbers.get(currentDupe);

            countCorrectNumbersAndIncorrectPlacement -= dupeNumber.getCountAppearencesCode() - 1;
            countCorrectNumbers -= dupeNumber.getCountAppearencesCode() - 1;

        }
        
        
        

        System.out.println("possibleCode: " + Arrays.toString(possibleCode) + " " + countCorrectNumbers + " " + countCorrectNumbersAndCorrectPlacement + " " + countCorrectNumbersAndIncorrectPlacement);

        System.out.println("combination: " + Arrays.toString(combination) + " " + countCorrectNumbersHint + " " + countCorrectNumbersAndCorrectPlacementHint + " " + countCorrectNumbersAndIncorrectPlacementHint);
        System.out.println(countCorrectNumbersHint == countCorrectNumbers
                && countCorrectNumbersAndCorrectPlacementHint == countCorrectNumbersAndCorrectPlacement
                && countCorrectNumbersAndIncorrectPlacementHint == countCorrectNumbersAndIncorrectPlacement);

        System.out.println("");

        return countCorrectNumbersHint == countCorrectNumbers
                && countCorrectNumbersAndCorrectPlacementHint == countCorrectNumbersAndCorrectPlacement
                && countCorrectNumbersAndIncorrectPlacementHint == countCorrectNumbersAndIncorrectPlacement;
    }

    private static ArrayList<Integer> findDupes(Integer[] possibleCode) {
        ArrayList<Integer> numbersUsed = new ArrayList<>();
        ArrayList<Integer> dupes = new ArrayList<>();
        // loop through the possible code
        for (int i = 0; i < possibleCode.length; i++) {
            // if the number is not in the numbersUsed array list add it to the numbersUsed array list
            if (!numbersUsed.contains(possibleCode[i])) {
                numbersUsed.add(possibleCode[i]);
            } // if the number is in the numbersUsed array list add it to the dupes array list
            else {
                if (!dupes.contains(possibleCode[i])) {
                    dupes.add(possibleCode[i]);
                }

            }

        }
        // return the dupes array list
        return dupes;
    }

    // getter for finalCode
    public Integer[] getFinalCode() {
        return finalCode;
    }
}
