package pl.wedt.bigrams.statsmaker;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Word;

public class BasicFormStatsMaker extends PrintStatsMaker {
	public BasicFormStatsMaker(DataProvider dp) {
		super(dp);
	}
	
	@Override
	protected String getWord(Word w) {
		return w.getBasicForm();
	}
}
