package unisa.progettobd.frame;

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

import unisa.progettobd.result.Frame_IncidenteResult;
import unisa.progettobd.service.DatabaseManager;

public class Frame_RicercaIncidente extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JComboBox comboIncidente;
    private JRadioButton radioWeekday, radioWeekend;

    public Frame_RicercaIncidente() {
        super();
        MongoCollection<Document> coll = null;
        coll = DatabaseManager.getFull();

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        contentPane.setLayout(new GridLayout(4, 0, 13, 13));

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(0, 2));

        radioWeekday = new JRadioButton("Weekday");
        radioWeekday.setSelected(true);

        radioWeekend = new JRadioButton("Weekend");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioWeekday);
        radioGroup.add(radioWeekend);

        radioPanel.add(radioWeekday);
        radioPanel.add(radioWeekend);

        // ----------------------------------------------------------

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2, 0));

        comboIncidente = new JComboBox();
        ArrayList<String> arrayResults = getArrayRiempimento("InjuryType", coll);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboIncidente.addItem(arrayResults.get(i));
        }
        comboIncidente.setSelectedIndex(0);

        secondPanel.add(new JLabel("Seleziona il tipo di infortunio da cercare:"));
        secondPanel.add(comboIncidente);

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
        String incidente = comboIncidente.getItemAt(comboIncidente.getSelectedIndex()).toString();

        if (radioWeekday.isSelected())
            tipo = "Weekday";
        else
            tipo = "Weekend";

        Frame_IncidenteResult result = new Frame_IncidenteResult(tipo, incidente);
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
