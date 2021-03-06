package unisa.progettobd2.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import unisa.progettobd2.starter.Starter;

import java.awt.Dimension;

public class Frame_Starter extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Frame_Starter() {
		super();
		JButton buttRicercaAteneo = new JButton("Ricerca per tipo di bicicletta");
		buttRicercaAteneo.setActionCommand("bikeType");
		buttRicercaAteneo.addActionListener(this);

		JButton buttRicercaMese = new JButton("Ricerca per Mese");
		buttRicercaMese.setActionCommand("mese");
		buttRicercaMese.addActionListener(this);

		JButton buttGrafici = new JButton("Aggregazione");
		buttGrafici.setActionCommand("aggregazione");
		buttGrafici.addActionListener(this);

		JButton buttNewQuery = new JButton("Grafici");
		buttNewQuery.setActionCommand("grafici");
		buttNewQuery.addActionListener(this);

		// container of the buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 0, 20, 20));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
		
		panel.add(buttRicercaAteneo);
		panel.add(buttRicercaMese);
		panel.add(buttGrafici);
		panel.add(buttNewQuery);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(new JLabel("Progetto BD2 - Bike Sharing a Chicago - 2020"), BorderLayout.PAGE_START);
		contentPane.add(new JLabel("\u00a9 Gennaro Alessio Robertazzi & Emanuele Barberis - June 2021"), BorderLayout.PAGE_END);

		setContentPane(contentPane);
		
		

	}
/**
	@Override
	public void actionPerformed(ActionEvent e) {
		String toDo = e.getActionCommand();

		if (toDo.equals("infortunio")) {

			Frame_RicercaIncidente frameInjury = new Frame_RicercaIncidente();
			frameInjury.setSize(400, 300);
			frameInjury.setTitle("Ricerca per infortunio");
			frameInjury.setResizable(true);
			frameInjury.setVisible(true);
		} else if (toDo.equals("anno")) {

			Frame_RicercaAnno frameAnno = new Frame_RicercaAnno();
			frameAnno.setSize(400, 300);
			frameAnno.setTitle("Ricerca per anno");
			frameAnno.setResizable(true);
			frameAnno.setVisible(true);
		} else if (toDo.equals("aggregazione")) {

			Frame_Aggregazione frameAggregazione = new Frame_Aggregazione();

			frameAggregazione.setSize(1000, 600);
			frameAggregazione.setTitle("Aggregazione");
			frameAggregazione.setResizable(false);
			frameAggregazione.setVisible(true);
		} else if (toDo.equals("grafici")) {
			Frame_SceltaGrafico frameGrafico = new Frame_SceltaGrafico();
			frameGrafico.setSize(500, 400);
			frameGrafico.setTitle("Grafici");
			frameGrafico.setResizable(true);
			frameGrafico.setVisible(true);
		}

	}
	**/

	@Override
	public void actionPerformed(ActionEvent e) {
		String toDo = e.getActionCommand();

		if (toDo.equals("bikeType")) {

			Frame_RicercaBikeType frameInjury = new Frame_RicercaBikeType();
			frameInjury.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
			frameInjury.setSize(600, 500);
			frameInjury.setTitle("Ricerca per tipo di bicicletta");
			frameInjury.setResizable(true);
			frameInjury.setVisible(true);
		}else if (toDo.equals("mese")) {

			Frame_RicercaMese frameMese = new Frame_RicercaMese();
			frameMese.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
			frameMese.setSize(400, 300);
			frameMese.setTitle("Ricerca per mese");
			frameMese.setResizable(true);
			frameMese.setVisible(true);
		

		}else if (toDo.equals("aggregazione")) {

			Frame_Aggregazione frameAggr = new Frame_Aggregazione();
			frameAggr.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
			frameAggr.setSize(1000, 600);
			frameAggr.setTitle("Aggregazione");
			frameAggr.setResizable(true);
			frameAggr.setVisible(true);
		

		} else if (toDo.equals("grafici")) {
			Frame_SceltaGrafico frameGrafico = new Frame_SceltaGrafico();
			frameGrafico.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
			frameGrafico.setSize(500, 400);
			frameGrafico.setTitle("Grafici");
			frameGrafico.setResizable(true);
			frameGrafico.setVisible(true);

		}
	}
}