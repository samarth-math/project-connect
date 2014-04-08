package GUIObjects;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

public class IPRangeWindow extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField from3;
	private JTextField from4;
	private JTextField to1;
	private JTextField to2;
	private JTextField to3;
	private JTextField to4;
	private final JList rangeList = new JList();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPRangeWindow frame = new IPRangeWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public IPRangeWindow() {
		setTitle("Specify IP Range");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(480, 500);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{82,35,35,35,35,16,35,35,35,35,70,30};
		gbl_contentPane.rowHeights = new int[]{40, 85, 30, 55, 80, 80, 40, 40, 50};
		gbl_contentPane.columnWeights = new double[]{1.0};
		gbl_contentPane.rowWeights = new double[]{Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		textField.setColumns(10);
		
		JTextField from1 = new JTextField();
		GridBagConstraints gbc_from1 = new GridBagConstraints();
		gbc_from1.anchor = GridBagConstraints.SOUTH;
		gbc_from1.insets = new Insets(0, 0, 5, 5);
		gbc_from1.fill = GridBagConstraints.HORIZONTAL;
		gbc_from1.gridx = 1;
		gbc_from1.gridy = 1;
		contentPane.add(from1, gbc_from1);
		
		JTextField from2 = new JTextField();
		GridBagConstraints gbc_from2 = new GridBagConstraints();
		gbc_from2.anchor = GridBagConstraints.SOUTH;
		gbc_from2.insets = new Insets(0, 0, 5, 5);
		gbc_from2.fill = GridBagConstraints.HORIZONTAL;
		gbc_from2.gridx = 2;
		gbc_from2.gridy = 1;
		contentPane.add(from2, gbc_from2);
		textField_1.setColumns(10);
		
		from3 = new JTextField();
		GridBagConstraints gbc_from3 = new GridBagConstraints();
		gbc_from3.anchor = GridBagConstraints.SOUTH;
		gbc_from3.insets = new Insets(0, 0, 5, 5);
		gbc_from3.fill = GridBagConstraints.HORIZONTAL;
		gbc_from3.gridx = 3;
		gbc_from3.gridy = 1;
		contentPane.add(from3, gbc_from3);
		from3.setColumns(10);
		
		from4 = new JTextField();
		GridBagConstraints gbc_from4 = new GridBagConstraints();
		gbc_from4.anchor = GridBagConstraints.SOUTH;
		gbc_from4.insets = new Insets(0, 0, 5, 5);
		gbc_from4.fill = GridBagConstraints.HORIZONTAL;
		gbc_from4.gridx = 4;
		gbc_from4.gridy = 1;
		contentPane.add(from4, gbc_from4);
		from4.setColumns(10);
		
		to1 = new JTextField();
		GridBagConstraints gbc_to1 = new GridBagConstraints();
		gbc_to1.anchor = GridBagConstraints.SOUTH;
		gbc_to1.insets = new Insets(0, 0, 5, 5);
		gbc_to1.fill = GridBagConstraints.HORIZONTAL;
		gbc_to1.gridx = 6;
		gbc_to1.gridy = 1;
		contentPane.add(to1, gbc_to1);
		to1.setColumns(10);
		
		to2 = new JTextField();
		GridBagConstraints gbc_to2 = new GridBagConstraints();
		gbc_to2.anchor = GridBagConstraints.SOUTH;
		gbc_to2.insets = new Insets(0, 0, 5, 5);
		gbc_to2.fill = GridBagConstraints.HORIZONTAL;
		gbc_to2.gridx = 7;
		gbc_to2.gridy = 1;
		contentPane.add(to2, gbc_to2);
		to2.setColumns(10);
		
		to3 = new JTextField();
		GridBagConstraints gbc_to3 = new GridBagConstraints();
		gbc_to3.anchor = GridBagConstraints.SOUTH;
		gbc_to3.insets = new Insets(0, 0, 5, 5);
		gbc_to3.fill = GridBagConstraints.HORIZONTAL;
		gbc_to3.gridx = 8;
		gbc_to3.gridy = 1;
		contentPane.add(to3, gbc_to3);
		to3.setColumns(10);
		
		to4 = new JTextField();
		GridBagConstraints gbc_to4 = new GridBagConstraints();
		gbc_to4.anchor = GridBagConstraints.SOUTH;
		gbc_to4.insets = new Insets(0, 0, 5, 5);
		gbc_to4.fill = GridBagConstraints.HORIZONTAL;
		gbc_to4.gridx = 9;
		gbc_to4.gridy = 1;
		contentPane.add(to4, gbc_to4);
		to4.setColumns(10);
		
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
		rangeList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		rangeList.setSelectedIndex(0);
		rangeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rangeList.setBackground(Color.WHITE);
		contentPane.add(rangeList, gbc_rangeList);
		
		JButton addButton = new JButton("Add");
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 5, 5);
		gbc_addButton.gridx = 10;
		gbc_addButton.gridy = 3;
		contentPane.add(addButton, gbc_addButton);
		
		JButton deleteButton = new JButton("Delete");
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = 10;
		gbc_deleteButton.gridy = 4;
		contentPane.add(deleteButton, gbc_deleteButton);
		
		JButton editButton = new JButton("Edit");
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.insets = new Insets(0, 0, 5, 5);
		gbc_editButton.gridx = 10;
		gbc_editButton.gridy = 5;
		contentPane.add(editButton, gbc_editButton);
		
		JButton applyButton = new JButton("Apply");
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.gridwidth = 5;
		gbc_applyButton.insets = new Insets(0, 0, 5, 5);
		gbc_applyButton.gridx = 3;
		gbc_applyButton.gridy = 7;
		contentPane.add(applyButton, gbc_applyButton);
	}

}
