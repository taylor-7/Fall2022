import java.util.regex.*;

/**
 * A class to create MovieReview objects
 * 
 * @author Taylor Keesee
 */
public class MovieReview {
    private String review;
    private String classification;
    private String [] wordsInReview;

    /**
     * Creates a blank MovieReview
     */
    public MovieReview(){
        review = "";
        classification = "";
    }

    /**
     * Creates a MovieReview with a string review and uses that review to create an array
     * with the words in the review
     * 
     * @param rev
     */
    public MovieReview(String rev){
        review = rev;
        wordsInReview = review.split("\\s+");
        classification = "";
    }

    /**
     * Creates a MovieReview with a string review and a classification,
     * and uses that review to create an array with the words in the review
     * 
     * @param rev
     * @param cla
     */
    public MovieReview(String rev, String cla){
        review = rev;
        wordsInReview = review.split("\\s+");
        classification = cla;
    }

    /**
     * Gets the review from a MovieReview object
     * 
     * @return a string with the review
     */
    public String getReview(){
        return review;
    }

    /**
     * Gets the classification from a MovieReview object
     * 
     * @return a string with the classification (pos or neg)
     */
    public String getClassification(){
        return classification;
    }

    /**
     * Gets the array that contains the words from the review
     * 
     * @return a string array with words from the review
     */
    public String [] getWords(){
        return wordsInReview;
    }

    /**
     * Sets the review string
     * 
     * @param rev
     */
    public void setReview(String rev){
        review = rev;
    }

    /**
     * Sets the classifcation string
     * 
     * @param cla
     */
    public void setClassification(String cla){
        classification = cla;
    }

    /**
     * Sets the word array
     * 
     * @param words
     */
    public void setWords(String [] words){
        wordsInReview = words;
    }

    /**
     * Removes all of the punctuation in the review
     */
    public void removePunct(){
        review = review.replaceAll("\\p{Punct}", "");
    }

    /**
     * Makes all of the letters in the review lowercase
     */
    public void makeLowerCase(){
        review = review.toLowerCase();
    }
}
