package ulearn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Course;
import models.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ulearn {
    private static final String ULEARN_URL = "https://api.ulearn.me/courses/%s/";
    private static final String COMMENTS_URL = "https://api.ulearn.me/comments?courseId=%s&slideId=%s&forInstructors=false";

    public static void addComments(Course course, String idCourse) throws IOException {
        var Tasks = course.getTasks();
        var tasksComments = getTasksComments(idCourse);
        for (Task task: Tasks) {
            task.addCountComments(tasksComments.getOrDefault(task.getName(), 0));
        }
    }

    public static Map<String, Integer> getTasksComments(String idCourse) throws IOException {
        var idTasks = new HashMap<String, Integer>();
        var url = new URL(String.format(ULEARN_URL, idCourse));
        JsonNode rootNode = getHTTP(url);

        for (JsonNode units: rootNode.get("units")) {
            for (JsonNode slides: units.get("slides")){
                if (!Objects.equals(slides.get("maxScore").toString(), "0")) {
                    var count = getCountComments(idCourse, slides.get("id").toString().replace("\"", ""));
                    idTasks.put(slides.get("title").toString().replace("\"", ""), count);
                }
            }
        }
        return idTasks;
    }

    public static int getCountComments(String idCourse, String idTask) throws IOException {
        var url = new URL(String.format(COMMENTS_URL, idCourse, idTask));
        JsonNode rootNode = getHTTP(url);

        var count = 0;
        for (JsonNode item: rootNode.get("topLevelComments")) {
            count += 1 + item.get("replies").size();
        }
        return count;
    }

    protected static JsonNode getHTTP(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        InputStream responseStream = connection.getInputStream();

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));

        String output;
        StringBuilder builder = new StringBuilder();
        while ((output = br.readLine()) != null) {
            builder.append(output);
        }
        connection.disconnect();

        String jsonResponse = builder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonResponse);
    }
}
