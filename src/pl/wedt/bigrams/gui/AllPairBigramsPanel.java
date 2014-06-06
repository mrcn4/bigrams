package pl.wedt.bigrams.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import pl.wedt.bigrams.statsmaker.DocumentStats;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.WordStats;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class AllPairBigramsPanel extends JPanel{

	private static Logger log = Logger.getLogger(AllPairBigramsPanel.class);
	
	private final String LEFT_LABEL = "Global bigrams frequency";
	private final String CENTER_LABEL = "Document list (click to see details)";
	private final String RIGHT_LABEL = "Bigrams statistics for document (count,sentenceCount,tfidf)";
	
	private JLabel leftlabel;
	private JList leftlist;
	private List<String> leftListData;
	
	private JLabel centerlabel;
	private JList centerlist;
	private List<String> centerListData;
	
	private JList rightlist;
	private JLabel rightlabel;
	private List<String> rightListData;
	List<DocumentStats> docStats;
	
	public AllPairBigramsPanel() {
		//init variables
		docStats = new ArrayList<DocumentStats>();
		
		//init gui
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		//leftPanel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		add(leftPanel);

		//title label
		leftlabel = new JLabel(LEFT_LABEL);
		leftPanel.add(leftlabel);

		// init list on scroll pane
		leftlist = new JList();
		leftlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String name = (String) leftlist.getSelectedValue();
					log.debug("Selected: \"" + name + "\"");
				}
			}
		});
		JScrollPane leftpane = new JScrollPane();
		leftpane.getViewport().add(leftlist);
		leftpane.setPreferredSize(new Dimension(250, 200));
		leftPanel.add(leftpane);
		//endof leftPanel
		
		//centerpanel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		add(centerPanel);

		centerlabel = new JLabel(CENTER_LABEL);
		centerPanel.add(centerlabel);

		// init list on scroll pane
		centerlist = new JList();
		centerlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String name = (String) centerlist.getSelectedValue();
					log.debug("Selected: \"" + name + "\"");
					
					Map<String, WordStats> rightStats = null;
					for(DocumentStats ds: docStats)
					{
						if(ds.getDocName().equals(name))
						{
							rightStats = ds.getWordStats();
							break;
						}
					}
					
					if(rightStats == null)
					{
						log.error("DocumentStats for " + name + " not found");
						return;
					}
					
					Ordering<Map.Entry<String, WordStats>> byMapValues = new Ordering<Map.Entry<String, WordStats>>() {
					   @Override
					   public int compare(Map.Entry<String, WordStats> left, Map.Entry<String, WordStats> right) {
					        return right.getValue().getWordCount().compareTo(left.getValue().getWordCount());
					   }
					};
					List<Map.Entry<String, WordStats>> listOfWordsSorted = Lists.newArrayList(rightStats.entrySet());
				    Collections.sort(listOfWordsSorted, byMapValues);
					
					List<String> listOfWords = new ArrayList<String>();
					for (Map.Entry<String, WordStats> me : listOfWordsSorted) {
						listOfWords.add(me.getValue().getWordCount() + "/" +
									me.getValue().getSentenceCount() + "/" + 
								    me.getValue().getTfidf() + "   " + me.getKey());
					}
					updateRightList(listOfWords);
				}
			}
		});

		JScrollPane centerpane = new JScrollPane();
		centerpane.getViewport().add(centerlist);
		centerpane.setPreferredSize(new Dimension(250, 200));
		centerPanel.add(centerpane);
		//endof centerpanel

		//right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		add(rightPanel);

		rightlabel = new JLabel(RIGHT_LABEL);
		rightPanel.add(rightlabel);

		// init list on scroll pane
		rightlist = new JList();
		rightlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String name = (String) rightlist.getSelectedValue();
					log.debug("Selected: \"" + name + "\"");
				}
			}
		});

		JScrollPane rightpane = new JScrollPane();
		rightpane.getViewport().add(rightlist);
		rightpane.setPreferredSize(new Dimension(250, 200));
		rightPanel.add(rightpane);
		//endof rightPanel
	}

	private void updateList(List<String> list) {
		this.leftListData = list;
		this.leftlist.setListData(list.toArray());
	}

	private void updateCenterList(List<String> list) {
		this.centerListData = list;
		this.centerlist.setListData(list.toArray());
	}
	
	private void updateRightList(List<String> list) {
		log.debug(list);
		this.rightListData = list;
		this.rightlist.setListData(list.toArray());
	}

	public void updateStats(IStatsMaker statsMaker)
	{
		//save for later
		docStats = statsMaker.getDocFunnyBigramStats();
		
		Map<String, Long> globalCount = statsMaker.getFunnyBigramCount();
		Ordering<Map.Entry<String, Long>> byMapValues = new Ordering<Map.Entry<String, Long>>() {
		   @Override
		   public int compare(Map.Entry<String, Long> left, Map.Entry<String, Long> right) {
		        return right.getValue().compareTo(left.getValue());
		   }
		};
		List<Map.Entry<String, Long>> globalCountSorted = Lists.newArrayList(globalCount.entrySet());
	    Collections.sort(globalCountSorted, byMapValues);
		
	    //update left pane
		List<String> listOfWords = new LinkedList<String>();
		for (Map.Entry<String, Long> me : globalCountSorted) {
			listOfWords.add(me.getValue() + " " + me.getKey());
		}
		updateList(listOfWords);
		
		//update center pane
		List<String> docnames = new LinkedList<String>();
		for(DocumentStats docstat: docStats)
		{
			docnames.add(docstat.getDocName());
		}
		updateCenterList(docnames);
	}
	
	void computingInProgess(boolean enable)
	{
		/*if(enable)
		{
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("Wait while stats are computed...");
			updateList(arrayList);
			updateCenterList(arrayList);
			updateRightList(arrayList);
		}*/
	}
}
