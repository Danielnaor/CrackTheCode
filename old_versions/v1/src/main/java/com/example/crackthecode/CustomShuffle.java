/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

/**
 *
 * @author Danielnaor
 */
import java.util.*;

public class CustomShuffle {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int differentIndices = 1;

        int[] shuffledArray = customShuffle(array, differentIndices);
        System.out.println("Original array: " + Arrays.toString(array));
        System.out.println("Shuffled array: " + Arrays.toString(shuffledArray));
    }

    public static int[] customShuffle(int[] array, int differentIndices) {
        if (differentIndices >= array.length) {
            throw new IllegalArgumentException("Number of different indices should be less than array length.");
        }

        int[] shuffledArray = Arrays.copyOf(array, array.length);
        Random random = new Random();
        Set<Integer> changedIndices = new HashSet<>();

        for (int i = 0; i < differentIndices; i++) {
            int index1 = random.nextInt(array.length);
            int index2;
            do {
                index2 = random.nextInt(array.length);
            } while (index2 == index1);

            // Swap elements at index1 and index2
            int temp = shuffledArray[index1];
            shuffledArray[index1] = shuffledArray[index2];
            shuffledArray[index2] = temp;

            // Keep track of changed indices
            changedIndices.add(index1);
            changedIndices.add(index2);
        }

        System.out.println("Indices changed: " + changedIndices);
        return shuffledArray;
    }
}
