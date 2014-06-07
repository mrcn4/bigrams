package pl.wedt.bigrams.dataprovider;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DataProvider {
	
	private List<File> directories;
	private List<String> posFilterList;
	
	public DataProvider(String XMLFilepath)
	{
		directories = new ArrayList<File>();
		posFilterList = new ArrayList<String>();
		Properties newprops = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(new File(XMLFilepath));
			newprops.loadFromXML(fis);
		} catch (Exception e) {
			System.err.println("Oh my.");
		}
		
		for (String path : newprops.getProperty("directories").replaceAll("\\[", "").replaceAll("\\]", "").split("[,]")) {
			directories.add(new File(path));
		}
		for (String pos : newprops.getProperty("posfilter").replaceAll("\\[", "").replaceAll("\\]", "").split("[,]")) {
			posFilterList.add(pos);
		}
	}
	
	public List<Document> getDocuments()
	{
		LinkedList<Document> listOfDocs = new LinkedList<Document>();
		for(File directory : directories) {			
			if (!directory.exists() || !directory.isDirectory())
				continue;
			
			for(File f: directory.listFiles())
			{
				if(f.isFile())
				{
					listOfDocs.add(new Document(f));
				}
			}
		}
		return listOfDocs;
	}

	public List<String> getPosFilterList() {
		return posFilterList;
	}
}
