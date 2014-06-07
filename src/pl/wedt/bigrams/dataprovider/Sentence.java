package pl.wedt.bigrams.dataprovider;

import java.util.LinkedList;
import java.util.List;

import pl.wedt.bigrams.StanfordLemmatizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {
	
	private CoreMap cm;

	public Sentence(CoreMap cm) {
		this.cm = cm;
	}
	
	public List<Word> getWords()
	{
		
		List<Word> wordList = new LinkedList<Word>(); 
		List<CoreLabel> wordsNLP = StanfordLemmatizer.getWords(cm);
		for(CoreLabel cm: wordsNLP)
		{
			wordList.add(new Word(cm));
		}
		return wordList;
	}
	
	@Override
	public String toString() {
		return cm.toString();
	}
}
