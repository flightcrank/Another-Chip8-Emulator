
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

class Chip8 {
	
	byte sys_mem[] = new byte[4096]; //array of 4k chip8 system memory
	int vReg[] = new int[16];	 //16 8 bit general purpose registers
	int iReg;			 //1 16 bit address register
	int sP;				 //1 16 bit stack pointer register
	int pC;				 //1 16bit program counter register
	int delayTimer;			 //1 8 bit delay timer register
	int soundTimer;			 //1 8 bit sound timer register
	boolean clock;
	int romSize;

	public Chip8() {
		
		//initialise the chip system
		initSystem();
	}
	
	public void initSystem() {
		
		//initilise system registers and load system font
		clock = true;

		//stack pointer index
		sP = 0xea0;
		
		//Program Counter index
		pC = 0x200;
		
		//general purpose registers
		Arrays.fill(vReg, 0, vReg.length, 0);
		
		//system memory  purpose registers
		Arrays.fill(sys_mem, 0, sys_mem.length,(byte) 0);
		
		loadSystemFont();
	}
	
	public void timerCountdown() {
		
		if (delayTimer > 0) {
			
			delayTimer -= 1;
		}
		
		if (soundTimer > 0) {
			
			soundTimer -= 1;
		}
	}

	//TODO implements screen wrapping in x and y(?)
	public void drawSprite(int x, int y, int data) {
		
		int width = 8; //screen width in bytes.  8 bytes wide (64 bits/pixles)
		int xByte = x / width; //byte where the x position located
		
		//calculate the memory offset of the screen buffer to begin the draw operation
		int offset = xByte + y * width;
		int xShift = 8 - ((xByte + 1) * 8 - x);

		int yOffset = 0;
		int pixel = 0;

		int unset = 0;

		//fill the screen buffer portion of system memory with the byte data of the sprite
		for (int i = 0; i < data; i++) {
			
			pixel = 0x000000ff & sys_mem[iReg + i];	//store the current byte of the sprite in a int
			pixel <<= 8;				//shift it over 8 bits to fit 16 bits wide
			pixel >>= xShift;			//shift it right to store it in the correct x position

			//a sprite is no more the 8 bits wide so at most is can only span over 2 bytes of the screen buffer
			byte b1 = (byte)((0x0000ff00 & pixel) >> 8); 	//first 8 bits of the pixel
			byte b2 = (byte)(0x000000ff & pixel);		//last 8 bits of the pixel
			
			//add the yoffset to the offset to account for the position of sprites that are more than 1 byte in height
			int byteOffset1 = offset + yOffset; 
			int byteOffset2 = (offset + yOffset + 1) % 256;
			
			//store the result of the draw operation
			byte res1 = (byte) (sys_mem[0xf00 + byteOffset1] ^ b1); 
			byte res2 = (byte) (sys_mem[0xf00 + byteOffset2] ^ b2);
			
			//detect if any bits have changed
			boolean changed1 = sys_mem[0xf00 + byteOffset1] != res1 ? true : false;
			boolean changed2 = sys_mem[0xf00 + byteOffset2] != res2 ? true : false;
			
			if (changed1 == true || changed2 == true) {
				
				unset++;
			}

			//draw the result to the actual screen buffer
			sys_mem[0xf00 + byteOffset1] = res1;
			sys_mem[0xf00 + byteOffset2] = res2;
			yOffset += width;
		}
		
		vReg[15] = unset > 0 ? 0x1 : 0x0;
	}

	public int runOpcode(int key) {
		
		byte opcode[] = {sys_mem[pC], sys_mem[pC + 1]};

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
					Arrays.fill(sys_mem, 0xf00, 0xfff, (byte) 0);
					pC += 2;

				} else if (highByte == 0x00 && lowByte == 0xee) {

					System.out.println("RET");
					int b1 = (0x0ff & sys_mem[sP]) << 8;
					int b2 = 0x0ff & sys_mem[sP + 1];
					pC = b1;
					pC = b1 | b2;

					if (sP != 0xea0) { 

						//clear the stack of unedded addres
						sys_mem[sP] = 0;
						sys_mem[sP + 1] = 0;
						sP -= 2;
					}

				} else if (highByte == 0x00 && lowByte != 0x00) {
					
					//cpu machine code instruction, no roms really use this. emulator will stop
					System.out.printf("CAL &%x%n", address);
					clock = false;
				
				} else {
				
					System.out.printf("NOP%n");
					clock = false;
				}
				break;
			
			case 0x01: 
				
				System.out.printf("JMP &%x%n", address); 
				pC = address;
				break;

