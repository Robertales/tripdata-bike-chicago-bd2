package unisa.progettobd.result;

import java.util.ArrayList;

import java.util.Collections;

import javax.swing.JFrame;

import org.bson.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.mongodb.client.MongoCollection;
import unisa.progettobd.service.DatabaseManager;

public class Frame_GraficoResult extends JFrame {

	private static final long serialVersionUID = 1L;
	Document query;
	MongoCollection<Document> coll;
	private DefaultCategoryDataset dataset;
	

	public Frame_GraficoResult(String applicationTitle, String chartTitle, String tipo) {
		super(applicationTitle);
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, tipo, "incidenti", createDataset(tipo),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset(String tipoGrafico) {
		coll = DatabaseManager.getFull();
		dataset = new DefaultCategoryDataset();
		if (tipoGrafico.equals("Hour")) {
			
			ArrayList<String> array = new ArrayList<String>();
			array = coll.distinct(tipoGrafico, String.class).into(new ArrayList<String>());
			Collections.sort(array);
			
			for (int i = 0; i < array.size(); i++) {
				query = new Document().append(tipoGrafico, array.get(i));
				dataset.addValue(coll.count(query), "dato", array.get(i));
				
			}
		} else {
			ArrayList<Integer> array = new ArrayList<Integer>();
			array = coll.distinct(tipoGrafico, Integer.class).into(new ArrayList<Integer>());
			Collections.sort(array);
			for (int i = 0; i < array.size(); i++) {
				query = new Document().append(tipoGrafico, array.get(i));
				dataset.addValue(coll.count(query), "dato", array.get(i));
			}
		}

		return dataset;
	}
}
