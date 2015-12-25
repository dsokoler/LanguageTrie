import java.io.File;


public class Predictions extends LanguageTrie {

	protected Predictions(File file) {
		super(file);
	}
	
	public static int[] commonFollowings(char c) {
		int[] cf = new int[26];
		
		commonFollowings(cf, c, LanguageTrie.root, false);
		
		return cf;
	}
	
	private static void commonFollowings(int[] cf, char c, Node node, boolean incrementValues) {
		for (Node child : node.children) {
			if (child.letter - 97 < 0 || child.letter - 97 > 25) {
				commonFollowings(cf, c, child, false);
				continue;
			}
			
			if (incrementValues) {
				cf[child.letter - 97]++;
			}
			if (child.letter == c) {
				commonFollowings(cf, c, child, true);
			}
			else {
				commonFollowings(cf, c, child, false);
			}
		}
	}

}
