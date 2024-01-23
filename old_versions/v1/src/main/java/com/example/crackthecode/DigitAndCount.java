/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.crackthecode;

/**
 *
 * @author carmitnaor
 */
public class DigitAndCount {

    Integer digit;
    Integer count;

    public DigitAndCount(Integer digit, Integer count) {
        this.digit = digit;
        this.count = count;
    }

    public Integer getDigit() {
        return digit;
    }

    public Integer getCount() {
        return count;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return digit + " " + count;
    }
    
}
