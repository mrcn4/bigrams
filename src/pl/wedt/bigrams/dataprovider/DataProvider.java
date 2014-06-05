package pl.wedt.bigrams.dataprovider;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DataProvider {
	
	private List<File> listOfFiles;
	private File directory;

	/**
	 * TEMP 
	 */
	public DataProvider() {
		directory = new File("testfiles");
	}
	
	public DataProvider(String XMLFilepath)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<Document> getDocuments()
	{
		LinkedList<Document> listOfDocs = new LinkedList<Document>();
		listOfFiles = new LinkedList<File>();
		for(File f: directory.listFiles())
		{
			if(f.isFile())
			{
				listOfDocs.add(new Document(f));
			}
		}
		return listOfDocs;
	}
	
}
