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
import unisa.progettobd.result.Frame_GraficoResult;

public class Frame_SceltaGrafico extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Frame_SceltaGrafico() {
		super();

		JButton buttGraficoAnno = new JButton("Grafico Anno");
		buttGraficoAnno.setActionCommand("graficoAnno");
		buttGraficoAnno.addActionListener(this);

		JButton buttGraficoOrario = new JButton("Grafico Orario");
		buttGraficoOrario.setActionCommand("graficoOrario");
		buttGraficoOrario.addActionListener(this);

		JButton buttGraficoGiorno = new JButton("Grafico Macchine coinvolte");
		buttGraficoGiorno.setActionCommand("graficoMacchine");
		buttGraficoGiorno.addActionListener(this);

		// container of the buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 0, 20, 20));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

		panel.add(buttGraficoAnno);
		panel.add(buttGraficoOrario);
		panel.add(buttGraficoGiorno);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(new JLabel("Grafico rappresentante numero di incidenti per tipo"), BorderLayout.PAGE_START);

		setContentPane(contentPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String toDo = e.getActionCommand();

		if (toDo.equals("graficoAnno")) {

			String title = "Grafico prova";
			String chartTitle = "Istogramma in base annuale";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "Year");
			frame.setSize(1000, 1000);
			frame.setTitle("Grafico numero incidenti per anno");

			frame.setVisible(true);

		} else if (toDo.equals("graficoOrario")) {

			String title = "Grafico prova";
			String chartTitle = "Istogramma in base oraria";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "Hour");

			frame.setSize(1200, 1000);
			frame.setTitle("Grafico numero incidenti per orario");

			frame.setVisible(true);

		} else if (toDo.equals("graficoMacchine")) {

			String title = "Grafico prova";
			String chartTitle = "Istogramma in base alle macchine coinvolte";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "InvolvedCars");

			frame.setSize(1000, 700);
			frame.setTitle("Grafico numero incidenti per macchine coinvolte");

			frame.setVisible(true);

		}

	}

}
