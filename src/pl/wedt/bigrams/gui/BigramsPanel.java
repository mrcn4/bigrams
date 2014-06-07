package pl.wedt.bigrams.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

import pl.wedt.bigrams.statsmaker.DocumentStats;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.WordStats;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class BigramsPanel extends JPanel {

	private static Logger log = Logger.getLogger(BigramsPanel.class);

	private final String LEFT_LABEL = "Global bigrams frequency";
	private final String CENTER_LABEL = "Document list (click to see details)";
	private final String RIGHT_LABEL = "Bigrams statistics for document";

	private final static List<String> COLUMN_NAMES_LEFT = new ArrayList<>();
	private final static List<String> COLUMN_NAMES_RIGHT = new ArrayList<>();
	static {
		// FIXME
		COLUMN_NAMES_LEFT.add("Bigram");
		COLUMN_NAMES_LEFT.add("Count");
		COLUMN_NAMES_LEFT.add("In Sentences");
		COLUMN_NAMES_LEFT.add("Document count");
		COLUMN_NAMES_LEFT.add("Document percent");
		COLUMN_NAMES_LEFT.add("PB");
		COLUMN_NAMES_LEFT.add("P1");
		COLUMN_NAMES_LEFT.add("P2");
		
		COLUMN_NAMES_RIGHT.add("Bigram");
		COLUMN_NAMES_RIGHT.add("Count");
		COLUMN_NAMES_RIGHT.add("Sentence");
		COLUMN_NAMES_RIGHT.add("TF-IDF");
	}

	private JLabel leftlabel;
	private SortingTable leftlist;
	
	private JLabel centerlabel;
	private JList centerlist;
	
	private JLabel rightlabel;
	private SortingTable rightlist;
	
	List<DocumentStats> docStats;

	public BigramsPanel() {
		// init variables
		docStats = new ArrayList<DocumentStats>();

		// init gui
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// leftPanel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		add(leftPanel);

		// title label
		leftlabel = new JLabel(LEFT_LABEL);
		leftPanel.add(leftlabel);

		// init list on scroll pane
		leftlist = new SortingTable();
		leftPanel.add(leftlist);
		
		// endof leftPanel

		// centerpanel
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
					updateRightListForDocumentName(name);
				}
			}
		});

		JScrollPane centerpane = new JScrollPane();
		centerpane.getViewport().add(centerlist);
		centerPanel.add(centerpane);
		// endof centerpanel

		// right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		add(rightPanel);

		rightlabel = new JLabel(RIGHT_LABEL);
		rightPanel.add(rightlabel);

		// init list on scroll pane

		rightlist = new SortingTable();
		rightPanel.add(rightlist);
		
		// endof rightPanel
	}

	private void updateRightListForDocumentName(String name) {
		Map<String, WordStats> rightStats = null;
		for (DocumentStats ds : docStats) {
			if (ds.getDocName().equals(name)) {
				rightStats = ds.getWordStats();
				break;
			}
		}

		if (rightStats == null) {
			log.error("DocumentStats for " + name + " not found");
		}
		
		List<List<Object>> data = new ArrayList<List<Object>>();

		for (Entry<String, WordStats> me : rightStats.entrySet()) {
			ArrayList<Object> row = new ArrayList<Object>();

			row.add(me.getKey());
			row.add(me.getValue().getWordCount());
			row.add(me.getValue().getSentenceCount());
			row.add(me.getValue().getTfidf());
			data.add(row);
		}
		updateRightList(data);
	}

	private void updateLeftTable(final List<List<Object>> data) {
		EventQueue.invokeLater(new Runnable() { 
        public void run() { 
			leftlist.getModel().setNewData(COLUMN_NAMES_LEFT, data);
        }});
	
	}

	private void updateCenterList(final List<String> list) {
		EventQueue.invokeLater(new Runnable() { 
	    public void run() { 
	        	centerlist.setListData(list.toArray());
        }});
	}

	private void updateRightList(final List<List<Object>> data) {
		EventQueue.invokeLater(new Runnable() { 
		    public void run() {
		    	rightlist.getModel().setNewData(COLUMN_NAMES_RIGHT, data);
		    }});
	}


	public void updateStats(IStatsMaker statsMaker) {
		log.debug("Updating stats " + statsMaker.getBigramCount().size());
		// save for later
		docStats = statsMaker.getDocBigramStats();

		Map<String, Long> globalCount = statsMaker.getBigramCount();

		// update left pane
		List<List<Object>> data = new ArrayList<List<Object>>();

		for (Map.Entry<String, Long> me : globalCount.entrySet()) {
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

			data.add(row);
		}
		updateLeftTable(data);

		// update center pane
		List<String> docnames = new LinkedList<String>();
		for (DocumentStats docstat : docStats) {
			docnames.add(docstat.getDocName());
		}
		updateCenterList(docnames);
	}

	void computingInProgess(boolean enable) {
	}
}
