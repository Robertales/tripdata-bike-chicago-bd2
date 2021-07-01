package unisa.progettobd.result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

//import unisa.progettobd.frame.Frame_RicercaAnno;
import unisa.progettobd.frame.Frame_RicercaBikeType;
import unisa.progettobd.service.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame_BikeTypeResult extends JFrame {

    private static final long serialVersionUID = 1L;

    public Frame_BikeTypeResult(String bikeType) {
        super();
        Boolean found = false;
        String[] colNames = {"ID", "Type", "Started at", "Start Station Name", "Ended at", "End Station Name", "Member"};

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(colNames);

        JTable table = new JTable();
        table.setSize(this.getWidth(), this.getHeight());
        table.setModel(dtm);

        JScrollPane scrollText = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollText);

        /*MongoCollection<Document> coll = null;
        if (tipo.equals("Weekday")) {

            coll = DatabaseManager.getWeekday();

        } else if (tipo.equals("Weekend")) {

            coll = DatabaseManager.getWeekend();

        }*/
        MongoCollection<Document> coll = DatabaseManager.getBike();
        Document docQuery = new Document().append("rideableType", bikeType);
        coll.createIndex(Indexes.ascending("rideableType"));
        if (coll != null) {
            FindIterable<Document> results = coll.find(docQuery);
            int c = 0;
            for (Document temp : results) {
                found = true;
                c++;
                dtm.addRow(new Object[]{temp.getInteger("id"), temp.getString("rideableType"),
                        temp.getString("startedAt"), temp.getString("startStationName"), temp.getString("endedAt"),
                        temp.getString("endStationName"), temp.getString("memberCasual")});
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
                start.setSize(400, 300);
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
