package pl.wedt.bigrams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.IDataProvider;
import pl.wedt.bigrams.dataprovider.POS;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.PrintStatsMaker;
import pl.wedt.bigrams.statsmaker.StatsMakerSerializer;


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

		IDataProvider dataProvider = new DataProvider("config.properties");
		IStatsMaker printStatsMaker = new PrintStatsMaker(dataProvider);


		Object[] choosenPOSObjectList = POS.getDefaultPOS().toArray();
		String[] choosenPOSStringArray = Arrays.copyOf(
				choosenPOSObjectList, choosenPOSObjectList.length,
				String[].class);
		printStatsMaker.setStopFlag(false);
		printStatsMaker.setPosFilter(choosenPOSStringArray);
		printStatsMaker.computeStats();
		
		new StatsMakerSerializer().writeToOutput(System.out, printStatsMaker);
		System.exit(0);
	}

}
