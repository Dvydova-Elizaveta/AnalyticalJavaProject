package test;

import Visualisation.darwer.XYDrawer;
import Visualisation.mapper.ChartDateMapper;
import Visualisation.mapper.ChartNormalizedDateMapper;
import Visualisation.mapper.ChartZNormalizedDateMapper;
import db.DbRepository;
import models.TaskInform;
import models.TaskInformFromDb;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VisualTest {
    public static void visualTest() throws Exception {
        var dbOrm = new DbRepository();
        dbOrm.connect();

        var csharpTasks = new ArrayList<TaskInform>();
        var javaTasks = new ArrayList<TaskInform>();
        var combinedTasks = new ArrayList<TaskInform>();

        csharpTasks = dbOrm.getTasksByCourse("C#")
                .stream()
                .map(TaskInformFromDb::map)
                .collect(Collectors.toCollection(ArrayList::new));
        javaTasks = dbOrm.getTasksByCourse("Java")
                .stream()
                .map(TaskInformFromDb::map)
                .collect(Collectors.toCollection(ArrayList::new));
        combinedTasks = dbOrm.getTasks()
                .stream()
                .map(TaskInformFromDb::map)
                .collect(Collectors.toCollection(ArrayList::new));

        for (var task: combinedTasks) {
            System.out.println(task);
        }

        ArrayList<TaskInform> finalCsharpTasks = csharpTasks;
        ArrayList<TaskInform> finalJavaTasks = javaTasks;
        ArrayList<TaskInform> finalCombinedTasks = combinedTasks;

        var csharpArray = new ArrayList<ArrayList<TaskInform>>();
        csharpArray.add(finalCsharpTasks);
        var javaArray = new ArrayList<ArrayList<TaskInform>>();
        javaArray.add(finalJavaTasks);
        var combinedArray = new ArrayList<ArrayList<TaskInform>>();
        combinedArray.add(finalCsharpTasks);
        combinedArray.add(finalJavaTasks);

        // главное окно
        JFrame frame = new JFrame("Графики");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // панель для графиков
        JPanel chartPanel = new JPanel(new CardLayout());
        frame.add(chartPanel, BorderLayout.CENTER);

        // панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);

        // переключение графиков
        CardLayout cardLayout = (CardLayout) chartPanel.getLayout();

        //Добавление графиков
        createGraph("C#", finalCsharpTasks, chartPanel, buttonPanel, cardLayout);
        createGraph("Java", finalJavaTasks, chartPanel, buttonPanel, cardLayout);
        createGraph("C#+Java", finalCombinedTasks, chartPanel, buttonPanel, cardLayout);

        createZNormalizedGraph("C# ZNormal", csharpArray, chartPanel, buttonPanel, cardLayout);
        createZNormalizedGraph("Java ZNormal", javaArray, chartPanel, buttonPanel, cardLayout);
        createZNormalizedGraph("C#+Java ZNormal", combinedArray, chartPanel, buttonPanel, cardLayout);
        createNormalizedGraph("C#+Java Normal", combinedArray, chartPanel, buttonPanel, cardLayout);

        frame.setVisible(true);
    }

    private static void createGraph(String name, ArrayList<TaskInform> tasks, JPanel chartPanel, JPanel buttonPanel, CardLayout cardLayout) {
        JPanel csharpChartPanel = XYDrawer.XYDrawer("График для " + name, ChartDateMapper.createDatasetMapper(tasks));
        chartPanel.add(csharpChartPanel, name);
        JButton button = new JButton(name);
        buttonPanel.add(button);
        button.addActionListener(e -> cardLayout.show(chartPanel, name));
    }

    private static void createZNormalizedGraph(String name, ArrayList<ArrayList<TaskInform>> course, JPanel chartPanel, JPanel buttonPanel, CardLayout cardLayout) {
        var b = ChartZNormalizedDateMapper.createDatasetMapper(course);
        JPanel csharpChartPanel = XYDrawer.XYDrawer("График для " + name, b);
        chartPanel.add(csharpChartPanel, name);
        JButton button = new JButton(name);
        buttonPanel.add(button);
        button.addActionListener(e -> cardLayout.show(chartPanel, name));
    }

    private static void createNormalizedGraph(String name, ArrayList<ArrayList<TaskInform>> course, JPanel chartPanel, JPanel buttonPanel, CardLayout cardLayout) {
        var b = ChartNormalizedDateMapper.createDatasetMapper(course);
        JPanel csharpChartPanel = XYDrawer.XYDrawer("График для " + name, b);
        chartPanel.add(csharpChartPanel, name);
        JButton button = new JButton(name);
        buttonPanel.add(button);
        button.addActionListener(e -> cardLayout.show(chartPanel, name));
    }
}
