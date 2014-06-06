package pl.wedt.bigrams.statsmaker;

import java.util.List;
import java.util.Map;

public interface IStatsMaker {

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#computeStats()
	 */
	public abstract void computeStats();

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalCount()
	 */
	public abstract Map<String, Long> getGlobalCount();

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalCount(java.util.Map)
	 */
	public abstract void setGlobalCount(Map<String, Long> globalCount);

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getSentenceCount()
	 */
	public abstract Map<String, Long> getSentenceCount();

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setSentenceCount(java.util.Map)
	 */
	public abstract void setSentenceCount(Map<String, Long> sentenceCount);

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getDocStats()
	 */
	public abstract List<DocumentStats> getDocStats();

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setDocStats(java.util.List)
	 */
	public abstract void setDocStats(List<DocumentStats> docStats);

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#getGlobalWordCount()
	 */
	public abstract Long getGlobalWordCount();

	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#setGlobalWordCount(java.lang.Long)
	 */
	public abstract void setGlobalWordCount(Long globalWordCount);

	public abstract Map<String, Long> getDocumentFreq();

	public abstract void setDocumentFreq(Map<String, Long> documentFreq);

	public abstract Map<String, Long> getBigramCount();

	public abstract void setBigramCount(Map<String, Long> bigramCount);

	public abstract Map<String, Long> getSentenceBigramCount();

	public abstract void setSentenceBigramCount(
			Map<String, Long> sentenceBigramCount);

	public abstract List<DocumentStats> getDocBigramStats();

	public abstract void setDocBigramStats(List<DocumentStats> docBigramStats);

	public abstract Map<String, Long> getDocumentBigramFreq();

	public abstract void setDocumentBigramFreq(
			Map<String, Long> documentBigramFreq);

	public abstract Long getGlobalBigramCount();

	public abstract void setGlobalBigramCount(Long globalBigramCount);

	public abstract Map<String, Long> getFunnyBigramCount();

	public abstract void setFunnyBigramCount(Map<String, Long> funnyBigramCount);

	public abstract Map<String, Long> getSentenceFunnyBigramCount();

	public abstract void setSentenceFunnyBigramCount(
			Map<String, Long> sentenceFunnyBigramCount);

	public abstract List<DocumentStats> getDocFunnyBigramStats();

	public abstract void setDocFunnyBigramStats(
			List<DocumentStats> docFunnyBigramStats);

	public abstract Map<String, Long> getDocumentFunnyBigramFreq();

	public abstract void setDocumentFunnyBigramFreq(
			Map<String, Long> documentFunnyBigramFreq);

	public abstract Long getGlobalFunnyBigramCount();

	public abstract void setGlobalFunnyBigramCount(Long globalFunnyBigramCount);
	
	public abstract Long getGlobalSentenceCount();
	
	public abstract void setGlobalSentenceCount(Long globalSentenceCount);
	
	public abstract void setPosFilter(String[] poses);
	
	public abstract Long getDocumentsCompleted();
	
	public abstract void setDocumentsCompleted(Long documentsCompleted);
	
	public abstract boolean isStopFlag();

	public abstract void setStopFlag(boolean stopFlag);

}