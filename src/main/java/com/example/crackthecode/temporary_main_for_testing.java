package com.example.crackthecode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author carmitnaor
 */
public class temporary_main_for_testing {

    static File FolderFullRiddle;
    static File FolderClues;
    static File FolderSolution;

    public static void main(String[] args) throws IOException {

        // we will test the riddle generator's custom random number generator
        ///*
        RiddleGenerator riddleGenerator = new RiddleGenerator(false);

        Riddle riddle = riddleGenerator.riddle;
        boolean copyTo = true;

        //Riddle fullRiddle = riddleGenerator.GenerateFullRiddle(riddle);
        
        Riddle fullRiddle = new Riddle();
 // Integer[] solution; // thos will be the solution to the riddle, solution is neccesery. 
    Integer base; //  An integer representing the base of the riddle's digits, meaning what is the max digit (exlusive), defult is 10.

    ArrayList<Clue> clues; // the clues that solves the riddle.
    boolean solvable = false;
    

        //clues.add();
        
        
        
        
        // if fullRiddle is not null check if it is solvanle if so print the clues and then wait for user input
        // 
        if (fullRiddle != null) {

            // create folders for the full riddle (will be used for debugging), a folder for the clues and a folder for the solution
            // we will write the full riddle to one file
            // we will write the clues to one file 
            // and the soluation in another file 
            FolderFullRiddle = new File("FullRiddle");
            if (!FolderFullRiddle.exists()) {
                FolderFullRiddle.mkdir();
            }

            FolderClues = new File("Clues");
            if (!FolderClues.exists()) {
                FolderClues.mkdir();
            }

            FolderSolution = new File("Solution");
            if (!FolderSolution.exists()) {
                FolderSolution.mkdir();
            }

            // read all the files in the folder full riddle
            File[] filesFullRiddleFolder = FolderFullRiddle.listFiles();
            // if the folder is empty the length will be 0
            if (filesFullRiddleFolder.length == 0) {
                // create a new file with the name fullRiddle.txt
                File fullRiddleFile = new File("FullRiddle" + File.separator + "fullRiddle.txt");
                fullRiddleFile.createNewFile();
                // write the full riddle to the file
                FileWriter fullRiddleFileWriter = new FileWriter(fullRiddleFile);
                fullRiddleFileWriter.write(fullRiddle.toString());
                fullRiddleFileWriter.close();

            } // else if there is one file in the filesFullRiddleFolder
            else if (filesFullRiddleFolder.length == 1) {
                // create a new file with the name fullRiddle1.txt
                File fullRiddleFile = new File("FullRiddle" + File.separator + "fullRiddle1.txt");
                fullRiddleFile.createNewFile();
                // write the full riddle to the file
                FileWriter fullRiddleFileWriter = new FileWriter(fullRiddleFile);
                fullRiddleFileWriter.write(fullRiddle.toString());
                fullRiddleFileWriter.close();
            } else {
                // all the files in the folder full riddle will be named fullRiddle1.txt, fullRiddle2.txt, fullRiddle3.txt, etc. so we will find the highest number and add 1 to it
                int highestNumber = 1;
                for (File file : filesFullRiddleFolder) {
                    String fileName = file.getName();

                    if (fileName.equals("fullRiddle.txt")) {
                        continue;
                    }

                    String[] fileNameSplit = fileName.split("fullRiddle");
                    String[] fileNameSplit2 = fileNameSplit[1].split(".txt");

                    int number = Integer.parseInt(fileNameSplit2[0]);

                    if (number > highestNumber) {
                        highestNumber = number;
                    }

                }

                // create a new file with the highest number + 1
                File fullRiddleFile = new File("FullRiddle" + File.separator + "fullRiddle" + (highestNumber + 1) + ".txt");

                fullRiddleFile.createNewFile();
                // write the full riddle to the file
                FileWriter fullRiddleFileWriter = new FileWriter(fullRiddleFile);

                fullRiddleFileWriter.write(fullRiddle.toString());
                fullRiddleFileWriter.close();

            }

            // read all the files in the folder clues
            File[] filesCluesFolder = FolderClues.listFiles();
            // if the folder is empty the length will be 0
            if (filesCluesFolder.length == 0) {
                // create a new file with the name clues.txt
                File cluesFile = new File("Clues" + File.separator + "clues.txt");
                cluesFile.createNewFile();
                // write the clues to the file
                FileWriter cluesFileWriter = new FileWriter(cluesFile);
                for (Clue clue : fullRiddle.getClues()) {
                    cluesFileWriter.write(clue.toString() + "\n");
                }
                cluesFileWriter.close();
            } // else if there is one file in the filesCluesFolder
            else if (filesCluesFolder.length == 1) {
                // create a new file with the name clues1.txt
                File cluesFile = new File("Clues" + File.separator + "clues1.txt");
                cluesFile.createNewFile();
                // write the clues to the file
                FileWriter cluesFileWriter = new FileWriter(cluesFile);
                for (Clue clue : fullRiddle.getClues()) {
                    cluesFileWriter.write(clue.toString() + "\n");
                }
                cluesFileWriter.close();
            } else {
                // all the files in the folder clues will be named clues1.txt, clues2.txt, clues3.txt, etc. so we will find the highest number and add 1 to it
                int highestNumber = 1;
                for (File file : filesCluesFolder) {
                    String fileName = file.getName();

                    if (fileName.equals("clues.txt")) {
                        continue;
                    }
                    String[] fileNameSplit = fileName.split("clues");
                    String[] fileNameSplit2 = fileNameSplit[1].split(".txt");
                    int number = Integer.parseInt(fileNameSplit2[0]);
                    if (number > highestNumber) {
                        highestNumber = number;
                    }
                }

                // create a new file with the highest number + 1
                File cluesFile = new File("Clues" + File.separator + "clues" + (highestNumber + 1) + ".txt");
                cluesFile.createNewFile();
                // write the clues to the file
                FileWriter cluesFileWriter = new FileWriter(cluesFile);
                for (Clue clue : fullRiddle.getClues()) {
                    cluesFileWriter.write(clue.toString() + "\n");
                }
                cluesFileWriter.close();
            }

            // read all the files in the folder solution
            File[] filesSolutionFolder = FolderSolution.listFiles();
            // if the folder is empty the length will be 0
            if (filesSolutionFolder.length == 0) {
                // create a new file with the name solution.txt
                File solutionFile = new File("Solution" + File.separator + "solution.txt");
                solutionFile.createNewFile();
                // write the solution to the file
                FileWriter solutionFileWriter = new FileWriter(solutionFile);
                solutionFileWriter.write(Arrays.toString(fullRiddle.getSolution()));
                solutionFileWriter.close();
            } // else if there is one file in the filesSolutionFolder
            else if (filesSolutionFolder.length == 1) {
                // create a new file with the name solution1.txt
                File solutionFile = new File("Solution" + File.separator + "solution1.txt");
                solutionFile.createNewFile();
                // write the solution to the file
                FileWriter solutionFileWriter = new FileWriter(solutionFile);
                solutionFileWriter.write(Arrays.toString(fullRiddle.getSolution()));
                solutionFileWriter.close();
            } else {
                // all the files in the folder solution will be named solution1.txt, solution2.txt, solution3.txt, etc. so we will find the highest number and add 1 to it
                int highestNumber = 1;
                for (File file : filesSolutionFolder) {
                    String fileName = file.getName();
                    if (fileName.equals("solution.txt")) {
                        continue;
                    }
                    String[] fileNameSplit = fileName.split("solution");
                    String[] fileNameSplit2 = fileNameSplit[1].split(".txt");
                    int number = Integer.parseInt(fileNameSplit2[0]);
                    if (number > highestNumber) {
                        highestNumber = number;
                    }
                }

                // create a new file with the highest number + 1
                File solutionFile = new File("Solution" + File.separator + "solution" + (highestNumber + 1) + ".txt");
                solutionFile.createNewFile();
                // write the solution to the file
                FileWriter solutionFileWriter = new FileWriter(solutionFile);
                solutionFileWriter.write(Arrays.toString(fullRiddle.getSolution()));
                solutionFileWriter.close();

            }

            // check if the riddle is solvable
            boolean isSolvable = fullRiddle.isSolvable();
            if (isSolvable) {

                // if file CopyTo.txt exist
                //  crackthecode_mobileapp
                if (copyTo) {
                    String userHome = System.getProperty("user.home");

                    // Append your file or directory name to the user's home directory
                    String filePath = userHome + File.separator + "NetBeansProjects/crackthecode_mobileapp/crackthecodeRiddles";

                    // copy the folders to the path
                    File FolderFullRiddleCopy = new File(filePath + File.separator + "FullRiddle");
                    if (!FolderFullRiddleCopy.exists()) {
                        FolderFullRiddleCopy.mkdir();
                    }
                    File FolderCluesCopy = new File(filePath + File.separator + "Clues");
                    if (!FolderCluesCopy.exists()) {
                        FolderCluesCopy.mkdir();
                    }
                    File FolderSolutionCopy = new File(filePath + File.separator + "Solution");
                    if (!FolderSolutionCopy.exists()) {
                        FolderSolutionCopy.mkdir();
                    }

                    // delete all the files in the folders
                    File[] filesFullRiddleFolderCopy = FolderFullRiddleCopy.listFiles();
                    if(filesFullRiddleFolderCopy != null && filesFullRiddleFolderCopy.length > 0){
                    for (File file : filesFullRiddleFolderCopy) {
                        file.delete();
                    }
                    }
                    File[] filesCluesFolderCopy = FolderCluesCopy.listFiles();
                                        if(filesCluesFolderCopy != null && filesCluesFolderCopy.length > 0){

                    for (File file : filesCluesFolderCopy) {
                        file.delete();
                    }
                                        }
                    File[] filesSolutionFolderCopy = FolderSolutionCopy.listFiles();
                    
                                                            if(filesSolutionFolderCopy != null && filesSolutionFolderCopy.length > 0){

                    for (File file : filesSolutionFolderCopy) {
                        file.delete();
                    }
                                                            }

                    // copy the latest files to the folders
                    File[] FullRiddleFolderFiles = FolderFullRiddle.listFiles();
                    File[] CluesFolderFiles = FolderClues.listFiles();
                    File[] SolutionFolderFiles = FolderSolution.listFiles();

                    // copy the latest files to the folders
                    File latestFullRiddle = FullRiddleFolderFiles[FullRiddleFolderFiles.length - 1];
                    File latestClues = CluesFolderFiles[CluesFolderFiles.length - 1];
                    File latestSolution = SolutionFolderFiles[SolutionFolderFiles.length - 1];

                    // copy the files to the folders
                    latestFullRiddle.renameTo(new File(filePath + File.separator + "FullRiddle" + File.separator + "FullRiddle.txt"));
                    latestClues.renameTo(new File(filePath + File.separator + "Clues" + File.separator + "Clues.txt"));
                    latestSolution.renameTo(new File(filePath + File.separator + "Solution" + File.separator + "Solution.txt"));

                }

                System.out.println("\033\143");

                try {
                    final String os = System.getProperty("os.name");
                    if (os.contains("Windows")) {
                        Runtime.getRuntime().exec("cls");
                    } else {
                        Runtime.getRuntime().exec("clear");
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            

            System.out.println("The riddle is solvable");
            System.out.println("The clues are: ");
            for (Clue clue : fullRiddle.getClues()) {
                System.out.println(clue);
            }

            // wait for user input
            Scanner scanner = new Scanner(System.in);
            // let the user know that we are waiting for input
            System.out.println("Please enter any input to see the solution");
            String input = scanner.nextLine();
            System.out.println("The code is: " + Arrays.toString(fullRiddle.getSolution()));
        } else {
            System.out.println("The riddle is not solvable");
        }
    }

    
        else {
            System.out.println("The riddle is null");
        // print all clues and the code 
        System.out.println("The clues are: ");
        for (Clue clue : riddle.getClues()) {
            System.out.println(clue);
        }

        System.out.println("The code is: " + Arrays.toString(riddle.getSolution()));

    }

}

}
