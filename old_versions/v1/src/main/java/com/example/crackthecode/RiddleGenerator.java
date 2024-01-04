/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Danielnaor
 */
public class RiddleGenerator {

    // this will generate a full riddle the riddle is made out of the clues and the combinaitons; 
    Integer[] Solution;
    Integer codeLength; // this will represent the amounts of digits the code have. if not privided we will use 3 as defult.
    Integer maxHints; // this is the max amount of hint, unless told other wise we will set it to two times the lenght of the code. 
    Integer base; //  An integer representing the base of the riddle's digits, meaning what is the max digit (exlusive), defult is 10.
    Integer numPossibleCombos;
    Boolean takeUserInput; // if takeUserInput is false just use the defult value;
    public static Riddle riddle;
    Integer ClueNothingCorrectIndex = null;
    Integer minHints = 50; // this is the min amount of hints, unless told other wise we will set it to the length of the code.

    Integer chanceToGetNothingIsCorrect = 15; // this is the chance to get nothing is correct,  set it to 15%
    Integer chanceToGetAllDigitsCorrect = 5;  // this is the chance to get all digits correct but not in the right place, set it to 5%

    public RiddleGenerator(Boolean takeUserInput) {

        // check if the user wants to take input from the user or not.
        if (takeUserInput == null || takeUserInput == false) {
            this.takeUserInput = false;
        } else {
            this.takeUserInput = true;
        }

        // if the user wants to take input from the user we will ask him how many digits he wants the code to be.
        if (this.takeUserInput) {
            Scanner scanner = new Scanner(System.in);

            // ask the user how many digits he wants the code to be.
            System.out.println("How many digits do you want the code to be?");
            // if an integer was not provided we will use 3 as defult.
            String response = scanner.nextLine();
            if (response == null || response == "") {
                this.codeLength = 3;
            } else {
                this.codeLength = Integer.parseInt(response);
            }

            // ask the user how many hints he wants to have.
            System.out.println("How many hints do you want to have?");
            // if an integer was not provided we will use 2 times the code length as defult.
            response = scanner.nextLine();
            if (response == null || response == "") {
                this.maxHints = 2 * this.codeLength;
            } else {
                this.maxHints = Integer.parseInt(response);
            }

            // ask the user what is the base of the riddle.
            System.out.println("What is the base of the riddle?");
            // if an integer was not provided we will use 10 as defult.
            response = scanner.nextLine();
            if (response == null || response == "") {
                this.base = 10;
            } else {
                this.base = Integer.parseInt(response);
            }

        } else {
            // set the defult values.
            this.codeLength = 3;
            this.maxHints = 3 * this.codeLength;
            this.base = 10;
            this.minHints = 2 * this.codeLength - 2;
        }

        // now based on this values we will generate the solution for the riddle.
        this.Solution = GenerateSolution();

        // create a new riddle.
        riddle = new Riddle(this.Solution, this.codeLength, this.maxHints, this.base);
        
    }

