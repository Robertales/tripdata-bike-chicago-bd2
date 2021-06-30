package unisa.progettobd.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame_Starter extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Frame_Starter() {
		super();

		JButton buttRicercaAteneo = new JButton("Ricerca per infortunio");
		buttRicercaAteneo.setActionCommand("infortunio");
		buttRicercaAteneo.addActionListener(this);

		JButton buttRicercaAnno = new JButton("Ricerca per anno");
		buttRicercaAnno.setActionCommand("anno");
		buttRicercaAnno.addActionListener(this);

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
		panel.add(buttRicercaAnno);
		panel.add(buttGrafici);
		panel.add(buttNewQuery);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(new JLabel("Progetto BD2 - Incidenti a Monroe 2003-2015"), BorderLayout.PAGE_START);

		setContentPane(contentPane);

	}

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

}
