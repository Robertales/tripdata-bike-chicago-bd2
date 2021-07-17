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

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

import unisa.progettobd2.result.Frame_AggregazioneResult;
import unisa.progettobd2.service.DatabaseManager;
import unisa.progettobd2.starter.Starter;

public class Frame_Aggregazione extends JFrame {

    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 1000;

    private ArrayList<String> colID;
    private ArrayList<String> colNames;

    private JCheckBox checkMonth, checkDay, checkHourStartDa, checkHourStartA, checkHourEndDa, checkHourEndA, checkStazioneStart, checkStazioneEnd, checkBikeType, checkMember;


    private JComboBox comboMonth, comboDay, comboHourStartDa, comboHourStartA, comboHourEndDa, comboHourEndA, comboStazioneStart, comboStazioneEnd, comboBikeType, comboMember;

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
        panel.setLayout(new GridLayout(8, 1));

        JPanel month = createMonthPanel();
        JPanel day = createDayPanel();
        JPanel hourStart = createHourPanelStart();
        JPanel hourEnd = createHourPanelEnd();
        //JPanel hourEnd = createHourEndPanel();
        JPanel stationStart = checkStazioneStartPanel();
        JPanel stationEnd = checkStazioneEndPanel();
        JPanel bikeType = createBikeTypePanel();
        JPanel member = createMemberPanel();

        panel.add(month);
        panel.add(day);
        panel.add(hourStart);
        panel.add(hourEnd);
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
      

