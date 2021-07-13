package unisa.progettobd.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

import unisa.progettobd.result.Frame_AggregazioneResult;
import unisa.progettobd.service.DatabaseManager;

public class Frame_Aggregazione extends JFrame {

    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 1000;

    private ArrayList<String> colID;
    private ArrayList<String> colNames;

    private JCheckBox checkYear, checkWeekend, checkHour, checkInvolvedCars, checkInjuryType, checkPrimaryFactor,
            checkReportedLocation;

    private JRadioButton radioMinore, radioMaggiore, radioUguale;

    private JComboBox comboYear, comboWeekend, comboHour, comboInjuryType, comboPrimaryFactor, comboReportedLocation,
            comboMediaMC;

    private MongoCollection<Document> coll_full;

    private ActionListener checkListener;
    private JButton btnMedia;

    private boolean flagMedia, flagSelezionato;

    public Frame_Aggregazione() {
        // setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // setResizable(false);
        colID = new ArrayList<String>();
        colNames = new ArrayList<String>();

        coll_full = DatabaseManager.getFull();
        checkListener = new clickCheck();

        flagMedia = false;
        flagSelezionato = false;
        add(createMainPanel());
    }

    public JPanel createMainPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JPanel query = createQueryPanel();
        query.setPreferredSize(new Dimension(FRAME_WIDTH, 500));
        panel.add(query);
        JPanel btn = mostraQueryPanel();
        btn.setPreferredSize(new Dimension(FRAME_WIDTH, 100));
        panel.add(btn);
        return panel;
    }

    public JPanel createQueryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

        JPanel year = createYearPanel();
        JPanel weekend = createWeekendPanel();
        JPanel hour = createHourPanel();
        JPanel involvedCars = createInvolvedCarsPanel();
        JPanel injuryType = createInjuryTypePanel();
        JPanel primaryFactor = createPrimaryFactorPanel();
        JPanel reportedLocation = createReportedLocationPanel();
        JPanel media = createMediaPanel();

        panel.add(year);
        panel.add(weekend);
        panel.add(hour);
        panel.add(involvedCars);
        panel.add(injuryType);
        panel.add(primaryFactor);
        panel.add(reportedLocation);
        panel.add(media);

        panel.setBorder(new TitledBorder(new EtchedBorder(), "Seleziona i campi da visualizzare"));
        return panel;
    }

    public JPanel createYearPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkYear = new JCheckBox("Year");
        comboYear = new JComboBox();

        ArrayList<Integer> arrayResults = getArrayRiempimentoInt("Year", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboYear.addItem(arrayResults.get(i));
        }

        comboYear.setEnabled(false);
        checkYear.addActionListener(checkListener);

        panel.add(checkYear);
        panel.add(comboYear);

        return panel;
    }

    public JPanel createWeekendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkWeekend = new JCheckBox("Weekend");
        comboWeekend = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("Weekend", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboWeekend.addItem(arrayResults.get(i));
        }

        comboWeekend.setEnabled(false);
        checkWeekend.addActionListener(checkListener);

        panel.add(checkWeekend);
        panel.add(comboWeekend);
        return panel;
    }

    public JPanel createHourPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkHour = new JCheckBox("Hour");
        comboHour = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("Hour", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboHour.addItem(arrayResults.get(i));
        }

        comboHour.setEnabled(false);
        checkHour.addActionListener(checkListener);

        panel.add(checkHour);
        panel.add(comboHour);
        return panel;
    }

    public JPanel createInvolvedCarsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkInvolvedCars = new JCheckBox("Involved Cars");

        checkInvolvedCars.addActionListener(checkListener);

        panel.add(checkInvolvedCars);

        return panel;
    }

    public JPanel createInjuryTypePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkInjuryType = new JCheckBox("Injury Type");
        comboInjuryType = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("InjuryType", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboInjuryType.addItem(arrayResults.get(i));

        }

        comboInjuryType.setEnabled(false);
        checkInjuryType.addActionListener(checkListener);

        panel.add(checkInjuryType);
        panel.add(comboInjuryType);
        return panel;
    }

    public JPanel createPrimaryFactorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkPrimaryFactor = new JCheckBox("Primary Factor");
        comboPrimaryFactor = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("PrimaryFactor", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboPrimaryFactor.addItem(arrayResults.get(i));
        }

        comboPrimaryFactor.setEnabled(false);
        checkPrimaryFactor.addActionListener(checkListener);

        panel.add(checkPrimaryFactor);
        panel.add(comboPrimaryFactor);
        return panel;
    }

    public JPanel createReportedLocationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkReportedLocation = new JCheckBox("Reported Location");
        comboReportedLocation = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("ReportedLocation", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboReportedLocation.addItem(arrayResults.get(i));
        }

        comboReportedLocation.setEnabled(false);
        checkReportedLocation.addActionListener(checkListener);

        panel.add(checkReportedLocation);
        panel.add(comboReportedLocation);
        return panel;
    }

    public JPanel createMediaPanel() {

        JPanel panel = new JPanel();
        comboMediaMC = new JComboBox();
        panel.setLayout(new GridLayout(1, 1));
        ButtonGroup group = new ButtonGroup();
        radioMinore = new JRadioButton("Minore di:");
        radioMaggiore = new JRadioButton("Maggiore di:");
        radioUguale = new JRadioButton("Uguale a:");
        radioMaggiore.setEnabled(false);
        radioMinore.setEnabled(false);
        radioUguale.setEnabled(false);
        comboMediaMC.setEnabled(false);
        btnMedia = new JButton("Media");
        ArrayList<Integer> arrayResults = getArrayRiempimentoInt("InvolvedCars", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
            comboMediaMC.addItem(arrayResults.get(i));
        }

        panel.add(radioMinore);
        panel.add(radioMaggiore);
        panel.add(radioUguale);
        panel.add(comboMediaMC);
        panel.add(btnMedia);
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Operazioni su numero di macchine coinvolte"));
        group.add(radioMaggiore);
        group.add(radioMinore);
        group.add(radioUguale);
        ActionListener listener = new clickButtonMedia();
        btnMedia.addActionListener(listener);
        return panel;

    }

    public ArrayList<Document> calcolaQuery() {

        ArrayList<Document> results = new ArrayList<>();
        ArrayList<Bson> filters = new ArrayList<Bson>();

        results = coll_full.find().into(new ArrayList<Document>());
        colNames.add("ID");
        colID.add("MasterRecordNumber");

        if (checkYear.isSelected()) {
            flagSelezionato = true;
            colNames.add("Year");
            colID.add("Year");

            if (!(comboYear.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("Year", comboYear.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("Year"));
            }
        }

        if (checkWeekend.isSelected()) {
            flagSelezionato = true;
            colNames.add("Weekend");
            colID.add("Weekend");

            if (!(comboWeekend.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("Weekend", comboWeekend.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("Weekend"));
            }
        }

        if (checkHour.isSelected()) {
            flagSelezionato = true;
            colNames.add("Hour");
            colID.add("Hour");
            if (!(comboHour.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("Hour", comboHour.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("Hour"));
            }
        }

        if (checkInvolvedCars.isSelected()) {
            flagSelezionato = true;
            colNames.add("InvolvedCars");
            colID.add("InvolvedCars");

        }

        if (checkInjuryType.isSelected()) {
            flagSelezionato = true;
            colNames.add("Injury Type");
            colID.add("InjuryType");

            if (!(comboInjuryType.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("InjuryType", comboInjuryType.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("InjuryType"));
            }
        }

        if (checkPrimaryFactor.isSelected()) {
            flagSelezionato = true;
            colNames.add("Primary Factor");
            colID.add("PrimaryFactor");

            if (!(comboPrimaryFactor.getSelectedItem().equals(""))) {

                filters.add(Aggregates.match(Filters.eq("PrimaryFactor", comboPrimaryFactor.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("PrimaryFactor"));
            }
        }

        if (radioMinore.isSelected()) {
            flagSelezionato = true;
            int valoreRicerca = Integer.parseInt(comboMediaMC.getSelectedItem().toString());
            filters.add(Aggregates.match(Filters.lt("InvolvedCars", valoreRicerca)));
            coll_full.createIndex(Indexes.ascending("InvolvedCars"));

        } else if (radioMaggiore.isSelected()) {
            flagSelezionato = true;
            int valoreRicerca = Integer.parseInt(comboMediaMC.getSelectedItem().toString());
            filters.add(Aggregates.match(Filters.gt("InvolvedCars", valoreRicerca)));
            coll_full.createIndex(Indexes.ascending("InvolvedCars"));

        }
        if (radioUguale.isSelected()) {
            flagSelezionato = true;
            int valoreRicerca = Integer.parseInt(comboMediaMC.getSelectedItem().toString());
            filters.add(Aggregates.match(Filters.eq("InvolvedCars", valoreRicerca)));
            coll_full.createIndex(Indexes.ascending("InvolvedCars"));
        }

        if (checkReportedLocation.isSelected()) {
            flagSelezionato = true;
            colNames.add("ReportedLocation");
            colID.add("ReportedLocation");
            if (!(comboReportedLocation.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("ReportedLocation", comboReportedLocation.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("ReportedLocation"));
            }
        }
        if (flagMedia) {
            filters.add(Aggregates.group("_id",
                    new BsonField("avgMediaMacchine", new BsonDocument("$avg", new BsonString("$InvolvedCars")))));
        }
        if (filters.size() > 0) {
            results = coll_full.aggregate(filters).into(new ArrayList<Document>());


        }

        return results;
    }

    public JPanel mostraQueryPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton btnVisualizza = new JButton("Visualizza");
        panel.add(btnVisualizza);

        class clickButton implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                ArrayList<Document> results = calcolaQuery();
                JButton btn = (JButton) e.getSource();
                if (btn.getText().equals("Visualizza")) {

                    if (!flagSelezionato) {
                        JOptionPane.showMessageDialog(null, "Selezionare almeno un campo", "Ricerca fallita",
                                JOptionPane.PLAIN_MESSAGE);
                        return;
                    }

                    if (results.size() > 0) {

                        System.out.println(results);

                        Frame_AggregazioneResult frame = new Frame_AggregazioneResult(results, colNames, colID);
                        frame.setLocationRelativeTo(null);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
                        Frame_Aggregazione.this.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Nessun risultato trovato", "Ricerca fallita",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
                results.clear();
                colNames.clear();
                colID.clear();

            }
        }

        ActionListener listener = new clickButton();
        btnVisualizza.addActionListener(listener);

        return panel;
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

    class clickCheck implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (checkYear.isSelected())
                comboYear.setEnabled(true);
            else
                comboYear.setEnabled(false);

            if (checkWeekend.isSelected())
                comboWeekend.setEnabled(true);
            else
                comboWeekend.setEnabled(false);

            if (checkHour.isSelected())
                comboHour.setEnabled(true);
            else
                comboHour.setEnabled(false);

            if (checkInvolvedCars.isSelected()) {
                comboMediaMC.setEnabled(true);
                radioMaggiore.setEnabled(true);
                radioMinore.setEnabled(true);
                radioUguale.setEnabled(true);
            } else {
                comboMediaMC.setEnabled(false);
                radioMaggiore.setEnabled(false);
                radioMinore.setEnabled(false);
                radioUguale.setEnabled(false);
            }

            if (checkInjuryType.isSelected())
                comboInjuryType.setEnabled(true);
            else
                comboInjuryType.setEnabled(false);

            if (checkPrimaryFactor.isSelected())
                comboPrimaryFactor.setEnabled(true);
            else
                comboPrimaryFactor.setEnabled(false);

            if (checkReportedLocation.isSelected())
                comboReportedLocation.setEnabled(true);
            else
                comboReportedLocation.setEnabled(false);

        }
    }

    class clickButtonMedia implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            flagMedia = true;
            ArrayList<Document> results = calcolaQuery();

            if (results.size() != 1) {
                JOptionPane.showMessageDialog(null, "Non e' stato possibile calcolare la media.", "Errore",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                JOptionPane.showMessageDialog(null,
                        "La media calcolata con i campi selezionati e' la seguente: "
                                + df.format(results.get(0).getDouble("avgMediaMacchine")) + "",
                        "Media", JOptionPane.PLAIN_MESSAGE);

            }

        }
    }
}