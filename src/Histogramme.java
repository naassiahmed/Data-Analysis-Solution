

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel; import org.jfree.chart.JFreeChart; import org.jfree.chart.plot.PlotOrientation; import org.jfree.data.category.CategoryDataset; import org.jfree.data.category.DefaultCategoryDataset; import org.jfree.ui.ApplicationFrame;

import org.jfree.ui.RefineryUtilities;



public class Histogramme extends ApplicationFrame { 
	public Histogramme( String applicationTitle , String chartTitle, String attribute, Object[][] data,int index ) {
		super( applicationTitle ); 
		JFreeChart barChart = ChartFactory.createBarChart( chartTitle, "Category", "Score", createDataset(attribute,data,index), PlotOrientation.VERTICAL, false, true, false ); 
		ChartPanel chartPanel = new ChartPanel( barChart );
		chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) ); 
		setContentPane( chartPanel ); 
        } 
         
        private CategoryDataset createDataset(String attribute,Object[][] data,int index) {
            
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset( ); 
                        
                        for(int i=0;i<data.length;i++){
                            dataset.addValue((double)data[i][index],String.valueOf(i+1), attribute);
                        }
                        
                        return dataset;
        }
 
	
	} 