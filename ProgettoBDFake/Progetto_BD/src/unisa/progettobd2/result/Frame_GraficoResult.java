package unisa.progettobd2.result;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.bson.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.mongodb.client.MongoCollection;
import unisa.progettobd2.service.DatabaseManager;

public class Frame_GraficoResult extends JFrame {

	private static final long serialVersionUID = 1L;
	Document query;
	MongoCollection<Document> coll;
	private DefaultCategoryDataset dataset;
	private ArrayList<String> arrayOra = new ArrayList<String>(List.of("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
	private ArrayList<Document> arrayMinuti = new ArrayList<Document>();

	public Frame_GraficoResult(String applicationTitle, String chartTitle, String tipo) {
		super(applicationTitle);
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, tipo, "Biciclette", createDataset(tipo),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset(String tipoGrafico) {
		coll = DatabaseManager.getBike();
		dataset = new DefaultCategoryDataset();
		if (tipoGrafico.equals("hourStart")) {
			
			ArrayList<String> array = new ArrayList<String>();
			
			array = coll.distinct(tipoGrafico, String.class).into(new ArrayList<String>());
			Collections.sort(array);
			
			for (int c = 0; c < arrayOra.size(); c++) {
				for (int i = 0; i < array.size(); i++) {
					if (Integer.parseInt(arrayOra.get(c))==Integer.parseInt(array.get(i).split(":")[0])) {
						query = new Document().append(tipoGrafico, array.get(i));
						arrayMinuti.add(query);
					}
				}
				
				int sum = 0;
				for (int i = 0; i < arrayMinuti.size(); i++) {
					sum+=coll.count(arrayMinuti.get(i));	
				}
				
				
				dataset.addValue(sum, "dato", arrayOra.get(c));
				arrayMinuti.clear();
			}

			
			
		} else if (tipoGrafico.equals("rideableType"))  {
			ArrayList<String> array = new ArrayList<String>();
			array = coll.distinct(tipoGrafico, String.class).into(new ArrayList<String>());
			Collections.sort(array);
			for (int i = 0; i < array.size(); i++) {
				query = new Document().append(tipoGrafico, array.get(i));
				dataset.addValue(coll.count(query), "dato", array.get(i));
			}
		} else if (tipoGrafico.equals("month"))  {
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
