//package project1;

import java.util.stream.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;

public class SentimentAnalysisApp {
    public static void main (String [] args){
        String posWords = args[0];
        String negWords = args[1];
        String posReviews = args[2];
        String negReviews = args[3];
        HashSet<String> positive = new HashSet<String>();
        HashSet<String> negative = new HashSet<String>();
        int correctPos = 0;
        int correctNeg = 0;
        int totalPos = 0;
        int totalNeg = 0;
        
        makeTables(positive, negative, posWords, negWords);
        
        File posFolder = new File(posReviews);
        File [] listPosReviews = posFolder.listFiles();
        String review = "";
        for (File file : listPosReviews){
            totalPos++;
            review = getReview(file.getAbsolutePath());
            review = review.replaceAll("\\p{Punct}", "");
            review = review.toLowerCase();
            String [] wordsInReview = review.split("\\s+");
            String classification = classifyReview(wordsInReview, positive, negative);
            
            if (classification.equals("positive")){
                correctPos++;
            }

            System.out.println("File Name: " + file.getName());
            System.out.println("Real Class: positive");
            System.out.println("Predicted Class: " + classification);
            System.out.println();
        }

        File negFolder = new File(negReviews);
        File [] listNegReviews = negFolder.listFiles();
        String review2 = "";
        for (File file : listNegReviews){
            totalNeg++;
            review2 = getReview(file.getAbsolutePath());
            review2 = review2.replaceAll("\\p{Punct}", "");
            review2 = review2.toLowerCase();
            String [] wordsInReview2 = review2.split("\\s+");
            String classification2 = classifyReview(wordsInReview2, positive, negative);
            
            if (classification2.equals("negative")){
                correctNeg++;
            }

            System.out.println("File Name: " + file.getName());
            System.out.println("Real Class: negative");
            System.out.println("Predicted Class: " + classification2);
            System.out.println();
        }

        printResults(correctPos, correctNeg, totalPos, totalNeg);
    }

    public static void makeTables(HashSet<String> positive, HashSet<String> negative, String posWords, String negWords){
        try {
            FileReader fr = new FileReader(posWords);
            Scanner fin = new Scanner(fr);
            String temp = "";
            while (fin.hasNext()){
                temp = fin.nextLine();
                if (!temp.contains(";")){
                    positive.add(temp);
                }
            }
            fin.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }

        try {
            FileReader file = new FileReader(negWords);
            Scanner fin = new Scanner(file);
            String temp = "";
            while (fin.hasNext()){
                temp = fin.nextLine();
                if (!temp.contains(";")){
                    negative.add(temp);
                }
            }
            fin.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static String getReview(String fileName){
        try {
            FileReader fr = new FileReader(fileName);
            Scanner fin = new Scanner(fr);
            String temp = fin.nextLine();
            fin.close();
            return temp;
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }

        return " ";
    }

    public static String classifyReview(String [] words, HashSet<String> positive, HashSet<String> negative){
        String theClass = "";
        int negWords = 0;
        int posWords = 0;

        for (int i = 0; i < words.length; i++){
            if (positive.contains(words[i])){
                posWords++;
            }
            else if (negative.contains(words[i])){
                negWords++;
            }
        }

        if (negWords >= posWords){
            theClass = "negative";
        }
        else{
            theClass = "positive";
        }
        
        return theClass;
    }

    public static void printResults(int correctPos, int correctNeg, int totalPos, int totalNeg){
        System.out.println("----------------------------------------------");
        System.out.println("- Correctly Classified Positive Reviews: " + correctPos + " -");
        System.out.println("- Correctly Classified Negative Reviews: " + correctNeg + " -");
        System.out.println("-                                            -");
        System.out.println("- Misclassified Positive Reviews: " + (totalPos - correctPos) + "        -");
        System.out.println("- Misclassified Negative Reviews: " + (totalNeg - correctNeg) + "        -");
        System.out.println("-                                            -");
        System.out.println("- Total Correctly Classified Reviews: " + (correctPos + correctNeg) + "    -");
        System.out.println("- Total Misclassified Reviews: " + ((totalPos - correctPos) + (totalNeg - correctNeg)) + "           -");
        System.out.println("----------------------------------------------");
        System.out.println("- Accuracy of Positive Reviews: " + (((correctPos * 1.0)/totalPos) * 100) + "%        -");
        double negAcc = ((correctNeg * 1.0)/totalNeg) * 100;
        System.out.print("- Accuracy of Negative Reviews: ");
        System.out.printf("%.1f",negAcc);
        System.out.println("%        -");
        System.out.println("- Accuracy of All Reviews: " + ((((correctNeg + correctPos) * 1.0)/(totalNeg + totalPos)) * 100) + "%             -");
        System.out.println("----------------------------------------------");
    }

}