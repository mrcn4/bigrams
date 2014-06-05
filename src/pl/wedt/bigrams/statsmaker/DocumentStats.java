package pl.wedt.bigrams.statsmaker;

import java.util.HashMap;
import java.util.Map;

import pl.wedt.bigrams.dataprovider.Document;
import pl.wedt.bigrams.dataprovider.Word;


public class DocumentStats {
	private Document doc;
	private Map<String, WordStats> wordStats;
	
	public DocumentStats(Document doc) {
		this.doc = doc;
		this.wordStats = new HashMap<>();
	}
	
	public Map<String, WordStats> getWordStats() {
		return wordStats;
	}
	
	public String getDocName() {
		return doc.getName();
	}
	
}
