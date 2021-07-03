package unisa.progettobd2.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import unisa.progettobd2.result.Frame_GraficoResult;

public class Frame_SceltaGrafico extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Frame_SceltaGrafico() {
		super();

		JButton buttGraficoAnno = new JButton("Grafico Mese");
		buttGraficoAnno.setActionCommand("graficoMese");
		buttGraficoAnno.addActionListener(this);

		JButton buttGraficoOrario = new JButton("Grafico Orario");
		buttGraficoOrario.setActionCommand("graficoOrario");
		buttGraficoOrario.addActionListener(this);

		JButton buttGraficoGiorno = new JButton("Grafico tipo bici");
		buttGraficoGiorno.setActionCommand("graficoBici");
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
		contentPane.add(new JLabel("Grafico rappresentante numero di bici per tipo"), BorderLayout.PAGE_START);

		setContentPane(contentPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String toDo = e.getActionCommand();

		if (toDo.equals("graficoMese")) {

			String title = "Grafico prova";
			String chartTitle = "Bici usate per mese";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "month");
			frame.setSize(1000, 1000);
			frame.setTitle("Grafico  bici usate per mese");

			frame.setVisible(true);

		} else if (toDo.equals("graficoOrario")) {

			String title = "Grafico prova";
			String chartTitle = "Istogramma corse per ora";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "hourStart");

			frame.setSize(1200, 1000);
			frame.setTitle("Grafico numero corse per ora");

			frame.setVisible(true);

		} else if (toDo.equals("graficoBici")) {

			String title = "Grafico prova";
			String chartTitle = "Istogramma in base al tipo di bici utilizzate";

			Frame_GraficoResult frame = new Frame_GraficoResult(title, chartTitle, "rideableType");

			frame.setSize(1000, 700);
			frame.setTitle("Grafico per tipologia di bici utilizzate");

			frame.setVisible(true);

		}

	}

}
