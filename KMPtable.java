import java.util.ArrayList;
import java.util.*;
public class KMPtable {
    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Usage: <string-to-find>");
            return;
        }
        int skips=0;
        int[][] skipMatrix;
        int arraySize=0;
        String phrase = args[0];
        String compare;
        String toCompare;
        //char[] charPhrase = phrase.toCharArray();
        char[] Chars= new char[phrase.length()];
        Chars = phrase.toCharArray();
        ArrayList<Character> uniqueChars = new ArrayList<Character>();

        //get the unique characters
        for(char ch : Chars){
            if(!uniqueChars.contains(ch)){
                uniqueChars.add(ch);
                arraySize++;
            }
        }   
        //sort the characters alphabetcally 
        Collections.sort(uniqueChars);
        uniqueChars.add('*');
        arraySize++;

        //Create the skip matrix
        skipMatrix = new int[uniqueChars.size()][Chars.length];

        // for each string character in the lsit 
        for(int i = 0;i<Chars.length;i++){
            String charExpected = String.valueOf(Chars[i]);
            for(int j=0;j<uniqueChars.size();j++){
                String currentFound=uniqueChars.get(j).toString();

                //if the String that we want is what we get 
                //no skips required potential full match
                if(charExpected.equals(currentFound)){
                    skipMatrix[j][i]=0;
                }
                else if(currentFound.equals("*")){
                    skipMatrix[j][i]= i+1;
                //if character dosnt match but is at the first position
                }else if(i==0){
                    skipMatrix[j][i]=i+1;
                }
                //calculate the required amount of skips
                else{
                    compare = phrase.substring(0, i);
                    compare= compare.concat(currentFound);
                    String shiftPhrase = phrase.substring(0,i);
                    shiftPhrase = shiftPhrase.concat(currentFound);

                    //Compare the subStr of what we saw to what we were could be looking for
                    //Shift until we match
                    while(true){
                        //adjust shift for when 
                        if(i == 1){
                            shiftPhrase = shiftPhrase.substring(1);
                            compare = compare.substring(0,i);
                        }else{
                            shiftPhrase = shiftPhrase.substring(1);
                            compare = compare.substring(0,compare.length()-1);
                        }
                        skips++;
                        //break condition when we have a potential match early or no match at all
                        if(shiftPhrase.equals(compare)||shiftPhrase.length() == 0){
                            skipMatrix[j][i]= skips;
                            
                            skips =0;
                            break;
                        }                       
                    } 
                }
            }
        }
        int i=0;
        System.out.print("  ");
        //print the phrase
        for(int j=0; j<Chars.length;j++){
            System.out.print( String.valueOf(Chars[j])+" ");
        }
        System.out.println("");
        for (int[] row : skipMatrix){
        // converting each row as string and then print in a separate line
        String uChar =uniqueChars.get(i).toString();
        i++;
        String str =Arrays.toString(row);

        //remove commas and brackets
        str = str.replace(",", "");  
        str = str.replace("[", "");  
        str = str.replace("]", ""); 
        //print to standard output
        System.out.println(uChar + " "+str);
        }


    }
    
}
