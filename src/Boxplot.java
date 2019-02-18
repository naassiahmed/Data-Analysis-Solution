/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Log;
import org.jfree.util.LogContext;


public class Boxplot extends ApplicationFrame {
    
     private static final LogContext LOGGER = Log.createContext(Boxplot.class);
     
      public Boxplot(final String title, String attribute,Object[][] data,int index) {

        super(title);
        
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset(title,data,index);
        
        final CategoryAxis xAxis = new CategoryAxis(attribute);
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setSeriesVisibleInLegend(false);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
            "Boite à moustache",
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            true
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        setContentPane(chartPanel);

    }
      
      private BoxAndWhiskerCategoryDataset createSampleDataset(String type,Object[][] data, int index) {
        
        final DefaultBoxAndWhiskerCategoryDataset dataset 
            = new DefaultBoxAndWhiskerCategoryDataset();
        final List list = new ArrayList();
        
        for (int i = 0; i < data.length; i++) {
            list.add((double)data[i][index]); 
        }
        
        LOGGER.debug("Adding series 1");
        LOGGER.debug(list.toString());
        dataset.add(list, "Series 1", type);

        return dataset;
    }

    
}
