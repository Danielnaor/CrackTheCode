/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.io.Serializable;
import java.lang.reflect.Array;
/**
 *
 * @author Danielnaor
 */
// CodeCracker.java
import java.util.*;

public class CodeCracker implements Cloneable, Serializable {

    private boolean foundCode = false;

    private int codeLength;
    private List<Clue> clues;
    private List<Integer>[] bannedNumbers;

    private Integer[] code;
    private ArrayList<Integer> CorrectNumbersButWrongPlace;

    int ClueNothingCorrectIndex;
    ArrayList<Integer[]> allPosibleArrayList;

    public CodeCracker(List<Clue> clues_input) {
        this.clues = new ArrayList<Clue>();
        
        
        

        for (Clue clue1 : clues_input) {
            this.clues.add((Clue) clue1.clone());
        }

        this.codeLength = clues.get(0).getCombination().length;

        this.bannedNumbers = new ArrayList[codeLength];

        for (int i = 0; i < codeLength; i++) {
            bannedNumbers[i] = new ArrayList<>();
        }

        // initalised code so it has the length of the codeLength and all index has the value of -1 
        this.code = new Integer[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = -1;
        }

        CorrectNumbersButWrongPlace = new ArrayList<>();
        allPosibleArrayList = new ArrayList<>();
    }

    public void addClue(Clue clue) {
        clues.add((Clue) clue.clone());
    }

    public Integer[] crackCode() {


        

        // first step to solve it will be to Check if there is a clue hint where none of the numbers are correct.
        // if so              // add it that clue's combinations into the banned list of all indexes and then delete that clue
        while (checkNothingCorrect()) {
            // add it that clue's combinations into the banned list of all indexes, then loop through all clues and remove numbers from the banned list
            Integer[] combinationDigits = clues.get(ClueNothingCorrectIndex).getCombination();
            for (int i = 0; i < combinationDigits.length; i++) {
                Integer digit = combinationDigits[i];
                addNumberToBannedList(digit);
            }

            //  delete that clue 
            clues.remove(ClueNothingCorrectIndex);

            // loop thorught all the combinations and remove numbers that are in the banned list
            loopThroughAllCombinationsAndRemovedBannedNumbers();

            // check if some of thme are solved
            SolveCombinationsCracked();

            

            // check if the code was found
            if (checkIfCodeWasFound()) {
                return code;
            }

        }

        // go thorugh all combinations and add all the numbers to a list then add any number from 0-9 that is not in that list to the banned list
       // loopThroughAllCombinationsAndAddNumbersToBannedList();

        // loop trougth all clues and check if any clue has numbers placed wrongly if so add all those numbers to that baned list too. 
        loopThroughAllCluesAndAddNumbersToBannedListIfNotCorrectlyPlaced();
        

        SolveCombinationsCracked();

        // loop thorught all the combinations and remove numbers that are in the banned list
        loopThroughAllCombinationsAndRemovedBannedNumbers();        

        System.out.println("\n");

        SolveCombinationsCracked();
        
        
        

        if (checkIfCodeWasFound()) {

            return code;
        }  else {
            

                    SolveCombinationsCracked();

            // all possible combinations are in the clues
            allPosibleArrayList = getAllPossibleCombinations();

            if (allPosibleArrayList.size() == 1) {
                code = allPosibleArrayList.get(0);
                return code;

            } else {
                // loop through and print all the possible combinations
                for (Integer[] combination : allPosibleArrayList) {
                    if (!checkIfCombinationIsValid(combination)) {
                        allPosibleArrayList.remove(combination);
                        System.out.println("removed combination: " + Arrays.toString(combination));

                    }

                }

            }

            if (allPosibleArrayList.size() == 1) {
                code = allPosibleArrayList.get(0);
                foundCode = true;
                return code;

            } 
            
            System.out.println("part code: " + Arrays.toString(code));

            System.out.println("remaining clues: ");
            for (Clue clue : clues) {
                System.out.println((clue));
            }

            // print all the CorrectNumbersButWrongPlace
            System.out.println("CorrectNumbersButWrongPlace: " + CorrectNumbersButWrongPlace);
            
            
            
            
            return null;

        }
    }

