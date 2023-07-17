/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.example.crackthecode;

import java.io.Serializable;
import static java.lang.System.out;
import java.lang.reflect.Array;
import java.util.*;
import static java.util.Collections.list;
import static java.util.Collections.shuffle;

/**
 *
 * @author Danielnaor
 */
public class Main implements Cloneable, Serializable {

    public static void main(String[] args) {

        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Clue> deepCopyOfClues = new ArrayList<>();

        clues.add(new Clue(new Integer[]{9, 2, 8, 5}, new Hint("One number is correct but wrong placed", 1, 0, 1, false)));
        clues.add(new Clue(new Integer[]{1, 9, 3, 7}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));
        clues.add(new Clue(new Integer[]{5, 2, 0, 1}, new Hint("One number is correct and well placed", 1, 1, 0, true)));
        clues.add(new Clue(new Integer[]{6, 5, 0, 7}, new Hint("Nothing is correct", 0, 0, 0, false)));
        clues.add(new Clue(new Integer[]{8, 5, 2, 4}, new Hint("Two numbers are correct but wrong placed", 2, 0, 2, false)));

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
            
            if(codeIsCorrect){
                System.out.println("The code is: " + Arrays.toString(code));

            }
        }

    }

}
/*clues.add(new Clue(new Integer[]{3, 6, 8}, new Hint("One number is correct and correctly placed", 1, true)));
        clues.add(new Clue(new Integer[]{3, 8, 7}, new Hint("Nothing is correct", 0, false)));
       clues.add(new Clue(new Integer[]{2, 7, 6}, new Hint("One Number is correct but wrongly placed", 1, false)));
       clues.add(new Clue(new Integer[]{4, 7, 1}, new Hint("Two Numbers are correct but wrongly placed", 2, false)));
 */

// 9, 2, 8, 5 one number is correct but wrong placed
// 1, 9, 3, 7 two numbers are correct but wrong placed
// 5, 2, 0, 1 one number is correct and well placed
// 6, 5, 0, 7 nothing is correct
// 8, 5, 2, 4 two numbers are correct but wrong placed
// solution: 3, 8 , 4 , 1
