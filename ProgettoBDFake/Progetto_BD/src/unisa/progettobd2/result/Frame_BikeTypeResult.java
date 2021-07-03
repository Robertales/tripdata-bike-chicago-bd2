package unisa.progettobd2.result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

import unisa.progettobd2.frame.Frame_RicercaBikeType;
import unisa.progettobd2.service.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame_BikeTypeResult extends JFrame {

    private static final long serialVersionUID = 1L;

    public Frame_BikeTypeResult(String bikeType, String month, String giorno) {
        super();
        Boolean found = false;
        int mese = Integer.parseInt(month);
        int day = Integer.parseInt(giorno);
        String[] colNames = {"ID", "Type", "Month", "DayStart", "HourStart", "Start Station Name", "DayEnd", "HourEnd", "End Station Name", "Member"};

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(colNames);

        JTable table = new JTable();
        table.setSize(this.getWidth(), this.getHeight());
        table.setModel(dtm);

        JScrollPane scrollText = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollText);

        MongoCollection<Document> coll = DatabaseManager.getBike();
        Document docQuery = new Document().append("rideableType", bikeType);
        docQuery.append("month", mese);
        docQuery.append("dayStart", day);
        coll.createIndex(Indexes.ascending("id"));
        if (coll != null) {
            FindIterable<Document> results = coll.find(docQuery);
            int c = 0;
            for (Document temp : results) {
                found = true;
                c++;
                dtm.addRow(new Object[]{temp.getInteger("id"), temp.getString("rideableType"), temp.getInteger("month"), temp.getInteger("dayStart"), temp.getString("hourStart"),
                		temp.getString("startStationName"), temp.getInteger("dayEnd"), temp.getString("hourEnd"), temp.getString("endStationName"), temp.getString("memberCasual")});
            }

            if (!found)
                JOptionPane.showMessageDialog(null, "Nessun risultato trovato", "Ricerca fallita",
                        JOptionPane.PLAIN_MESSAGE);

            JPanel panel = createEndPanel(c);
            this.add(panel, BorderLayout.SOUTH);
        }

        DatabaseManager.close();

    }

    public JPanel createEndPanel(int risultati) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Trovati " + risultati + " risultati    ");
        JButton btn = new JButton("Indietro");
        panel.add(label, BorderLayout.EAST);
        panel.add(btn, BorderLayout.WEST);


        class clickButton implements ActionListener {

            public void actionPerformed(ActionEvent e) {

            	Frame_RicercaBikeType start = new Frame_RicercaBikeType();
                start.setSize(600, 500);
                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // start.setLocationRelativeTo(null);
                start.setVisible(true);
                Frame_BikeTypeResult.this.dispose();
            }
        }

        ActionListener listener = new clickButton();
        btn.addActionListener(listener);

        return panel;
    }
}
