/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.example.crackthecode;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Danielnaor
 */
public class Clue implements Cloneable, Serializable {

    private Integer[] combination;
    private Hint hint;

    public Clue(Clue that) {
        this(that.getCombination(), new Hint(that.getHint()));
    }

    public Clue(Integer[] combination, Hint hint) {
        this.combination = combination;
        this.hint = hint;
    }

    public Integer[] getCombination() {
        return combination;
    }

    public void setCombination(Integer[] combination) {
        this.combination = combination;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {

        String output = "";
        for (Integer digit : combination) {
            output += digit + " ";
        }

        output += " " + hint.getMessage();

        return output;

    }

    @Override
    public Object clone() {
        Clue clue = null;
        try {
            clue = (Clue) super.clone();
        } catch (CloneNotSupportedException e) {
            clue = new Clue(
                    this.getCombination(), this.getHint());
        }
        clue.hint = (Hint) this.hint.clone();
        return clue;
    }

}
