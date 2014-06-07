package pl.wedt.bigrams.dataprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import pl.wedt.bigrams.StanfordLemmatizer;
import edu.stanford.nlp.util.CoreMap;

public class Document implements IDocument {
	private File f;

	public Document(File f) {
		this.f = f;
	}
	
	@Override
	public String getName()
	{
		return f.getName();
	}

	private String readDoc(File f) {
		String text = "";
		int read, N = 1024 * 1024;
		char[] buffer = new char[N];

		BufferedReader br = null;
		try {
		    FileReader fr = new FileReader(f);
		    br = new BufferedReader(fr);

		    while(true) {
		        read = br.read(buffer, 0, N);
		        text += new String(buffer, 0, read);

		        if(read < N) {
		            break;
		        }
		    }
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

		return text;
	}
	
	@Override
	public List<Sentence> getSentences()
	{
		List<Sentence> sentencesList = new LinkedList<Sentence>(); 
		String documentText = readDoc(f);
		List<CoreMap> sentences = StanfordLemmatizer.getSentences(documentText);
		for(CoreMap cm: sentences)
		{
			sentencesList.add(new Sentence(cm));
		}
		return sentencesList;
	}
}
