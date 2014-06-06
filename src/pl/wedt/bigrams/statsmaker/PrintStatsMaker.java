package pl.wedt.bigrams.statsmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Document;
import pl.wedt.bigrams.dataprovider.Sentence;
import pl.wedt.bigrams.dataprovider.Word;

public class PrintStatsMaker implements IStatsMaker {
	private DataProvider dp;
	private Long globalSentenceCount;
	private List<String> posFilter;
	
	//single words
	private Map<String, Long> globalCount; // liczba wystąpień słowa w repozytorium
	private Map<String, Long> sentenceCount; // liczba zdań, w których dane słowo wystąpiło, w repozytorium
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

	public PrintStatsMaker(DataProvider dp) {
		this.dp = dp;
		this.globalSentenceCount = 0L;
		this.posFilter = new ArrayList<>();
		
		this.globalCount = new HashMap<>();
		this.sentenceCount = new HashMap<>();
		this.docStats = new ArrayList<>();
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
	
	
	
	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#computeStats()
	 */
	@Override
	public void computeStats()
	{
		//get documents
		List<Document> documents = dp.getDocuments();
		
		for (Document doc : documents) {
			DocumentStats ds = new DocumentStats(doc);
			Set<String> uniqueWords = new HashSet<String>();
			String lastWord = null;
			
			for (Sentence s: doc.getSentences()) {
				globalSentenceCount++;
				for (Word w: s.getWords()) {
					if (posFilter.indexOf(w.getPOS()) != -1)
						continue;
					
					uniqueWords.add(getWord(w));
					
					WordStats ws = ds.getWordStats().get(getWord(w));
					if (ws == null) {
						ws = new WordStats();
					}
					
					globalWordCount++;
					
					Long wordGlobalCount = globalCount.get(getWord(w));
					if (wordGlobalCount == null) {
						wordGlobalCount = 0L;
					}
					globalCount.put(getWord(w), wordGlobalCount+1);
					
					Long wordDocumentCount = ws.getWordCount();
					if (wordDocumentCount == null) {
						wordDocumentCount = 0L;
					}
					ws.setWordCount(wordDocumentCount+1);
					
					ds.getWordStats().put(getWord(w), ws);
				}
				
				Set<Word> uniqueSentenceWords = new HashSet<>(s.getWords());
				
				for (Word w: uniqueSentenceWords) {
					
					WordStats ws = ds.getWordStats().get(getWord(w));
					
					Long sentenceGlobalCount = sentenceCount.get(getWord(w));
					if (sentenceGlobalCount == null) {
						sentenceGlobalCount = 0L;
					}
					sentenceCount.put(getWord(w), sentenceGlobalCount+1);
					
					Long sentenceDocumentCount = ws.getSentenceCount();
					if (sentenceDocumentCount == null) {
						sentenceDocumentCount = 0L;
					}
					ws.setSentenceCount(sentenceDocumentCount+1);
				}
			}
			docStats.add(ds);
			
			for (String w : uniqueWords) {
				Long wordFreq = documentFreq.get(w);
				if (wordFreq == null) {
					wordFreq = 0L;
				}
				documentFreq.put(w, wordFreq+1);
			}
		}
		
		for (DocumentStats ds: docStats) {
			for (String w : ds.getWordStats().keySet()) {
				ds.getWordStats().get(w).setTfidf((double)ds.getWordStats().get(w).getWordCount() * Math.log((double)documents.size() / (double)documentFreq.get(w)));
			}
		}
	}
	
	protected String getBigram(Word w1, Word w2) {
		
		if (getWord(w1).length() <= getWord(w2).length())
			return getWord(w1) + ", " + getWord(w2);
		
		else 
			return getWord(w2) + ", " + getWord(w1);
		
	}

	/* abstract */ protected String getWord(Word w) { return w.getBasicForm(); }

	@Override
	public void setPosFilter(String... poses) {
		posFilter.clear();
		for (String pos : poses)
			posFilter.add(pos);
	}
	
	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalCount()
	 */
	@Override
	public Map<String, Long> getGlobalCount() {
		return globalCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalCount(java.util.Map)
	 */
	@Override
	public void setGlobalCount(Map<String, Long> globalCount) {
		this.globalCount = globalCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getSentenceCount()
	 */
	@Override
	public Map<String, Long> getSentenceCount() {
		return sentenceCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setSentenceCount(java.util.Map)
	 */
	@Override
	public void setSentenceCount(Map<String, Long> sentenceCount) {
		this.sentenceCount = sentenceCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocStats()
	 */
	@Override
	public List<DocumentStats> getDocStats() {
		return docStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocStats(java.util.List)
	 */
	@Override
	public void setDocStats(List<DocumentStats> docStats) {
		this.docStats = docStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalWordCount()
	 */
	@Override
	public Long getGlobalWordCount() {
		return globalWordCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalWordCount(java.lang.Long)
	 */
	@Override
	public void setGlobalWordCount(Long globalWordCount) {
		this.globalWordCount = globalWordCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocumentFreq()
	 */
	@Override
	public Map<String, Long> getDocumentFreq() {
		return documentFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocumentFreq(java.util.Map)
	 */
	@Override
	public void setDocumentFreq(Map<String, Long> documentFreq) {
		this.documentFreq = documentFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getBigramCount()
	 */
	@Override
	public Map<String, Long> getBigramCount() {
		return bigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setBigramCount(java.util.Map)
	 */
	@Override
	public void setBigramCount(Map<String, Long> bigramCount) {
		this.bigramCount = bigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getSentenceBigramCount()
	 */
	@Override
	public Map<String, Long> getSentenceBigramCount() {
		return sentenceBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setSentenceBigramCount(java.util.Map)
	 */
	@Override
	public void setSentenceBigramCount(Map<String, Long> sentenceBigramCount) {
		this.sentenceBigramCount = sentenceBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocBigramStats()
	 */
	@Override
	public List<DocumentStats> getDocBigramStats() {
		return docBigramStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocBigramStats(java.util.List)
	 */
	@Override
	public void setDocBigramStats(List<DocumentStats> docBigramStats) {
		this.docBigramStats = docBigramStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocumentBigramFreq()
	 */
	@Override
	public Map<String, Long> getDocumentBigramFreq() {
		return documentBigramFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocumentBigramFreq(java.util.Map)
	 */
	@Override
	public void setDocumentBigramFreq(Map<String, Long> documentBigramFreq) {
		this.documentBigramFreq = documentBigramFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalBigramCount()
	 */
	@Override
	public Long getGlobalBigramCount() {
		return globalBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalBigramCount(java.lang.Long)
	 */
	@Override
	public void setGlobalBigramCount(Long globalBigramCount) {
		this.globalBigramCount = globalBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getFunnyBigramCount()
	 */
	@Override
	public Map<String, Long> getFunnyBigramCount() {
		return funnyBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setFunnyBigramCount(java.util.Map)
	 */
	@Override
	public void setFunnyBigramCount(Map<String, Long> funnyBigramCount) {
		this.funnyBigramCount = funnyBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getSentenceFunnyBigramCount()
	 */
	@Override
	public Map<String, Long> getSentenceFunnyBigramCount() {
		return sentenceFunnyBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setSentenceFunnyBigramCount(java.util.Map)
	 */
	@Override
	public void setSentenceFunnyBigramCount(Map<String, Long> sentenceFunnyBigramCount) {
		this.sentenceFunnyBigramCount = sentenceFunnyBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocFunnyBigramStats()
	 */
	@Override
	public List<DocumentStats> getDocFunnyBigramStats() {
		return docFunnyBigramStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocFunnyBigramStats(java.util.List)
	 */
	@Override
	public void setDocFunnyBigramStats(List<DocumentStats> docFunnyBigramStats) {
		this.docFunnyBigramStats = docFunnyBigramStats;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocumentFunnyBigramFreq()
	 */
	@Override
	public Map<String, Long> getDocumentFunnyBigramFreq() {
		return documentFunnyBigramFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocumentFunnyBigramFreq(java.util.Map)
	 */
	@Override
	public void setDocumentFunnyBigramFreq(Map<String, Long> documentFunnyBigramFreq) {
		this.documentFunnyBigramFreq = documentFunnyBigramFreq;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalFunnyBigramCount()
	 */
	@Override
	public Long getGlobalFunnyBigramCount() {
		return globalFunnyBigramCount;
	}



	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalFunnyBigramCount(java.lang.Long)
	 */
	@Override
	public void setGlobalFunnyBigramCount(Long globalFunnyBigramCount) {
		this.globalFunnyBigramCount = globalFunnyBigramCount;
	}



	public Long getGlobalSentenceCount() {
		return globalSentenceCount;
	}



	public void setGlobalSentenceCount(Long globalSentenceCount) {
		this.globalSentenceCount = globalSentenceCount;
	}
}
