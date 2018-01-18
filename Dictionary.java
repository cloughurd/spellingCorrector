package spell;

public class Dictionary implements ITrie{

    private Node root = new Node();
    private int uniqueWordCount = 0;
    private int wordCount = 0;
    private int nodeCount = 0;

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
            if(place.nodes[start - 'a'] == NULL){
                place.nodes[start - 'a'] = new Node();
                nodeCount++;
            }
            add(place.nodes[start - 'a'], remains);
        }
    }

    public Node find(string word){
        word = word.toLowerCase();
        return add(root, word);
    }
    private Node find(Node place, String remains){
        if(place == NULL){
            return NULL;
        }
        else if("".equals(remains)){
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
        return "toString()";
    }
	@Override
	public int hashCode() {
        return (nodeCount * wordCount) % uniqueWordCount;
    }
	@Override
	public boolean equals(Object o) {
        if(o == NULL){
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

}