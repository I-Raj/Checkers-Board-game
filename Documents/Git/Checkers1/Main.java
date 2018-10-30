package checkers;

import java.awt.Color;

import javax.swing.JFrame;

import checkers.Board;

public class Main {
	public static void main(String[] args)
	{
	Board check=new Board();
	check.setLayout(null);
	check.setSize(800,400);
	check.getContentPane().setBackground(Color.GREEN);
	check.setVisible(true);
	check.setLocationRelativeTo(null);
	check.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}