package unisa.progettobd.result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

import unisa.progettobd.frame.Frame_Aggregazione;
import unisa.progettobd.frame.Frame_RicercaAnno;
import unisa.progettobd.service.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame_AnnoResult extends JFrame {

    private static final long serialVersionUID = 1L;

    public Frame_AnnoResult(String tipo, String anno) {
        super();
        int year = Integer.parseInt(anno);
        MongoCollection<Document> coll = null;
        String[] colNames = {"ID", "Day", "Year", "Involved Cars", "Injury Type", "Primary Factor", "Location"};

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(colNames);


        JTable table = new JTable();
        table.setSize(this.getWidth(), this.getHeight());
        table.setModel(dtm);

        JScrollPane scrollText = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollText);

        if (tipo.equals("Weekday"))

            coll = DatabaseManager.getWeekday();

        else if (tipo.equals("Weekend"))

            coll = DatabaseManager.getWeekend();

        Document docQuery = new Document().append("Year", year);
        coll.createIndex(Indexes.ascending("Year"));

        if (coll != null) {
            FindIterable<Document> results = coll.find(docQuery);
            Boolean found = false;
            int c = 0;
            for (Document temp : results) {
                c++;
                found = true;
                dtm.addRow(new Object[]{temp.getInteger("MasterRecordNumber"), temp.getString("Weekend"),
                        temp.getInteger("Year"), temp.getInteger("InvolvedCars"), temp.getString("InjuryType"),
                        temp.getString("PrimaryFactor"), temp.getString("ReportedLocation")});
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

                Frame_RicercaAnno start = new Frame_RicercaAnno();
                start.setSize(400, 300);
                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // start.setLocationRelativeTo(null);
                start.setVisible(true);
                Frame_AnnoResult.this.dispose();
            }
        }

        ActionListener listener = new clickButton();
        btn.addActionListener(listener);

        return panel;
    }
}
