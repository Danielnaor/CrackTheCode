/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.util.ArrayList;

/**
 *
 * @author carmitnaor
 */
public class DupeNumbers {

    int num;
    int countAppearencesCode; // how many times this number appears in the code
    int countAppearencesCombination; // how many times this number appears in the combination

    //boolean CorrectlyPlaced; // if the number is correctly placed in the code
    ArrayList<Boolean> CorrectlyPlaced;

    boolean isInEqualAppearences; // if the number appears in the code and in the combination the same number of times

    // constructor
    public DupeNumbers(int num, int count, int countAppearencesCombination, ArrayList<Boolean> CorrectlyPlaced, boolean isInEqualAppearences) {
        this.num = num;
        this.countAppearencesCode = count;
        this.countAppearencesCombination = countAppearencesCombination;
        this.CorrectlyPlaced = CorrectlyPlaced;
        this.isInEqualAppearences = isInEqualAppearences;
    }

    // eampty constructor
    public DupeNumbers() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCountAppearencesCode() {
        return countAppearencesCode;
    }

    public void setCountAppearencesCode(int count) {
        this.countAppearencesCode = count;
    }

    public boolean isCorrectlyPlaced(int index) {
        if (index >= CorrectlyPlaced.size()) {
            return false;
        }

        return CorrectlyPlaced.get(index);

    }

    public void setCorrectlyPlaced(int index, boolean CorrectlyPlaced) {
        if (index >= this.CorrectlyPlaced.size()) {
            return;
        }

        this.CorrectlyPlaced.set(index, CorrectlyPlaced);
    }

    public int getCountAppearencesCombination() {
        return countAppearencesCombination;
    }

    public void setCountAppearencesCombination(int countAppearencesCombination) {
        this.countAppearencesCombination = countAppearencesCombination;
    }

    public ArrayList<Boolean> getCorrectlyPlaced() {
        return CorrectlyPlaced;
    }

    public void setCorrectlyPlaced(ArrayList<Boolean> CorrectlyPlaced) {
        this.CorrectlyPlaced = CorrectlyPlaced;
    }

    public boolean isInEqualAppearences() {
        return isInEqualAppearences;
    }

    public void setIsInEqualAppearences(boolean isInEqualAppearences) {
        this.isInEqualAppearences = isInEqualAppearences;
    }

    @Override
    public String toString() {
        return "DupeNumbers{" + "num=" + num + ", countAppearencesCode=" + countAppearencesCode + ", countAppearencesCombination=" + countAppearencesCombination + ", CorrectlyPlaced=" + CorrectlyPlaced + '}';
    }

}
