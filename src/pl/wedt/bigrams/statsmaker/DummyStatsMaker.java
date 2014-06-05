package pl.wedt.bigrams.statsmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.wedt.bigrams.dataprovider.DataProvider;

public class DummyStatsMaker implements IStatsMaker{
	private Map<String, Long> globalCount;
	private List<DocumentStats> docStats;
	private Map<String, Long> sentenceCount;
	private Long globalWordCount;

	public DummyStatsMaker() {
		this.globalCount = new HashMap<>();
		this.docStats = new ArrayList<>();
		this.sentenceCount = new HashMap<>();
		this.globalWordCount = 0l;
	}
	
	
	@Override
	public void computeStats() {
		//"No! Try not. Do, or do not. There is no try.";
		String sentence1 = "We become what we think about";
		String sentence2 = "The mind is everything. What you think you become.";
		
		globalCount.put("we", 2L);
		globalCount.put("become", 2L);
		globalCount.put("what", 2L);
		globalCount.put("think", 2L);
		globalCount.put("about", 1L);
		globalCount.put("the", 1L);
		globalCount.put("mind", 1L);
		globalCount.put("is", 1L);
		globalCount.put("everything", 1L);
		globalCount.put("you", 2L);
		
		sentenceCount.put("we", 1L);
		sentenceCount.put("become", 2L);
		sentenceCount.put("what", 2L);
		sentenceCount.put("think", 2L);
		sentenceCount.put("about", 1L);
		sentenceCount.put("the", 1L);
		sentenceCount.put("mind", 1L);
		sentenceCount.put("is", 1L);
		sentenceCount.put("everything", 1L);
		sentenceCount.put("you", 1L);
		
		DocumentStats documentStats1 = new DocumentStats("Earl Nightingale");
		Map<String, WordStats> wordStats1 = documentStats1.getWordStats();
		//"we"
		wordStats1.put("we", new WordStats(2L, 1L, 1.0d));
		wordStats1.put("become", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("what", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("think", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("about", new WordStats(1L, 1L, 1.0d));
		
		docStats.add(documentStats1);
		
		DocumentStats documentStats2 = new DocumentStats("Earl Nightingale");
		Map<String, WordStats> wordStats2 = documentStats1.getWordStats();
		//"we"
		wordStats2.put("the", new WordStats(2L, 1L, 1.0d));
		wordStats2.put("mind", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("is", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("everything", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("become", new WordStats(1L, 1L, 1.0d));
		
		docStats.add(documentStats2);
		
		
	}

	@Override
	public Map<String, Long> getGlobalCount() {
		
		return null;
	}

	@Override
	public void setGlobalCount(Map<String, Long> globalCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Long> getSentenceCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSentenceCount(Map<String, Long> sentenceCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DocumentStats> getDocStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDocStats(List<DocumentStats> docStats) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getGlobalWordCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlobalWordCount(Long globalWordCount) {
		// TODO Auto-generated method stub
		
	}
	
}
