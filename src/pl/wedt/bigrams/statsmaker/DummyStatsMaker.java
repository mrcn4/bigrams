package pl.wedt.bigrams.statsmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Word;
import pl.wedt.bigrams.gui.BigramsGUI;

public class DummyStatsMaker implements IStatsMaker{
	private Long globalSentenceCount;
	private List<String> posFilter;
	private Long documentsCompleted;
	private boolean stopFlag;
	
	private Map<String, Long> globalCount;
	private Map<String, Long> sentenceCount;
	private List<DocumentStats> docStats;
	private Map<String, Long> documentFreq;
	private Long globalWordCount;

	//bigrams
	private Map<String, Long> bigramCount;
	private Map<String, Long> sentenceBigramCount;
	private List<DocumentStats> docBigramStats;
	private Map<String, Long> documentBigramFreq;
	private Long globalBigramCount;
	
	//funny bigrams
	private Map<String, Long> funnyBigramCount;
	private Map<String, Long> sentenceFunnyBigramCount;
	private List<DocumentStats> docFunnyBigramStats;
	private Map<String, Long> documentFunnyBigramFreq;
	private Long globalFunnyBigramCount;
	

	private static Logger log = Logger.getLogger(DummyStatsMaker.class);
	

	public DummyStatsMaker() {
		this.globalSentenceCount = 0L;
		this.posFilter = new ArrayList<>();
		this.documentsCompleted = 0L;
		this.stopFlag = false;
		
		this.globalCount = new HashMap<>();
		this.docStats = new ArrayList<>();
		this.sentenceCount = new HashMap<>();
		this.documentFreq = new HashMap<>();
		this.globalWordCount = 0L;
		
		this.bigramCount = new HashMap<>();
		this.sentenceBigramCount = new HashMap<>();
		this.docBigramStats = new ArrayList<>();
		this.documentBigramFreq = new HashMap<>();
		this.globalBigramCount = 0L;
		
		this.funnyBigramCount = new HashMap<>();
		this.sentenceFunnyBigramCount = new HashMap<>();
		this.docFunnyBigramStats = new ArrayList<>();
		this.documentFunnyBigramFreq = new HashMap<>();
		this.globalFunnyBigramCount = 0L;
	}
	
	
	@Override
	public void computeStats() {
		String sentence1 = "We become what we think about";
		String sentence2 = "The mind is everything. What you think you become.";
		
		//wordcounts
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
		
		documentFreq.put("we", 1L);
		documentFreq.put("become", 2L);
		documentFreq.put("what", 2L);
		documentFreq.put("think", 2L);
		documentFreq.put("about", 1L);
		documentFreq.put("the", 1L);
		documentFreq.put("mind", 1L);
		documentFreq.put("is", 1L);
		documentFreq.put("everything", 1L);
		documentFreq.put("you", 1L);
		
		DocumentStats documentStats1 = new DocumentStats("Earl Nightingale");
		Map<String, WordStats> wordStats1 = documentStats1.getWordStats();
		wordStats1.put("we", new WordStats(2L, 1L, 1.0d));
		wordStats1.put("become", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("what", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("think", new WordStats(1L, 1L, 1.0d));
		wordStats1.put("about", new WordStats(1L, 1L, 1.0d));
		docStats.add(documentStats1);
		
		DocumentStats documentStats2 = new DocumentStats("Buddha");
		Map<String, WordStats> wordStats2 = documentStats2.getWordStats();
		wordStats2.put("the", new WordStats(2L, 1L, 1.0d));
		wordStats2.put("mind", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("is", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("everything", new WordStats(1L, 1L, 1.0d));
		wordStats2.put("become", new WordStats(1L, 1L, 1.0d));
		docStats.add(documentStats2);
		
		log.debug(documentStats2);
		
		globalWordCount = 15L; 
		
		//bigram counts
		bigramCount.put("we become", 1L);
		bigramCount.put("become what", 1L);
		bigramCount.put("what we", 1L);
		bigramCount.put("we think", 1L);
		bigramCount.put("think about", 1L);
		bigramCount.put("The mind", 1L);
		bigramCount.put("mind is", 1L);
		bigramCount.put("is everything", 1L);
		bigramCount.put("what you", 1L);
		bigramCount.put("you think", 1L);
		bigramCount.put("think you", 1L);
		bigramCount.put("you become", 1L);
		
		sentenceBigramCount.put("we become", 1L);
		sentenceBigramCount.put("become what", 1L);
		sentenceBigramCount.put("what we", 1L);
		sentenceBigramCount.put("we think", 1L);
		sentenceBigramCount.put("think about", 1L);
		sentenceBigramCount.put("the mind", 1L);
		sentenceBigramCount.put("mind is", 1L);
		sentenceBigramCount.put("is everything", 1L);
		sentenceBigramCount.put("what you", 1L);
		sentenceBigramCount.put("you think", 1L);
		sentenceBigramCount.put("think you", 1L);
		sentenceBigramCount.put("you become", 1L);
		
		documentBigramFreq.put("we become", 1L);
		documentBigramFreq.put("become what", 1L);
		documentBigramFreq.put("what we", 1L);
		documentBigramFreq.put("we think", 1L);
		documentBigramFreq.put("think about", 1L);
		documentBigramFreq.put("the mind", 1L);
		documentBigramFreq.put("mind is", 1L);
		documentBigramFreq.put("is everything", 1L);
		documentBigramFreq.put("what you", 1L);
		documentBigramFreq.put("you think", 1L);
		documentBigramFreq.put("think you", 1L);
		documentBigramFreq.put("you become", 1L);
		
		DocumentStats documentBigramStats1 = new DocumentStats("Earl Nightingale");
		Map<String, WordStats> wordBigramStats1 = documentBigramStats1.getWordStats();
		wordBigramStats1.put("we become", new WordStats(1L, 1L, 1.0d));
		wordBigramStats1.put("become what", new WordStats(1L, 1L, 1.0d));
		wordBigramStats1.put("what we", new WordStats(1L, 1L, 1.0d));
		wordBigramStats1.put("we think", new WordStats(1L, 1L, 1.0d));
		wordBigramStats1.put("think about", new WordStats(1L, 1L, 1.0d));
		docBigramStats.add(documentBigramStats1);
		
		DocumentStats documentBigramStats2 = new DocumentStats("Buddha");
		Map<String, WordStats> wordBigramStats2 = documentBigramStats2.getWordStats();
		wordBigramStats2.put("the mind", new WordStats(2L, 1L, 1.0d));
		wordBigramStats2.put("mind is", new WordStats(1L, 1L, 1.0d));
		wordBigramStats2.put("is everything", new WordStats(1L, 1L, 1.0d));
		wordBigramStats2.put("what you", new WordStats(1L, 1L, 1.0d));
		wordBigramStats2.put("you think", new WordStats(1L, 1L, 1.0d));
		wordBigramStats2.put("think you", new WordStats(1L, 1L, 1.0d));
		wordBigramStats2.put("you become", new WordStats(1L, 1L, 1.0d));
		docBigramStats.add(documentBigramStats2);
		
		globalBigramCount = 12L;
		
		//funny bigram
		//bigram counts
		funnyBigramCount.put("we become", 1L);
		funnyBigramCount.put("we what", 1L);
		funnyBigramCount.put("we we", 1L);
		funnyBigramCount.put("we think", 1L);
		funnyBigramCount.put("we about", 1L);
		funnyBigramCount.put("become what", 1L);
		funnyBigramCount.put("become we", 1L);
		funnyBigramCount.put("become about", 1L);
		funnyBigramCount.put("what we", 1L);
		funnyBigramCount.put("what think", 1L);
		funnyBigramCount.put("what about", 1L);
		funnyBigramCount.put("think about", 1L);
		
		funnyBigramCount.put("The mind", 1L);
		funnyBigramCount.put("The is", 1L);
		funnyBigramCount.put("The everything", 1L);
		funnyBigramCount.put("mind is", 1L);
		funnyBigramCount.put("mind everything", 1L);
		funnyBigramCount.put("is everything", 1L);
		funnyBigramCount.put("what you", 2L);
		funnyBigramCount.put("what think", 1L);
		funnyBigramCount.put("what become", 1L);
		funnyBigramCount.put("you think", 1L);
		funnyBigramCount.put("you become", 1L);
		
		sentenceFunnyBigramCount.put("we become", 1L);
		sentenceFunnyBigramCount.put("become what", 1L);
		sentenceFunnyBigramCount.put("what we", 1L);
		sentenceFunnyBigramCount.put("we think", 1L);
		sentenceFunnyBigramCount.put("think about", 1L);
		sentenceFunnyBigramCount.put("the mind", 1L);
		sentenceFunnyBigramCount.put("mind is", 1L);
		sentenceFunnyBigramCount.put("is everything", 1L);
		sentenceFunnyBigramCount.put("what you", 1L);
		sentenceFunnyBigramCount.put("you think", 1L);
		sentenceFunnyBigramCount.put("think you", 1L);
		sentenceFunnyBigramCount.put("you become", 1L);
		
		documentFunnyBigramFreq.put("we become", 1L);
		documentFunnyBigramFreq.put("become what", 1L);
		documentFunnyBigramFreq.put("what we", 1L);
		documentFunnyBigramFreq.put("we think", 1L);
		documentFunnyBigramFreq.put("think about", 1L);
		documentFunnyBigramFreq.put("the mind", 1L);
		documentFunnyBigramFreq.put("mind is", 1L);
		documentFunnyBigramFreq.put("is everything", 1L);
		documentFunnyBigramFreq.put("what you", 1L);
		documentFunnyBigramFreq.put("you think", 1L);
		documentFunnyBigramFreq.put("think you", 1L);
		documentFunnyBigramFreq.put("you become", 1L);
		
		DocumentStats documentFunnyBigramStats1 = new DocumentStats("Earl Nightingale");
		Map<String, WordStats> wordFunnyBigramStats1 = documentFunnyBigramStats1.getWordStats();
		wordFunnyBigramStats1.put("we become", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats1.put("become what", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats1.put("what we", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats1.put("we think", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats1.put("think about", new WordStats(1L, 1L, 1.0d));
		docFunnyBigramStats.add(documentFunnyBigramStats1);
		
		DocumentStats documentFunnyBigramStats2 = new DocumentStats("Buddha");
		Map<String, WordStats> wordFunnyBigramStats2 = documentFunnyBigramStats2.getWordStats();
		wordFunnyBigramStats2.put("the mind", new WordStats(2L, 1L, 1.0d));
		wordFunnyBigramStats2.put("mind is", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats2.put("is everything", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats2.put("what you", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats2.put("you think", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats2.put("think you", new WordStats(1L, 1L, 1.0d));
		wordFunnyBigramStats2.put("you become", new WordStats(1L, 1L, 1.0d));
		docFunnyBigramStats.add(documentFunnyBigramStats2);
		
		globalFunnyBigramCount = 12L;
	}
	
	protected String getBigram(Word w1, Word w2) {
		
		if (getWord(w1).compareToIgnoreCase(getWord(w2)) <= 0)
			return getWord(w1) + ", " + getWord(w2);
		
		else 
			return getWord(w2) + ", " + getWord(w1);
		
	}

	/* abstract */ protected String getWord(Word w) { return w.getBasicForm(); }

	@Override
	public void setPosFilter(String[] poses) {
		posFilter.clear();
		for (String pos : poses)
			posFilter.add(pos);
	}

	@Override
	public Map<String, Long> getGlobalCount() {
		
		return globalCount;
	}

	@Override
	public void setGlobalCount(Map<String, Long> globalCount) {
				
	}

	@Override
	public Map<String, Long> getSentenceCount() {
		return sentenceCount;
	}

	@Override
	public void setSentenceCount(Map<String, Long> sentenceCount) {
	}

	@Override
	public List<DocumentStats> getDocStats() {
		return docStats;
	}

	@Override
	public void setDocStats(List<DocumentStats> docStats) {
		this.docStats = docStats;
	}

	@Override
	public Long getGlobalWordCount() {
		return globalWordCount;
	}

	@Override
	public void setGlobalWordCount(Long globalWordCount) {
		this.globalWordCount = globalWordCount;
	}

	@Override
	public Map<String, Long> getDocumentFreq() {
		
		return documentFreq;
	}


	@Override
	public Long getGlobalSentenceCount() {
		return globalSentenceCount;
	}


	@Override
	public void setGlobalSentenceCount(Long globalSentenceCount) {
		this.globalSentenceCount = globalSentenceCount;
		
	}
	

	public Map<String, Long> getBigramCount() {
		return bigramCount;
	}


	public void setBigramCount(Map<String, Long> bigramCount) {
		this.bigramCount = bigramCount;
	}


	public Map<String, Long> getSentenceBigramCount() {
		return sentenceBigramCount;
	}


	public void setSentenceBigramCount(Map<String, Long> sentenceBigramCount) {
		this.sentenceBigramCount = sentenceBigramCount;
	}


	public List<DocumentStats> getDocBigramStats() {
		return docBigramStats;
	}


	public void setDocBigramStats(List<DocumentStats> docBigramStats) {
		this.docBigramStats = docBigramStats;
	}


	public Map<String, Long> getDocumentBigramFreq() {
		return documentBigramFreq;
	}


	public void setDocumentBigramFreq(Map<String, Long> documentBigramFreq) {
		this.documentBigramFreq = documentBigramFreq;
	}


	public Long getGlobalBigramCount() {
		return globalBigramCount;
	}


	public void setGlobalBigramCount(Long globalBigramCount) {
		this.globalBigramCount = globalBigramCount;
	}


	public Map<String, Long> getFunnyBigramCount() {
		return funnyBigramCount;
	}


	public void setFunnyBigramCount(Map<String, Long> funnyBigramCount) {
		this.funnyBigramCount = funnyBigramCount;
	}


	public Map<String, Long> getSentenceFunnyBigramCount() {
		return sentenceFunnyBigramCount;
	}


	public void setSentenceFunnyBigramCount(
			Map<String, Long> sentenceFunnyBigramCount) {
		this.sentenceFunnyBigramCount = sentenceFunnyBigramCount;
	}


	public List<DocumentStats> getDocFunnyBigramStats() {
		return docFunnyBigramStats;
	}


	public void setDocFunnyBigramStats(List<DocumentStats> docFunnyBigramStats) {
		this.docFunnyBigramStats = docFunnyBigramStats;
	}


	public Map<String, Long> getDocumentFunnyBigramFreq() {
		return documentFunnyBigramFreq;
	}


	public void setDocumentFunnyBigramFreq(Map<String, Long> documentFunnyBigramFreq) {
		this.documentFunnyBigramFreq = documentFunnyBigramFreq;
	}


	public Long getGlobalFunnyBigramCount() {
		return globalFunnyBigramCount;
	}


	public void setGlobalFunnyBigramCount(Long globalFunnyBigramCount) {
		this.globalFunnyBigramCount = globalFunnyBigramCount;
	}


	public void setDocumentFreq(Map<String, Long> documentFreq) {
		this.documentFreq = documentFreq;
	}
	

	public Long getDocumentsCompleted() {
		return documentsCompleted;
	}

	public void setDocumentsCompleted(Long documentsCompleted) {
		this.documentsCompleted = documentsCompleted;
	}


	public boolean isStopFlag() {
		return stopFlag;
	}


	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}
	
}
