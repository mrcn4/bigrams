package pl.wedt.bigrams.statsmaker;

import java.util.List;
import java.util.Map;

public interface IStatsMaker {

	public abstract void computeStats();

	public abstract Map<String, Long> getGlobalCount();

	public abstract void setGlobalCount(Map<String, Long> globalCount);

	public abstract Map<String, Long> getSentenceCount();

	public abstract void setSentenceCount(Map<String, Long> sentenceCount);

	public abstract List<DocumentStats> getDocStats();

	public abstract void setDocStats(List<DocumentStats> docStats);

	public abstract Long getGlobalWordCount();

	public abstract void setGlobalWordCount(Long globalWordCount);

}