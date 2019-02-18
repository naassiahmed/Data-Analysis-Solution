/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.RenderingHints;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author Nazim
 */
public class ScatterPlot extends ApplicationFrame {
    
    private float[][] data;

    

    public ScatterPlot(final String title, String X, String Y,Object[][] data) {

        super(title);
        this.data = new float[2][data[0].length];
        populateData(data);
        final NumberAxis domainAxis = new NumberAxis(X);
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis(Y);
        rangeAxis.setAutoRangeIncludesZero(false);
        final FastScatterPlot plot = new FastScatterPlot(this.data, domainAxis, rangeAxis);
        final JFreeChart chart = new JFreeChart("Fast Scatter Plot", plot);
        
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        setContentPane(panel);

    }
    
    private void populateData(Object[][] data) {

        for (int i = 0; i < this.data.length; ++i) {
            for (int j=0;j<this.data[0].length; ++j) {
                this.data[i][j] = (float) data[i][j];
            }
        }

    }
}
