package unisa.progettobd2.frame;

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

import unisa.progettobd2.result.Frame_AggregazioneResult;
import unisa.progettobd2.service.DatabaseManager;

public class Frame_Aggregazione extends JFrame {

    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 1000;

    private ArrayList<String> colID;
    private ArrayList<String> colNames;

    private JCheckBox checkMonth, checkDay, checkHourStart, checkStazioneStart, checkStazioneEnd, checkBikeType, checkMember;


    private JComboBox comboMonth, comboDay, comboHourStart, comboHourEnd, comboStazioneStart, comboStazioneEnd, comboBikeType, comboMember;

    private MongoCollection<Document> coll_full;

    private ActionListener checkListener;
    private JButton btnMedia;

    private boolean flagSelezionato;

    public Frame_Aggregazione() {
        // setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // setResizable(false);
        colID = new ArrayList<String>();
        colNames = new ArrayList<String>();

        coll_full = DatabaseManager.getBike();
        checkListener = new clickCheck();

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

        JPanel month = createMonthPanel();
        JPanel day = createDayPanel();
        JPanel hourStart = createHourPanel();
        //JPanel hourEnd = createHourEndPanel();
        JPanel stationStart = checkStazioneStartPanel();
        JPanel stationEnd = checkStazioneEndPanel();
        JPanel bikeType = createBikeTypePanel();
        JPanel member = createMemberPanel();

        panel.add(month);
        panel.add(day);
        panel.add(hourStart);
        //panel.add(hourEnd);
        panel.add(stationStart);
        panel.add(stationEnd);
        panel.add(bikeType);
        panel.add(member);

        panel.setBorder(new TitledBorder(new EtchedBorder(), "Seleziona i campi da visualizzare"));
        return panel;
    }

    public JPanel createMonthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        
        checkMonth = new JCheckBox("Month");
        comboMonth = new JComboBox();
        
        ArrayList<Integer> arrayResultsGiorno = getArrayRiempimentoInt("dayStart", coll_full);
        ArrayList<Integer> arrayResultsMonth = getArrayRiempimentoInt("month", coll_full);
        
