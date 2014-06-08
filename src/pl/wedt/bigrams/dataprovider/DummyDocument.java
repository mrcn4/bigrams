package pl.wedt.bigrams.dataprovider;

import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.util.CoreMap;

public class DummyDocument implements IDocument {

	@Override
	public String getName() {
		return "DummyDocument";
	}

	@Override
	public List<Sentence> getSentences() {
		String documentText = "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the graphic elements of a document or visual presentation. By replacing the distraction of meaningful content with filler text of scrambled Latin it allows viewers to focus on graphical elements such as font, typography, and layout. The lorem ipsum text is typically a mangled section of De finibus bonorum et malorum, a 1st-century BC Latin text by Cicero, with words altered, added, and removed that make it nonsensical, improper Latin. A variation of the common lorem ipsum text has been used during typesetting since the 1960s or earlier, when it was popularized by advertisements for Letraset transfer sheets. It was introduced to the Digital Age by Aldus Corporation in the mid-1980s, which employed it in graphics and word processing templates for its breakthrough desktop publishing program, PageMaker for the Apple Macintosh.";
		
		List<Sentence> sentencesList = new LinkedList<Sentence>();
		List<CoreMap> sentences = StanfordLemmatizer.getSentences(documentText);
		for(CoreMap cm: sentences)
		{
			sentencesList.add(new Sentence(cm));
		}
		return sentencesList;
	}

}
