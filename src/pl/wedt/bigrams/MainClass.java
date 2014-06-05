package pl.wedt.bigrams;

import java.io.ObjectInputStream.GetField;
import java.util.List;
import java.util.Scanner;

import morfologik.stemming.WordData;

public class MainClass {
	static morfologik.stemming.PolishStemmer dict = new morfologik.stemming.PolishStemmer();
	
	private static String getStem(String word)
	{
		List<WordData> lookup = dict.lookup(word.toLowerCase());
		if (lookup.size() > 0)
		{
			//return first
			return lookup.get(0).getStem().toString();
		}
		else
		{
			return "";
		}
	}
	
	public static void main(String[] args) {
		String end = "q";
		String input = "";
		
		System.out.println("Bigrams morfologic test");
		System.out.println("Enter q to quit, enter word to see it's stem");
		
		Scanner sc = new Scanner(System.in);
		do{
			input = sc.next();
			System.out.println("Stem for \"" + input + "\" is " + getStem(input));
		}
		while(!input.equals(end));
		
		
		
	}

}
