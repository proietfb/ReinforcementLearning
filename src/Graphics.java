import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by proietfb on 9/19/16.
 */

public class Graphics extends ApplicationFrame {

    public Graphics(String applicationTitle, String chartTitle, ArrayList<double[]> arrayList, int[] nTrains) {
        super(applicationTitle);
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "avg. step Training",createDataset(arrayList, nTrains), PlotOrientation.VERTICAL,true,true,false);

        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0 ,Color.RED);
        renderer.setSeriesStroke(0 ,new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);


    }

    private XYDataset createDataset(ArrayList<double[]> arrayList, int[] nTrains){
        final XYSeries individual = new XYSeries("Individual");
        for (int i = 0; i<arrayList.size();i++){
            for(int j=0;j<arrayList.get(i).length;j++){
                individual.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individual);
        return dataset;
    }

    public void runChart (ArrayList<double[]> arrayList, int nTrains){

    }
}
