/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;


import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Danielnaor
 */

import java.util.List;

public class CodeValidator {
    private List<Clue> clues;
    private Integer[] code;
    ArrayList<Integer[]> allPosibleArrayList;

    public CodeValidator(List<Clue> clues, Integer[] code,ArrayList<Integer[]> allPosibleArrayList) {
        this.clues = clues;
        this.code = code;
        this.allPosibleArrayList = allPosibleArrayList;


    }

   

      public boolean validateCode(Integer[] code) {


    for (Clue clue : clues) {
        int correctDigits = 0;
        Integer[] combination = clue.getCombination();

        String codeAsString = Arrays.toString(code);

        if(codeAsString.contains(Arrays.toString(combination))) {
            correctDigits++;
        }
        


        // 


        
    }

    return true;
}



    }