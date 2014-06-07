package pl.wedt.bigrams.dataprovider;

import java.util.List;

public interface IDataProvider {

	public abstract List<IDocument> getDocuments();

	public abstract List<String> getPosFilterList();

}