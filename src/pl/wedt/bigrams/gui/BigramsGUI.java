package pl.wedt.bigrams.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import pl.wedt.bigrams.dataprovider.DataProvider;
import pl.wedt.bigrams.dataprovider.POS;
import pl.wedt.bigrams.gui.panels.AllPairBigramsPanel;
import pl.wedt.bigrams.gui.panels.BigramsPanel;
import pl.wedt.bigrams.gui.panels.WordsPanel;
import pl.wedt.bigrams.gui.utils.ListDialog;
import pl.wedt.bigrams.statsmaker.BasicFormStatsMaker;
import pl.wedt.bigrams.statsmaker.IStatsMaker;
import pl.wedt.bigrams.statsmaker.PrintStatsMaker;
import pl.wedt.bigrams.statsmaker.RawFormStatsMaker;
import pl.wedt.bigrams.statsmaker.StatsMakerSerializer;

public class BigramsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BigramsGUI.class);

	// static strings
	private static final String DIALOG_TITLE = "Choose POS";
	private static final String DIALOG_DESCRIPTION = "Choose POS (Part of Speech) you want to include in analisys.\n\n"
			+ "Hold CTRL to select multiple items. \n\n"
			+ "Tooltips contains help for POS symbols.";
	private static final String MENU_RUN_RAW = "Run for raw form of word";
	private static final String MENU_RUN_BASIC = "Run for basic form of word";
	private static final String MENU_RUN_RAW_FILE = "Run for raw form of word and save to file...";
	private static final String MENU_RUN_BASIC_FILE = "Run for basic form of word and save to file...";
	private static final String MENU_SAVE = "Save results to file...";
	private static final String MENU_STOP = "Stop";
	private static final String MENU_CHOOSE_POS = "Choose POS...";
	private static final String MENU_EXIT = "Exit";
	private static final String TAB_PAIR_BIGRAMS = "All pairs in sentence bigrams";
	private static final String TAB_SEQUENTIONAL_BIGRAMS = "Sequentional bigrams";
	private static final String TAB_WORDS_NAME = "Words";
	private static final String WINDOW_TITLE = "Bigram analyser";
	private static final String STATUS_DEFAULT = "";
	private static final String STATUS_COMPUTATION_STARTS = "Computation has started. Wait for results...";
	private static final String STATUS_COMPUTATION_ENDS = "Computation has ended. You can view results now.";
	private static final Integer UPDATE_STATUS_EVERY = 2000;
	
	// GUI objects
	private WordsPanel wordsPanel;
	private BigramsPanel bigramsPanel;
	private AllPairBigramsPanel allPairBigramsPanel;
	private JLabel statusLabel;
	private JMenuItem menuRaw;
	private JMenuItem menuBasic;
	private JMenuItem menuStop;

	// dependent objects
	private IStatsMaker statsMaker;
	private List<String> choosenPOS = POS.getDefaultPOS();
	ScheduledExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();
	private boolean saveToFile=false;
	private String filepath = "";
	private String configPath= "config.properties";

	private JMenuItem menuRawF;

	private JMenuItem menuBasicF;	

	public BigramsGUI(final IStatsMaker statsMaker) {
		this.statsMaker = statsMaker;
		initUI();
	}

	/**
	 * Callback after computing ends
	 */
	private void updatePanels() {
		wordsPanel.updateStats(statsMaker);
		bigramsPanel.updateStats(statsMaker);
		allPairBigramsPanel.updateStats(statsMaker);
	}

	private synchronized void computeStats() {
		switchGUIWhileComputing(true);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Object[] choosenPOSObjectList = choosenPOS.toArray();
				String[] choosenPOSStringArray = Arrays.copyOf(
						choosenPOSObjectList, choosenPOSObjectList.length,
						String[].class);
				log.debug("choosenPOSStringArray "
						+ Arrays.toString(choosenPOSStringArray));
				statsMaker.setPosFilter(choosenPOSStringArray);
				statsMaker.setStopFlag(false);
				statsMaker.computeStats();
				exec.shutdown();
				try {
					exec.awaitTermination(UPDATE_STATUS_EVERY,
							TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!saveToFile)
				{
					setStatus("Updating UI...");
					updatePanels();
				}
				else
				{
					setStatus("Writing results to " + filepath + "...");
					StatsMakerSerializer statsMakerSerializer = new StatsMakerSerializer();
					try {
						statsMakerSerializer.writeToOutput(new FileOutputStream(filepath), statsMaker);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, "Unable to open file to read: " + filepath);
					}
				}
				switchGUIWhileComputing(false);
				if (statsMaker.isStopFlag()) {
					setStatus("Computation stopped! Analysed "
							+ statsMaker.getDocumentsCompleted() + " of "
							+ statsMaker.getDocumentCount() + " documents.");
				}

			}

		});
		thread.start();
		exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				updateProgress();
			}
		}, 0, UPDATE_STATUS_EVERY, TimeUnit.MILLISECONDS);
	}

	protected void updateProgress() {
		setStatus("Progress: " + statsMaker.getDocumentsCompleted() * 100.0
				/ statsMaker.getDocumentCount() + "%");
	}

	/**
	 * Makes necesarry changes in GUI while computing
	 * 
	 * @param isComputing
	 */
	private void switchGUIWhileComputing(boolean isComputing) {

		wordsPanel.computingInProgess(isComputing);
		if (isComputing) {
			setStatus(STATUS_COMPUTATION_STARTS);
			menuRaw.setEnabled(false);
			menuBasic.setEnabled(false);
			menuRawF.setEnabled(false);
			menuBasicF.setEnabled(false);
			menuStop.setEnabled(true);
		} else {
			setStatus(STATUS_COMPUTATION_ENDS);
			menuRaw.setEnabled(true);
			menuBasic.setEnabled(true);
			menuRawF.setEnabled(true);
			menuBasicF.setEnabled(true);
			menuStop.setEnabled(false);
		}
	}

	private void setStatus(String status) {
		statusLabel.setText(status);
		log.debug("Status set to: " + status);
	}

	private JPanel statusBar() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel(STATUS_DEFAULT);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		return statusPanel;
	}

	private void menuAction(String action) {
		switch (action) {
		case MENU_RUN_RAW:
			saveToFile = false;
			statsMaker = new RawFormStatsMaker(new DataProvider(configPath));
			computeStats();
			break;
		case MENU_RUN_BASIC:
			saveToFile = false;
			statsMaker = new BasicFormStatsMaker(new DataProvider(configPath));
			computeStats();
			break;
		case MENU_STOP:
			computeStop();
			break;
		case MENU_RUN_BASIC_FILE:
			saveToFile = true;
			FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.SAVE);
			fd.setVisible(true);
			String filename = fd.getDirectory()+File.separator+fd.getFile();
			if (filename == null)
			{
				log.debug("Cancelled the choice");
			}
			else
			{
				this.filepath = filename;
				statsMaker = new BasicFormStatsMaker(new DataProvider(configPath));
				computeStats();
			}
			
			break;
		case MENU_RUN_RAW_FILE:
			saveToFile = true;
			FileDialog fdr = new FileDialog(this, "Choose a file", FileDialog.SAVE);
			fdr.setVisible(true);
			String filenamer = fdr.getDirectory()+File.separator+fdr.getFile();
			if (filenamer == null)
			{
				log.debug("Cancelled the choice");
			}
			else
			{
				this.filepath = filenamer;
				statsMaker = new BasicFormStatsMaker(new DataProvider(configPath));
				computeStats();
			}
			
			break;
		case MENU_SAVE:
			throw new UnsupportedOperationException();
		case MENU_CHOOSE_POS:
			List<String> selectedPOS = ListDialog.showDialog(this,
					DIALOG_DESCRIPTION, DIALOG_TITLE, POS.getPOSMap(),
					choosenPOS, "XXXXXXXX");
			log.debug("Selected POS " + selectedPOS);
			if (selectedPOS != null) {
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

	private void computeStop() {
		statsMaker.setStopFlag(true);
	}

	private void initUI() {

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Menu");

		ActionListener menuActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				menuAction(event.getActionCommand());
			}
		};

		JMenuItem item;

		menuRaw = new JMenuItem(MENU_RUN_RAW);
		fileMenu.add(menuRaw);
		menuRaw.addActionListener(menuActionListener);

		menuBasic = new JMenuItem(MENU_RUN_BASIC);
		fileMenu.add(menuBasic);
		menuBasic.addActionListener(menuActionListener);

		menuRawF = new JMenuItem(MENU_RUN_RAW_FILE);
		fileMenu.add(menuRawF);
		menuRawF.addActionListener(menuActionListener);
		
		menuBasicF = new JMenuItem(MENU_RUN_BASIC_FILE);
		fileMenu.add(menuBasicF);
		menuBasicF.addActionListener(menuActionListener);
		
		
		menuStop = new JMenuItem(MENU_STOP);
		fileMenu.add(menuStop);
		menuStop.addActionListener(menuActionListener);
		menuStop.setEnabled(false);
		
		item = new JMenuItem(MENU_SAVE);
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
		run(new PrintStatsMaker(new DataProvider("config.properties")));
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
