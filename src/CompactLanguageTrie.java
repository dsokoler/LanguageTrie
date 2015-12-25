import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;


public class CompactLanguageTrie {
	static Node root;
	static ArrayList<LinkedList<Node>> treeStructure;
	
	protected CompactLanguageTrie() {
		root = new Node();
		treeStructure = new ArrayList<LinkedList<Node>>();
	}
	
	
	/*
	 * Read the file and create a new Trie out of it
	 */
	public static CompactLanguageTrie newTrie (File f) {
		
		CompactLanguageTrie t = new CompactLanguageTrie();
    	try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
    		String line = reader.readLine();
			
    		while (line != null) {
        		line.toLowerCase();
    			
    			char[] letters = line.toCharArray();
    			
    			if (letters.length == 0 || letters[0] == 10) {
    				line = reader.readLine();
    				continue;
    			}
    			CompactLanguageTrie.addWordToCompactTrie(CompactLanguageTrie.treeStructure, null, letters, 0);
    			
    			line = reader.readLine();
    		}
    		reader.close();
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("Cannot find the specified file");
    		System.exit(1);
		}
    	catch (IOException e) {
			System.out.println("An error occured reading from file");
    		System.exit(1);
		}
    	
    	return t;	
	}
	
	private static void addWordToCompactTrie (ArrayList<LinkedList<Node>> ts, Node parent, char[] letters, int index) {
		if (index == letters.length) {
			return;
		}
		
		LinkedList<Node> level = null;
		try {
			level = ts.get(index);
			
			for (Node node : level) {
				if (node.letter == letters[index]) {    //NEED TO ASSURE NODES HAVE CORRECT CHILDREN
					boolean found = false;
					for (Node child : node.children) {
						if (child.letter == letters[index]) {
							found = true;
						}
					}
					if (!found && parent != null) {
						parent.children.add(node);
					}
					if (index == letters.length - 1) {
						return;
					}
					addWordToCompactTrie(ts, node, letters, index + 1);
				}
			}
		}
		catch (IndexOutOfBoundsException e) {
			LinkedList<Node> newLevel = new LinkedList<Node>();
			ts.add(newLevel);
			
			Node node = new Node(letters[index]);
			root.children.add(node);
			node.parent = root;
			updateSize(root);
			
			newLevel.add(node);
			addWordToCompactTrie(ts, null, letters, index + 1);
		}
	}
	

	
	private static void updateSize(Node root) {
		root.size++;
		if (root.isRoot) {
			return;
		}
		else {
			updateSize(root.parent);
		}
	}
}
