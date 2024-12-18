package Visualisation.darwer;

import models.TaskInform;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Visualisation.mapper.ChartDateMapper;

public class XYDrawer extends JFrame {
    public static JPanel XYDrawer(String title, XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                title,
                "Количество комментариев",
                "Оценка (от 0 до 1)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Настраиваем внешний вид графика
        XYPlot plot = chart.getXYPlot();
        var renderer = getXyLineAndShapeRenderer();

        plot.setRenderer(renderer);

        // Настраиваем цвета сетки
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        // Создаем панель с графиком
        return new ChartPanel(chart);
    }

    private static XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true); // Точки
        renderer.setSeriesLinesVisible(0, false); // Линии

        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(1, false);

        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesPaint(2, Color.RED);

        renderer.setSeriesShapesVisible(3, false);
        renderer.setSeriesLinesVisible(3, true);
        renderer.setSeriesPaint(3, Color.BLUE);
        return renderer;
    }
}
