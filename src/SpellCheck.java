import java.io.File;
import java.util.Scanner;


public class SpellCheck extends LanguageTrie {

	protected SpellCheck(File file) {
		super(file);
	}
	
	

    public static boolean isWord(String word) {
    	char[] letters = word.toCharArray();
    	return isWord(root, letters, 0);
    }
    
    private static boolean isWord(Node root, char[] letters, int index) {
    	if (index == letters.length) return false;
    	for (Node child : root.children) {
    		if (child.letter == letters[index] && index == letters.length - 1 && child.endOfWord) {
    			System.out.println("MATCHED WORD");
    			return true;
    		}
    		else if (child.letter == letters[index]) {
    			return isWord(child, letters, index + 1);
    		}
    	}
    	return false;
    }
    
    protected static boolean checkWord (String word) {
    	char[] letters = word.toCharArray();
    	if (letters.length == 0) return false;
    	for (char c : letters) {
    		if (c <= 31 || c >=127) return false;
    	}
    	
    	return true;
    }
	
    public static void printSize(Node root){
    	System.out.printf("Size is %d\n", root.size);
    	System.out.println();
    }
    
    public static int longestWordLength() {
    	return longestWordLength(LanguageTrie.root);
    }
    
    private static int longestWordLength(Node root) {
    	int sizeOfSubtree = 0;
    	if (root.children.isEmpty()) {
    		return sizeOfSubtree + 1;
    	}
    	else {
    		for (Node child : root.children) {
    			int tempSize = longestWordLength(child);
    			if (tempSize > sizeOfSubtree) {
    				sizeOfSubtree = tempSize;
    			}
    		}
    		return sizeOfSubtree + 1;
    	}
    }
    
    public static float[] letterDistributions() {
    	float[] distributions = new float[26];
    	for (int i = 0; i < 26; i++) {
    		char viewing = (char)(i + 97);
        	letterDistributions(distributions, viewing, 0, root);
    		distributions[i] = 100*distributions[i]/root.size;
    	}
    	return distributions;
    }
    
    private static void letterDistributions(float[] distributions, char c, int total, Node node) {
    	if (node.children.isEmpty()) {
    		distributions[(int) (c - 97)] = total;
    		return;
    	}
    	for (Node child : node.children) {
    		if (child.letter == c) {
    			total += child.size;
			}
			letterDistributions(distributions, c, total, child);
    	}
    }
}