			case 0x02: 
				
				System.out.printf("SUB &%x%n", address); 
				
				int next = pC + 2;

				//if the stack pointer is non zero it must be pointing to some address already
				//so advance the stack pointer. if it points to 2 zero bytes, it must be in the
				// starting position 0xea0. no need to advance it in that case
				int nsp = (sys_mem[sP] == 0 && sys_mem[sP + 1] == 0) ? 0 : 2;
				sP += nsp;

				//store current pC in the stack space
				byte b1 = (byte) ((0x0000ff00 & next) >> 8);
				byte b2 = (byte) (0x000000ff & next);
				
				sys_mem[sP] = b1;
				sys_mem[sP + 1] = b2;
				
				//jump to address
				pC = address;
				break;

			case 0x03: 
				
				System.out.printf("SKP V%x == %x%n", vx, lowByte); 
				int res = ((0x0ff & vReg[vx]) == (0x0ff & lowByte)) ? 4 : 2;
				pC += res;
				break;

			case 0x04: 
				
				System.out.printf("SKP V%x != %x%n", vx, lowByte); 
				res = ((0x0ff & vReg[vx]) != lowByte) ? 4 : 2;
				pC += res;
				break;

			case 0x05:

				System.out.printf("SKP V%x == V%x%n", vx, vy); 
				res = ((0x0ff & vReg[vx]) == (0x0ff & vReg[vy])) ? 4 : 2;
				pC += res;
				break;

			case 0x06: 
				
				System.out.printf("SET V%x = %x%n", vx, lowByte);
				vReg[vx] = lowByte;
				pC += 2;
				break;

			case 0x07: 
				
				System.out.printf("ADD V%x += %x%n", vx, lowByte); 
				vReg[vx] = 0x0ff & (vReg[vx] + lowByte);
				pC += 2;
				break;

			case 0x08: 

				if (lowCode == 0x00) {
					
					System.out.printf("SET V%x = V%x%n", vx, vy);
					vReg[vx] = 0x0ff & vReg[vy];
					pC += 2;
				
				} else if (lowCode == 0x01) {
					
					System.out.printf("OR V%x = V%x | V%x%n", vx, vx, vy); 
					vReg[vx] = (0x0ff & vReg[vx]) | (0x0ff & vReg[vy]);
					pC += 2;
				
				} else if (lowCode == 0x02) {
					
					System.out.printf("AND V%x = V%x & V%x%n", vx, vx, vy); 
					vReg[vx] = (0x0ff & vReg[vx]) & (0x0ff & vReg[vy]);
					pC += 2;
				
				} else if (lowCode == 0x03) {
					
					System.out.printf("XOR V%x = V%x ^ V%x%n", vx, vx, vy); 
					vReg[vx] = (0x0ff & vReg[vx]) ^ (0x0ff & vReg[vy]);
					pC += 2;

				} else if (lowCode == 0x04) {
					
					System.out.printf("ADD V%x += V%x%n", vx, vy);
					vReg[vx] = (0x0ff & vReg[vx]) + (0x0ff & vReg[vy]);
					vReg[15] = (vReg[vx] + vReg[vy] > 0xff) ? 0x1 : 0x0;
					pC += 2;

				} else if (lowCode == 0x05) {
					/*
					This may cause issues i should test it
					SET V0 = 5
					SUB V0 -= value more than 5
					*/
					System.out.printf("SUB V%x -= V%x%n", vx, vy);
					vReg[15] = ((0x0ff & vReg[vx]) - (0x0ff & vReg[vy]) < 0) ? 0x0 : 0x1;
					vReg[vx] = (0x0ff & vReg[vx]) - (0x0ff & vReg[vy]);
					pC += 2;

				} else if (lowCode == 0x06) {
					
					System.out.printf("SET V%x = V%x >> 1%n", vx, vy);
					vReg[15] = ((0x0ff & vReg[vx]) & 1) > 0 ? 0x1 : 0x0;
					int val = (0x00000000 | (0x0ff & vReg[vx])) >> 1;
					vReg[vx] = 0x0ff &  val;
					pC += 2;

				} else if (lowCode == 0x07) {
					
					System.out.printf("SET V%x = V%x - V%x%n", vx, vy, vx);
					vReg[15] = (vReg[vy] - vReg[vx] < 0x0) ? 0x0 : 0x1;
					vReg[vx] = (byte)vReg[vy] - vReg[vx];
					pC += 2;
				
				} else if (lowCode == 0x0e) {
					
					System.out.printf("SET V%x = V%x << 1%n", vx, vx);
					vReg[15] = ((0x0ff & vReg[vx]) >> 7) > 0 ? 0x1 : 0x0;
					vReg[vx]  = (0x0ff & vReg[vx]) << 1;
					pC += 2;

				} else {
				
					System.out.printf("NOP%n"); 
				}

