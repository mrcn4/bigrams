package pl.wedt.bigrams;

import java.io.ObjectInputStream.GetField;
import java.util.List;
import java.util.Scanner;


public class MainClass {
	
	public static void main(String[] args) {
		String end = "q";
		String input = "";
		
		System.out.println("Bigrams morfologic test");
		System.out.println("Enter q to quit, enter word to see it's stem");
		
		Scanner sc = new Scanner(System.in);
		StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
		do{
			input = sc.next();
			System.out.println("Stem for \"" + input + "\" is " + lemmatizer.lemmatize(input));
		}
		while(!input.equals(end));
		
		
		
	}

}
