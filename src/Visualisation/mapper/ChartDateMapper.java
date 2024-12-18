package Visualisation.mapper;

import models.TaskInform;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChartDateMapper {
    public static XYSeriesCollection createDatasetMapper(ArrayList<TaskInform> tasks) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Разделяем темы по типам
        List<TaskInform> homeworks = tasks.stream()
                .filter(task -> "ДЗ".equalsIgnoreCase(task.getTape()))
                .collect(Collectors.toList());

        List<TaskInform> tests = tasks.stream()
                .filter(task -> "КВ".equalsIgnoreCase(task.getTape()) || "Упр".equalsIgnoreCase(task.getTape()) )
                .collect(Collectors.toList());

        dataset.addSeries(createSeries(homeworks, "Домашние задания"));
        dataset.addSeries(createSeries(tests, "Упражнения"));
        dataset.addSeries(createRegressionSeries(homeworks, "Корреляция Домашнего задания"));
        dataset.addSeries(createRegressionSeries(tests, "Корреляция Упражнений"));
        return dataset;
    }

    private static XYSeries createSeries(List<TaskInform> tasks, String tape) {
        XYSeries series = new XYSeries(tape);
        for (var task : tasks) {
            series.add(task.getCountComments(), task.getScore());
        }
        return series;
    }

    private static XYSeries createRegressionSeries(List<TaskInform> tasks, String name) {
        XYSeries regressionSeries = new XYSeries(name);

        double minX = tasks.stream().mapToDouble(TaskInform::getCountComments).min().orElse(0);
        double maxX = tasks.stream().mapToDouble(TaskInform::getCountComments).max().orElse(0);

        var coefficients = calculateRegressionLine(tasks);
        double a = coefficients[0];
        double b = coefficients[1];

        regressionSeries.add(minX, a * minX + b);
        regressionSeries.add(maxX, a * maxX + b);

        return regressionSeries;
    }

    private static double[] calculateRegressionLine(List<TaskInform> tasks) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = tasks.size();

        for (TaskInform task : tasks) {
            double x = task.getCountComments();
            double y = task.getScore();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX); // Угловой коэффициент
        double b = (sumY - a * sumX) / n;
        return new double[]{a, b};
    }
}
