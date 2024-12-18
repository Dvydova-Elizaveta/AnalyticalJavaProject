package Visualisation.mapper;

import models.TaskInform;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChartZNormalizedDateMapper {
    public static XYSeriesCollection createDatasetMapper(ArrayList<ArrayList<TaskInform>> course) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        var homeworks = new ArrayList<List<TaskInform>>();
        var tests = new ArrayList<List<TaskInform>>();
        for (var tasks: course) {
            homeworks.add(tasks.stream()
                    .filter(task -> "ДЗ".equalsIgnoreCase(task.getTape()))
                    .collect(Collectors.toList()));

            tests.add(tasks.stream()
                    .filter(task -> "КВ".equalsIgnoreCase(task.getTape()) || "Упр".equalsIgnoreCase(task.getTape()))
                    .collect(Collectors.toList()));
        }

        dataset.addSeries(createSeries(homeworks, "Домашние задания"));
        dataset.addSeries(createSeries(tests, "Упражнения"));
        dataset.addSeries(createRegressionSeries(homeworks, "Корреляция Домашнего задания"));
        dataset.addSeries(createRegressionSeries(tests, "Корреляция Упражнений"));

        return dataset;
    }

    private static XYSeries createSeries(ArrayList<List<TaskInform>> course, String tape) {
        XYSeries series = new XYSeries(tape);
        for (var tasks:course) {
            var m = createMathematicalExpectation(tasks);
            var sigma = createSigma(tasks);
            for (var task : tasks) {
                var z = (task.getCountComments() - m) / sigma;
                if (z < 3) {
                    series.add(z, task.getScore());
                }
            }
        }
        return series;
    }

    private static XYSeries createRegressionSeries(ArrayList<List<TaskInform>> course, String name) {
        XYSeries regressionSeries = new XYSeries(name);
        double min = -1;
        double max = -1;
        double minZ = 0, minY = 0, maxZ = 0, maxY = 0;

        for (var tasks:course) {
            var m = createMathematicalExpectation(tasks);
            var sigma = createSigma(tasks);
            var coefficients = calculateRegressionLine(tasks);
            double a = coefficients[0];
            double b = coefficients[1];

            double minX = tasks.stream().mapToDouble(TaskInform::getCountComments).filter(x -> (x - m) / sigma < 3).min().orElse(0);
            double maxX = tasks.stream().mapToDouble(TaskInform::getCountComments).filter(x -> (x - m) / sigma < 3).max().orElse(0);
            if (min == -1 || minX < min) {
                min = minX;
                minZ = (minX - m) / sigma;
                minY = a * minZ + b;
            }
            if (max == -1 || maxX > max) {
                max = maxX;
                maxZ = (maxX - m) / sigma;
                maxY = a * maxZ + b;
            }
        }

        regressionSeries.add(minZ, minY);
        regressionSeries.add(maxZ, maxY);

        return regressionSeries;
    }

    private static double[] calculateRegressionLine(List<TaskInform> tasks) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = tasks.size();

        var m = createMathematicalExpectation(tasks);
        var sigma = createSigma(tasks);

        for (TaskInform task : tasks) {
            double x = (task.getCountComments() - m) / sigma;
            double y = task.getScore();
            if (x < 3) {
                sumX += x;
                sumY += y;
                sumXY += x * y;
                sumX2 += x * x;
            }
        }

        double a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double b = (sumY - a * sumX) / n;
        return new double[]{a, b};
    }

    private static double createMathematicalExpectation(List<TaskInform> tasks) {
        return tasks.stream().mapToDouble(TaskInform::getCountComments).sum() / tasks.size();
    }

    private static double createSigma(List<TaskInform> tasks) {
        var m = createMathematicalExpectation(tasks);
        return tasks.stream().mapToDouble(TaskInform::getCountComments).map(x -> (x - m) * (x - m)).sum() / tasks.size();
    }
}
