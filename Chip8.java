
import java.io.*;
import java.util.Arrays;

class Chip8 {
	
	byte sys_mem[] = new byte[4096]; //array of 4k chip8 system memory
	byte vReg[] = new byte[16];	 //16 8 bit general purpose registers
	short iReg;			 //1 16 bit address register
	short sP;			 //1 16 bit stack pointer register
	short pC;			 //1 16bit program counter register
	byte delayTimer;		 //1 8 bit delay timer register
	byte soundTimer;		 //1 8 bit sound timer register
	final byte I = 0x10;			

	public Chip8() {
		
		//initilise system registers and load system font
		
		//stack pointer index
		sP = 0xea0;
		
		//Program Counter index
		pC = 0x200;

		//I register
		iReg = 0;

		//Timers
		delayTimer = 0;
		soundTimer = 0;

		//V Registers
		for (int i = 0; i < vReg.length; i++) {
			
			vReg[i] = 0;
		}

		loadSystemFont();
	}
	
	public void clearScreen() {
		

		Arrays.fill(sys_mem, 0xf00, 0xfff, (byte) 0);
	}
	
	public void setReg(byte index, byte val) {
	
		//set the I register
		if (index == I) {
			
			iReg = (short) (vReg[val] * 5);
		
		//set the V register to val at the index location
		} else {
			
			vReg[index] = val;
		}
	}

	public void drawSprite(int x, int y, int data) {
		
		int width = 8; //screen width in bytes. 8 bytes wide (64 bits/pixles)
		int xByte = x / width; //byte where the x position located
		
		//calculate the memory offset of the screen buffer to begin the draw operation
		int offset = xByte + y * width;
		int xShift = 8 - ((xByte + 1) * 8 - x);

		int yOffset = 0;

		int pixel = 0;

		//fill the screen buffer portion of system memory with the byte data of the sprite
		for (int i = 0; i < data; i++) {
			
			pixel = 0x000000ff & sys_mem[iReg + i];	//store the current byte of the sprite in a int
			pixel <<= 8;				//shift it over 8 bits to fit 16 bits wide
			pixel >>= xShift;			//shift it right to store it in the correct x position

			//a sprite is no more the 8 bits wide so at most is can only span over 2 bytes of the screen buffer
			byte b1 = (byte)((0x0000ff00 & pixel) >> 8); 	//first 8 bits of the pixel
			byte b2 = (byte)(0x000000ff & pixel);		//last 8 bits of the pixel
			
			//add the yoffset to the offset to account for the position of sprites that are more than 1 byte in height
			int byteOffset = offset + yOffset; 
	
			sys_mem[0xf00 + byteOffset] ^= b1;
			sys_mem[0xf00 + byteOffset + 1] ^= b2;
			yOffset += 8;
		}

		//TODO set register VF to 0 or 1 if any bits were changed
	}