    public Riddle GenerateFullRiddle(Riddle riddle) {
        // this method will generate a riddle based on the riddle provided.
        // the riddle provided is a riddle object, that contains the solution, the code length, the max hints and the base the part it is missing is the clues. 
        // the method will return a riddle object that contains the solution, the code length, the max hints, the base and the clues.

        // generate the clues.
        ArrayList<Clue> clues = new ArrayList<>();

        // max failed attempts will be 30
        Integer maxFailedAttempts = 200;

        Integer failedAttempts = 0;

        Integer maxHints = riddle.getMaxHints();

        boolean RiddleIsSolvalbeWithTheClues = false;

        // while failed attempts is less then max failed attempts, the riddle is not solvable with the clues and while the amount of clues is less then the min amount of hints.
        while (failedAttempts < maxFailedAttempts && !RiddleIsSolvalbeWithTheClues) {

            if (this.chanceToGetNothingIsCorrect != 0 && chanceToGetNothingIsCorrect > 0 && chanceToGetNothingIsCorrect != 100) {
                // split evenly so that the chance to get chanceToGetNothingIsCorrect is the same as all others (except chanceToGetAllDigitsCorrect)

                this.chanceToGetNothingIsCorrect = (int) (100 / (riddle.getCodeLength()));
                System.out.println("the chance to get nothing is correct is " + this.chanceToGetNothingIsCorrect);
            }

            // generate a clue.
            Clue clue = GenerateClue(riddle);

            clues.add(clue);

            riddle.setClues(clues);

            if (clues.size() == this.maxHints - 1 && !IsSolvableWithTheClues(riddle.getSolution(), clues)) {
                boolean hasNothingCorrectClue = LookForNothingCorrect(clues);

                if (!hasNothingCorrectClue) {

                    if (chanceToGetNothingIsCorrect != (int) (100 / (riddle.getCodeLength()))) {
                        System.out.println("chanceToGetNothingIsCorrect is not (int) (100 / (riddle.getCodeLength()) and it should be");

                    }
                    chanceToGetNothingIsCorrect = 100;

                }

            }

            // if the amount of clues is within the min and max hints we will check if the riddle is solvable with the clues.
            if (clues.size() > this.minHints && clues.size() <= this.maxHints) {
                // the amount of clues is within the min and max hints.
                // check if the riddle is solvable with the clues.
                RiddleIsSolvalbeWithTheClues = IsSolvableWithTheClues(riddle.getSolution(), clues);

                if (RiddleIsSolvalbeWithTheClues) {

                    if (numPossibleCombos > 1) {

                        // check if there is nothing is correct
                        boolean hasNothingCorrectClue = LookForNothingCorrect(clues);

                        // if there is nothing is correct 
                        if (hasNothingCorrectClue) {
                            // create a hashmap of the digits and the amount of times they appear in the clues
                            HashMap<Integer, Integer> digitsAndCount = new HashMap<>();

                            Integer[] comboOfNothingCorrect = clues.get(ClueNothingCorrectIndex).getCombination();
                            // loop through the combination of the nothing is correct clue and add the digits to the hashmap and increase the count by one if the digit is already in the hashmap
                            for (Integer digit : comboOfNothingCorrect) {
                                if (digitsAndCount.containsKey(digit)) {
                                    digitsAndCount.put(digit, digitsAndCount.get(digit) + 1);
                                } else {
                                    digitsAndCount.put(digit, 1);
                                }

                            }

                            // loop through all the clues and add the digits to the hashmap and increase the count by one if the digit is already in the hashmap
                            for (Clue clue2 : clues) {
                                Integer[] combination = clue.getCombination();
                                for (Integer digit : combination) {
                                    if (digitsAndCount.containsKey(digit)) {
                                        digitsAndCount.put(digit, digitsAndCount.get(digit) + 1);
                                    }
                                }
                            }

                            // now check if check how many of the digits appear in the hashmap 0 times 
                            Integer countOfDigitsThatAppear0Times = 0;
                            for (Integer digit : digitsAndCount.keySet()) {
                                if (digitsAndCount.get(digit) == 0) {
                                    countOfDigitsThatAppear0Times++;
                                }
                            }

                            // while the count of digits that appear 0 times is more then 1 we will regenerate the nothing is correct clue
                            while (countOfDigitsThatAppear0Times > 1) {
                                clues.remove(ClueNothingCorrectIndex);

                                // change the chance to get nothing is correct to 100
                                chanceToGetNothingIsCorrect = 100;

                                // generate a new nothing is correct clue
                                clue = GenerateClue(riddle);
                                clues.add(clue);
                                riddle.setClues(clues);

                                // create a hashmap of the digits and the amount of times they appear in the clues
                                digitsAndCount = new HashMap<>();

                                LookForNothingCorrect(clues);
                                comboOfNothingCorrect = clues.get(ClueNothingCorrectIndex).getCombination();
                                // loop through the combination of the nothing is correct clue and add the digits to the hashmap and increase the count by one if the digit is already in the hashmap
                                for (Integer digit : comboOfNothingCorrect) {
                                    if (digitsAndCount.containsKey(digit)) {
                                        digitsAndCount.put(digit, digitsAndCount.get(digit) + 1);
                                    } else {
                                        digitsAndCount.put(digit, 1);
                                    }

                                }

                                // loop through all the clues and add the digits to the hashmap and increase the count by one if the digit is already in the hashmap
                                for (Clue clue2 : clues) {
                                    Integer[] combination = clue.getCombination();
                                    for (Integer digit : combination) {
                                        if (digitsAndCount.containsKey(digit)) {
                                            digitsAndCount.put(digit, digitsAndCount.get(digit) + 1);
                                        }
                                    }
                                }

                                // now check if check how many of the digits appear in the hashmap 0 times 
                                countOfDigitsThatAppear0Times = 0;
                                for (Integer digit : digitsAndCount.keySet()) {
                                    if (digitsAndCount.get(digit) == 0) {
                                        countOfDigitsThatAppear0Times++;
                                    }
                                }
                            }
                        }

                    }
                    riddle.setClues(clues);
                    riddle.setSolvable(true);

                    return riddle;
                }

            } else if (clues.size() > this.maxHints) {
                //else if the amount of clues passed the max 
                // clear the clues 
                clues.clear();
                failedAttempts++;
            }

        }

        return null;

    }

