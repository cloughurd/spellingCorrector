package spell;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

public class SpellCorrector implements ISpellCorrector{
    Dictionary referenceDictionary;

	public void useDictionary(String dictionaryFileName) throws IOException{
        referenceDictionary = new Dictionary();
        Scanner sc;
        File inputFile;

        inputFile = new File(dictionaryFileName);
        sc = new Scanner(inputFile);

        while(sc.hasNext()){
            String next = sc.next();
            boolean isValid = true;
            char[] nextAsChar = next.toCharArray();
            for(char c : nextAsChar){
                if(c < 'A' || c > 'z' || (c > 'Z' && c < 'a')){
                    isValid = false;
                }
            }
            if(isValid){
                referenceDictionary.add(next);
            }
        }
    }


	public String suggestSimilarWord(String inputWord){
        if(inputWord.length() < 1){
            return null;
        }
        boolean isValid = true;
        char[] inputAsChar = inputWord.toCharArray();
        for(char c : inputAsChar){
            if(c < 'A' || c > 'z' || (c > 'Z' && c < 'a')){
                isValid = false;
            }
        }
        if(!isValid){
            return null;
        }
        inputWord = inputWord.toLowerCase();
        if(referenceDictionary.find(inputWord) != null){
            return inputWord;
        }
        ArrayList<String> distanceOf1 = new ArrayList<String>();
        distanceOf1.addAll(generateDeletionList(inputWord));
        distanceOf1.addAll(generateTranspositionList(inputWord));
        distanceOf1.addAll(generateAlterationList(inputWord));
        distanceOf1.addAll(generateInsertionList(inputWord));

        int numDistance1 = distanceOf1.size();
        int winningCount = 0;
        String winner = "";
        for(int i = 0; i < numDistance1; i++){
            Node result = referenceDictionary.find(distanceOf1.get(i));
            if(result != null){
                if(result.getValue() > winningCount){
                    winner = distanceOf1.get(i);
                    winningCount = result.getValue();
                }
            }
        }
        if(winningCount > 0){
            return winner;
        }

        else{
            ArrayList<String> distanceOf2 = new ArrayList<String>();
            for(int i = 0; i < numDistance1; i++){
                distanceOf2.addAll(generateDeletionList(distanceOf1.get(i)));
                distanceOf2.addAll(generateTranspositionList(distanceOf1.get(i)));
                distanceOf2.addAll(generateAlterationList(distanceOf1.get(i)));
                distanceOf2.addAll(generateInsertionList(distanceOf1.get(i)));
            }
            int numDistance2 = distanceOf2.size();
            for(int i = 0; i < numDistance2; i++){
                Node result = referenceDictionary.find(distanceOf2.get(i));
                if(result != null){
                    if(result.getValue() > winningCount){
                        winner = distanceOf2.get(i);
                        winningCount = result.getValue();
                    }
                }
            }
            if(winningCount > 0){
                return winner;
            }
        }
        return null;
    }

    private ArrayList<String> generateDeletionList(String word){
        ArrayList<String> deletionList = new ArrayList<String>();
        int wordLength = word.length();
        if(wordLength == 1){
            return deletionList;
        }
        deletionList.add(word.substring(1));
        for(int i = 1; i < wordLength -1; i++){
            deletionList.add(word.substring(0, i) + word.substring(i + 1));
        }
        deletionList.add(word.substring(0,wordLength - 1));
        return deletionList;
    }
    private ArrayList<String> generateTranspositionList(String word){
        ArrayList<String> transpositionList = new ArrayList<String>();
        int wordLength = word.length();
        if(wordLength == 1){
            return transpositionList;
        }
        if(wordLength == 2){
            transpositionList.add(word.substring(1) + word.substring(0,1));
            return transpositionList;
        }
        transpositionList.add(word.substring(1,2) + word.substring(0,1) + word.substring(2));
        for(int i = 1; i < wordLength - 2; i++){
            String start = word.substring(0,i);
            String swap = word.substring(i+1, i+2) + word.substring(i, i+1);
            String end = word.substring(i+2);
            transpositionList.add(start + swap + end);
        }
        transpositionList.add(word.substring(0,wordLength - 2) + word.substring(wordLength - 1) + word.substring(wordLength - 2, wordLength - 1));
        return transpositionList;
    }
    private ArrayList<String> generateAlterationList(String word){
        ArrayList<String> alterationList = new ArrayList<String>();
        char[] charArrayWord = word.toCharArray();
        int wordLength = word.length();

        for(int i = 0; i < wordLength; i++){
            char[] temp = word.toCharArray();
            for(int j = 0; j < 26; j++){
                if((char)(j+'a') == charArrayWord[i]);
                else{
                    temp[i] = (char)(j + 'a');
                    String modified = new String(temp);
                    alterationList.add(modified);
                }
            }
        }
        return alterationList;
    }
    private ArrayList<String> generateInsertionList(String word){
        ArrayList<String> insertionList = new ArrayList<String>();
        int wordLength = word.length();
        char[] charArrayWord = new char[wordLength+1];
        for(int i = 0; i < wordLength; i++){
            charArrayWord[i+1] = word.charAt(i);
        }
        for(int i = 0; i < wordLength + 1; i++){
            for(int j = 0; j < 26; j++){
                charArrayWord[i] = (char)(j + 'a');
                String modified = new String(charArrayWord);
                insertionList.add(modified);
            }
            if(i == wordLength);
            else{
                charArrayWord[i] = charArrayWord[i+1];
            }
        }
        return insertionList;
    }
}