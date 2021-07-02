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

import unisa.progettobd2.result.Frame_BikeTypeResult;
import unisa.progettobd2.service.DatabaseManager;

public class Frame_RicercaBikeType extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JComboBox comboBikeType;

    public Frame_RicercaBikeType() {
        super();
        MongoCollection<Document> coll = null;
        coll = DatabaseManager.getBike();

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        contentPane.setLayout(new GridLayout(4, 0, 13, 13));

        // ----------------------------------------------------------

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2, 0));

        comboBikeType = new JComboBox();
        ArrayList<String> arrayResults = getArrayRiempimento("rideableType", coll);
        
        for (int i = 0; i < arrayResults.size(); i++) {
        	comboBikeType.addItem(arrayResults.get(i));
        }
        comboBikeType.setSelectedIndex(0);

        secondPanel.add(new JLabel("Seleziona il tipo di bicicletta:"));
        secondPanel.add(comboBikeType);

        // ----------------------------------------------------------------------------------
        JButton buttVai = new JButton("Vai");
        buttVai.addActionListener(this);

        contentPane.add(new JLabel("Seleziona il tipo di ricerca:"));
        
        contentPane.add(secondPanel);
        contentPane.add(buttVai);
        setContentPane(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String bikeType = comboBikeType.getItemAt(comboBikeType.getSelectedIndex()).toString();
        Frame_BikeTypeResult result = new Frame_BikeTypeResult(bikeType);
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


}
