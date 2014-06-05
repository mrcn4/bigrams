package pl.wedt.bigrams;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer {

	protected static StanfordCoreNLP pipeline;

	static {
		// Create StanfordCoreNLP object properties, with POS tagging
		// (required for lemmatization), and lemmatization
		Properties props;
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");

		//RedwoodConfiguration.empty().capture(System.err).apply();
		
		// StanfordCoreNLP loads a lot of models, so you probably
		// only want to do this once per execution
		pipeline = new StanfordCoreNLP(props);
		
		//RedwoodConfiguration.current().clear().apply();
	}
	
	public static List<CoreMap> getSentences(String documentText)
	{
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(documentText);

		// run all Annotators on this text
		pipeline.annotate(document);

		// Iterate over all of the sentences found
		return document.get(SentencesAnnotation.class);
	}

	public static List<CoreLabel> getWords(CoreMap sentence)
	{
		return sentence.get(TokensAnnotation.class);
	}
	
	public static List<String> lemmatize(String documentText) {
		List<String> lemmas = new LinkedList<String>();

		List<CoreMap> sentences = getSentences(documentText);
		
		for (CoreMap sentence : sentences) {
			
			System.out.println("Sentence: " + sentence.toString());
			
			// Iterate over all tokens in a sentence
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// Retrieve and add the lemma for each word into the
				// list of lemmas
				System.out.println("Token: " + token.toString());
				System.out.println("Lemma: " + token.get(LemmaAnnotation.class));
				System.out.println("POS: " + token.get(PartOfSpeechAnnotation.class));
			}
		}

		return lemmas;
	}
}