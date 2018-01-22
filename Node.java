package spell;

public class Node implements ITrie.INode{

    public final int ALPHABET_SIZE = 26;
    public Node[] nodes = new Node[ALPHABET_SIZE];
    private int count = 0;

    public int getValue(){
        return count;
    }

    public boolean incValue(){
        count++;
        return count == 1;
    }

}