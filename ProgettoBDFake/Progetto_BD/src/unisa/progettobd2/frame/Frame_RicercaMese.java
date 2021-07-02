package unisa.progettobd2.frame;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import unisa.progettobd2.result.Frame_MeseResult;
import unisa.progettobd2.service.DatabaseManager;

public class Frame_RicercaMese extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JComboBox comboMese;
	private JRadioButton radioMember, radioCasual;
	MongoCollection<Document> coll = null;
	public Frame_RicercaMese() {
		super();
		

		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
		contentPane.setLayout(new GridLayout(4, 0, 13, 13));

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(0, 2));

		radioMember = new JRadioButton("Member");
		radioMember.setSelected(true);

		radioCasual = new JRadioButton("Casual");

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(radioCasual);
		radioGroup.add(radioMember);

		radioPanel.add(radioCasual);
		radioPanel.add(radioMember);
		

		// ----------------------------------------------------------

		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(2, 0));
		coll = DatabaseManager.getBike();
		comboMese = new JComboBox();
		ArrayList<Integer> arrayResults = getArrayRiempimento("month", coll);
		for (int i = 0; i < arrayResults.size(); i++) {
			comboMese.addItem(arrayResults.get(i));
			
		}
		
		System.out.println(arrayResults.size());
		
		comboMese.setSelectedIndex(0);

		secondPanel.add(new JLabel("Seleziona mesi da ricercare:"));
		secondPanel.add(comboMese);

		// ----------------------------------------------------------------------------------
		JButton buttVai = new JButton("Vai");
		buttVai.addActionListener(this);

		contentPane.add(new JLabel("Seleziona il tipo di ricerca:"));
		contentPane.add(radioPanel);
		;
		contentPane.add(secondPanel);
		contentPane.add(buttVai);
		setContentPane(contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String tipo = "";
		String mese = comboMese.getItemAt(comboMese.getSelectedIndex()).toString();

		if (radioMember.isSelected())
			tipo = "Member";
		else
			tipo = "Casual";

		Frame_MeseResult result = new Frame_MeseResult(tipo, mese);
		result.setSize(1200, 500);
		result.setTitle("Results");
		result.setVisible(true);

	}
	
	public ArrayList<Integer> getArrayRiempimento(String tipo, MongoCollection<Document> collection) {

		ArrayList<Integer> results = new ArrayList<Integer>();
		results = collection.distinct(tipo, Integer.class).into(new ArrayList<Integer>());
		Collections.sort(results);
		return results;
	}

}