        for (int i = 0; i < arrayResultsMonth.size(); i++) {
        	comboMonth.addItem(arrayResultsMonth.get(i));
        }
        
        
        comboMonth.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (comboMonth.getSelectedIndex() == 10 || comboMonth.getSelectedIndex() == 3 || 
                		comboMonth.getSelectedIndex() == 5 || comboMonth.getSelectedIndex() == 8) {
                	comboDay.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                    	comboDay.addItem(arrayResultsGiorno.get(i));
                    	if (i==29) {
                    		break;                    		
                    	}
                    }
                } else if(comboMonth.getSelectedIndex() == 1) {
                	comboDay.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                		comboDay.addItem(arrayResultsGiorno.get(i));
                    	if (i==27) {
                    		break;                    		
                    	}
                    }
                	
                } else if(comboMonth.getSelectedIndex() == 0 || comboMonth.getSelectedIndex() == 2 || 
                		comboMonth.getSelectedIndex() == 4 || comboMonth.getSelectedIndex() == 6
                		|| comboMonth.getSelectedIndex() == 7 || comboMonth.getSelectedIndex() == 9 || comboMonth.getSelectedIndex() == 11) {
                	comboDay.removeAllItems();
                	for (int i = 0; i < arrayResultsGiorno.size(); i++) {
                		comboDay.addItem(arrayResultsGiorno.get(i));
                    	if (i==30) {
                    		break;                    		
                    	}
                    }
                }
            }
        });

        comboMonth.setEnabled(false);
        checkMonth.addActionListener(checkListener);
        
        panel.add(checkMonth);
        panel.add(comboMonth);

        return panel;
    }
    
    public JPanel createDayPanel() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1, 2));
    	checkDay = new JCheckBox("Day");
        comboDay = new JComboBox();
        panel.add(checkDay);
        panel.add(comboDay);

        comboDay.setEnabled(false);
        checkDay.addActionListener(checkListener);
        
        return panel;
    }
      

    public JPanel createHourPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));

        checkHourStart = new JCheckBox("Hour");
        comboHourStart = new JComboBox();
        comboHourEnd = new JComboBox();
        JLabel label = new JLabel();
        ArrayList<String> arrayResultsHourStart = getArrayRiempimento("hourStart", coll_full);
        for (int i = 0; i < arrayResultsHourStart.size(); i++) {
            comboHourStart.addItem(arrayResultsHourStart.get(i));
        }

        ArrayList<String> arrayResultsHourEnd = getArrayRiempimento("hourEnd", coll_full);
        for (int i = 0; i < arrayResultsHourEnd.size(); i++) {
            comboHourEnd.addItem(arrayResultsHourEnd.get(i));
        }
        
        comboHourStart.setEnabled(false);
        checkHourStart.addActionListener(checkListener);
        comboHourEnd.setEnabled(false);
        
        panel.add(checkHourStart);
        panel.add(comboHourStart);
        panel.add(label);
        panel.add(comboHourEnd);
        return panel;
    }
    

    public JPanel checkStazioneStartPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkStazioneStart = new JCheckBox("Station Start Name");
        comboStazioneStart = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("startStationName", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
        	comboStazioneStart.addItem(arrayResults.get(i));

        }

        comboStazioneStart.setEnabled(false);
        checkStazioneStart.addActionListener(checkListener);

        panel.add(checkStazioneStart);
        panel.add(comboStazioneStart);
        return panel;
    }
    
    public JPanel checkStazioneEndPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkStazioneEnd = new JCheckBox("Station End Name");
        comboStazioneEnd = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("endStationName", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
        	comboStazioneEnd.addItem(arrayResults.get(i));

        }

        comboStazioneEnd.setEnabled(false);
        checkStazioneEnd.addActionListener(checkListener);

        panel.add(checkStazioneEnd);
        panel.add(comboStazioneEnd);
        return panel;
    }
    
    public JPanel createBikeTypePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkBikeType = new JCheckBox("Tipo di bicicletta");
        comboBikeType = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("rideableType", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
        	comboBikeType.addItem(arrayResults.get(i));
        }

        comboBikeType.setEnabled(false);
        checkBikeType.addActionListener(checkListener);

        panel.add(checkBikeType);
        panel.add(comboBikeType);
        return panel;
    }

    public JPanel createMemberPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        checkMember = new JCheckBox("Member or Casual");
        comboMember = new JComboBox();

        ArrayList<String> arrayResults = getArrayRiempimento("memberCasual", coll_full);
        for (int i = 0; i < arrayResults.size(); i++) {
        	comboMember.addItem(arrayResults.get(i));
        }

        comboMember.setEnabled(false);
        checkMember.addActionListener(checkListener);

        panel.add(checkMember);
        panel.add(comboMember);
        return panel;
    }

    
    public ArrayList<Document> calcolaQuery() {

        ArrayList<Document> results = new ArrayList<>();
        ArrayList<Bson> filters = new ArrayList<Bson>();

        results = coll_full.find().into(new ArrayList<Document>());
        System.out.println(results.size());
        colNames.add("ID");
        colID.add("id");

        if (checkMonth.isSelected()) {
            flagSelezionato = true;
            colNames.add("Month");
            colID.add("month");

            if (!(comboMonth.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("month", comboMonth.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("month"));
            }
        }

        if (checkDay.isSelected()) {
            flagSelezionato = true;
            colNames.add("DayStart");
            colID.add("dayStart");

            if (!(comboDay.getSelectedItem().equals(""))) {
            	filters.add(Aggregates.match(Filters.eq("dayStart", comboDay.getSelectedItem())));
            	System.out.println(comboDay.getSelectedItem());
                coll_full.createIndex(Indexes.ascending("dayStart"));
            }
        }

        if (checkHourStart.isSelected()) {
            flagSelezionato = true;
            colNames.add("HourStart");
            colID.add("hourStart");
            colNames.add("HourEnd");
            colID.add("hourEnd");
            if (!(comboHourStart.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("hourStart", comboHourStart.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("hourStart"));
                filters.add(Aggregates.match(Filters.eq("hourEnd", comboHourEnd.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("hourEnd"));
            }
        }


        if (checkStazioneStart.isSelected()) {
            flagSelezionato = true;
            colNames.add("Start Station Name");
            colID.add("startStationName");

            if (!(comboStazioneStart.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("startStationName", comboStazioneStart.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("startStationName"));
            }
        }

        if (checkStazioneEnd.isSelected()) {
            flagSelezionato = true;
            colNames.add("End Station Name");
            colID.add("endStationName");

            if (!(comboStazioneEnd.getSelectedItem().equals(""))) {

                filters.add(Aggregates.match(Filters.eq("endStationName", comboStazioneEnd.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("endStationName"));
            }
        }
        
        if (checkBikeType.isSelected()) {
            flagSelezionato = true;
            colNames.add("Member");
            colID.add("rideableType");
            if (!(comboBikeType.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("rideableType", comboBikeType.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("rideableType"));
            }
        }
        

        if (checkMember.isSelected()) {
            flagSelezionato = true;
            colNames.add("Member");
            colID.add("memberCasual");
            if (!(comboMember.getSelectedItem().equals(""))) {
                filters.add(Aggregates.match(Filters.eq("memberCasual", comboMember.getSelectedItem())));
                coll_full.createIndex(Indexes.ascending("memberCasual"));
            }
        }
        
        if (filters.size() > 0) {
        	System.out.println(filters.size()); //1
            results = coll_full.aggregate(filters).into(new ArrayList<Document>());
            System.out.println(results.toString()); 
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
                System.out.println(results.toString());
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
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

            if (checkMonth.isSelected())
                comboMonth.setEnabled(true);
            else
            	comboMonth.setEnabled(false);

            if (checkDay.isSelected())
                comboDay.setEnabled(true);
            else
            	comboDay.setEnabled(false);

            if (checkHourStart.isSelected())
            	comboHourStart.setEnabled(true);
            else
            	comboHourStart.setEnabled(false);
            
            if (checkHourStart.isSelected())
            	comboHourEnd.setEnabled(true);
            else
            	comboHourEnd.setEnabled(false);
            
            if (checkStazioneStart.isSelected())
            	comboStazioneStart.setEnabled(true);
            else
            	comboStazioneStart.setEnabled(false);

            if (checkStazioneEnd.isSelected())
            	comboStazioneEnd.setEnabled(true);
            else
            	comboStazioneEnd.setEnabled(false);
            
            if (checkBikeType.isSelected())
            	comboBikeType.setEnabled(true);
            else
            	comboBikeType.setEnabled(false);
            
            if (checkMember.isSelected())
                comboMember.setEnabled(true);
            else
            	comboMember.setEnabled(false);

        }
    }
}