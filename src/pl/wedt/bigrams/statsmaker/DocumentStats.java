package pl.wedt.bigrams.statsmaker;

import java.util.HashMap;
import java.util.Map;

import pl.wedt.bigrams.dataprovider.Document;
import pl.wedt.bigrams.dataprovider.Word;


public class DocumentStats {
	private String docName;
	private Map<String, WordStats> wordStats;
	
	public DocumentStats(Document doc) {
		this.docName = doc.getName();
		this.wordStats = new HashMap<>();
	}
	
	public DocumentStats(String docName) {
		this.docName = docName;
		this.wordStats = new HashMap<>();
	}
	
	public Map<String, WordStats> getWordStats() {
		return wordStats;
	}
	
	public String getDocName() {
		return docName;
	}
	
	@Override
	public String toString() {
		return "DocumentStats [docName=" + docName +
				"; wordStats=" + wordStats + "]";
	}
	
}
