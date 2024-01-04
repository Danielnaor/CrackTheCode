/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

/**
 *
 * @author carmitnaor
 */
public class DigitAndIndex {

    private int digit;
    private int index;

    public DigitAndIndex(int digit, int index) {
        this.digit = digit;
        this.index = index;
    }

    public int getDigit() {
        return digit;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "DigitAndIndex{" + "digit=" + digit + ", index=" + index + '}';
    }

}
