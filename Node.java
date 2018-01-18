#package spell;

public class Node implements INode{

    public Node[] nodes = new Node[26];
    private int count = 0;

    public int getValue(){
        return count;
    }

    public boolean incValue(){
        count++;
        return count == 1;
    }

}