	public void runOpcode(byte opcode[]) {
		
		//convert opcode bytes to ints as java has no unsigned bytes
		int highByte = 0x000000ff & opcode[0];
		int lowByte = 0x000000ff & opcode[1];
		
		int highCode = highByte >> 4; 		//upper nibble (first 4 bits) of the opcode
		int lowCode = 0x0000000f & lowByte; 	//lower nibble (last 4 bits) of the opcode
		
		int vx = 0x000000f & highByte;		//lower nibble (last 4 bits) of the high byte of the opcode
		int vy = (0x00000f0 & lowByte) >> 4;	//upper nibble (first 4 bits) of the low byte of the opcode

		//memory address's are stored in the last 12 bits of the opcode
		int address = (0x0000000f & highByte) << 8;
		address = address | lowByte;

		switch (highCode) {
		
			case 0x00:
				
				if (highByte == 0x00 && lowByte == 0xe0) {
					
					System.out.println("CLS");
					clearScreen();

				} else if (highByte == 0x00 && lowByte == 0xee) {

					System.out.println("RET");

				} else if (highByte == 0x00) {

					System.out.printf("CAL &%x%n", address);
				
				} else {
				
					System.out.printf("NOP%n"); 
				}
			break;
			
			case 0x01: System.out.printf("JMP &%x%n", address); break;

			case 0x02: System.out.printf("SUB &%x%n", address); break;

			case 0x03: System.out.printf("SKP V%x == %x%n", vx, lowByte); break;

			case 0x04: System.out.printf("SKP V%x != %x%n", vx, lowByte); break;

			case 0x05: System.out.printf("SKP V%x == V%x%n", vx, vy); break;

			case 0x06: 
				
				System.out.printf("SET V%x = %x%n", vx, lowByte); 
				setReg((byte) vx, (byte) lowByte);
			break;

			case 0x07: System.out.printf("ADD V%x += %x%n", vx, lowByte); break;

			case 0x08: 

				if (lowCode == 0x00) {
					
					System.out.printf("SET V%x = V%x%n", vx, vy); 
				
				} else if (lowCode == 0x01) {
					
					System.out.printf("OR V%x = V%x | V%x%n", vx, vx, vy); 
				
				} else if (lowCode == 0x02) {
					
					System.out.printf("AND V%x = V%x & V%x%n", vx, vx, vy); 
				
				} else if (lowCode == 0x03) {
					
					System.out.printf("XOR V%x = V%x ^ V%x%n", vx, vx, vy); 

				} else if (lowCode == 0x04) {
					
					System.out.printf("ADD V%x += V%x%n", vx, vy); 

				} else if (lowCode == 0x05) {
					
					System.out.printf("SUB V%x -= V%x%n", vx, vy); 

				} else if (lowCode == 0x06) {
					
					System.out.printf("SET V%x = V%x >> 1%n", vx, vx); 

				} else if (lowCode == 0x07) {
					
					System.out.printf("SET V%x = V%x - V%x%n", vx, vy, vx); 
				
				} else if (lowCode == 0x0e) {
					
					System.out.printf("SET V%x = V%x << 1%n", vx, vx); 
				} else {
				
					System.out.printf("NOP%n"); 
				}


			break;

			case 0x09: System.out.printf("SKP V%x != V%x%n", vx, vy); break;

			case 0x0a: System.out.printf("SET I = &%x%n", address); break;

			case 0x0b: System.out.printf("JMP &%x + V0%n", address); break;
			
			case 0x0c: System.out.printf("SET V%x = V%x & ?%n", vx, vx); break;

			case 0x0d: 
				
				System.out.printf("DRW %x %x %x%n", vReg[vx], vReg[vy], lowCode); 
				drawSprite(vReg[vx], vReg[vy], lowCode);

			break;

			case 0x0e: 
				
				if (lowByte == 0x9e) {

					System.out.printf("SKP V%x == key%n", vx); 
				
				} else if (lowByte == 0xa1) {
				
					System.out.printf("SKP V%x != key%n", vx); 
				
				} else {
				
					System.out.printf("NOP%n"); 
				}
			break;

			case 0x0f: 
				
				if (lowByte == 0x07) {

					System.out.printf("SET V%x = delay%n", vx); 

				} else if (lowByte == 0x0a) {
				
					System.out.printf("SET V%x = key%n", vx); 

				} else if (lowByte == 0x15) {
				
					System.out.printf("SET delay = V%x%n", vx); 

				} else if (lowByte == 0x18) {
					
					System.out.printf("SET sound = V%x%n", vx); 
				
				} else if (lowByte == 0x1e) {
					
					System.out.printf("ADD I +=  V%x%n", vx); 
				
				} else if (lowByte == 0x29) {
				
					System.out.printf("SET I = & of V%x%n", vx);
					setReg(I, (byte) vx);

				} else if (lowByte == 0x33) {
					
					System.out.printf("BCD V%x%n", vx); 
				
				} else if (lowByte == 0x55) {
					
					System.out.printf("DMP V0 - V%x%n", vx); 
				
				} else if (lowByte == 0x65) {
				
					System.out.printf("LOD V0 - V%x%n", vx); 
				
				} else {
				
					System.out.printf("NOP%n"); 
				}

			break;

			default:
				System.out.printf("Invalid Opcode%n"); 
			break;

		}
	}

	public void loadRom(String fileName) {
		
		int size;
		byte opcode[] = new byte[2];

		// Use try-with-resources to close the stream.
		try (FileInputStream file = new FileInputStream(fileName)) {
			
			size = file.available();
			System.out.println("file size = " + size);

			while (file.available() > 1) {
				
				file.read(opcode);
				//OperationCode(opcode);
				//System.out.printf("%02x%n", opcode[0]);
			}

		} catch (IOException e) {
		
			System.out.println("I/O Error: " + e);
		}
	}

	void loadSystemFont() {
		
		int index = 0;
		int font[][] = {{0xf0, 0x90, 0x90, 0x90, 0xf0},  //0 
				{0x20, 0x60, 0x20, 0x20, 0x70},  //1
				{0xf0, 0x10, 0xf0, 0x80, 0xf0},  //2
				{0xf0, 0x10, 0xf0, 0x10, 0xf0},  //3
				{0x90, 0x90, 0xf0, 0x10, 0x10},  //4
				{0xf0, 0x80, 0xf0, 0x10, 0xf0},  //5
				{0xf0, 0x80, 0xf0, 0x90, 0xf0},  //6
				{0xf0, 0x10, 0x20, 0x40, 0x40},  //7
				{0xf0, 0x90, 0xf0, 0x90, 0xf0},  //8
				{0xf0, 0x90, 0xf0, 0x10, 0xf0},  //9
				{0xf0, 0x90, 0xf0, 0x90, 0x90},  //a
				{0xe0, 0x90, 0xe0, 0x90, 0xe0},  //b
				{0xf0, 0x80, 0x80, 0x80, 0xf0},  //c
				{0xe0, 0x90, 0x90, 0x90, 0xe0},  //d
				{0xf0, 0x80, 0xf0, 0x80, 0xf0},  //e
				{0xf0, 0x80, 0xf0, 0x80, 0x80}}; //f
		
		for(int i = 0; i < font.length; i++) {
			
			for(int j = 0; j < font[i].length; j++) {
				
				sys_mem[index] = (byte) font[i][j];
				index++;
			}
		}
	}
}


