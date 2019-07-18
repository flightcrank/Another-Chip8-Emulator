
import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Debugger extends JComponent {

	private int w, h;			//Width and height of Screen JComponent
	private JTextField textFieldv0;
	private JTextField textFieldv4;
	private JTextField textFieldv8;
	private JTextField textFieldvc;
	private JTextField textFieldv1;
	private JTextField textFieldv5;
	private JTextField textFieldv9;
	private JTextField textFieldvd;
	private JTextField textFieldv2;
	private JTextField textFieldv6;
	private JTextField textFieldva;
	private JTextField textFieldve;
	private JList list1;
	private JTextField textFieldi;
	private JTextField textFieldpc;
	private JButton startButton;
	private JButton pauseButton;
	private JButton stepButton;
	private JTextField textFieldv3;
	private JTextField textFieldv7;
	private JTextField textFieldvb;
	private JTextField textFieldvf;
	private JButton resetButton;
	private JTextField textFieldop;
	private JTextField textFieldsp;
	private JComboBox comboBox2;
	private JTextField textFieldtmr;
	private DefaultListModel lm;
	private JTextField textFieldsnd;
	private Chip8 c8;
	
	public Debugger(Chip8 c8) {
		
		this.c8 = c8;
		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout(0, 0));
		this.add(panel2, BorderLayout.EAST);
		panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "System Memory", TitledBorder.LEFT, TitledBorder.TOP));
		final JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setVerticalScrollBarPolicy(22);
		panel2.add(scrollPane1, BorderLayout.CENTER);
		scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
		list1 = new JList();
		list1.setFixedCellWidth(100);
		list1.setVisibleRowCount(8);
		scrollPane1.setViewportView(list1);
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout(0, 0));
		this.add(panel3, BorderLayout.CENTER);
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new GridBagLayout());
		panel3.add(panel4, BorderLayout.NORTH);
		panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Debugger Output", TitledBorder.LEFT, TitledBorder.TOP));
		final JLabel label1 = new JLabel();
		label1.setText("V0");
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		panel4.add(label1, gbc);
		final JLabel label2 = new JLabel();
		label2.setText("V4");
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		panel4.add(label2, gbc);
		final JLabel label3 = new JLabel();
		label3.setText("V8");
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		panel4.add(label3, gbc);
		final JLabel label4 = new JLabel();
		label4.setText("VC");
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		panel4.add(label4, gbc);
		final JLabel label5 = new JLabel();
		label5.setText("V1");
		gbc = new GridBagConstraints();
		gbc.gridx = 7;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label5, gbc);
		final JLabel label6 = new JLabel();
		label6.setText("V5");
		gbc = new GridBagConstraints();
		gbc.gridx = 7;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label6, gbc);
		final JLabel label7 = new JLabel();
		label7.setText("V9");
		gbc = new GridBagConstraints();
		gbc.gridx = 7;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label7, gbc);
		final JLabel label8 = new JLabel();
		label8.setText("VD");
		gbc = new GridBagConstraints();
		gbc.gridx = 7;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label8, gbc);
		final JLabel label9 = new JLabel();
		label9.setText("V2");
		gbc = new GridBagConstraints();
		gbc.gridx = 9;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label9, gbc);
		final JLabel label10 = new JLabel();
		label10.setText("V6");
		gbc = new GridBagConstraints();
		gbc.gridx = 9;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label10, gbc);
		final JLabel label11 = new JLabel();
		label11.setText("VA");
		gbc = new GridBagConstraints();
		gbc.gridx = 9;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label11, gbc);
		final JLabel label12 = new JLabel();
		label12.setText("VE");
		gbc = new GridBagConstraints();
		gbc.gridx = 9;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label12, gbc);
		final JLabel label13 = new JLabel();
		label13.setText("V3");
		gbc = new GridBagConstraints();
		gbc.gridx = 11;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label13, gbc);
		final JLabel label14 = new JLabel();
		label14.setText("V7");
		gbc = new GridBagConstraints();
		gbc.gridx = 11;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label14, gbc);
		final JLabel label15 = new JLabel();
		label15.setText("VB");
		gbc = new GridBagConstraints();
		gbc.gridx = 11;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label15, gbc);
		final JLabel label16 = new JLabel();
		label16.setText("VF");
		gbc = new GridBagConstraints();
		gbc.gridx = 11;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		panel4.add(label16, gbc);
		textFieldv0 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv0, gbc);
		textFieldv4 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv4, gbc);
		textFieldv8 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv8, gbc);
		textFieldv1 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 8;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv1, gbc);
		textFieldv5 = new JTextField();
		textFieldv5.setText("");
		gbc = new GridBagConstraints();
		gbc.gridx = 8;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv5, gbc);
		textFieldv9 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 8;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv9, gbc);
		textFieldvd = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 8;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldvd, gbc);
		textFieldv2 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 10;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv2, gbc);
		textFieldv6 = new JTextField();
		textFieldv6.setText("");
		gbc = new GridBagConstraints();
		gbc.gridx = 10;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldv6, gbc);
		textFieldva = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 10;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldva, gbc);
		textFieldve = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 10;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldve, gbc);
		final JSeparator separator1 = new JSeparator();
		separator1.setOrientation(1);
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridheight = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 10, 0, 10);
		panel4.add(separator1, gbc);
		final JLabel label17 = new JLabel();
		label17.setText("General Purpose Registers");
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 7;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 0, 0, 0);
		panel4.add(label17, gbc);
		final JLabel label18 = new JLabel();
		label18.setText("Special Registers");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		panel4.add(label18, gbc);
		textFieldv3 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 12;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 10);
		panel4.add(textFieldv3, gbc);
		textFieldv7 = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 12;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 10);
		panel4.add(textFieldv7, gbc);
		textFieldvb = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 12;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 10);
		panel4.add(textFieldvb, gbc);
		textFieldvf = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 12;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 10);
		panel4.add(textFieldvf, gbc);
		final JSeparator separator2 = new JSeparator();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 13;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		panel4.add(separator2, gbc);
		final JSeparator separator3 = new JSeparator();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 13;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		panel4.add(separator3, gbc);
		final JLabel label19 = new JLabel();
		label19.setText("Next Opcode");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 10, 10, 10);
		panel4.add(label19, gbc);
		textFieldop = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = 10;
		gbc.insets = new Insets(0, 0, 10, 0);
		panel4.add(textFieldop, gbc);
		final JLabel label20 = new JLabel();
		label20.setText("I");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel4.add(label20, gbc);
		final JLabel label21 = new JLabel();
		label21.setText("PC");
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel4.add(label21, gbc);
		textFieldi = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		panel4.add(textFieldi, gbc);
		textFieldpc = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		panel4.add(textFieldpc, gbc);
		textFieldtmr = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 30;
		panel4.add(textFieldtmr, gbc);
		final JLabel label22 = new JLabel();
		label22.setText("Timer");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 5);
		panel4.add(label22, gbc);
		final JLabel label23 = new JLabel();
		label23.setText("Sound");
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 5);
		panel4.add(label23, gbc);
		textFieldsnd = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		panel4.add(textFieldsnd, gbc);
		final JLabel label24 = new JLabel();
		label24.setText("SP");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel4.add(label24, gbc);
		textFieldsp = new JTextField();
		textFieldsp.setText("");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel4.add(textFieldsp, gbc);
		comboBox2 = new JComboBox();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel4.add(comboBox2, gbc);
		final JLabel label25 = new JLabel();
		label25.setText("Stack");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 5);
		panel4.add(label25, gbc);
		textFieldvc = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 35;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel4.add(textFieldvc, gbc);
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new GridBagLayout());
		panel3.add(panel5, BorderLayout.CENTER);
		panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controls", TitledBorder.LEFT, TitledBorder.TOP));
		startButton = new JButton();
		startButton.setText("Start");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel5.add(startButton, gbc);
		pauseButton = new JButton();
		pauseButton.setText("Pause");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel5.add(pauseButton, gbc);
		resetButton = new JButton();
		resetButton.setText("Reset");
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel5.add(resetButton, gbc);
		stepButton = new JButton();
		stepButton.setText("Step");
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel5.add(stepButton, gbc);
		//setPreferredSize(new Dimension(w, h));
		//setDoubleBuffered(true);
	
		lm = new DefaultListModel();
		list1.setModel(lm);
		
		for (int i = 0; i < c8.sys_mem.length; i+=2) {
		
			lm.addElement(String.format("0x%02x%02x", c8.sys_mem[i], c8.sys_mem[i + 1]));
		}

		// list1.setVisibleRowCount(4);
		pauseButton.addActionListener(new PauseAction());
		stepButton.addActionListener(new StepAction());
		resetButton.addActionListener(new ResetAction());
		startButton.addActionListener(new StartAction());
	}
	
	public void updateData() {
		
		textFieldv0.setText(String.format("0x%02x", c8.vReg[0]));
		textFieldv1.setText(String.format("0x%02x", c8.vReg[1]));
		textFieldv2.setText(String.format("0x%02x", c8.vReg[2]));
		textFieldv3.setText(String.format("0x%02x", c8.vReg[3]));
		textFieldv4.setText(String.format("0x%02x", c8.vReg[4]));
		textFieldv5.setText(String.format("0x%02x", c8.vReg[5]));
		textFieldv6.setText(String.format("0x%02x", c8.vReg[6]));
		textFieldv7.setText(String.format("0x%02x", c8.vReg[7]));
		textFieldv8.setText(String.format("0x%02x", c8.vReg[8]));
		textFieldv9.setText(String.format("0x%02x", c8.vReg[9]));
		textFieldva.setText(String.format("0x%02x", c8.vReg[10]));
		textFieldvb.setText(String.format("0x%02x", c8.vReg[11]));
		textFieldvc.setText(String.format("0x%02x", c8.vReg[12]));
		textFieldvd.setText(String.format("0x%02x", c8.vReg[13]));
		textFieldve.setText(String.format("0x%02x", c8.vReg[14]));
		textFieldvf.setText(String.format("0x%02x", c8.vReg[15]));
		textFieldpc.setText(String.format("0x%03x", c8.pC));
		textFieldi.setText(String.format("0x%03x", c8.iReg));
		textFieldsnd.setText(String.format("0x%02x", c8.soundTimer));
		textFieldtmr.setText(String.format("0x%02x", c8.delayTimer));
		textFieldsp.setText(String.format("0x%03x", c8.sP));
		textFieldop.setText(String.format("0x%02X%02X",  c8.sys_mem[c8.pC], c8.sys_mem[c8.pC + 1]));

		comboBox2.removeAllItems();

		int address = 0;
		for (int i = 0; i < 16; i++) {
			
			String stackElement = String.format("0x%03x => 0x%02x%02x", 0xea0 + address, c8.sys_mem[0xea0 + i], c8.sys_mem[0xea0 + i +1]);
			comboBox2.addItem(stackElement);
			address += 2;
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		//Graphics2D g2d = (Graphics2D) g;
		//Toolkit.getDefaultToolkit().sync(); //smoother animation in linux
		//drawScreenBuffer(g);
	}
	
	private class ResetAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			c8.sP = 0xea0;
			c8.pC = 0x200;
			Arrays.fill(c8.vReg, 0, c8.vReg.length, 0);
			Arrays.fill(c8.sys_mem, 0xf00, 0xfff, (byte) 0);
			Arrays.fill(c8.sys_mem, 0xea0, 0xea0 + 32, (byte) 0);

		}
	}

	private class PauseAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			c8.clock = false;
			updateData();
			int loc = c8.pC / 2;
			list1.setSelectedIndex(loc);
			list1.ensureIndexIsVisible(loc);
		}
	}
	
	private class StepAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (c8.clock == false) {
				
				c8.timerCountdown();
				c8.runOpcode(-1);
				updateData();
				int loc = c8.pC / 2;
				list1.setSelectedIndex(loc);
				list1.ensureIndexIsVisible(loc);
			}
		}
	}
	
	private class StartAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			c8.clock = true;
		}
	}
}

