package pl.wedt.bigrams.dataprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POS {
	
	private static Map<String,String> map = new HashMap<String,String>();

	private static final String[] defaultEnabledPOSs= {
		"FW", "JJ", "JJR", "JJS", "MD","NN","NNP","NNPS","NNS",
		"PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM",
		"TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP",
		"WP$","WRB"};
	
	static
	{
		map.put("$","dollar - $ -$ --$ A$ C$ HK$ M$ NZ$ S$ U.S.$ US$");
		map.put("``","opening quotation mark - ` ``");
		map.put("''","closing quotation mark - ' ''");
		map.put("(","opening parenthesis - ( [ {");
		map.put(")","closing parenthesis - ) ] }");
		map.put(",","comma - ,");
		map.put("--","dash - --");
		map.put(".","sentence terminator - . ! ?");
		map.put(":","colon or ellipsis - : ; ...");
		map.put("CC","conjunction, coordinating - & 'n and both but either et for less minus neither nor or plus so therefore times v. versus vs. whether yet");
		map.put("CD","numeral, cardinal - mid-1890 nine-thirty forty-two one-tenth ten million 0.5 one forty-seven 1987 twenty '79 zero two 78-degrees eighty-four IX '60s .025 fifteen 271,124 dozen quintillion DM2,000 ...");
		map.put("DT","determiner - all an another any both del each either every half la many much nary neither no some such that the them these this those");
		map.put("EX","existential there - there");
		map.put("FW","foreign word - gemeinschaft hund ich jeux habeas Haementeria Herr K'ang-si vous lutihaw alai je jour objets salutaris fille quibusdam pas trop Monte terram fiche oui corporis ...");
		map.put("IN","preposition or conjunction, subordinating - astride among uppon whether out inside pro despite on by throughout below within for towards near behind atop around if like until below next into if beside ...");
		map.put("JJ","adjective or numeral, ordinal - third ill-mannered pre-war regrettable oiled calamitous first separable ectoplasmic battery-powered participatory fourth still-to-be-named multilingual multi-disciplinary ...");
		map.put("JJR","adjective, comparative - bleaker braver breezier briefer brighter brisker broader bumper busier calmer cheaper choosier cleaner clearer closer colder commoner costlier cozier creamier crunchier cuter ...");
		map.put("JJS","adjective, superlative - calmest cheapest choicest classiest cleanest clearest closest commonest corniest costliest crassest creepiest crudest cutest darkest deadliest dearest deepest densest dinkiest ...");
		map.put("LS","list item marker - A A. B B. C C. D E F First G H I J K One SP-44001 SP-44002 SP-44005 SP-44007 Second Third Three Two \\* a b c d first five four one six three two");
		map.put("MD","modal auxiliary - can cannot could couldn't dare may might must need ought shall should shouldn't will would");
		map.put("NN","noun, common, singular or mass - common-carrier cabbage knuckle-duster Casino afghan shed thermostat investment slide humour falloff slick wind hyena override subhumanity machinist ...");
		map.put("NNP","noun, proper, singular - Motown Venneboerger Czestochwa Ranzer Conchita Trumplane Christos Oceanside Escobar Kreisler Sawyer Cougar Yvette Ervin ODI Darryl CTCA Shannon A.K.C. Meltex Liverpool ...");
		map.put("NNPS","noun, proper, plural - Americans Americas Amharas Amityvilles Amusements Anarcho-Syndicalists Andalusians Andes Andruses Angels Animals Anthony Antilles Antiques Apache Apaches Apocrypha ...");
		map.put("NNS","noun, common, plural - undergraduates scotches bric-a-brac products bodyguards facets coasts divestitures storehouses designs clubs fragrances averages subjectivists apprehensions muses factory-jobs ...");
		map.put("PDT","pre-determiner - all both half many quite such sure this");
		map.put("POS","genitive marker - ' 's");
		map.put("PRP","pronoun, personal - hers herself him himself hisself it itself me myself one oneself ours ourselves ownself self she thee theirs them themselves they thou thy us");
		map.put("PRP$","pronoun, possessive - her his mine my our ours their thy your");
		map.put("RB","adverb - occasionally unabatingly maddeningly adventurously professedly stirringly prominently technologically magisterially predominately swiftly fiscally pitilessly ...");
		map.put("RBR","adverb, comparative - further gloomier grander graver greater grimmer harder harsher healthier heavier higher however larger later leaner lengthier less-perfectly lesser lonelier longer louder lower more ...");
		map.put("RBS","adverb, superlative - best biggest bluntest earliest farthest first furthest hardest heartiest highest largest least less most nearest second tightest worst");
		map.put("RP","particle - aboard about across along apart around aside at away back before behind by crop down ever fast for forth from go high i.e. in into just later low more off on open out over per pie raising start teeth that through under unto up up-pp upon whole with you");
		map.put("SYM","symbol - % & ' '' ''. ) ). * + ,. < = > @ A[fj] U.S U.S.S.R \\* \\*\\* \\*\\*\\*");
		map.put("TO","\"to\" - as preposition or infinitive marker - to");
		map.put("UH","interjection - Goodbye Goody Gosh Wow Jeepers Jee-sus Hubba Hey Kee-reist Oops amen huh howdy uh dammit whammo shucks heck anyways whodunnit honey golly man baby diddle hush sonuvabitch ...");
		map.put("VB","verb, base form - ask assemble assess assign assume atone attention avoid bake balkanize bank begin behold believe bend benefit bevel beware bless boil bomb boost brace break bring broil brush build ...");
		map.put("VBD","verb, past tense - dipped pleaded swiped regummed soaked tidied convened halted registered cushioned exacted snubbed strode aimed adopted belied figgered speculated wore appreciated contemplated ...");
		map.put("VBG","verb, present participle or gerund - telegraphing stirring focusing angering judging stalling lactating hankerin' alleging veering capping approaching traveling besieging encrypting interrupting erasing wincing ...");
		map.put("VBN","verb, past participle - multihulled dilapidated aerosolized chaired languished panelized used experimented flourished imitated reunifed factored condensed sheared unsettled primed dubbed desired ...");
		map.put("VBP","verb, present tense, not 3rd person singular - predominate wrap resort sue twist spill cure lengthen brush terminate appear tend stray glisten obtain comprise detest tease attract emphasize mold postpone sever return wag ...");
		map.put("VBZ","verb, present tense, 3rd person singular - bases reconstructs marks mixes displeases seals carps weaves snatches slumps stretches authorizes smolders pictures emerges stockpiles seduces fizzes uses bolsters slaps speaks pleads ...");
		map.put("WDT","WH-determiner - that what whatever which whichever");
		map.put("WP","WH-pronoun - that what whatever whatsoever which who whom whosoever");
		map.put("WP$","WH-pronoun, possessive - whose");
		map.put("WRB","Wh-adverb - how however whence whenever where whereby whereever wherein whereof why");

	}
	
	public static Map<String, String> getPOSMap()
	{
		return map;
	}
	
	
	public static List<String> getDefaultPOS()
	{
		return Arrays.asList(defaultEnabledPOSs);
	}
}
