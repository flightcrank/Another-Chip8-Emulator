

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JComponent {
	
	private int w, h;			//Width and height of Screen JComponent
	private Chip8 c8;			//Chip8 opject to access system memory screen buffer
	private BufferedImage screenBuffer;	//The image object that contains the pixel buffer

	public byte[] pixelBuffer;		//Raw pixel data
	
	Timer timer;				//timer to update the ScreenBuffer periodically as the pixelbuffer changed
	ActionListener taskPerformer;
	
	public Screen() {
			
		//initialise pixel buffer
		pixelBuffer = new byte[256];

		//initialise screen buffer
		DataBuffer db = new DataBufferByte(pixelBuffer, pixelBuffer.length);
		WritableRaster wr = Raster.createPackedRaster(db, 64, 32, 1, null);
		ColorModel cm = new IndexColorModel(1, 2,
				new byte[]{(byte) 0, (byte) 255},
				new byte[]{(byte) 0, (byte) 255},
				new byte[]{(byte) 0, (byte) 255});
		
		screenBuffer = new BufferedImage(cm, wr, false, null);
		
		//set size of this JComponent
		this.w = screenBuffer.getWidth() * 10;
		this.h = screenBuffer.getHeight() * 10;
		
		setPreferredSize(new Dimension(w, h));
		setDoubleBuffered(true);
		
		//initialise actionListener to periodically update screenBuffer with the pixelBuffer so when ever
		//change is made to the screen buffer portion of the chip8 system memory the screenBuffer images is updated
		taskPerformer = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				System.arraycopy(c8.sys_mem, 0xf00, pixelBuffer, 0, 256);	
				repaint();
			}
		};
		
		timer = new Timer(33, taskPerformer);
	}
	
	//set the chip8 emulator intstace so this JComponent can access the screenbuffer portion of system memory to display.
	public void setInstance(Chip8 instance) {
		
		this.c8 = instance;
		timer.start();
	}

	//draw scaled version of the screen buffer to this JComponent
	public void drawScreenBuffer(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		
		Image outputImg = screenBuffer.getScaledInstance(w, h, 1);

		g2d.drawImage(outputImg, null, null);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Toolkit.getDefaultToolkit().sync(); //smoother animation in linux
		drawScreenBuffer(g);
	}
}

