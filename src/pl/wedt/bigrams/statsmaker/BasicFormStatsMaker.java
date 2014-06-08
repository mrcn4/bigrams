package pl.wedt.bigrams.statsmaker;

import pl.wedt.bigrams.dataprovider.IDataProvider;
import pl.wedt.bigrams.dataprovider.Word;

public class BasicFormStatsMaker extends StatsMaker {
	public BasicFormStatsMaker(IDataProvider dp) {
		super(dp);
	}
	
	@Override
	protected String getWord(Word w) {
		return w.getBasicForm().toLowerCase();
	}
}
