import java.io.*;
import java.util.*;
public class KMPsearch {
    public static void main(String[]args){

        int lineCount=0;
        int lineIndex = 1;
        String line;

        //check for correct number of arguments 
        if(args.length != 2){
            System.err.println("Usage <KMP-skip-array-file> <File-to-read>");
            return;
        }

        try {
            //assignen variables
            String skipArrayFilename = args[0];
            String filename = args[1];
            //Skip array file reader 
            FileReader skipArrayFileReader = new FileReader(skipArrayFilename);
            BufferedReader arrayReader =new BufferedReader(skipArrayFileReader);

            //target file reader
            FileReader fileReader = new FileReader(filename);
            BufferedReader reader =new BufferedReader(fileReader);

            //read the skip array phrase
            String kmpPhrase = arrayReader.readLine();
            kmpPhrase = kmpPhrase.replace(" ","");
            //get each character 
            char[] kmpChars = kmpPhrase.toCharArray();
            String skipLine = arrayReader.readLine();
            //add each unique character to the lsit 
            ArrayList<Character> uniqueChars = new ArrayList<Character>();
            for(char ch : kmpChars){
                if(!uniqueChars.contains(ch)){
                    uniqueChars.add(ch);
                }
            } 
            //create an array to store each skip array
            String[] skipArray = new String[uniqueChars.size()+1];
            //add to the skip array
            int i=0;
            while(skipLine!= null){
                skipLine = skipLine.replace(" ","");
                skipArray[i]= skipLine;
                skipLine = arrayReader.readLine();
                i++;
            }

            ArrayList<String> phraseArray = new ArrayList<String>();
            int phraseLength = kmpPhrase.length();
            //char[] phraseArray = kmpPhrase.toCharArray();
            for(int j=0; j< kmpPhrase.length();j++) {
                phraseArray.add(String.valueOf(kmpPhrase.charAt(j)));
            }

            //count the successful matches
            int successfulMatches =0;
            String currentChar;
            //get the next exspectd string 
            line = reader.readLine();
            
            //search the file
            while(line != null){
                //reset line index and increment line count 
                lineIndex=0;             
                lineCount++;
                int pPosition = 0;

                //process the current line
                while((lineIndex < line.length())){
                    //get the next character 
                    currentChar = String.valueOf(line.charAt(lineIndex));
                    //check if we got the target string at the current expected phrase position
                    if(currentChar.equals(String.valueOf(phraseArray.get(pPosition)))){
                        //if matched move increment pPosition and number of successful matches
                        successfulMatches++;
                        pPosition++;
                        lineIndex++;
                        if(successfulMatches == phraseLength){
                            break;
                        } 
 
                    }else{
                        //if we dont get a matching character 
                        //calculate how far we need to skip
                        int Skips = getSkip(phraseArray,skipArray,currentChar, pPosition);
                        //reset our current pointer 
                        lineIndex = lineIndex -pPosition;
                        pPosition =0;
                        successfulMatches=0;
                        //ship required amount of characters in the string 
                        lineIndex = lineIndex+ Skips;

                    }
                }
                //if a match is found 
                if(successfulMatches == phraseLength){
                    // set the index to the start of the string 
                    lineIndex = lineIndex-phraseLength;
                    //output 
                    System.out.println("line:   "+lineCount+" Index:    "+(lineIndex+1));
                }
                   
                successfulMatches=0;
                line = reader.readLine();

            }
            //if the phrase is not in the text
            if(line == null){
                System.out.print("'"+kmpPhrase+ "'  was not found in the text");
            }
            //close out readers 
            reader.close();
            arrayReader.close();
            
        } catch (Exception e) {
            System.err.println(e);
        }


    }
    /**
     * 
     * @param phraseArray the array that contains the phrase
     * @param skipArray the array that contains the skip arrays for each symbol
     * @param sFound    the striing that we found 
     * @param pPosition the position we are at in the phrase
     * @return the required skips
     */
    public static int getSkip(ArrayList<String> phraseArray,String[] skipArray,String sFound,int pPosition){
        int skip=0;
    
        if(!phraseArray.contains(sFound)){
            String sFoundTable = skipArray[skipArray.length-1];
            skip = Integer.parseInt(String.valueOf(sFoundTable.charAt(pPosition+1)));
            return skip;
        }
        //find the line in the skip matrix that matches the character we found
        for (String sFoundTable : skipArray){
            //check if we have found the correct array
            if(sFound.equals(String.valueOf(sFoundTable.charAt(0)))){
                //get the amount of skips needed
                skip = Integer.parseInt(String.valueOf(sFoundTable.charAt(pPosition+1)));
            }
        }


        return skip;
    }
}
