Test.java

Course: CSC242 Spring 2016
 
Assignment: Project01, Tic-Tac-Toe
 
Author: Adeeb Sheikh

I DID NOT COLLABORATE ON THIS PROJECT AND ALL WORK IS MY OWN

To run Test.java:

Download and extract the file to the desktop
Go to the console:
> cd Desktop\Project03\
> set path=%path%;C:\Program Files\Java\jdk1.8.0_20\bin
> javac Test.java
> java Test

Test.java DESCRIPTION:

All Test.java does is create an instance of the XONoughtCross and run 9-Board Tic-Tac-Toe.

When playing 9-Board with XONoughtCross, it will prompt the user to select whether it should play X or O. Pick either to start the game, any invalid input here will end the game. If there should be an invalid move in the middle of the game, XONoughtCross will either throw an error, ending the game, or if the move is of a valid form, but not valid in the context of the game, it will prompt the user to enter a valid move. Upon the completion of the game, XONoughtCross will prompt the user to play again.

FILE DESCRIPTIONS:

Test.java: 				The dummy class containing the main method that creates an instance of XONoughtCross.java
XO.java: 				The A.I. class for playing vanilla Tic-Tac-Toe
TicTacTree.java:		Represents a node and state in the state-space search tree for Tic-Tac-Toe
XONoughtCross.java:		The A.I. class for playing the 9-Board variant of Tic-Tac-Toe
NineTicTacTree.java:	Represents a node and state in the state-space search tree for the 9-Board variant of Tic-Tac-Toe