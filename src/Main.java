import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
	static LanguageTrie t;
	
	private static class Compare implements Comparator<Compare> {
		char letter;
		int occurences;
		private Compare (char c, int num) {
			this.letter = c;
			this.occurences = num;
		}
		
		public static final Comparator<Compare> c = new Comparator<Compare>() {
			@Override
			public int compare(Compare a, Compare b) {
				return a.occurences - b.occurences;
			}
		};

		@Override
		public int compare(Compare arg0, Compare arg1) {
			return arg0.occurences - arg1.occurences;
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("Please enter the name of the file to read from");
		Scanner readFile = new Scanner(System.in);
		String f1 = readFile.nextLine();
		
		String fileName = System.getProperty("user.dir")
				+ "\\" + f1 + ".txt";

		System.out.printf("Reading from file \"%s.txt\" in location \"%s\"\n",
							f1, System.getProperty("user.dir"));
		System.out.println();
		
		File file = new File(fileName);
		t = LanguageTrie.newTrie(file);
		
		//printTrie(t);
		//System.out.println();
		
		//printSize(t.root);
		//System.out.println();
		
		
		while(true) {
			System.out.println("Please Select an Option:");
			System.out.println(" 1. Check if a word exists");
			System.out.println(" 2. Display a distribution of all letters in this language");
			System.out.println(" 3. Display the longest word in this language");
			System.out.println(" 4. Display the size of this language");
			System.out.println(" 5. Display the letters most likely to come after a certain letter");
			System.out.println(" 6. Display all words of a certain length");
			System.out.println("10. Exit Program");
			System.out.println();

			Scanner scanner = new Scanner(System.in);
			if (!scanner.hasNextInt()) {
				System.out.println("Please enter a valid option");
				System.out.println();
				continue;
			}
			int choice = scanner.nextInt();
			System.out.println();

			switch(choice) {
				case 1:
					System.out.println();
					System.out.println("Please enter a word");
					Scanner sc = new Scanner(System.in);
					String word = sc.nextLine();
					System.out.println();
					if (SpellCheck.checkWord(word)) {
						if (SpellCheck.isWord(word)) {
							System.out.printf("%s is a word\n", word);
						}
						else {
							System.out.printf("%s is not a word\n", word);
						}
					}
					else {
						System.out.println("Invalid word format");
					}
					break;
					
				case 2:
					float[] distribution = SpellCheck.letterDistributions();
					for (int i = 0; i < 26; i++) {
						System.out.printf("%c:%.4f%%\n", (char)(i + 97), distribution[i]);
					}
					break;
					
				case 3: 
					System.out.println();
					System.out.println("Longest word: \"" + t.longest + "\"");
					
					break;
					
				case 4:
					SpellCheck.printSize(LanguageTrie.root);
					System.out.println("For more information on this statistic, enter 1");
					System.out.println("To continue using the program, enter anything else");
					System.out.println();
					Scanner information = new Scanner(System.in);
					if (scanner.hasNextInt()) {
						int c = scanner.nextInt();
						if (c == 1) {
							System.out.println();
							System.out.println("Size takes into account the number of individual");
							System.out.println("combinations of 2 letters required to represent,");
							System.out.println("the specified language, including the first and");
							System.out.println("last letters as a combination.  For example, the");
							System.out.println("word \"hello\" has 4 combinations. \"he\", \"el\",");
							System.out.println("\"ll\",\"lo\", and the letters \"h\" and \"o\". So a");
							System.out.println("language composed solely of the word \"hello\" would");
							System.out.println("have a size of 5");
						}
					}
					break;
					
				case 5:
					boolean correct = false;
					char character = 0;
					while (!correct) {
						System.out.println("Please enter the character, or 0 to exit");
						Scanner readChar = new Scanner(System.in);
						if (readChar.hasNextLine()) {
							char[] chr = readChar.nextLine().toLowerCase().toCharArray();
							if (chr[0] == '0') break;
							if (chr.length == 1 && (int)chr[0] >= 97 && (int)chr[0] <= 122) {
								correct = true;
								character = chr[0];
							}
						}
						if (!correct) {
							System.out.println("Please enter a valid character, or 0 to exit");
						}
					}
					
					int[] cf = Predictions.commonFollowings(character);
					ArrayList<Compare> compares = new ArrayList<Compare>();
					for (int i = 0; i < 26; i++) {
						Compare c = new Compare((char)(i + 97), cf[i]);
						compares.add(c);
					}
					Collections.sort(compares, Compare.c);  //NEED TO FIX THIS, SORT NOT WORKING
					for (int i = 0; i < 26; i++){
						System.out.printf("#%d: %c : %d\n", i+1, (char)(i + 97), cf[i]);
					}
					break;
					
				case 6:
					System.out.println("Please enter a size:");
					Scanner scan = new Scanner(System.in);
					int size = scan.nextInt();
					ArrayList<String> wordList = LanguageTrie.findWordsOfLength(size);
					
					System.out.println();
					System.out.println("All words of size " + size + ":");
					
					for(String wordOfSize : wordList) {
						System.out.printf("\t%s\n", wordOfSize);
					}
					System.out.println();
					break;
					
				case 10:
					System.out.println("Exiting Program");
					System.exit(1);
					
				default:
					System.out.println();
					System.out.println();
					System.out.println("Please Enter a Valid Choice");
					break;
			}

			System.out.println();
			System.out.println();
		}
	}
}
