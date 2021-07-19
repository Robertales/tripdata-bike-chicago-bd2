package unisa.progettobd2.result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

/*import unisa.progettobd2.frame.Frame_Aggregazione;*/
import unisa.progettobd2.frame.Frame_RicercaMese;
import unisa.progettobd2.service.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame_MeseResult extends JFrame {

    private static final long serialVersionUID = 1L;

    public Frame_MeseResult(String tipo, String mese) {
        super();
        int month = Integer.parseInt(mese);
        MongoCollection<Document> coll = null;
        String[] colNames = {"ID", "Type", "Month", "DayStart", "HourStart", "Start Station Name", "DayEnd", "HourEnd", "End Station Name", "Member"};

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(colNames);


        JTable table = new JTable();
        table.setSize(this.getWidth(), this.getHeight());
        table.setModel(dtm);

        JScrollPane scrollText = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollText);

        Document docQuery = new Document().append("month", month);
        
        if (tipo.equals("Member")) {

            coll = DatabaseManager.getBike();
        	docQuery.append("memberCasual", "member");
        }
        
        else if (tipo.equals("Casual")) {

            coll = DatabaseManager.getBike();
        	docQuery.append("memberCasual", "casual");
        	
        }

        coll.createIndex(Indexes.ascending("id"));

        if (coll != null) {
            FindIterable<Document> results = coll.find(docQuery);
            Boolean found = false;
            int c = 0;
            for (Document temp : results) {
                c++;
                found = true;
                dtm.addRow(new Object[]{temp.getInteger("id"), temp.getString("rideableType"), temp.getInteger("month"), 
                		temp.getInteger("dayStart"), temp.getString("hourStart"), temp.getString("startStationName"), 
                		temp.getInteger("dayEnd"), temp.getString("hourEnd"), 
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
        panel.add(label, BorderLayout.EAST);
        

        class clickButton implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                Frame_RicercaMese start = new Frame_RicercaMese();
                start.setSize(400, 300);
                start.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // start.setLocationRelativeTo(null);
                start.setVisible(true);
                Frame_MeseResult.this.dispose();
            }
        }

   
        return panel;
    }
}
