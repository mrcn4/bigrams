package pl.wedt.bigrams.dataprovider;

import java.io.File;
import java.util.List;

public interface IDocument {

	public abstract String getName();

	public abstract List<Sentence> getSentences();

}