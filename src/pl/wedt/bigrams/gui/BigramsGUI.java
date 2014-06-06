package pl.wedt.bigrams.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.POS;
import pl.wedt.bigrams.statsmaker.DocumentStats;
import pl.wedt.bigrams.statsmaker.DummyStatsMaker;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.PrintStatsMaker;
import pl.wedt.bigrams.statsmaker.WordStats;

public class BigramsGUI extends JFrame {
	private static Logger log = Logger.getLogger(BigramsGUI.class);
	
	//static strings
	private static final String DIALOG_TITLE = "Choose POS";
	private static final String DIALOG_DESCRIPTION = 
			"Choose POS (Part of Speech) you want to include in analisys.\n\n" +
			"Hold CTRL to select multiple items. \n\n" +
			"Tooltips contains help for POS symbols.";
	private static final String MENU_RUN = "Run";
	private static final String MENU_CHOOSE_POS = "Choose POS...";
	private static final String MENU_EXIT = "Exit";
	private static final String TAB_PAIR_BIGRAMS = "All pairs in sentence bigrams";
	private static final String TAB_SEQUENTIONAL_BIGRAMS = "Sequentional bigrams";
	private static final String TAB_WORDS_NAME = "Words";
	private static final String WINDOW_TITLE = "Bigram analyser";
	private static final String STATUS_DEFAULT = "";
	private static final String STATUS_COMPUTATION_STARTS = "Computation has started. Wait for results...";
	private static final String STATUS_COMPUTATION_ENDS = "Computation has ended. You can view results now.";

	//GUI objects
	private WordsPanel wordsPanel;
	private BigramsPanel bigramsPanel;
	private AllPairBigramsPanel allPairBigramsPanel;
	private JLabel statusLabel;
	
	//dependent objects
	private IStatsMaker statsMaker;
	private List<String> choosenPOS = POS.getDefaultPOS();

	
	public BigramsGUI(final IStatsMaker statsMaker) {
		this.statsMaker = statsMaker;
		initUI();
	}
	
	
	/**
	 * Callback after computing ends
	 */
	private void updatePanels()
	{
		wordsPanel.updateStats(statsMaker);
		bigramsPanel.updateStats(statsMaker);
		allPairBigramsPanel.updateStats(statsMaker);
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
				//TODO: revert from vararg in setPosFilter
				Object[] choosenPOSObjectList = choosenPOS.toArray();
				String[] choosenPOSStringArray = Arrays.copyOf(
						choosenPOSObjectList,choosenPOSObjectList.length,String[].class);
				
				statsMaker.setPosFilter(choosenPOSStringArray);
				statsMaker.computeStats();
				updatePanels();
				switchGUIWhileComputing(false);
			}
			
		});
		thread.start();
		
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
	
	private void menuAction(String action)
	{
		switch(action)
		{
			case MENU_RUN:
				computeStats();
				break;
			case MENU_CHOOSE_POS:
			    List<String> selectedPOS = ListDialog.showDialog(
			                                this,
			                                DIALOG_DESCRIPTION,
			                                DIALOG_TITLE,
			                                POS.getPOSMap(),
			                                POS.getDefaultPOS(),
			                                "XXXXXXXX");
			    log.debug("Selected POS" + selectedPOS);
			    if(selectedPOS != null)
			    {
			    	choosenPOS = selectedPOS;
			    }
			    break;
			case MENU_EXIT:
				System.exit(0);
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	private void initUI() {
		
		JMenuBar menuBar = new JMenuBar();
	    JMenu fileMenu = new JMenu("Menu");

        ActionListener menuActionListener = new ActionListener(  ) {
            public void actionPerformed(ActionEvent event) {
               menuAction(event.getActionCommand(  ));
            }
         };
        
	    JMenuItem item = new JMenuItem(MENU_RUN);
	    fileMenu.add(item);
        item.addActionListener(menuActionListener);
        
        item = new JMenuItem(MENU_CHOOSE_POS);
	    fileMenu.add(item);
        item.addActionListener(menuActionListener);
        
        item = new JMenuItem(MENU_EXIT);
	    fileMenu.add(item);
        item.addActionListener(menuActionListener);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        
		JTabbedPane tabbedPane = new JTabbedPane();
		
		wordsPanel = new WordsPanel();
		tabbedPane.addTab(TAB_WORDS_NAME, wordsPanel);
		
		bigramsPanel = new BigramsPanel();
		tabbedPane.addTab(TAB_SEQUENTIONAL_BIGRAMS, bigramsPanel);
		
		allPairBigramsPanel = new AllPairBigramsPanel();
		tabbedPane.addTab(TAB_PAIR_BIGRAMS, allPairBigramsPanel);
		add(tabbedPane);
		
		add(statusBar(), BorderLayout.SOUTH);
		
		pack();
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		//run(new PrintStatsMaker(new DataProvider()));
		run(new DummyStatsMaker());
	}

	private static void run(final IStatsMaker statsMaker) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BigramsGUI ex = new BigramsGUI(statsMaker);
				ex.setVisible(true);
			}
		});
	}
}