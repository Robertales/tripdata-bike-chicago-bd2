package unisa.progettobd.starter;

import unisa.progettobd.frame.Frame_Starter;

public class Starter {

	public static void main(String[] args) {

		// frame iniziale
		Frame_Starter initFrame = new Frame_Starter();
		initFrame.setSize(600, 400);
		initFrame.setTitle("Progetto Base di dati 2");
		initFrame.setDefaultCloseOperation(Frame_Starter.EXIT_ON_CLOSE);
		initFrame.setResizable(false);
		initFrame.setVisible(true);

	}

}
