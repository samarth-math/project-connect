package GUIObjects;

import globalfunctions.JTextFieldLimit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Color;

import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IPRangeWindow extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField from1;
	private JTextField from2;
	private JTextField from3;
	private JTextField from4;
	private JTextField to1;
	private JTextField to2;
	private JTextField to3;
	private JTextField to4;
	private DefaultListModel<String> jmodel = new DefaultListModel<String>();
	private String f1, f2, f3, f4, t1, t2, t3, t4;
	private static File rangeFile;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				IPRangeWindow frame = new IPRangeWindow();
				frame.setVisible(true);
				
				ArrayList<String> result = IPRangeWindow.getIPRanges();
			}
		});
	}

	public static ArrayList<String> getIPRanges() 
	{
		ArrayList <String>range = new ArrayList<String>();
		if(rangeFile.exists())
		{
			BufferedReader reader = null;
			try 
			{
				reader = new BufferedReader(new FileReader(rangeFile));
				String line = null;
				while ((line = reader.readLine()) != null) 
				{
					range.add(line);
				}	
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
		return range;
	}
	
	public IPRangeWindow() 
	{
		String oldPathString = System.getProperty("user.dir");
		String newPathString = oldPathString+"/rangelist";
		
		File newPath = new File(newPathString);
		newPath.mkdirs();
		rangeFile = new File(newPath,"IPRange");
		
		setResizable(false);
		setTitle("Specify IP Range to detect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(480, 500);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{82,35,35,35,35,16,35,35,35,35,70,30};
		gbl_contentPane.rowHeights = new int[]{40, 85, 30, 80, 80, 80, 15, 40, 50};
		gbl_contentPane.columnWeights = new double[]{1.0};
		gbl_contentPane.rowWeights = new double[]{Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		from1 = new JTextField();
		GridBagConstraints gbc_from1 = new GridBagConstraints();
		gbc_from1.anchor = GridBagConstraints.SOUTH;
		gbc_from1.insets = new Insets(0, 0, 5, 5);
		gbc_from1.fill = GridBagConstraints.HORIZONTAL;
		gbc_from1.gridx = 1;
		gbc_from1.gridy = 1;
		from1.setColumns(10);
		from1.setDocument(new JTextFieldLimit(3));
		contentPane.add(from1, gbc_from1);
		
		from2 = new JTextField();
		GridBagConstraints gbc_from2 = new GridBagConstraints();
		gbc_from2.anchor = GridBagConstraints.SOUTH;
		gbc_from2.insets = new Insets(0, 0, 5, 5);
		gbc_from2.fill = GridBagConstraints.HORIZONTAL;
		gbc_from2.gridx = 2;
		gbc_from2.gridy = 1;
		contentPane.add(from2, gbc_from2);
		from2.setColumns(10);
		from2.setDocument(new JTextFieldLimit(3));
		
		from3 = new JTextField();
		GridBagConstraints gbc_from3 = new GridBagConstraints();
		gbc_from3.anchor = GridBagConstraints.SOUTH;
		gbc_from3.insets = new Insets(0, 0, 5, 5);
		gbc_from3.fill = GridBagConstraints.HORIZONTAL;
		gbc_from3.gridx = 3;
		gbc_from3.gridy = 1;
		contentPane.add(from3, gbc_from3);
		from3.setColumns(10);
		from3.setDocument(new JTextFieldLimit(3));
		
		from4 = new JTextField();
		GridBagConstraints gbc_from4 = new GridBagConstraints();
		gbc_from4.anchor = GridBagConstraints.SOUTH;
		gbc_from4.insets = new Insets(0, 0, 5, 5);
		gbc_from4.fill = GridBagConstraints.HORIZONTAL;
		gbc_from4.gridx = 4;
		gbc_from4.gridy = 1;
		contentPane.add(from4, gbc_from4);
		from4.setColumns(10);
		from4.setDocument(new JTextFieldLimit(3));
		
		to1 = new JTextField();
		GridBagConstraints gbc_to1 = new GridBagConstraints();
		gbc_to1.anchor = GridBagConstraints.SOUTH;
		gbc_to1.insets = new Insets(0, 0, 5, 5);
		gbc_to1.fill = GridBagConstraints.HORIZONTAL;
		gbc_to1.gridx = 6;
		gbc_to1.gridy = 1;
		contentPane.add(to1, gbc_to1);
		to1.setColumns(10);
		to1.setDocument(new JTextFieldLimit(3));
		
		to2 = new JTextField();
		GridBagConstraints gbc_to2 = new GridBagConstraints();
		gbc_to2.anchor = GridBagConstraints.SOUTH;
		gbc_to2.insets = new Insets(0, 0, 5, 5);
		gbc_to2.fill = GridBagConstraints.HORIZONTAL;
		gbc_to2.gridx = 7;
		gbc_to2.gridy = 1;
		contentPane.add(to2, gbc_to2);
		to2.setColumns(10);
		to2.setDocument(new JTextFieldLimit(3));
		
		to3 = new JTextField();
		GridBagConstraints gbc_to3 = new GridBagConstraints();
		gbc_to3.anchor = GridBagConstraints.SOUTH;
		gbc_to3.insets = new Insets(0, 0, 5, 5);
		gbc_to3.fill = GridBagConstraints.HORIZONTAL;
		gbc_to3.gridx = 8;
		gbc_to3.gridy = 1;
		contentPane.add(to3, gbc_to3);
		to3.setColumns(10);
		to3.setDocument(new JTextFieldLimit(3));
		
		to4 = new JTextField();
		GridBagConstraints gbc_to4 = new GridBagConstraints();
		gbc_to4.anchor = GridBagConstraints.SOUTH;
		gbc_to4.insets = new Insets(0, 0, 5, 5);
		gbc_to4.fill = GridBagConstraints.HORIZONTAL;
		gbc_to4.gridx = 9;
		gbc_to4.gridy = 1;
		contentPane.add(to4, gbc_to4);
		to4.setColumns(10);
		to4.setDocument(new JTextFieldLimit(3));
		
		JLabel fromLabel = new JLabel("From");
		GridBagConstraints gbc_fromLabel = new GridBagConstraints();
		gbc_fromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fromLabel.gridx = 3;
		gbc_fromLabel.gridy = 2;
		contentPane.add(fromLabel, gbc_fromLabel);
		
		JLabel toLabel = new JLabel("To");
		GridBagConstraints gbc_toLabel = new GridBagConstraints();
		gbc_toLabel.insets = new Insets(0, 0, 5, 5);
		gbc_toLabel.gridx = 7;
		gbc_toLabel.gridy = 2;
		contentPane.add(toLabel, gbc_toLabel);
		
		GridBagConstraints gbc_rangeList = new GridBagConstraints();
		gbc_rangeList.fill = GridBagConstraints.BOTH;
		gbc_rangeList.gridheight = 4;
		gbc_rangeList.gridwidth = 9;
		gbc_rangeList.insets = new Insets(0, 0, 5, 5);
		gbc_rangeList.gridx = 1;
		gbc_rangeList.gridy = 3;
		
		final JList<String> rangeList = new JList<String>(jmodel);
		rangeList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		rangeList.setSelectedIndex(0);
		rangeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rangeList.setLayoutOrientation(JList.VERTICAL_WRAP);
		rangeList.setBackground(Color.WHITE);
		contentPane.add(rangeList, gbc_rangeList);
		
		//***************************************************************
		// code that gets IP range list from file on the storage on startup
		
		if(rangeFile.exists())
		{
			BufferedReader reader = null;
			String lineparts[];
			try 
			{
				reader = new BufferedReader(new FileReader(rangeFile));
				String line = null;
				while ((line = reader.readLine()) != null) 
				{
					lineparts = line.split("\\|");
					line = lineparts[0]+" | "+lineparts[1];
					jmodel.addElement(line);
				}	
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}				
		//**********************************************************************

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				f1 = from1.getText();
				f2 = from2.getText();
				f3 = from3.getText();
				f4 = from4.getText();
				t1 = to1.getText();
				t2 = to2.getText();
				t3 = to3.getText();
				t4 = to4.getText();
				
				if(t1.isEmpty() || t2.isEmpty() || t3.isEmpty() || t4.isEmpty() || f1.isEmpty() || f2.isEmpty() || f3.isEmpty() || f4.isEmpty())
					;
				else
				{
					jmodel.addElement(""+f1+"."+f2+"."+f3+"."+f4+" | "+t1+"."+t2+"."+t3+"."+t4);
					from1.setText("");
					from2.setText("");
					from3.setText("");
					from4.setText("");
					to1.setText("");
					to2.setText("");
					to3.setText("");
					to4.setText("");
				}
			}
		});
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addButton.insets = new Insets(0, 0, 5, 5);
		gbc_addButton.gridx = 10;
		gbc_addButton.gridy = 3;
		contentPane.add(addButton, gbc_addButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				int index = rangeList.getSelectedIndex();
				if(index>=0)
				{	
					jmodel.remove(index);
				}    
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = 10;
		gbc_deleteButton.gridy = 4;
		contentPane.add(deleteButton, gbc_deleteButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String chosen = rangeList.getSelectedValue();
				int index = rangeList.getSelectedIndex();
				
				if(index>=0)
				{
					jmodel.remove(index);
					
					String IPItems[] = chosen.split("\\|");
				
					String from = IPItems[0].trim();
					String to = IPItems[1].trim();
				
					String fromArray[] = from.split("\\.");
					String toArray[] = to.split("\\.");
				
					from1.setText(fromArray[0]);
					from2.setText(fromArray[1]);
					from3.setText(fromArray[2]);
					from4.setText(fromArray[3]);
					to1.setText(toArray[0]);
					to2.setText(toArray[1]);
					to3.setText(toArray[2]);
					to4.setText(toArray[3]);
					
				}
					
			}
		});
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_editButton.insets = new Insets(0, 0, 5, 5);
		gbc_editButton.gridx = 10;
		gbc_editButton.gridy = 5;
		contentPane.add(editButton, gbc_editButton);
		
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				int size = jmodel.getSize();
				if(size>0)
				{
					String allRanges = "";
					for(int w=0;w<size;w++)
					{
						String tempItem  = jmodel.getElementAt(w);
						
						String tempIPItems[] = tempItem.split("\\|");
						
						String tempfrom = tempIPItems[0].trim();
						String tempto = tempIPItems[1].trim();
						
						tempItem = tempfrom+"|"+tempto;
						allRanges += tempItem+"\n";
					}
					try 
					{	
						rangeFile.createNewFile();
						FileWriter FileWriter = new FileWriter(rangeFile);				
						FileWriter.write(allRanges);
						FileWriter.flush();
						FileWriter.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.gridwidth = 5;
		gbc_applyButton.insets = new Insets(0, 0, 5, 5);
		gbc_applyButton.gridx = 3;
		gbc_applyButton.gridy = 7;
		contentPane.add(applyButton, gbc_applyButton);
	}

}