    /* go thorugh all combinations and add all the numbers to a list then add any number from 0-9 that is not in that list to the banned list (of all indexes)
     */
    private void loopThroughAllCombinationsAndAddNumbersToBannedList() {
        HashSet<Integer> allNumbersPresentInOneOfTheCombinations = new HashSet<>();

        // loop through all clues
        for (Clue clue : clues) {
            // loop through all combination
            Integer[] combination = clue.getCombination();
            for (Integer digit : combination) {
                // add the digit to the set
                if (digit != null) {
                    allNumbersPresentInOneOfTheCombinations.add(digit);
                 //   System.out.println("digit: " + digit);
                }
               // System.out.println("null digit");
            }
        }
        

if(!allNumbersPresentInOneOfTheCombinations.isEmpty()){
        // loop through all numbers from 0-9
        for (int i = 0; i < 10; i++) {
            // if the number is not in the set add it to the banned list
            if (!allNumbersPresentInOneOfTheCombinations.contains(i)) {
                addNumberToBannedList(i);
            }
        }
}
    }

    /**
     * Checks if there is a clue hint where none of the numbers are correct.
     *
     * @return true if there is a clue with no correct numbers, false otherwise.
     */
    private boolean checkNothingCorrect() {

        // loop between clues
        for (int i = 0; i < clues.size(); i++) {
            Clue clue = clues.get(i);

            if (clue.getHint().getCorrectCount() == 0) {
                ClueNothingCorrectIndex = i;
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a number to the banned list of all indexes.
     *
     * @param num the number to be added to the banned list.
     */
    private void addNumberToBannedList(int num) {
        
        
        // if num equals to 0 print who called this method
        if (num == 0) {
            System.out.println("addNumberToBannedList was called from: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        }

     
        for (List<Integer> bannedList : bannedNumbers) {

            if (!bannedList.contains(num)) {
                bannedList.add(num);
            }
        }

    }

    /**
     * Adds a number to the banned list of a specific index.
     *
     * @param num the number to be added to the banned list.
     * @param index the index at which the number should be banned.
     */
    private void addNumberToBannedListAtSpecificIndex(int num, int index) {
        // if num equals to 0 and index is 1 print who called this method
        if (num == 0 && index == 1) {
            System.out.println("addNumberToBannedListAtSpecificIndex was called from: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        }
        
        if (!bannedNumbers[index].contains(num)) {
            bannedNumbers[index].add(num);
        }

    }

    /**
     * Checks if some combinations are cracked based on the number of correct
     * numbers from the Hint and based on if some of the digits were discovered
     * already.
     *
     *
     */
    private void SolveCombinationsCracked() {


        int NumOfNumbersNotNull = 0;
        int numOfCorrectNumbersInClueAndCorrectlyPlaced = 0;
        int numOfCorrectNumbersInClueAndIncorrectlyPlaced = 0;

        // NumbersNotNullAndIndexes
        ArrayList<DigitAndIndex> NumbersNotNullAndIndexes;

        ArrayList<Integer> indexesOfSolvedHintsThatNeedsToBeRemoved = new ArrayList<>();
        for (int j = 0; j < clues.size(); j++) {
            Clue clue = clues.get(j);
            NumOfNumbersNotNull = 0;
            numOfCorrectNumbersInClueAndCorrectlyPlaced = clue.getHint().   getCountCorrectNumbersAndCorrectPlacement();
            numOfCorrectNumbersInClueAndIncorrectlyPlaced = clue.getHint().getCountCorrectNumbersAndIncorrectPlacement();
            int numOfCorrectNumbersInClue = clue.getHint().getCorrectCount();

            if(numOfCorrectNumbersInClue != (numOfCorrectNumbersInClueAndCorrectlyPlaced+numOfCorrectNumbersInClueAndIncorrectlyPlaced))
            {
                throw new RuntimeException("the number of correct numbers in the clue is not equal to the number of correct numbers and correct placement and the number of correct numbers and incorrect placement");
            }

            
            NumbersNotNullAndIndexes = new ArrayList<>();

            Integer[] combinationDigits = clue.getCombination();
            for (int i = 0; i < combinationDigits.length; i++) {

                Integer digit = combinationDigits[i];
                // if the digit is not null, check if it is in the banned list of that index, if so replace it with null
                if (digit != null) {
                    NumOfNumbersNotNull++;

                    // add the digit and the index to the NumbersNotNullAndIndexes
                    NumbersNotNullAndIndexes.add(new DigitAndIndex(digit, i));

                }

            }

            if (NumOfNumbersNotNull == numOfCorrectNumbersInClue && clue.getHint().isCorrectlyPlaced()) {
                
                // loop through the NumbersNotNullAndIndexes and add the numbers to the code
                for (DigitAndIndex digitAndIndex : NumbersNotNullAndIndexes) {
                    code[digitAndIndex.getIndex()] = digitAndIndex.getDigit();
                }

                indexesOfSolvedHintsThatNeedsToBeRemoved.add(j);

            } else if (NumOfNumbersNotNull == numOfCorrectNumbersInClue && !clue.getHint().isCorrectlyPlaced()) {
                // add the numbers from to the Correct Numbers But Wrong Place NumbersNotNullAndIndexes
                for (DigitAndIndex digitAndIndex : NumbersNotNullAndIndexes) {
                    if (!CorrectNumbersButWrongPlace.contains(digitAndIndex.getDigit())) {
                        CorrectNumbersButWrongPlace.add(digitAndIndex.getDigit());
                    }
                }
                
                
                indexesOfSolvedHintsThatNeedsToBeRemoved.add(j);
            }

            else if( NumOfNumbersNotNull == numOfCorrectNumbersInClue && clue.getHint().getCountCorrectNumbersAndCorrectPlacement() >= 1 && clue.getHint().getCountCorrectNumbersAndIncorrectPlacement() >= 1){

// add the numbers from to the Correct Numbers But Wrong Place NumbersNotNullAndIndexes
                for (DigitAndIndex digitAndIndex : NumbersNotNullAndIndexes) {
                    if (!CorrectNumbersButWrongPlace.contains(digitAndIndex.getDigit())) {
                        CorrectNumbersButWrongPlace.add(digitAndIndex.getDigit());
                    }
                }
            


            } 

            // remove the solved clues from the clues list
            // make sure that the indexes are removed from the end to the start in order to not mess up the indexes mkae that the indexes are sorted
            Collections.sort(indexesOfSolvedHintsThatNeedsToBeRemoved);

            for (int i = indexesOfSolvedHintsThatNeedsToBeRemoved.size() - 1; i >= 0; i--) {
                clues.remove((int) indexesOfSolvedHintsThatNeedsToBeRemoved.get(i));
            }

        }

        

                        

    }

    /**
     * Loops through all clues and adds numbers to the banned number list of
     * corresponding indexes if the hint does not have any numbers correctly
     * placed.
     */
    private void loopThroughAllCluesAndAddNumbersToBannedListIfNotCorrectlyPlaced() {
        // if the say that the numbers are not correctly placed, move through the combination and for each digit add it to the banned list of that index
        // loop through all clues
        /// call isCluePlacedCorrectly and if false start comparing to the other combinations. 
        for(Clue clue: clues){
        
            if(!isCluePlacedCorrectly(clue)){
                // loop trought all indexes
                Integer[] combinationDigits = clue.getCombination();
                for (int i = 0; i < combinationDigits.length; i++) {
                    Integer digit = combinationDigits[i];
                    // if the digit is not null, check if at any other clues the digit at that index is the same and is correctly placed, if so add it to the banned list of that index
                    if (digit != null) {
                        for(int j = 0; j < clues.size(); j++){
                            Clue clue2 = clues.get(j);
                            if(isCluePlacedCorrectly(clue2)){
                                Integer[] combinationDigits2 = clue2.getCombination();
                                if(i != j && combinationDigits2[i] == digit){
                                    addNumberToBannedList(digit);
                                }
                            }
                        }
                       
                    }
                }
            }
        }

                loopThroughAllCombinationsAndRemovedBannedNumbers();


        
    }

  






    public boolean isCluePlacedCorrectly(Clue clue) {
        return clue.getHint().isCorrectlyPlaced();
    }

    /**
     * Moves through all combinations and replaces banned numbers with null. a
     * method to loop through all combinations, then loop through all indexes,
     * if the value at the index is not null, check if the value of that index
     * is not in the index's banned number list, and if it is replace that
     * number with a null
     */
    private void loopThroughAllCombinationsAndRemovedBannedNumbers() {
        
        // print what method called this method(loopThroughAllCombinationsAndRemovedBannedNumbers)
     //   System.out.println("the method which called loopThroughAllCombinationsAndRemovedBannedNumbers: " + Thread.currentThread().getStackTrace()[2].getMethodName());

        for (Clue clue : clues) {
            Integer[] combinationDigits = clue.getCombination();
            // loop through all digits of combinations
            for (int i = 0; i < combinationDigits.length; i++) {
                Integer digit = combinationDigits[i];
                // if the digit is not null, check if it is in the banned list of that index, if so replace it with null
                if (digit != null) {
                    if (bannedNumbers[i].contains(digit)) {
                        // print the current combination

                        combinationDigits[i] = null;
                    }
                }
            }
        }

                SolveCombinationsCracked();


    }

    /**
     * Return a list of all possible combinations for the code conidering the
     * banned numbers and the numbers that are already discovered.
     */
    private ArrayList<Integer[]> getAllPossibleCombinations() {
        
        SolveCombinationsCracked();

        ArrayList<Integer[]> combinations = new ArrayList<>();
        generateCombinations(combinations, new Integer[codeLength], 0);

        // remove combinations that doesnt contains all the numbers that are in the CorrectNumbersButWrongPlace
        for (int i = combinations.size() - 1; i >= 0; i--) {
            Integer[] combination = combinations.get(i);
            for (Integer digit : CorrectNumbersButWrongPlace) {
                if (!Arrays.asList(combination).contains(digit)) {
                    combinations.remove(i);
                    break;
                }
            }
        }

        
        return combinations;
    }

    private void generateCombinations(ArrayList<Integer[]> combinations, Integer[] currentCombination, int index) {
        if (index == codeLength) {
            combinations.add(currentCombination.clone());
            return;
        }

        if (code[index] != -1) {
            currentCombination[index] = code[index];
            generateCombinations(combinations, currentCombination, index + 1);
            return;
        }

        for (int digit = 0; digit < 10; digit++) {
            // print if the digit is in the banned list
            if (!bannedNumbers[index].contains(digit)) {

                currentCombination[index] = digit;
                generateCombinations(combinations, currentCombination, index + 1);
            }
        }
    }

    private boolean checkIfCodeWasFound() {

        if (clues.size() == 0) {
                allPosibleArrayList = getAllPossibleCombinations();

            if (allPosibleArrayList.size() == 1) {
                code = allPosibleArrayList.get(0);
                foundCode = true;
                return true;

            } 
        }




        for (int i = 0; i < codeLength; i++) {
            if (code[i] == -1) {
                return false;

            }
        }


        
        foundCode = true;

        return true;
    }

    // getter for allPosibleArrayList
    public ArrayList<Integer[]> getAllPosibleArrayList() {
        return allPosibleArrayList;
    }

    // method to anlyse whether or not a combination is a valid solution to the code (will check if the combination contains any banned numbers at any index)
    private boolean checkIfCombinationIsValid(Integer[] combination) {
        for (int i = 0; i < combination.length; i++) {
            if (bannedNumbers[i].contains(combination[i])) {

                return false;
            }
        }
        return true;
    }



    public boolean isFoundCode() {
        return foundCode;
    }

    public void setFoundCode(boolean foundCode) {
        this.foundCode = foundCode;
    }

}
