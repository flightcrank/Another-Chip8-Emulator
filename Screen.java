

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Screen extends JComponent {
	
	private int w, h;			//Width and height of Screen JComponent
	private Chip8 c8;			//Chip8 opject to access system memory screen buffer
	private BufferedImage screenBuffer;	//The image object that contains the pixel buffer

	public Screen() {
			
		//set size of this JComponent
		this.w = 64 * 10;
		this.h = 32 * 10;
		
		setPreferredSize(new Dimension(w, h));
		setDoubleBuffered(true);
	}
	
	//set the chip8 emulator intstace so this JComponent can access the screenbuffer portion of system memory to display.
	public void setInstance(Chip8 instance) {
		
		this.c8 = instance;
		
		//initialise screen buffer
		DataBuffer db = new DataBufferByte(c8.sys_mem, 256, 0xf00);
		WritableRaster wr = Raster.createPackedRaster(db, 64, 32, 1, null);
		ColorModel cm = new IndexColorModel(1, 2,
				new byte[]{(byte) 0, (byte) 255},
				new byte[]{(byte) 0, (byte) 255},
				new byte[]{(byte) 0, (byte) 255});
		
		screenBuffer = new BufferedImage(cm, wr, false, null);
		
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
		//Toolkit.getDefaultToolkit().sync(); //smoother animation in linux
		drawScreenBuffer(g);
	}
}

