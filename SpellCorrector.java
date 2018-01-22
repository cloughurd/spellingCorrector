package spell;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

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
        if(referenceDictionary.find(inputWord) != null){
            return inputWord;
        }
        ArrayList<String> distanceOf1 = new ArrayList<String>();
        distanceOf1.addAll(generateDeletionList(inputWord));
        distanceOf1.addAll(generateTranspositionList(inputWord));
        distanceOf1.addAll(generateAlterationList(inputWord));
        distanceOf1.addAll(generateInsertionList(inputWord));
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
        
    }
    private ArrayList<String> generateInsertionList(String word){
        
    }
}