

# ACE: Another Chip8 Emulator
This is another chip8 emulator coded in Java. It was made for the purpose of understanding how emulators work.
So far it boots and correctly runs the test rom's test_opcode.ch8 and BC_test.ch8. It also boots into the title screens
of some games like space invaders

Things left to implement are

* sprite screen wrapping
* key board input to play the game roms
* rom compatibility
* sound
* timer delay

Its currently still under active development.

Compilation instructions are as follows...

	$ javac Ace.java

That should compile all necessary .java files. to run the program type the command

	$ java Ace "path to rom file"

## Images
![Imgur](https://i.imgur.com/uCrcYWT.png)

![Imgur](https://i.imgur.com/8jE8oXQ.png)
