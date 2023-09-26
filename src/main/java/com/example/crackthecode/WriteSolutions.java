/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author carmitnaor
 */
public class WriteSolutions {

    // instance variables
    private HashMap<Integer, Integer[]> solutions;
    int testCaseNumber;

    // constructor
    public WriteSolutions() {
        solutions = new HashMap<>();
        testCaseNumber = 1;
    }

    // add solution
    public void addSolution(Integer[] solution) {
        solutions.put(testCaseNumber, solution);
        testCaseNumber++;
    }

    // getters and setters
    public HashMap<Integer, Integer[]> getSolutions() {
        return solutions;
    }

    public void setSolutions(HashMap<Integer, Integer[]> solutions) {
        this.solutions = solutions;
    }

    public int getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(int testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }

    // to string
    @Override
    public String toString() {
        return "WriteSolutions{" + "solutions=" + solutions + ", testCaseNumber=" + testCaseNumber + '}';
    }

    // write solutions to file
    public void writeSolutionsToFile() {
        try {
            File file = new File("Solutions.txt");

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            // write solutions to file (one line per solution) and only write the digits
            for (int i = 1; i <= solutions.size(); i++) {
                Integer[] solution = solutions.get(i);
                writer.write(Arrays.toString(solution));

                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
