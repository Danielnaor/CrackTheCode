/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.crackthecode;

/**
 *
 * @author carmitnaor
 */
public class CorrectnessCountsResult {

    private int countCorrectNumbers; // will store the number of correct numbers regardless of their placement 

    private int countCorrectNumbersAndCorrectPlacement; // will store the number of correct numbers which also have correct placement 

    private int countCorrectNumbersAndIncorrectPlacement; // will store the number of correct numbers which also have incorrect placement 

    // constructor
    public CorrectnessCountsResult(int countCorrectNumbers, int countCorrectNumbersAndCorrectPlacement, int countCorrectNumbersAndIncorrectPlacement) {
        this.countCorrectNumbers = countCorrectNumbers;
        this.countCorrectNumbersAndCorrectPlacement = countCorrectNumbersAndCorrectPlacement;
        this.countCorrectNumbersAndIncorrectPlacement = countCorrectNumbersAndIncorrectPlacement;
    }

    // getters and setters
    public int getCountCorrectNumbers() {
        return countCorrectNumbers;
    }

    public void setCountCorrectNumbers(int countCorrectNumbers) {
        this.countCorrectNumbers = countCorrectNumbers;
    }

    public int getCountCorrectNumbersAndCorrectPlacement() {
        return countCorrectNumbersAndCorrectPlacement;
    }

    public void setCountCorrectNumbersAndCorrectPlacement(int countCorrectNumbersAndCorrectPlacement) {
        this.countCorrectNumbersAndCorrectPlacement = countCorrectNumbersAndCorrectPlacement;
    }

    public int getCountCorrectNumbersAndIncorrectPlacement() {
        return countCorrectNumbersAndIncorrectPlacement;
    }

    public void setCountCorrectNumbersAndIncorrectPlacement(int countCorrectNumbersAndIncorrectPlacement) {
        this.countCorrectNumbersAndIncorrectPlacement = countCorrectNumbersAndIncorrectPlacement;
    }

    // to string
    @Override
    public String toString() {
        return "CorrectnessCountsResult{" + "countCorrectNumbers=" + countCorrectNumbers + ", countCorrectNumbersAndCorrectPlacement=" + countCorrectNumbersAndCorrectPlacement + ", countCorrectNumbersAndIncorrectPlacement=" + countCorrectNumbersAndIncorrectPlacement + '}';
    }

    public String SimplifiedToString() {
        return "CorrectnessCountsResult{" + countCorrectNumbers + ", " + countCorrectNumbersAndCorrectPlacement + ", " + countCorrectNumbersAndIncorrectPlacement + '}';

    }

}
