package pl.wedt.bigrams.dataprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyDataProvider implements IDataProvider {

	@Override
	public List<IDocument> getDocuments() {
		// TODO Auto-generated method stub
		List<IDocument> test = new ArrayList<IDocument>();
		test.add(new DummyDocument());
		return test;
	}

	@Override
	public List<String> getPosFilterList() {
		String[] defaultEnabledPOSs= {
			"FW", "JJ", "JJR", "JJS", "MD","NN","NNP","NNPS","NNS",
			"PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM",
			"TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP",
			"WP$","WRB"};
		
		return Arrays.asList(defaultEnabledPOSs);
	}

}
