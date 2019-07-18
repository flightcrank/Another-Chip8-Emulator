
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Ace {

	private static JFrame frame;
	private Screen screen1;
	private JPanel panel1;
	private JMenuBar menuBar1;
	private JMenu fileMenu;
	private JMenu optionsMenu;
	private JMenuItem openRom, quit, debug;
	private Chip8 c8;
	private int key = -1;

	private Debugger dbg;

	Timer timer;                //timer to execute opcodes at a constant rate
	ActionListener taskPerformer;

	public Ace(String fileName) {

		menuBar1 = new JMenuBar();
		fileMenu = new JMenu("File");
		optionsMenu = new JMenu("Options");
		openRom = new JMenuItem("Open Rom");
		quit = new JMenuItem("Quit");
		debug = new JMenuItem("Debugger");

		fileMenu.add(openRom);
		fileMenu.addSeparator();
		fileMenu.add(quit);
		optionsMenu.add(debug);
		menuBar1.add(fileMenu);
		menuBar1.add(optionsMenu);

		openRom.addActionListener(new OpenAction());
		quit.addActionListener(new QuitAction());
		debug.addActionListener(new DebuggerAction());

		//set up the program window frame
		frame = new JFrame("ACE (Another Chip-8 Emulator)");
		frame.getContentPane().add(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menuBar1);
		frame.pack();
		frame.setVisible(true);
		screen1.addKeyListener(new KeyAction());

		start(fileName);
	}

	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				//Use native look and feel
				try {

					//for linux systems like KDE try to use GTK's looks and feel
					if (System.getProperty("os.name").equals("Linux")) {

						//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
						UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

					} else {

						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}

				} catch (Exception e) {

					System.out.println(e);
				}

				//create and show the Swing GUI and create the chip 8 instance
				Ace emulator = new Ace(args[0]);
			}
		});
	}

	public void start(String fileName) {

		c8 = new Chip8();     //set up the chip8 emulator instance
		c8.loadRom(fileName);            //load the rom into the emulator

		screen1.setInstance(c8);        //let the display JComponent access the chip8 instance
		frame.pack();

		taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (c8.clock == true) {
					
					//while chip8 is running give key board foucs to the screen panel
					screen1.requestFocus();

					//decrement the sound and delay timers
					c8.timerCountdown();

					//run the opcode function multiple times to run approximate 500hz (8 * 60hz)
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);
					c8.runOpcode(key);

					//update screen object to reflect the changes to the screen bufffer
					screen1.repaint();

				} else {

					//stop chip8 clock from running
					c8.clock = false;
					screen1.repaint();
				}
			}
		};

		//run the chip 8 clock at approx 60hz (1000ms / 60ms)
		timer = new Timer(16, taskPerformer);
		timer.start();
	}

	private class KeyAction implements KeyListener {

		public void keyPressed(KeyEvent e) {

			key = e.getKeyCode();

			switch (key) {

				case KeyEvent.VK_1:key = 0x1;break;
				case KeyEvent.VK_2:key = 0x2;break;
				case KeyEvent.VK_3:key = 0x3;break;
				case KeyEvent.VK_4:key = 0xc;break;
				case KeyEvent.VK_Q:key = 0x4;break;
				case KeyEvent.VK_W:key = 0x5;break;
				case KeyEvent.VK_E:key = 0x6;break;
				case KeyEvent.VK_R:key = 0xd;break;
				case KeyEvent.VK_A:key = 0x7;break;
				case KeyEvent.VK_S:key = 0x8;break;
				case KeyEvent.VK_D:key = 0x9;break;
				case KeyEvent.VK_F:key = 0xe;break;
				case KeyEvent.VK_Z:key = 0xa;break;
				case KeyEvent.VK_X:key = 0x0;break;
				case KeyEvent.VK_C:key = 0xb;break;
				case KeyEvent.VK_V:key = 0xf;break;
				default:key = -1;break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

			key = -1;
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

	private class QuitAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);
		}
	}
	
	private class DebuggerAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			dbg = new Debugger(c8);
			panel1.add(dbg, BorderLayout.CENTER);    //add the debugger to the main window
			frame.pack();
		}
	}

	private class OpenAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
			fd.setVisible(true);
			String fName = fd.getFile();

			//stop the opcodes from executing
			timer.stop();

			if (fName != null) {

				System.out.println(fName);
				c8.initSystem();
				c8.loadRom(fName);

			} else {

				System.out.println("no file selected");
			}

			timer.start();
		}
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout(0, 0));
		panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());
		panel1.add(panel2, BorderLayout.NORTH);
		screen1 = new Screen();
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel2.add(screen1, gbc);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return panel1;
	}

}
	
