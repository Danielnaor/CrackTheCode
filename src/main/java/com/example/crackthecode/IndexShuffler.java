/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crackthecode;

/**
 *
 * @author Danielnaor
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IndexShuffler {

    List<int[]> shuffledArrays;

    /*    public static void main(String[] args) {
            IndexShuffler indexShuffler = new IndexShuffler(3);
        }
    //*/

    public IndexShuffler(int codeLen) {
        int[] originalArray = new int[codeLen];
        for (int i = 0; i < codeLen; i++) {
            originalArray[i] = i;
        }

        shuffledArrays = generateShuffledArrays(originalArray);

        for (int i = 0; i < shuffledArrays.size(); i++) {
            System.out.println("Shuffled array #" + (i + 1) + ": " + Arrays.toString(shuffledArrays.get(i)));
        }
    }

    public static List<int[]> generateShuffledArrays(int[] originalArray) {
        List<int[]> shuffledArrays = new ArrayList<>();
        int n = originalArray.length;
        int[] tempArray = Arrays.copyOf(originalArray, n);

        do {
            shuffleArray(tempArray);
            if (!containsSameValueInSameIndex(originalArray, tempArray)) {
                shuffledArrays.add(Arrays.copyOf(tempArray, n));
            }
        } while (nextPermutation(tempArray));

        return shuffledArrays;
    }

    public static void shuffleArray(int[] array) {
        Collections.shuffle(Arrays.asList(array));
    }

    public static boolean containsSameValueInSameIndex(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] == arr2[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean nextPermutation(int[] array) {
        int i = array.length - 2;
        while (i >= 0 && array[i] >= array[i + 1]) {
            i--;
        }

        if (i < 0) {
            return false; // All permutations are generated
        }

        int j = array.length - 1;
        while (array[i] >= array[j]) {
            j--;
        }

        swap(array, i, j);
        reverse(array, i + 1);
        return true;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void reverse(int[] array, int start) {
        int i = start;
        int j = array.length - 1;
        while (i < j) {
            swap(array, i, j);
            i++;
            j--;
        }
    }

    public void setShuffledArrays(List<int[]> shuffledArrays) {
        this.shuffledArrays = shuffledArrays;
    }

    public List<int[]> getShuffledArrays() {
        return shuffledArrays;
    }

}
