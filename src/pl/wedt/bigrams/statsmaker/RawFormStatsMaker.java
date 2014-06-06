package pl.wedt.bigrams.statsmaker;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.Word;

public class RawFormStatsMaker extends PrintStatsMaker {
	public RawFormStatsMaker(DataProvider dp) {
		super(dp);
	}
	
	@Override
	protected String getWord(Word w) {
		return w.getRawForm();
	}
}
