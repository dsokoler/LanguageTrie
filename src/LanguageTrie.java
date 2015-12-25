import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class LanguageTrie {
	static Node root;
	int numberOfWords;
	
	protected LanguageTrie (File file) {
		root = new Node();
	}
	
	/*
	 * Read the file and create a new Trie out of it
	 */
	public static LanguageTrie newTrie (File f) {
		
		LanguageTrie t = new LanguageTrie(f);
    	try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
    		String line = reader.readLine();
			
    		while (line != null) {
        		line.toLowerCase();
    			
    			char[] letters = line.toCharArray();
    			
    			if (letters.length == 0 || letters[0] == 10) {
    				line = reader.readLine();
    				continue;
    			}
    			t.addWordToTrie(LanguageTrie.root, letters, 0);
    			
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
	
	/*
	 * Adds a word specified the by char array to the trie rooted at root
	 */
	private void addWordToTrie(Node root, char[] word, int index) {
		if (index == word.length) {
			return;
		}
		
		while (word[index] <= 31 || word[index] >=127) {
			index++;
			
			if (index == word.length) {
				return;
			}
		}
		
		char c = word[index];
		
		boolean found = false;
		for (Node child : root.children) {
			if (child.letter == c) {
				addWordToTrie(child, word, index + 1);
				found = true;
				break;
			}
		}
		
		if (!found) {
			Node child = new Node(c);
			root.children.add(child);
			child.parent = root;
			updateSize(root);
			
			if (index == word.length - 1) {
				child.setEnd();
			}
			
			addWordToTrie(child, word, index + 1);
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
	
	/*
	 * Print the given Trie
	 */
    public static void printTrie(LanguageTrie t) {
    	printTrie(LanguageTrie.root, 0);
    }
    
    private static void printTrie(Node root, int spaces) {
    	for (int i = 0; i < spaces; i++) {
    		System.out.printf(" ");
    	}
    	
    	if (root.isRoot) {
    		System.out.println("Root");
    	}
    	else {
    		System.out.printf("%c", root.letter);
    		if (root.endOfWord) {
    			System.out.println(" END");
    		}
    		else {
    			System.out.println();
    		}
    	}
    	
    	for (Node child : root.children) {
    		printTrie(child, spaces + 1);
    	}
    }
}
