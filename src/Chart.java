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

public class Chart extends ApplicationFrame {

    public Chart(String applicationTitle, String chartTitle, ArrayList<double[]> arrayList, ArrayList<double[]> arrayList2, ArrayList<double[]> arrayList3, int[] nTrains) {
        super(applicationTitle);
        JFreeChart xyLineChart;
        xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "avg. step testing", createDatasetPoliciesCoops(arrayList, arrayList2, arrayList3, nTrains), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public Chart(String applicationTitle, String chartTitle, ArrayList<long[]> arrayList, ArrayList<long[]> arrayList2, ArrayList<long[]> arrayList3, ArrayList<long[]> arrayList4, int[] nTrains) {
        super(applicationTitle);
        JFreeChart xyLineChart;
        xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "Time (nanoTime)", createDatasetPolicies(arrayList, arrayList2, arrayList3, arrayList4, nTrains), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.BLACK);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesStroke(3, new BasicStroke(1.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public Chart(String applicationTitle, String chartTitle, ArrayList<double[]> arrayList, ArrayList<double[]> arrayList2, int[] nTrains, String type, int value) {
        super(applicationTitle);
        JFreeChart xyLineChart;
        if (type == "single")
            xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "avg. step testing", createDatasetMoves1(arrayList, arrayList2, nTrains), PlotOrientation.VERTICAL, true, true, false);
        else
            xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "avg. step testing", createDatasetMoves2(arrayList, arrayList2, nTrains), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public Chart(String applicationTitle, String chartTitle, ArrayList<long[]> arrayList, ArrayList<long[]> arrayList2, int[] nTrains, String type) {
        super(applicationTitle);
        JFreeChart xyLineChart;
        if (type == "single")
            xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "execution Time (nanoSeconds)", createDatasetTimes1(arrayList, arrayList2, nTrains), PlotOrientation.VERTICAL, true, true, false);
        else
            xyLineChart = ChartFactory.createXYLineChart(chartTitle, "nTrials", "execution Time (nanoSeconds)", createDatasetTimes2(arrayList, arrayList2, nTrains), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    private XYDataset createDatasetPoliciesCoops(ArrayList<double[]> arrayList, ArrayList<double[]> arrayList2, ArrayList<double[]> arrayList3, int[] nTrains) {

        final XYSeries coopMax = new XYSeries("Multi Agent MaxNextVal");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                coopMax.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries coopEpsilon = new XYSeries("Multi Agent EpsilonPolicy(0.8)");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                coopEpsilon.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeries coopTruth = new XYSeries("Multi Agent TruthValue(0.6)");
        for (int i = 0; i < arrayList3.size(); i++) {
            for (int j = 0; j < arrayList3.get(i).length; j++) {
                coopTruth.add(nTrains[i], arrayList3.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(coopEpsilon);
        dataset.addSeries(coopMax);
        dataset.addSeries(coopTruth);
        return dataset;
    }


    private XYDataset createDatasetPolicies(ArrayList<long[]> arrayList, ArrayList<long[]> arrayList2, ArrayList<long[]> arrayList3, ArrayList<long[]> arrayList4, int[] nTrains) {
        final XYSeries individual = new XYSeries("Single Agent MaxPolicy");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                individual.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries individualEpsilon = new XYSeries("Single Agent EpsilonPolicy");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                individualEpsilon.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeries coopMax = new XYSeries("Multi Agent MaxPolicy");
        for (int i = 0; i < arrayList3.size(); i++) {
            for (int j = 0; j < arrayList3.get(i).length; j++) {
                coopMax.add(nTrains[i], arrayList3.get(i)[j]);
            }
        }
        final XYSeries coopEpsilon = new XYSeries("Multi Agent EpsilonPolicy");
        for (int i = 0; i < arrayList4.size(); i++) {
            for (int j = 0; j < arrayList4.get(i).length; j++) {
                coopEpsilon.add(nTrains[i], arrayList4.get(i)[j]);
            }
        }


        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individual);
        dataset.addSeries(individualEpsilon);
        dataset.addSeries(coopMax);
        dataset.addSeries(coopEpsilon);
        return dataset;
    }

    private XYDataset createDatasetMoves1(ArrayList<double[]> arrayList, ArrayList<double[]> arrayList2, int[] nTrains) {
        final XYSeries individual = new XYSeries("Single Agent Max");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                individual.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries individualEpsilon = new XYSeries("Single Agent Epsilon");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                individualEpsilon.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individual);
        dataset.addSeries(individualEpsilon);
        return dataset;
    }

    private XYDataset createDatasetMoves2(ArrayList<double[]> arrayList, ArrayList<double[]> arrayList2, int[] nTrains) {
        final XYSeries individualCoop = new XYSeries("MaxCoop");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                individualCoop.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries individualEpsilonCoop = new XYSeries("EpsilonCoop");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                individualEpsilonCoop.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individualCoop);
        dataset.addSeries(individualEpsilonCoop);
        return dataset;
    }

    private XYDataset createDatasetTimes1(ArrayList<long[]> arrayList, ArrayList<long[]> arrayList2, int[] nTrains) {
        final XYSeries individual = new XYSeries("Single Agent Max");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                individual.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries individualEpsilon = new XYSeries("Single Agent Epsilon");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                individualEpsilon.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individual);
        dataset.addSeries(individualEpsilon);
        return dataset;
    }

    private XYDataset createDatasetTimes2(ArrayList<long[]> arrayList, ArrayList<long[]> arrayList2, int[] nTrains) {
        final XYSeries individualCoop = new XYSeries("MaxCoop");
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                individualCoop.add(nTrains[i], arrayList.get(i)[j]);
            }
        }
        final XYSeries individualEpsilonCoop = new XYSeries("EpsilonCoop");
        for (int i = 0; i < arrayList2.size(); i++) {
            for (int j = 0; j < arrayList2.get(i).length; j++) {
                individualEpsilonCoop.add(nTrains[i], arrayList2.get(i)[j]);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(individualCoop);
        dataset.addSeries(individualEpsilonCoop);
        return dataset;
    }

}
