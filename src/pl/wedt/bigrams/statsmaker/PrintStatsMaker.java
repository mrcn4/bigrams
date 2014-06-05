package pl.wedt.bigrams.statsmaker;

import java.util.List;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Document;
import pl.wedt.bigrams.dataprovider.Sentence;
import pl.wedt.bigrams.dataprovider.Word;

public class PrintStatsMaker {
	private DataProvider dp;

	public PrintStatsMaker(DataProvider dp) {
		this.dp = dp;
	}
	
	public void printStats()
	{
		//get documents
		List<Document> documents = dp.getDocuments();
		
		//get first doc and print it's name
		Document doc = documents.get(0);
		System.out.println(doc.getName());
		
		//print sentences
		for(Sentence s: doc.getSentences())
		{
			//System.out.println("Sentence: " + s.toString());
			System.out.print("Sentence in basic forms: ");
			for(Word w: s.getWords())
			{
				System.out.print(w.getBasicForm() + "(" + w.getPOS() + ") ");
			}
			System.out.println();
		}
	}
}
