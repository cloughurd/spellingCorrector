package spell;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary implements ITrie{

    private Node root = new Node();
    private int uniqueWordCount = 0;
    private int wordCount = 0;
    private int nodeCount = 0;

    public static void main(String[] args){
        String inputFilename = args[0];
        Dictionary testDictionary = new Dictionary();
        Scanner sc;
        File inputFile;

        try{
            inputFile = new File(inputFilename);
            sc = new Scanner(inputFile);
        }
        catch (FileNotFoundException fnfe){
            System.out.println("Invalid Filename");
            return;
        }

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
                testDictionary.add(next);
            }
        }

        System.out.println(testDictionary.toString());
    }

    public void add(String word){
        word = word.toLowerCase();
        wordCount++;
        add(root, word);
    }
    private void add(Node place, String remains){
        if("".equals(remains)){
            if(place.incValue()){
                uniqueWordCount++;
            }
        }
        else{
            char start = remains.charAt(0);
            remains = remains.substring(1);
            if(place.nodes[start - 'a'] == null){
                place.nodes[start - 'a'] = new Node();
                nodeCount++;
            }
            add(place.nodes[start - 'a'], remains);
        }
    }

    public Node find(String word){
        word = word.toLowerCase();
        return find(root, word);
    }
    private Node find(Node place, String remains){
        if(place == null){
            return null;
        }
        else if("".equals(remains)){
            if(place.getValue() == 0){
                return null;
            }
            return place;
        }
        else{
            char start = remains.charAt(0);
            remains = remains.substring(1);
            return find(place.nodes[start - 'a'], remains);
        }
    }

	public int getWordCount() {
        return uniqueWordCount;
    }
	public int getNodeCount() {
        return nodeCount;
    }


	@Override
	public String toString() {
        return toString(root, "");
    }
    public String toString(Node place, String word){
        String result = "";
        if(place == null){
            return "";
        }else{
            if(place.getValue() > 0){
                result = result + word + "\n";
            }
            for(int i = 0; i < place.ALPHABET_SIZE; i++){
                result = result + toString(place.nodes[i], word + Character.toString((char) (i + 'a')));
            }
            return result;
        }
    }

	@Override
	public int hashCode() {
        return (nodeCount * wordCount) % uniqueWordCount;
    }
	@Override
	public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        else if(this.hashCode() != o.hashCode()){
            return false;
        }
        else if(this.toString() == o.toString()){
            return true;
        }
        else return false;
    }

}