    private boolean LookForNothingCorrect(ArrayList<Clue> clues) {

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

    private boolean IsSolvableWithTheClues(Integer[] solution, ArrayList<Clue> clues) {

        ArrayList<Clue> deepCopyOfClues = new ArrayList<>();

        for (Clue clue : clues) {
            Clue shallowCopy = new Clue(clue.getCombination().clone(), (Hint) clue.getHint().clone());

            deepCopyOfClues.add(shallowCopy);
        }

        CodeCracker codeCracker = new CodeCracker(deepCopyOfClues);
        Integer[] code = codeCracker.crackCode();

        boolean foundCode = codeCracker.isFoundCode();

        if (!foundCode) {

            numPossibleCombos = codeCracker.getAllPosibleArrayList().size();

            CodeValidator codeValidator = new CodeValidator(clues, code, codeCracker.getAllPosibleArrayList());
            codeValidator.validateAllPossibleSolutionsWithHints();

            foundCode = codeValidator.isFoundFinalCode();
            if (foundCode) {
                Integer[] finalCode = codeValidator.getFinalCode();

                // check if the final code is the solution.
                boolean isSolution = Arrays.equals(finalCode, solution);

                if (isSolution) {
                    System.out.println("The code is: " + Arrays.toString(finalCode));
                    return true;
                } else {
                    System.out.println("The code is not the solution");
                    return false;
                }

            } else {
                System.out.println("The code was not found");
                return false;
            }
            //s*/

        } else {
            boolean codeIsCorrect = CodeValidator.validateCode(code, clues);
            numPossibleCombos = 1;
            if (codeIsCorrect) {
                System.out.println("The code is: " + Arrays.toString(code));
                return true;

            } else if (!codeIsCorrect) {
                System.out.println("The code is not correct");
                return false;

            }
        }

        return false;

    }

    public Clue GenerateClue(Riddle riddle) {

        
        // if the soluation to the riddle contains a dupe we will set the chances to get all digits to 0;
        if (countOfUniqeDigits(riddle.getSolution()) < riddle.getCodeLength()) {
            this.chanceToGetAllDigitsCorrect = 0;
            System.out.println("the solution contains a dupe so we will set the chance to get all digits correct to " + this.chanceToGetAllDigitsCorrect);
        }

        // mkae the chanceToGetNothingIsCorrect and the chance to get all other digits the same unless chanceToGetNothingIsCorrect is 0
        // this method will generate a clue for the riddle.
        // to start off we will need to generate the amount of digits that are right, and then based on that we will generate the amount of digits that are in the correct place and the amount of digits that are in the wrong place.
        // we will use a custom random number generator to the amount of correct digits becuse we only want one clue where 0 digits are correct and one clue where all digits are correct and only one clue where all digits are correct but not in the right place.
        //Integer correctDigits = CustomRandomNumberGenerator();
        
        
        Integer countCorrectNumbers = CustomRandomNumberGenerator();
        // now we will randomly chose out of correctDigits, how many digits are correct
        Integer countCorrectNumbersAndCorrectPlacement;
        Integer countCorrectNumbersAndIncorrectPlacement;
        if (countCorrectNumbers > 0) {
            // now we will randomly chose out of correctDigits, how many digits are correct but not in the right place.
            
            countCorrectNumbersAndCorrectPlacement = (int) (Math.random() * countCorrectNumbers);

            // if the amount of correct digits is odd and the amount of correct digits equals the length of the code and the amount of correct digits and correct placement is equal to the amount of correct digits minus 1 then we will generate a new amount of correct digits and correct placement.
            if (riddle.getCodeLength() % 2 == 1 && countCorrectNumbers == riddle.getCodeLength() && countCorrectNumbersAndCorrectPlacement == (riddle.getCodeLength() - 1 )) {
 
                
                
                while(countCorrectNumbersAndCorrectPlacement == (countCorrectNumbers -1)){
                            countCorrectNumbersAndCorrectPlacement = (int) (Math.random() * countCorrectNumbers);
                }
                
                

            }

            countCorrectNumbersAndIncorrectPlacement = countCorrectNumbers - countCorrectNumbersAndCorrectPlacement;








        } else {
            countCorrectNumbersAndCorrectPlacement = 0;
            countCorrectNumbersAndIncorrectPlacement = 0;

        }
        
    
                System.out.println("GenerateClue summery:");
                System.out.println("the solution is: " + Arrays.toString(riddle.getSolution()));
                System.out.println("countCorrectNumbers: " + countCorrectNumbers);
                System.out.println("countCorrectNumbersAndCorrectPlacement: " + countCorrectNumbersAndCorrectPlacement);
                System.out.println("countCorrectNumbersAndIncorrectPlacement: " + countCorrectNumbersAndIncorrectPlacement);
                System.out.println("GenerateClue summery end");
                

        // now we will make sure that the amount of correct digits which are correct and the amount of correct digits which are incorrect is equal to the amount of correct digits.
        if (countCorrectNumbersAndCorrectPlacement + countCorrectNumbersAndIncorrectPlacement != countCorrectNumbers) {
            throw new Error("countCorrectNumbersAndCorrectPlacement + countCorrectNumbersAndIncorrectPlacement != correctDigits");
        }

        // generate the message for the hint.
        String message = generateHintMessage(countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement);

        CorrectnessCountsResult correctnessCountsResult = new CorrectnessCountsResult(countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement);
        // now we will determine the amount of digits that are incorrect.
        Integer countIncorrectNumbers = riddle.getCodeLength() - countCorrectNumbers;

        Hint hint = new Hint(message, countCorrectNumbers, countCorrectNumbersAndCorrectPlacement, countCorrectNumbersAndIncorrectPlacement, false);

        // in order to place the digits the correct way we will need to use the solution.
        Integer[] solution = riddle.getSolution();

        Integer[] combination = new Integer[riddle.getCodeLength()]; // this will be the combination that we will return with the clue.

        if (countCorrectNumbers == 0) {

            // we will loop through the solution and add the digits to the combination
            for (int i = 0; i < combination.length; i++) {
                if (combination[i] == null) {
                    // generate a random number that is not in the solution.
                    Integer randomNumber = RandomNumberNotInTheSoluation(riddle);

                    // make sure that the number is not in the combination
                    while (Arrays.asList(combination).contains(randomNumber)) {
                        randomNumber = RandomNumberNotInTheSoluation(riddle);
                    }

                    // set the combination at the index to the random number.
                    combination[i] = randomNumber;
                    countIncorrectNumbers--;
                }

            }

            if (countOfUniqeDigits(solution) != riddle.getCodeLength()) {
                throw new Error("the nothing is correct contains a dupe");
            }

        } else if(countCorrectNumbers == riddle.getCodeLength()){
              // we will loop through the solution and add the digits to the combination
            for (int i = 0; i < combination.length; i++) {
                combination[i] = solution[i];
            }

            if (countCorrectNumbers == countCorrectNumbersAndIncorrectPlacement) {
                System.out.println("before the shuffle the combination is ");
                System.out.println(Arrays.toString(combination));


                combination = allIndexsShuffle(combination);

                System.out.println("after the shuffle the combination is ");
                System.out.println(Arrays.toString(combination));
            

                while (countOfCorrectDigitsInCorrectPlace(combination, solution) != 0) {
                    combination = allIndexsShuffle(combination);
                }


                
                // use this.countOfCorrectDigitsInIncorrectPlace(combination, solution) to make sure that there are no correct digits in the wrong place.
                 if (countOfCorrectDigitsInIncorrectPlace(combination, solution) != countCorrectNumbers) {
                    System.out.println("this.countOfCorrectDigitsInIncorrectPlace(combination, solution) is " + countOfCorrectDigitsInIncorrectPlace(combination, solution));
                    System.out.println("countCorrectNumbers is " + countCorrectNumbers);
                    throw new Error("this.countOfCorrectDigitsInIncorrectPlace(combination, solution) !== countCorrectNumbers");

                }

                if (countOfCorrectDigitsInIncorrectPlace(combination, solution) == countCorrectNumbers) {
                    countCorrectNumbersAndIncorrectPlacement = 0;
                    countCorrectNumbers = 0;
                }

            } else {
                combination = customShuffle(combination, countCorrectNumbersAndIncorrectPlacement / 2); // we will shuffle the combination.
                if(countOfCorrectDigitsInCorrectPlace(combination, solution) != countCorrectNumbersAndCorrectPlacement){

                    throw new Error("this.countOfCorrectDigitsInCorrectPlace(combination, solution) !== countCorrectNumbersAndCorrectPlacement");
                } 

                if (countOfCorrectDigitsInIncorrectPlace(combination, solution) != countCorrectNumbersAndIncorrectPlacement) {
                    throw new Error("this.countOfCorrectDigitsInIncorrectPlace(combination, solution) !== countCorrectNumbersAndIncorrectPlacement");
                }

                countCorrectNumbersAndIncorrectPlacement = 0;
                countCorrectNumbers = 0;
                countCorrectNumbersAndCorrectPlacement = 0;



            }

        }

        // a possible solution to creating the combination for the clue is in addion then making the ArrayList of selected index, there will also be a set (Set<Integer>) of the chosen number - we will be using a set because a set will make sure that thier is onlu uniqr numbers in the combination and not any due. 
        // ArrayList of selected index
        ArrayList<Integer> selectedIndex = new ArrayList<Integer>();

        // Set of the chosen number
        Set<Integer> chosenNumbers = new HashSet<Integer>();

        // now we will popluate the selectedIndex randomly, until the size of chosenNumbers is equal to the amount of correct digits.
        while (chosenNumbers.size() < countCorrectNumbers) {
            // generate a random index.
            System.out.println("loop 1");
            Integer index = (int) (Math.random() * riddle.getCodeLength());

            Integer chosenNumber = solution[index];

            // if the chosen number is already in the chosenNumbers set we will continue to the next iteration.
            if (chosenNumbers.contains(chosenNumber)) {
                System.out.println("we will pass this number since it is a already in the chose numbers");
                continue;
            } else {
                chosenNumbers.add(chosenNumber);
                selectedIndex.add(index);
            }

        }

        // while loop to place the numbers that are correct and correctly placed.
        while (countCorrectNumbersAndCorrectPlacement > 0) {
                        System.out.println("loop 2");

            // generate a random index from the selectedIndex. - this will be the index of one number from the solution that we will set to the combination at that index.
            Integer index = selectedIndex.get((int) (Math.random() * selectedIndex.size()));

            // set the combination at the index to the solution at the index.
            combination[index] = solution[index];

            // remove the index from the selectedIndex.
            selectedIndex.remove(index);

            // decrease the countCorrectNumbersAndCorrectPlacement by 1.
            countCorrectNumbersAndCorrectPlacement--;

            // decrease the countCorrectNumbers by 1.
            countCorrectNumbers--;

        }
        
        
         System.out.println("GenerateClue summery:");
                System.out.println("the solution is: " + Arrays.toString(riddle.getSolution()));
                System.out.println("countCorrectNumbers: " + correctnessCountsResult.getCountCorrectNumbers());
                System.out.println("countCorrectNumbersAndCorrectPlacement: " + correctnessCountsResult.getCountCorrectNumbersAndCorrectPlacement());
                System.out.println("countCorrectNumbersAndIncorrectPlacement: " + correctnessCountsResult.getCountCorrectNumbersAndIncorrectPlacement());
                System.out.println("GenerateClue summery end");
                
        int tries = 100;
        // while loop to place the numbers that are correct but not correctly placed.
        while (countCorrectNumbersAndIncorrectPlacement > 0) {
            // generate a random index from the selectedIndex. - this will be the index of one number from the solution that we will set to the combination at a different index.
            
            if(selectedIndex.size() == 0) {
                System.out.println("the selectedIndex is empty");
                break;
            }
            
      
            Integer index = selectedIndex.get((int) (Math.random() * selectedIndex.size()));

            Integer combinationIndex = (int) (Math.random() * riddle.getCodeLength());

            while(index == combinationIndex){
            // generate a random index from the combination. - this will be the index of where we will set the number from the solution.
                combinationIndex = (int) (Math.random() * riddle.getCodeLength());
            }
            
                //System.out.println("loop 3");

            boolean allNumbersFilled = true;
            for (int i = 0; i < combination.length; i++) {
                if (combination[i] == null) {
                    allNumbersFilled = false;
                    break;
                }
            }
            
            
            
             if(allNumbersFilled ) {
                System.out.println("all numbers are filled");
                System.out.println("countCorrectNumbersAndIncorrectPlacement is " + countCorrectNumbersAndIncorrectPlacement);
                // if all the numbers in the combination are filled, we will break out of the loop.
                break;

            }
            
            
            // check if in the combination at the combinationIndex there is already a number, or if the combinationIndex is equal to the index.
            if (combination[combinationIndex] != null || combinationIndex == index) {
                // if there is already a number in the combination at the combinationIndex or if the combinationIndex is equal to the index we will continue to the next iteration.
                continue;
            }

            // set the combination at the combinationIndex to the solution at the index.
            combination[combinationIndex] = solution[index];

            // remove the index from the selectedIndex.
            selectedIndex.remove(index);

            // decrease the countCorrectNumbersAndIncorrectPlacement by 1.
            countCorrectNumbersAndIncorrectPlacement--;

            // decrease the countCorrectNumbers by 1.
            countCorrectNumbers--;

        }

        // throw an error if the amount of correct digits is not 0.
        if (countCorrectNumbers != 0) {
            throw new Error("correctDigits != 0");
        }

        // for each index in the combination that is null we will set it to a number that is not in the solution.
        for (int i = 0; i < combination.length; i++) {
                                        System.out.println("loop 4");

            if (combination[i] == null) {

                // generate a random number that is not in the solution.
                Integer randomNumber = RandomNumberNotInTheSoluation(riddle);
                // set the combination at the index to the random number.
                combination[i] = randomNumber;
                countIncorrectNumbers--;
            }

        }

        if (countIncorrectNumbers != 0) {

            throw new Error("countIncorrectNumbers != 0");
        }
        // create a new clue.
        Clue clue = new Clue(combination, hint);

        // validate the clue.
        boolean isValid = CodeValidator.validateClue(riddle.getSolution(), clue);

        if (!isValid) {
            throw new Error("the clue is not valid");
        } else {
            System.out.println("the clue is valid");
        }

        return clue;
    }

    public Integer CustomRandomNumberGenerator() {

        // this method will generate a random number based on the chances provided.
        // the chances are the chances to get 0, 1 ... up to the length of the code correct.
        // the method will return an integer representing the amount of correct digits.
        // generate a random number between 0 and 100.
        Integer randomNumber = (int) (Math.random() * 100);

        // if the random number is less then the chance to get nothing correct.
        if (randomNumber < this.chanceToGetNothingIsCorrect) {
            // return 0. and turn the chance to get nothing correct to 0.
            System.out.println("before changing nothing is correct " + chanceToGetNothingIsCorrect);

            this.chanceToGetNothingIsCorrect = 0;
            System.out.println("after changing nothing is correct" + chanceToGetNothingIsCorrect);

            return 0;

        } else if (randomNumber < this.chanceToGetNothingIsCorrect + this.chanceToGetAllDigitsCorrect && chanceToGetAllDigitsCorrect != 0) {

            System.out.println("all digits are correct");
            System.out.println("randomNumber: " + randomNumber);
            System.out.println("chanceToGetNothingIsCorrect: " + this.chanceToGetNothingIsCorrect);
            System.out.println("chanceToGetAllDigitsCorrect: " + this.chanceToGetAllDigitsCorrect);

            // if the random number is less then the chance to get nothing correct + the chance to get all digits correct.
            // return the length of the code. and turn the chance to get all digits correct to 0.
            this.chanceToGetAllDigitsCorrect = 0;

            System.out.println(codeLength);

            return this.codeLength;

        } else {
            // if the random number is more then the chance to get nothing correct + the chance to get all digits correct.
            // Return a random number between 1 and the length of the code (exclusive). 
            int randNumber = (int) (Math.random() * (this.codeLength - 1)) + 1;

            if (randNumber == this.codeLength) {
                // throw an error.
                throw new Error("randNumber is equal to the code length");
            }

            if (randNumber == 0) {
                // throw an error.
                throw new Error("randNumber is equal to 0");
            }

            return randNumber;

        }
    }

    public Integer countOfUniqeDigits(Integer[] array) {
        // this method will return the amount of uniqe digits in the array.
        // the array is an array of integers.
        // the method will return an integer representing the amount of uniqe digits in the array.

        Set<Integer> uniqeDigits = new HashSet<Integer>();

        for (Integer digit : array) {
            uniqeDigits.add(digit);
        }

        return uniqeDigits.size();

    }

    private Integer[] GenerateSolution() {
        // this method will generate a solution for the riddle.
        // the solution is an array of integers, each integer is a digit in the solution.
        // the length of the array is the length of the code.
        // the max digit is the base of the riddle. (exlusive)

        Integer[] possibleSolution = new Integer[this.codeLength];

        for (int i = 0; i < codeLength; i++) {

            // generate a random digit.
            Integer digit = (int) (Math.random() * this.base);

            // add the digit to the possible solution.
            possibleSolution[i] = digit;
        }

        while (countOfUniqeDigits(possibleSolution) <= 2) {
            // if the amount of uniqe digits is less then 2 we will generate a new digit.

            // generate a random digit.
            Integer digit = RandomNumberNotInTheSoluationArray(possibleSolution);

            // add the digit to the possible solution at a random index.
            Integer index = (int) (Math.random() * this.codeLength);
            possibleSolution[index] = digit;
        }

        return possibleSolution;
    }
    
     public Integer RandomNumberNotInTheSoluationArray(Integer[] solution) {
        // this method will generate a random number that is not in the solution.
        // the riddle is a riddle object that contains the solution.
        // the method will return an integer representing a random number that is not in the solution.

        // generate a random number.
        Integer randomNumber = (int) (Math.random() * base);
        
        // while the solution contains the random number.
        while (Arrays.asList(solution).contains(randomNumber)) {
            // generate a new random number.
            randomNumber = (int) (Math.random() * base);
        }

        return randomNumber;
    }
     
     

    public Integer RandomNumberNotInTheSoluation(Riddle riddle) {
        // this method will generate a random number that is not in the solution.
        // the riddle is a riddle object that contains the solution.
        // the method will return an integer representing a random number that is not in the solution.

        // generate a random number.
        Integer randomNumber = (int) (Math.random() * riddle.getBase());

        Integer[] solution = riddle.getSolution();
        // while the solution contains the random number.
        while (Arrays.asList(solution).contains(randomNumber)) {
            // generate a new random number.
            randomNumber = (int) (Math.random() * riddle.getBase());
        }

        return randomNumber;
    }

    public static String generateHintMessage(int correctCount, int countCorrectNumbersAndCorrectPlacement, int countCorrectNumbersAndIncorrectPlacement) {
        if (correctCount != (countCorrectNumbersAndCorrectPlacement + countCorrectNumbersAndIncorrectPlacement)) {
            System.out.println("The amount of correct numbers is incorrect (problem with generator)");
        }

        String[] NUMBER_WORDS = {
            "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
        };

        // store the to be verb for each number
        String[] toBeVerbs = {
            "are", "is", "are", "are", "are", "are", "are", "are", "are", "are"
        };

        String message = "";
        if (correctCount == 0) {
            message = "Nothing is correct";
        } else if (correctCount == 1) {
            message = NUMBER_WORDS[correctCount] + " number is correct";
            if (countCorrectNumbersAndCorrectPlacement == 1) {
                message += " and well placed";
            } else if (countCorrectNumbersAndIncorrectPlacement == 1) {
                message += " but wrongly placed";
            }
        } else {
            message = NUMBER_WORDS[correctCount] + " numbers are correct";
            if (countCorrectNumbersAndCorrectPlacement > 0 && countCorrectNumbersAndIncorrectPlacement > 0) {
                //message += ". " + NUMBER_WORDS[countCorrectNumbersAndCorrectPlacement] + " are well placed and "
                //       + NUMBER_WORDS[countCorrectNumbersAndIncorrectPlacement] + " are wrongly placed.";
                // we will use the to be verb is if there is either one number that is well placed or one number that is wrongly placed and are if there more than one number that is well placed or more than one number that is wrongly placed
                message += ". " + NUMBER_WORDS[countCorrectNumbersAndCorrectPlacement] + " " + toBeVerbs[countCorrectNumbersAndCorrectPlacement] + " well placed and ";

                // because this is in the middle of the sentence we will uncapitalize the NUMBER_WORDS using String.toLowerCase()
                if (countCorrectNumbersAndIncorrectPlacement == 1) {
                    message += "the other " + toBeVerbs[countCorrectNumbersAndIncorrectPlacement] + " wrongly placed";
                } else {
                    message += "the other " + NUMBER_WORDS[countCorrectNumbersAndIncorrectPlacement].toLowerCase() + " " + toBeVerbs[countCorrectNumbersAndIncorrectPlacement] + " wrongly placed";
                }

            } else if (countCorrectNumbersAndCorrectPlacement > 0) {
                message += " and well placed";
            } else if (countCorrectNumbersAndIncorrectPlacement > 0) {
                message += " but wrongly placed";
            }
        }

        return message;
    }

    
     public static Integer[] customShuffle(Integer[] array, int differentIndices) {
        if (differentIndices >= array.length) {
            throw new IllegalArgumentException("Number of different indices should be less than array length.");
        }

        Integer[] shuffledArray = Arrays.copyOf(array, array.length);
        Random random = new Random();
        Set<Integer> changedIndices = new HashSet<>();

        for (int i = 0; i < differentIndices; i++) {
            int index1 = random.nextInt(array.length);
            int index2;
            do {
                index2 = random.nextInt(array.length);
            } while (index2 == index1);

            // Swap elements at index1 and index2
            int temp = shuffledArray[index1];
            shuffledArray[index1] = shuffledArray[index2];
            shuffledArray[index2] = temp;

            // Keep track of changed indices
            changedIndices.add(index1);
            changedIndices.add(index2);
        }

        System.out.println("Indices changed: " + changedIndices);
        return shuffledArray;
    }

    public static Integer[] allIndexsShuffle(Integer[] array) {
        Integer[] shuffledArray = Arrays.copyOf(array, array.length);
        Random random = new Random();

        for (int i = shuffledArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            // Swap elements at i and j
            int temp = shuffledArray[i];
            shuffledArray[i] = shuffledArray[j];
            shuffledArray[j] = temp;
        }

        return shuffledArray;
    }

    private int countOfCorrectDigitsInCorrectPlace(Integer[] combination, Integer[] solution) {
        // this method will count the amount of correct digits in the correct place.
        // the combination is the combination that is being checked.
        // the solution is the solution of the riddle.
        // the method will return an integer representing the amount of correct digits in the correct place.

        int count = 0;
        for (int i = 0; i < combination.length; i++) {
            if (combination[i] == solution[i]) {
                count++;
            }
        }

        return count;

    } 

    private int countOfCorrectDigitsInIncorrectPlace(Integer[] combination, Integer[] solution) {
        // this method will count the amount of correct digits in the incorrect place.
        // the combination is the combination that is being checked.
        // the solution is the solution of the riddle.
        // the method will return an integer representing the amount of correct digits in the incorrect place.

        int count = 0;
        for (int i = 0; i < combination.length; i++) {
            if (Arrays.asList(solution).contains(combination[i]) && combination[i] != solution[i]) {
                count++;
            }
        }

        return count;
    }
}
