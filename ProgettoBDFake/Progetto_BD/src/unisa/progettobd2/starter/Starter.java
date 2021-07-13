package unisa.progettobd2.starter;


import java.awt.*;
import java.io.IOException;

import javax.swing.JFrame;

import unisa.progettobd2.frame.Frame_Starter;

public class Starter {

	public static void main(String[] args) {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"mongod\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Frame_Starter initFrame = new Frame_Starter();
		initFrame.setSize(600, 400);
		initFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
		initFrame.setTitle("Progetto Base di dati 2");
		initFrame.setDefaultCloseOperation(Frame_Starter.EXIT_ON_CLOSE);
		initFrame.setResizable(false);
		initFrame.setVisible(true);
		

	}

}
