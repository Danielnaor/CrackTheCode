/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.example.crackthecode;

/**
 *
 * @author Danielnaor
 */


// Clue.java
public class Clue {
    private Integer[] combination;
    private Hint hint;

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clue{combination=[");
        for (int i = 0; i < combination.length; i++) {
            sb.append(combination[i]);
            if (i < combination.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("], hint=").append(hint).append('}');
        return sb.toString();
    }

    
    
    

    
}
