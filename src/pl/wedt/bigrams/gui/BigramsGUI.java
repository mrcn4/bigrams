package pl.wedt.bigrams.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.statsmaker.DocumentStats;
import pl.wedt.bigrams.statsmaker.DummyStatsMaker;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.PrintStatsMaker;
import pl.wedt.bigrams.statsmaker.WordStats;

public class BigramsGUI extends JFrame {

	private static final String WINDOW_TITLE = "Bigram analyser";
	private final String LEFT_LABEL = "Global words frequency";
	private final String CENTER_LABEL = "Document list (click to see details)";
	private final String RIGHT_LABEL = "Word statistics for document (count,sentenceCount,tfidf)";
	
	private JLabel leftlabel;
	private JList leftlist;
	private List<String> leftListData;
	
	private JLabel centerlabel;
	private JList centerlist;
	private List<String> centerListData;
	private JList rightlist;
	private JLabel rightlabel;
	private List<String> rightListData;

	private static Logger log = Logger.getLogger(BigramsGUI.class);
	private IStatsMaker statsMaker;
	
	
	public BigramsGUI(final IStatsMaker statsMaker) {
		this.statsMaker = statsMaker;
		initUI();
		computeStats();
	}
	
	private void switchGUIAvailabilityWhileComputing(boolean enable)
	{
		
	}
	
	/**
	 * fixme - synchronize
	 */
	private synchronized void computeStats()
	{
		switchGUIAvailabilityWhileComputing(false);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				//wait for it
				
				
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add("Wait while stats are computed...");
				updateList(arrayList);
				updateCenterList(arrayList);
				updateRightList(arrayList);
				
				statsMaker.computeStats();
				
				ArrayList<String> arrayListEmpty = new ArrayList<String>();
				updateList(arrayListEmpty);
				updateCenterList(arrayListEmpty);
				updateRightList(arrayListEmpty);
				
				updateStats();
				
				switchGUIAvailabilityWhileComputing(true);
			}
			
		});
		thread.start();
		
	}
	
	private void updateStats()
	{
		Map<String, Long> globalCount = statsMaker.getGlobalCount();
		//sort this shit
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
		List<DocumentStats> docStats = statsMaker.getDocStats();
		List<String> docnames = new LinkedList<String>();
		for(DocumentStats docstat: docStats)
		{
			docnames.add(docstat.getDocName());
		}
		updateCenterList(docnames);
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
	private JPanel wordsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		//leftPanel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		panel.add(leftPanel);

		//onleftpanel
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
		panel.add(centerPanel);

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
					
					List<DocumentStats> docStats = statsMaker.getDocStats();
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
					
					//sort this shit
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
		panel.add(rightPanel);

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
		//endof rightpanel
		
		return panel;
	}
	
	
	private void initUI() {

		add(wordsPanel());
		
		pack();
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BigramsGUI ex = new BigramsGUI(new PrintStatsMaker(new DataProvider()));
				ex.setVisible(true);
			}
		});
	}
}