//package project1;

import java.util.stream.*;
import java.util.regex.*;
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

}