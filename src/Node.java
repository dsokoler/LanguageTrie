import java.util.LinkedList;

public class Node {
	int size = 0;
	Node parent;
	char letter;
	int numEnds = 0;
	boolean isRoot = false;
	boolean endOfWord = false;
	LinkedList<Node> children;
	
	public Node () {							//Constructor for the root
		this.isRoot = true;
		children = new LinkedList<Node>();
	}
	
	public Node (char c) {						//Constructor for all other nodes
		this.letter = c;
		children = new LinkedList<Node>();
	}
	
	public void increaseEnds() {
		this.numEnds++;
	}
	
	public char getLetter() {
		return this.letter;
	}
	
	public void setLetter(char c) {
		this.letter = c;
	}
	
	public void setEnd() {
		this.endOfWord = true;
	}
	
	public void setParent(Node n) {
		this.parent = n;
	}
	
	public void addChild(Node child) {
		this.children.add(child);
	}
}
