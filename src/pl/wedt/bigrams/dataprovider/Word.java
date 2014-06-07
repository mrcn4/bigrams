package pl.wedt.bigrams.dataprovider;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

public class Word {
	
	private CoreLabel cm;
	
	public Word(CoreLabel cm) {
		this.cm = cm;
	}

	public String getPOS()
	{		
		return cm.get(PartOfSpeechAnnotation.class);
	}
	
	@Override
	public String toString() {
		return cm.toString();
	}
	
	public String getRawForm()
	{
		return cm.toString();
	}
	
	public String getBasicForm()
	{
		return cm.get(LemmaAnnotation.class);
	}
}
