package unisa.progettobd2.result;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;
import unisa.progettobd2.frame.Frame_Aggregazione;
import unisa.progettobd2.service.DatabaseManager;

public class Frame_AggregazioneResult extends JFrame {

    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 1300;
    private final int FRAME_HEIGHT = 700;
    private ArrayList<Document> risultati;
    private ArrayList<String> colonneNome;
    private ArrayList<String> colonneID;

    public Frame_AggregazioneResult(ArrayList<Document> ris, ArrayList<String> colNome, ArrayList<String> colID) {
        super();
        risultati = ris;
        colonneNome = colNome;
        colonneID = colID;

        this.setLayout(new BorderLayout());

        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        DefaultTableModel dtm = new DefaultTableModel();
        // .toarray perche' setcolum ha bisogno di un vector mentre colonneNome e' un
        // arraylist
        dtm.setColumnIdentifiers(colonneNome.toArray());

        JTable table = new JTable();

        table.setSize(this.getWidth(), this.getHeight());
        table.setModel(dtm);

        JScrollPane scrollText = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollText);
//stampa
        for (Document temp : risultati) {

            Object[] elemento = new Object[colonneID.size() + 1];
            for (int i = 0; i < colonneID.size(); i++) {
                elemento[i] = temp.get(colonneID.get(i).toString());
            }
            dtm.addRow(elemento);

            JPanel panel = createEndPanel(risultati.size());
            this.add(panel, BorderLayout.SOUTH);
            DatabaseManager.close();
        }
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

                Frame_Aggregazione start = new Frame_Aggregazione();
                start.setSize(1000, 600);
                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // start.setLocationRelativeTo(null);
                start.setVisible(true);
                Frame_AggregazioneResult.this.dispose();
            }
        }

        ActionListener listener = new clickButton();
        btn.addActionListener(listener);

        return panel;
    }

}