    public JPanel createHourPanelStart() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));

        checkHourStartDa = new JCheckBox("Hour Start da");
        checkHourStartA = new JCheckBox("Hour Start a");
        comboHourStartDa = new JComboBox();
        comboHourStartA = new JComboBox();
        JLabel label = new JLabel();
        ArrayList<String> arrayResultsHourStart = getArrayRiempimento("hourStart", coll_full);
        for (int i = 0; i < arrayResultsHourStart.size(); i++) {
        	comboHourStartDa.addItem(arrayResultsHourStart.get(i));
        }

        ArrayList<String> arrayResultsHourEnd = getArrayRiempimento("hourStart", coll_full);
        for (int i = 0; i < arrayResultsHourEnd.size(); i++) {
        	comboHourStartA.addItem(arrayResultsHourEnd.get(i));
        }
        
        comboHourStartDa.setEnabled(false);
        checkHourStartDa.addActionListener(checkListener);
        comboHourStartA.setEnabled(false);
        checkHourStartA.addActionListener(checkListener);
        
        panel.add(checkHourStartDa);
        panel.add(comboHourStartDa);
        panel.add(checkHourStartA);
        panel.add(comboHourStartA);
        return panel;
    }
    
    public JPanel createHourPanelEnd() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));

        checkHourEndDa = new JCheckBox("Hour End da:");
        checkHourEndA = new JCheckBox("Hour End a:");
        comboHourEndDa = new JComboBox();
        comboHourEndA = new JComboBox();
        JLabel label = new JLabel();
        ArrayList<String> arrayResultsHourStart = getArrayRiempimento("hourEnd", coll_full);
        for (int i = 0; i < arrayResultsHourStart.size(); i++) {
        	comboHourEndDa.addItem(arrayResultsHourStart.get(i));
        }

        ArrayList<String> arrayResultsHourEnd = getArrayRiempimento("hourEnd", coll_full);
        for (int i = 0; i < arrayResultsHourEnd.size(); i++) {
        	comboHourEndA.addItem(arrayResultsHourEnd.get(i));
        }
        
        comboHourEndDa.setEnabled(false);
        checkHourEndDa.addActionListener(checkListener);
        comboHourEndA.setEnabled(false);
        checkHourEndA.addActionListener(checkListener);
        
        panel.add(checkHourEndDa);
        panel.add(comboHourEndDa);
        panel.add(checkHourEndA);
        panel.add(comboHourEndA);
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

        if (checkHourStartDa.isSelected()) {
            flagSelezionato = true;
            colNames.add("HourStart");
            colID.add("hourStart");
            if (checkHourStartA.isSelected()) {
            	if (!(comboHourStartDa.getSelectedItem().equals("")) && !(comboHourStartA.getSelectedItem().equals(""))) {
                    filters.add(Aggregates.match(Filters.gte("hourStart", comboHourStartDa.getSelectedItem())));
                    filters.add(Aggregates.match(Filters.lte("hourStart", comboHourStartA.getSelectedItem())));
                    coll_full.createIndex(Indexes.ascending("hourStart"));
                }
            } else if (!(checkHourStartA.isSelected()) && !(checkHourEndA.isSelected()) && !(checkHourEndDa.isSelected())){
            	if (!(comboHourStartDa.getSelectedItem().equals(""))) {
                    filters.add(Aggregates.match(Filters.gte("hourStart", comboHourStartDa.getSelectedItem())));
                    coll_full.createIndex(Indexes.ascending("hourStart"));
                }
            } else if (checkHourEndA.isSelected() && !(checkHourStartA.isSelected()) && !(checkHourEndDa.isSelected())){
            	if (!(comboHourStartDa.getSelectedItem().equals("")) && !(comboHourEndA.getSelectedItem().equals(""))) {
            		colNames.add("HourEnd");
                    colID.add("hourEnd");
                    filters.add(Aggregates.match(Filters.gte("hourStart", comboHourStartDa.getSelectedItem())));
                    filters.add(Aggregates.match(Filters.lte("hourEnd", comboHourEndA.getSelectedItem())));
                    coll_full.createIndex(Indexes.ascending("hourStart"));
                }
            }
            
        }
        
        if (checkHourEndDa.isSelected()) {
            flagSelezionato = true;
            colNames.add("HourEnd");
            colID.add("hourEnd");
            if (checkHourEndA.isSelected()) {
            	if (!(comboHourEndDa.getSelectedItem().equals("")) && !(comboHourEndA.getSelectedItem().equals(""))) {
                    filters.add(Aggregates.match(Filters.gte("hourEnd", comboHourEndDa.getSelectedItem())));
                    filters.add(Aggregates.match(Filters.lte("hourEnd", comboHourEndA.getSelectedItem())));
                    coll_full.createIndex(Indexes.ascending("hourEnd"));
                }
            } else if (!(checkHourEndA.isSelected())) {
            	if (!(comboHourEndDa.getSelectedItem().equals(""))) {
                    filters.add(Aggregates.match(Filters.gte("hourEnd", comboHourEndDa.getSelectedItem())));
                    coll_full.createIndex(Indexes.ascending("hourEnd"));
                }
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
            colNames.add("BikeType");
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
                        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/image/bike3_2.png")));
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

            if (checkHourStartDa.isSelected())
            	comboHourStartDa.setEnabled(true);
            else
            	comboHourStartDa.setEnabled(false);
            
            if (checkHourStartA.isSelected())
            	comboHourStartA.setEnabled(true);
            else
            	comboHourStartA.setEnabled(false);
            
            if (checkHourEndDa.isSelected())
            	comboHourEndDa.setEnabled(true);
            else
            	comboHourEndDa.setEnabled(false);
            
            if (checkHourEndA.isSelected())
            	comboHourEndA.setEnabled(true);
            else
            	comboHourEndA.setEnabled(false);
            
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
            
            if (checkHourStartDa.isSelected() && checkHourStartA.isSelected() && checkHourEndDa.isSelected() && checkHourEndA.isSelected()) {
            	comboHourStartDa.setEnabled(false);
            	comboHourStartA.setEnabled(false);
            	comboHourEndDa.setEnabled(false);
            	comboHourEndA.setEnabled(false);
            }
            
            if (checkHourStartDa.isSelected() && checkHourEndDa.isSelected() && checkHourEndA.isSelected()) {
            	comboHourStartDa.setEnabled(false);
            	comboHourEndDa.setEnabled(false);
            	comboHourEndA.setEnabled(false);
            }
            
            if (checkHourStartA.isSelected() && checkHourEndDa.isSelected() && checkHourEndA.isSelected()) {
            	comboHourStartA.setEnabled(false);
            	comboHourEndDa.setEnabled(false);
            	comboHourEndA.setEnabled(false);
            }
            
            if (checkHourStartDa.isSelected() && checkHourStartA.isSelected() && checkHourEndDa.isSelected()) {
            	comboHourStartDa.setEnabled(false);
            	comboHourStartA.setEnabled(false);
            	comboHourEndDa.setEnabled(false);
            }
            if (checkHourStartDa.isSelected() && checkHourStartA.isSelected() && checkHourEndA.isSelected()) {
            	comboHourStartDa.setEnabled(false);
            	comboHourStartDa.setEnabled(false);
            	comboHourEndA.setEnabled(false);
            }
            if (checkHourStartA.isSelected() && checkHourEndA.isSelected()) {
            	comboHourStartA.setEnabled(false);
            	comboHourEndA.setEnabled(false);
            }

        }
    }
}