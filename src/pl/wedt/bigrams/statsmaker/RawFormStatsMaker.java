package pl.wedt.bigrams.statsmaker;

import pl.wedt.bigrams.dataprovider.IDataProvider;
import pl.wedt.bigrams.dataprovider.Word;

public class RawFormStatsMaker extends StatsMaker {
	public RawFormStatsMaker(IDataProvider dp) {
		super(dp);
	}
	
	@Override
	protected String getWord(Word w) {
		return w.getRawForm().toLowerCase();
	}
}
