//package project1;

import java.util.stream.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;

/**
 * Classifies a set of movie reviews as positive or negative based on their 
 * sentiment, and shows the accuracy of the classifications.
 * 
 * @author Taylor Keesee
 */

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
        for (File file : listPosReviews){
            totalPos++;
            MovieReview review = new MovieReview(SentimentAnalysisApp.getReview(file.getAbsolutePath()));
            review.removePunct();
            review.makeLowerCase();
            review.setClassification(classifyReview(review.getWords(), positive, negative));
            //review = getReview(file.getAbsolutePath());
            //review = review.replaceAll("\\p{Punct}", "");
            //review = review.toLowerCase();
            //String [] wordsInReview = review.split("\\s+");
            //String classification = classifyReview(wordsInReview, positive, negative);
                
            if (review.getClassification().equals("positive")){
                correctPos++;
            }
    
            System.out.println("File Name: " + file.getName());
            System.out.println("Real Class: positive");
            System.out.println("Predicted Class: " + review.getClassification());
            System.out.println();
        }

        File negFolder = new File(negReviews);
        File [] listNegReviews = negFolder.listFiles();
        for (File file : listNegReviews){
            totalNeg++;
            MovieReview review2 = new MovieReview(SentimentAnalysisApp.getReview(file.getAbsolutePath()));
            review2.removePunct();
            review2.makeLowerCase();
            //System.out.println(review2.getReview());
            review2.setClassification(classifyReview(review2.getWords(), positive, negative));
            //totalNeg++;
            //review2 = getReview(file.getAbsolutePath());
            //review2 = review2.replaceAll("\\p{Punct}", "");
            //review2 = review2.toLowerCase();
            //String [] wordsInReview2 = review2.split("\\s+");
            //String classification2 = classifyReview(wordsInReview2, positive, negative);
                
            if (review2.getClassification().equals("negative")){
                correctNeg++;
            }
    
            System.out.println("File Name: " + file.getName());
            System.out.println("Real Class: negative");
            System.out.println("Predicted Class: " + review2.getClassification());
            System.out.println();
        }

        printResults(correctPos, correctNeg, totalPos, totalNeg);
    }

    /**
     * Creates the lookup tables (2) with the positive and negative words in each
     * 
     * @param positive
     * @param negative
     * @param posWords
     * @param negWords
     */
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

    /**
     * Gets the review from a file
     * 
     * @param fileName
     * @return the review in a string
     */
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

    /**
     * Classifies the review as either positive or negative
     * 
     * @param words
     * @param positive
     * @param negative
     * @return the classification of the review (positive or negative)
     */
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

    /**
     * Prints the results of the classifications made
     * 
     * @param correctPos
     * @param correctNeg
     * @param totalPos
     * @param totalNeg
     */
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
