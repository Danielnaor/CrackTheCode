/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.io.Serializable;

/**
 *
 * @author Danielnaor
 */
public class Hint implements Cloneable, Serializable {

    private String message;
    private int correctCount;
    private int countCorrectNumbersAndCorrectPlacement;
    private int countCorrectNumbersAndIncorrectPlacement;
    private boolean correctlyPlaced;

    public Hint(String message, int correctCount, boolean correctlyPlaced) {
        this.message = message;
        this.correctCount = correctCount;
        this.correctlyPlaced = correctlyPlaced;

        this.countCorrectNumbersAndCorrectPlacement = -1;
        this.countCorrectNumbersAndIncorrectPlacement = -1;

    }

    public Hint(String message, int correctCount, int countCorrectNumbersAndCorrectPlacement, int countCorrectNumbersAndIncorrectPlacement, boolean correctlyPlaced) {
        this.message = message;
        this.correctCount = correctCount;
        this.countCorrectNumbersAndCorrectPlacement = countCorrectNumbersAndCorrectPlacement;
        this.countCorrectNumbersAndIncorrectPlacement = countCorrectNumbersAndIncorrectPlacement;
        this.correctlyPlaced = correctlyPlaced;
    }

    public Hint(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public boolean isCorrectlyPlaced() {
        return correctlyPlaced;
    }

    public void setCorrectlyPlaced(boolean correctlyPlaced) {
        this.correctlyPlaced = correctlyPlaced;
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

    @Override
    public String toString() {
        if (countCorrectNumbersAndCorrectPlacement == -1 && countCorrectNumbersAndIncorrectPlacement == -1) {
            return "Hint{" + "message=" + message + ", correctCount=" + correctCount + ", correctlyPlaced=" + correctlyPlaced + '}';
        }
        return "Hint{" + "message=" + message + ", correctCount=" + correctCount + ", countCorrectNumbersAndCorrectPlacement=" + countCorrectNumbersAndCorrectPlacement + ", countCorrectNumbersAndIncorrectPlacement=" + countCorrectNumbersAndIncorrectPlacement + ", correctlyPlaced=" + correctlyPlaced + '}';
    }

    @Override
    public Object clone() {
        try {
            return (Hint) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Hint(this.message, this.getCorrectCount(), this.getCountCorrectNumbersAndCorrectPlacement(), this.getCountCorrectNumbersAndIncorrectPlacement(), this.isCorrectlyPlaced());
        }
    }

    public Hint(Hint that) {
        this(that.message, that.correctCount, that.countCorrectNumbersAndCorrectPlacement, that.countCorrectNumbersAndIncorrectPlacement, that.correctlyPlaced);
    }

}
