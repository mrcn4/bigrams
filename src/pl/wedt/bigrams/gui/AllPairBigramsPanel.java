package pl.wedt.bigrams.gui;

import java.awt.Dimension;
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

public class AllPairBigramsPanel extends JPanel {

	private static Logger log = Logger.getLogger(AllPairBigramsPanel.class);

	private final String LEFT_LABEL = "Global bigrams frequency";
	private final String CENTER_LABEL = "Document list (click to see details)";
	private final String RIGHT_LABEL = "Bigrams statistics for document";
	
	private final static List<String> COLUMN_NAMES_LEFT = new ArrayList<>();
	private final static List<String> COLUMN_NAMES_RIGHT = new ArrayList<>();
	static {
		// FIXME
		COLUMN_NAMES_LEFT.add("Bigram");
		COLUMN_NAMES_LEFT.add("Count");
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
	private List<String> leftListData;

	private JLabel centerlabel;
	private JList centerlist;
	private List<String> centerListData;

	private SortingTable rightlist;
	private JLabel rightlabel;
	private List<String> rightListData;
	List<DocumentStats> docStats;

	public AllPairBigramsPanel() {
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
			return;
		}
		List<List<String>> data = new ArrayList<List<String>>();

		for (Entry<String, WordStats> me : rightStats.entrySet()) {
			ArrayList<String> row = new ArrayList<String>();

			row.add(me.getKey());
			row.add(me.getValue().getWordCount().toString());
			row.add(me.getValue().getSentenceCount().toString());
			row.add(me.getValue().getTfidf().toString());
			data.add(row);
		}
		updateRightList(data);
	}

	private void updateLeftTable(List<List<String>> data) {
		this.leftlist.getModel().setNewData(COLUMN_NAMES_LEFT, data);
		this.leftlist.packMeNow();
		
	}

	private void updateCenterList(List<String> list) {
		this.centerListData = list;
		this.centerlist.setListData(list.toArray());
	}

	private void updateRightList(List<List<String>> data) {
		this.rightlist.getModel().setNewData(COLUMN_NAMES_RIGHT, data);
	}

	public void updateStats(IStatsMaker statsMaker) {
		// save for later
		docStats = statsMaker.getDocFunnyBigramStats();

		Map<String, Long> globalCount = statsMaker.getFunnyBigramCount();

		// update left pane
		List<List<String>> data = new ArrayList<List<String>>();

		for (Map.Entry<String, Long> me : globalCount.entrySet()) {
			ArrayList<String> row = new ArrayList<String>();

			row.add(me.getKey());

			row.add(me.getValue().toString());

			String P_Bigram = String.format("%.4f", (me.getValue()) * 1.0
					/ statsMaker.getGlobalFunnyBigramCount());
			row.add(P_Bigram);

			String firstWord = me.getKey().split(", ")[0];
			Long firstWordCount = statsMaker.getGlobalCount().get(firstWord);
			String P_s1 = String.format("%.4f", firstWordCount * 1.0
					/ statsMaker.getGlobalWordCount());
			row.add(P_s1);

			String secondWord = me.getKey().split(", ")[0];
			Long secondWordCount = statsMaker.getGlobalCount().get(secondWord);
			String P_s2 = String.format("%.4f", secondWordCount * 1.0
					/ statsMaker.getGlobalWordCount());
			row.add(P_s2);

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
