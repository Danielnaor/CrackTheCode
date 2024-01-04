/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Danielnaor
 */
public class Riddle {

    Integer[] solution; // thos will be the solution to the riddle, solution is neccesery. 
    Integer codeLength; // this will represent the amounts of digits the code have. if not privided we will use 3 as defult.
    Integer maxHints; // this is the max amount of hint, unless told other wise we will set it to two times the lenght of the code. 
    Integer base; //  An integer representing the base of the riddle's digits, meaning what is the max digit (exlusive), defult is 10.

    ArrayList<Clue> clues; // the clues that solves the riddle.
    boolean solvable = false;
    Integer numPossibilities;

    public Riddle() {

        codeLength = 3;
        maxHints = 2 * codeLength;
        base = 10;

    }

    public Riddle(Integer[] solution, Integer codeLength, Integer maxHints, Integer base) {
        this.solution = solution;
        this.codeLength = codeLength;
        this.maxHints = maxHints;
        this.base = base;

        if (codeLength == null) {
            codeLength = solution.length;
        }

        if (maxHints == null) {
            maxHints = 2 * codeLength;

        }

        if (base == null) {
            base = 10;
        }

    }

    public Integer[] getSolution() {
        return solution;
    }

    public void setSolution(Integer[] solution) {
        this.solution = solution;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public Integer getMaxHints() {
        return maxHints;
    }

    public void setMaxHints(Integer maxHints) {
        this.maxHints = maxHints;
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public ArrayList<Clue> getClues() {
        return clues;
    }

    public void setClues(ArrayList<Clue> clues) {
        this.clues = clues;
    }

    public Integer getNumPossibilities() {
        return numPossibilities;
    }

    public void setNumPossibilities(Integer numPossibilities) {
        this.numPossibilities = numPossibilities;
    }

    @Override
    public String toString() {
        return "Riddle{" + "solution=" + Arrays.toString(solution) + ", codeLength=" + codeLength + ", maxHints=" + maxHints + ", base=" + base + ", clues=" + clues + ", solvable=" + solvable + ", numPossibilities=" + numPossibilities + '}';
    }

    public boolean isSolvable() {
        return solvable;
    }

    public void setSolvable(boolean solvable) {
        this.solvable = solvable;
    }

}
