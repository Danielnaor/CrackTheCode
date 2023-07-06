/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.example.crackthecode;

import java.util.*;

/**
 *
 * @author Danielnaor
 */


public class Main {
    public static void main(String[] args) {
        List<Clue> clues = new ArrayList<>();
         clues.add(new Clue(new Integer[]{3, 6, 8}, new Hint("One number is correct and correctly placed", 1, true)));
        clues.add(new Clue(new Integer[]{3, 8, 7}, new Hint("Nothing is correct", 0, false)));
        clues.add(new Clue(new Integer[]{2, 7, 6}, new Hint("One Number is correct but wrongly placed", 1, false)));
        clues.add(new Clue(new Integer[]{4, 7, 1}, new Hint("Two Numbers are correct but wrongly placed", 2, false)));
  
        // זה סוג של פותר את זה אבל זה נותן לי שתי קומבינציות שאחת מהן נכונה ואחת היא בדיוק כמו ברמז
/*
 זה לא מצליח לפתור את זה

        clues.add(new Clue(new Integer[]{9, 2, 8, 5}, new Hint("One number is correct but wrong placed", 1, false)));
        clues.add(new Clue(new Integer[]{1, 9, 3, 7}, new Hint("Two numbers are correct but wrong placed", 2, false)));
        clues.add(new Clue(new Integer[]{5, 2, 0, 1}, new Hint("One number is correct and well placed", 1, true)));
        clues.add(new Clue(new Integer[]{6, 5, 0, 7}, new Hint("Nothing is Correct", 0, false)));
        clues.add(new Clue(new Integer[]{8, 5, 2, 4}, new Hint("Two numbers are correct but wrong placed", 2, false)));
 */ 

        


      
        System.out.println(clues.size()); 
        CodeCracker codeCracker = new CodeCracker(clues);
        Integer[] code = codeCracker.crackCode();

        System.out.println("The code is: " + Arrays.toString(code));

        // make sure the code is correct by checking it with the clues and the codevalidator
        //CodeValidator codeValidator = new CodeValidator(clues, code);

        //System.out.println("The code is " + (codeValidator.validateCode(code) ? "correct" : "incorrect"));
    }
}
