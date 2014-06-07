package pl.wedt.bigrams.statsmaker;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Joiner;

public class StatsMakerSerializer {
	
	public void writeToOutput(OutputStream os, IStatsMaker statsMaker) {
		PrintStream printStream = new PrintStream(os);
		//get words stats

		// global Stats
		printStream.println("Globals word stats");
		Map<String, Long> globalCount = statsMaker.getGlobalCount();
		for (Map.Entry<String, Long> me : globalCount.entrySet()) {
			ArrayList<Object> row = new ArrayList<Object>();

			row.add(me.getKey());
			row.add(me.getValue());
			row.add(statsMaker.getSentenceCount().get(me.getKey()));
			row.add(statsMaker.getDocumentFreq().get(me.getKey()));
			row.add(statsMaker.getDocumentFreq().get(me.getKey()) * 1.0
					/ statsMaker.getDocumentCount());

			Joiner joiner = Joiner.on(";").skipNulls();
			printStream.println(joiner.join(row));
		}

		printStream.println();
		
		//get docwise stats
		for(DocumentStats ds: statsMaker.getDocStats())
		{
			printStream.println("Document-wise word stats for " + ds.getDocName());
			for (Entry<String, WordStats> me : ds.getWordStats().entrySet()) {
				ArrayList<Object> row = new ArrayList<Object>();

				row.add(me.getKey());
				row.add(me.getValue().getWordCount());
				row.add(me.getValue().getSentenceCount());
				row.add(me.getValue().getTfidf());
				
				Joiner joiner = Joiner.on(";").skipNulls();
				printStream.println(joiner.join(row));
			}
			printStream.println();
		}
		
		printStream.println("Global sequential-bigram stats");
		for (Map.Entry<String, Long> me : statsMaker.getBigramCount().entrySet()) {
			ArrayList<Object> row = new ArrayList<Object>();

			row.add(me.getKey());

			row.add(me.getValue());

			row.add(statsMaker.getSentenceBigramCount().get(me.getKey()));
			
			row.add(statsMaker.getDocumentBigramFreq().get(me.getKey()));
			row.add(statsMaker.getDocumentBigramFreq().get(me.getKey())*1.0/statsMaker.getDocumentCount());
			
			
			row.add((statsMaker.getSentenceBigramCount().get(me.getKey())) * 1.0
					/ statsMaker.getGlobalSentenceCount());

			String firstWord = me.getKey().split(", ")[0];
			Long firstWordCount = statsMaker.getSentenceCount().get(firstWord);
			row.add(firstWordCount * 1.0
					/ statsMaker.getGlobalSentenceCount());

			String secondWord = me.getKey().split(", ")[1];
			Long secondWordCount = statsMaker.getSentenceCount().get(secondWord);
			row.add(secondWordCount * 1.0
					/ statsMaker.getGlobalSentenceCount());

			Joiner joiner = Joiner.on(";").skipNulls();
			printStream.println(joiner.join(row));
		}


		printStream.println();
		
		for(DocumentStats ds: statsMaker.getDocBigramStats())
		{
			printStream.println("Document-wise sequential-bigram stats for " + ds.getDocName());
			for (Entry<String, WordStats> me : ds.getWordStats().entrySet()) {
				ArrayList<Object> row = new ArrayList<Object>();

				row.add(me.getKey());
				row.add(me.getValue().getWordCount());
				row.add(me.getValue().getSentenceCount());
				row.add(me.getValue().getTfidf());
				
				Joiner joiner = Joiner.on(";").skipNulls();
				printStream.println(joiner.join(row));
			}

			printStream.println();
		}
		
		//all pairs bigrams
		//global
		printStream.println("Global all-pair-bigram stats");
		for (Map.Entry<String, Long> me : statsMaker.getFunnyBigramCount().entrySet()) {
			ArrayList<Object> row = new ArrayList<Object>();

			row.add(me.getKey());

			row.add(me.getValue());

			row.add(statsMaker.getSentenceFunnyBigramCount().get(me.getKey()));
			
			row.add(statsMaker.getDocumentFunnyBigramFreq().get(me.getKey()));
			row.add(statsMaker.getDocumentFunnyBigramFreq().get(me.getKey())*1.0/statsMaker.getDocumentCount());
			
			
			row.add((statsMaker.getSentenceFunnyBigramCount().get(me.getKey())) * 1.0
					/ statsMaker.getGlobalSentenceCount());

			String firstWord = me.getKey().split(", ")[0];
			Long firstWordCount = statsMaker.getSentenceCount().get(firstWord);
			row.add(firstWordCount * 1.0
					/ statsMaker.getGlobalSentenceCount());

			String secondWord = me.getKey().split(", ")[1];
			Long secondWordCount = statsMaker.getSentenceCount().get(secondWord);
			row.add(secondWordCount * 1.0
					/ statsMaker.getGlobalSentenceCount());

			Joiner joiner = Joiner.on(";").skipNulls();
			printStream.println(joiner.join(row));
		}


		printStream.println();
		
		for(DocumentStats ds: statsMaker.getDocBigramStats())
		{
			printStream.println("Document-wise all-pair-bigram stats for " + ds.getDocName());
			for (Entry<String, WordStats> me : ds.getWordStats().entrySet()) {
				ArrayList<Object> row = new ArrayList<Object>();

				row.add(me.getKey());
				row.add(me.getValue().getWordCount());
				row.add(me.getValue().getSentenceCount());
				row.add(me.getValue().getTfidf());
				
				Joiner joiner = Joiner.on(";").skipNulls();
				printStream.println(joiner.join(row));
			}

			printStream.println();
		}
		
		//get bigram stats
	}
}
