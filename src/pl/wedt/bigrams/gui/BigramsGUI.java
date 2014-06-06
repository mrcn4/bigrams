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
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.synth.SynthTextAreaUI;

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
	
	private static final String STATUS_DEFAULT = "";
	private static final String STATUS_COMPUTATION_STARTS = "Computation has started. Wait for results...";
	private static final String STATUS_COMPUTATION_ENDS = "Computation has ended. You can view results now.";
	
	
	private static Logger log = Logger.getLogger(BigramsGUI.class);
	private IStatsMaker statsMaker;

	private WordsPanel wordsPanel;

	private JLabel statusLabel;
	
	
	public BigramsGUI(final IStatsMaker statsMaker) {
		this.statsMaker = statsMaker;
		initUI();
		computeStats();
	}
	
	/**
	 * Makes necesarry changes in GUI while computing
	 * @param isComputing
	 */
	private void switchGUIWhileComputing(boolean isComputing)
	{
		
		wordsPanel.computingInProgess(isComputing);
		if(isComputing)
		{
			setStatus(STATUS_COMPUTATION_STARTS);
		}
		else
		{
			setStatus(STATUS_COMPUTATION_ENDS);
		}
	}
	
	/**
	 * Callback after computing ends
	 */
	private void updatePanels()
	{
		wordsPanel.updateStats(statsMaker);
	}
	
	/**
	 * fixme - synchronize
	 */
	private synchronized void computeStats()
	{
		switchGUIWhileComputing(true);
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				statsMaker.computeStats();
				updatePanels();
				switchGUIWhileComputing(false);
			}
			
		});
		thread.start();
		
	}
	private void setStatus(String status)
	{
		statusLabel.setText(status);
	}
	
	private JPanel statusBar()
	{
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel(STATUS_DEFAULT);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		return statusPanel;
	}
	
	private void initUI() {
		JTabbedPane tabbedPane = new JTabbedPane();
		wordsPanel = new WordsPanel();
		tabbedPane.addTab("Words", wordsPanel);
		add(tabbedPane);
		
		add(statusBar(), BorderLayout.SOUTH);
		
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