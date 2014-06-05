package pl.wedt.bigrams.statsmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Document;
import pl.wedt.bigrams.dataprovider.Sentence;
import pl.wedt.bigrams.dataprovider.Word;

public class PrintStatsMaker implements IStatsMaker {
	private DataProvider dp;
	private Map<String, Long> globalCount;
	private Map<String, Long> sentenceCount;
	private List<DocumentStats> docStats;
	private Long globalWordCount;

	public PrintStatsMaker(DataProvider dp) {
		this.dp = dp;
		this.globalCount = new HashMap<>();
		this.docStats = new ArrayList<>();
	}
	
	
	
	/* (non-Javadoc)
	 * @see pl.wedt.bigrams.statsmaker.IStatsMaker#computeStats()
	 */
	@Override
	public void computeStats()
	{
		//get documents
		List<Document> documents = dp.getDocuments();
		
		//get first doc and print its name
		Document doc = documents.get(0);
		System.out.println(doc.getName());
		
		//print sentences
		for(Sentence s: doc.getSentences())
		{
			//System.out.println("Sentence: " + s.toString());
			System.out.print("Sentence in basic forms: ");
			for(Word w: s.getWords())
			{
				System.out.print(w.getBasicForm() + "(" + w.getPOS() + ") ");
			}
			System.out.println();
		}
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
}