				break;

			case 0x09: 
				
				System.out.printf("SKP V%x != V%x%n", vx, vy); 
				res = ((0x0ff & vReg[vx]) != (0x0ff &vReg[vy])) ? 4 : 2;
				pC += res;
				break;

			case 0x0a: 
				
				System.out.printf("SET I = &%x%n", address); 
				iReg = address;
				pC += 2;		
				break;

			case 0x0b: 

				System.out.printf("JMP &%x + V0%n", address); 
				pC = address + (0x0ff & vReg[0]);
				break;
			
			case 0x0c: 
				
				System.out.printf("SET V%x = V%x & ?%n", vx, vx); 
				vReg[vx] = new Random().nextInt() & lowByte; 
				pC += 2;
				break;

			case 0x0d: 
				
				System.out.printf("DRW %x %x %x%n", vReg[vx], vReg[vy], lowCode); 
				drawSprite((0x0ff & vReg[vx]), (0x0ff & vReg[vy]), lowCode);
				pC += 2;
				break;

			case 0x0e: 
				
				if (lowByte == 0x9e) {

					System.out.printf("SKP V%x == key%n", vx);
					res = ((0x0ff & vReg[vx]) == key) ? 4 : 2;
					pC += res;
				
				} else if (lowByte == 0xa1) {
				
					System.out.printf("SKP V%x != key%n", vx); 
					res = ((0x0ff & vReg[vx]) != key) ? 4 : 2;
					pC += res;
				
				} else {
				
					System.out.printf("NOP%n"); 
				}
				break;

			case 0x0f: 
				
				if (lowByte == 0x07) {

					System.out.printf("SET V%x = delay%n", vx);
					vReg[vx] = 0x0ff & delayTimer;
					pC += 2;

				} else if (lowByte == 0x0a) {
				
					System.out.printf("SET V%x = key%n", vx);
					
					if (key != -1) {
						
						vReg[vx] = key;
						pC += 2;

					} else {
						
						break;
					}

				} else if (lowByte == 0x15) {
				
					System.out.printf("SET delay = V%x%n", vx); 
					delayTimer = 0x0ff & vReg[vx];
					pC += 2;

				} else if (lowByte == 0x18) {
					
					System.out.printf("SET sound = V%x%n", vx); 
					soundTimer = 0x0ff & vReg[vx];
					pC += 2;
				
				} else if (lowByte == 0x1e) {
					
					System.out.printf("ADD I +=  V%x (%x)%n", vx, vReg[vx]);
					iReg +=  0x0ff & vReg[vx];
					pC += 2;
				
				} else if (lowByte == 0x29) {
				
					System.out.printf("SET I = & of V%x%n", vx);
					iReg = vReg[vx] * 5;
					pC += 2;

				} else if (lowByte == 0x33) {
					
					System.out.printf("BCD V%x%n", vx);
					int num = 0x0ff & vReg[vx];
					int ones = num % 10;
					int tens = num / 10 % 10;
					int hundreds = num / 100;
					sys_mem[iReg] = (byte) hundreds;
					sys_mem[iReg + 1] = (byte) tens;
					sys_mem[iReg + 2] = (byte) ones;
					pC += 2;
				
				} else if (lowByte == 0x55) {
					
					System.out.printf("DMP V0 - V%x%n", vx);
					
					for (int i = 0; i <= vx; i++) {
						
						sys_mem[iReg + i] = (byte) (0x0ff & vReg[i]);
					}
					
					pC += 2;
				
				} else if (lowByte == 0x65) {
				
					System.out.printf("LOD V0 - V%x%n", vx); 
					
					for (int i = 0; i <= vx; i++) {
						
						vReg[i] = 0x0ff & sys_mem[iReg + i];
					}
					
					pC += 2;
				
				} else {
				
					System.out.printf("NOP%n"); 
				}

				break;

			default:
			
				System.out.printf("Invalid Opcode%n"); 
				break;
		}

		return highCode;
	}

	public void loadRom(String fileName) {
		
		int count = 0;

		// Use try-with-resources to close the stream.
		try (FileInputStream file = new FileInputStream(fileName)) {
			
			romSize = file.available();
			System.out.println("file size = " + romSize);

			while (file.available() > 0) {
				
				byte b = (byte) file.read();
				sys_mem[0x200 + count] = b;
				count++;
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


