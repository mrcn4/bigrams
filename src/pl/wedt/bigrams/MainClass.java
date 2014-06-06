package pl.wedt.bigrams;

import java.io.ObjectInputStream.GetField;
import java.util.List;
import java.util.Scanner;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.PrintStatsMaker;


public class MainClass {
	
	public void interactiveTest()
	{
		String end = "q";
		String input = "";
		
		System.out.println("Bigrams morfologic test");
		System.out.println("Enter q to quit, enter word to see it's stem");
		
		Scanner sc = new Scanner(System.in);
		do{
			input = sc.nextLine();
			System.out.println("Stem for \"" + input + "\" is " + StanfordLemmatizer.lemmatize(input));
		}
		while(!input.equals(end));
		sc.close();
	}
	
	public static void main(String[] args) {
		
		DataProvider dataProvider = new DataProvider();
		IStatsMaker printStatsMaker = new PrintStatsMaker(dataProvider);
		printStatsMaker.computeStats();
	}

}
