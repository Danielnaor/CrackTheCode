/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

/**
 *
 * @author Danielnaor
 */
public class Hint {

    private String message;
    private int correctCount;
    private boolean correctlyPlaced;

    public Hint(String message, int correctCount, boolean correctlyPlaced) {
        this.message = message;
        this.correctCount = correctCount;
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

    @Override
    public String toString() {
        return "Hint{" + "message=" + message + ", correctCount=" + correctCount + ", correctlyPlaced=" + correctlyPlaced + '}';
    }

}
