import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Result {

    public String chatId;
    public  String brigade;
    public  String key;
    public  boolean q;
    public  String answer;
    public int ball;
    public int flag;
    public String text;
    public Keys keys;

    public Result(String chatId, String brigade, String key, boolean q, String answer, int ball, int flag, String text, Keys keys){
        this.chatId = chatId;
        this.brigade = brigade;
        this.key = key;
        this.q = q;
        this.answer = answer;
        this.ball = ball;
        this.flag = flag;
        this.text = text;
        this.keys = keys;
    }

    public static Result[] getResult(){
        Gson gson = new GsonBuilder().create();
        Result[] results = new Result[120];
        String fileName = "src/main/resources/result.json";
        Path path = new File(fileName).toPath();

        Reader reader;

        {
            try {
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                results = gson.fromJson(reader, Result[].class);

                Arrays.stream(results).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public static void setResult(Result results)  throws IOException {
        String fileName = "src/main/resources/result.json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            Gson gson = new Gson();
            List<Result> items = new ArrayList<>();
            Collections.addAll(items, results);

            gson.toJson(items, isr);
        }

        System.out.println("Items written to file");
    }
}
