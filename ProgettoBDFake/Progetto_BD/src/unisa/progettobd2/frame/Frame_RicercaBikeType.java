package unisa.progettobd2.frame;

import java.awt.GridLayout;
import java.awt.Toolkit;
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

import unisa.progettobd2.result.Frame_BikeTypeResult;
import unisa.progettobd2.service.DatabaseManager;
import unisa.progettobd2.starter.Starter;

public class Frame_RicercaBikeType extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JComboBox comboBikeType;
	private JComboBox comboMese;
	private JComboBox comboGiorno;

    public Frame_RicercaBikeType() {
        super();
        MongoCollection<Document> coll = null;
        coll = DatabaseManager.getBike();

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        contentPane.setLayout(new GridLayout(6, 0, 13, 13));

        // ----------------------------------------------------------

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(3, 0));
        
        comboMese = new JComboBox<>();
        comboGiorno = new JComboBox<>();
        comboBikeType = new JComboBox();
        ArrayList<Integer> arrayResultsMonth = getArrayRiempimentoInt("month", coll);
        ArrayList<Integer> arrayResultsGiorno = getArrayRiempimentoInt("dayStart", coll);
        ArrayList<String> arrayResultsBikeType = getArrayRiempimento("rideableType", coll);
        
        for (int i = 0; i < arrayResultsBikeType.size(); i++) {
        	comboBikeType.addItem(arrayResultsBikeType.get(i));
        }
        for (int i = 0; i < arrayResultsMonth.size(); i++) {
        	comboMese.addItem(arrayResultsMonth.get(i).toString());
        }
        comboBikeType.setSelectedIndex(0);
        comboMese.setSelectedIndex(0);
        secondPanel.add(new JLabel("Seleziona il mese:"));
        secondPanel.add(comboMese);
        comboMese.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (comboMese.getSelectedIndex() == 10 || comboMese.getSelectedIndex() == 3 || 
                		comboMese.getSelectedIndex() == 5 || comboMese.getSelectedIndex() == 8) {
                	comboGiorno.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                    	comboGiorno.addItem(arrayResultsGiorno.get(i).toString());
                    	if (i==29) {
                    		break;                    		
                    	}
                    }
                } else if(comboMese.getSelectedIndex() == 1) {
                	comboGiorno.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                		comboGiorno.addItem(arrayResultsGiorno.get(i).toString());
                    	if (i==27) {
                    		break;                    		
                    	}
                    }
                	
                } else if(comboMese.getSelectedIndex() == 0 || comboMese.getSelectedIndex() == 2 || 
                		comboMese.getSelectedIndex() == 4 || comboMese.getSelectedIndex() == 6
                		|| comboMese.getSelectedIndex() == 7 || comboMese.getSelectedIndex() == 9 || comboMese.getSelectedIndex() == 11) {
                	comboGiorno.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                		comboGiorno.addItem(arrayResultsGiorno.get(i).toString());
                    	if (i==30) {
                    		break;                    		
                    	}
                    }
                }
            }
        });	
        secondPanel.add(new JLabel("Seleziona il giorno:"));
        secondPanel.add(comboGiorno);
        secondPanel.add(new JLabel("Seleziona il tipo di bicicletta:"));
        secondPanel.add(comboBikeType);

        // ----------------------------------------------------------------------------------
        JButton buttVai = new JButton("Vai");
        buttVai.addActionListener(this);

        contentPane.add(new JLabel("Seleziona il mese/giorno/tipo di bicicletta:"));
        
        contentPane.add(secondPanel);
        contentPane.add(buttVai);
        setContentPane(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String bikeType = comboBikeType.getItemAt(comboBikeType.getSelectedIndex()).toString();
        String month = comboMese.getItemAt(comboMese.getSelectedIndex()).toString();
        String giorno = comboGiorno.getItemAt(comboGiorno.getSelectedIndex()).toString();
        
        Frame_BikeTypeResult result = new Frame_BikeTypeResult(bikeType, month, giorno);
        result.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
        result.setSize(1200, 500);
        result.setTitle("Results");
        result.setVisible(true);

    }

    public ArrayList<String> getArrayRiempimento(String tipo, MongoCollection<Document> collection) {

        ArrayList<String> results = new ArrayList<String>();
        results = collection.distinct(tipo, String.class).into(new ArrayList<String>());
        Collections.sort(results);
        return results;
    }
    
    public ArrayList<Integer> getArrayRiempimentoInt(String tipo, MongoCollection<Document> collection) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        results = collection.distinct(tipo, Integer.class).into(new ArrayList<Integer>());
        Collections.sort(results);
        return results;
    }


}
