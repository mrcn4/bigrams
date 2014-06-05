package pl.wedt.bigrams.statsmaker;

public class WordStats {
	
	private Long wordCount = 0L; // liczba wystąpienia słowa w dokumencie
	private Long sentenceCount = 0L; // liczba zdań, w których wystąpiło słowo, w dokumencie
	private Double tfidf = 0.0d;
	
	public WordStats() {
	}
	
	public WordStats(Long wordCount, Long sentenceCount, Double tfidf) {
		this.wordCount = wordCount;
		this.sentenceCount = sentenceCount;
		this.tfidf = tfidf;
	}
	
	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount(Long wordCount) {
		this.wordCount = wordCount;
	}

	public Long getSentenceCount() {
		return sentenceCount;
	}

	public void setSentenceCount(Long sentenceCount) {
		this.sentenceCount = sentenceCount;
	}

	public Double getTfidf() {
		return tfidf;
	}

	public void setTfidf(Double tfidf) {
		this.tfidf = tfidf;
	}
	
	
}
