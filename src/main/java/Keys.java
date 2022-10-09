import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Keys {

    public  int key;
    public  String q;
    public  String a1;
    public  String a2;
    public  String a3;
    public  String a4;
    public  String trueA;

    public Keys(){}

    public Keys(int key, String q, String a1,String a2,String a3,String a4, String trueA){
        this.key = key;
        this.q = q;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.trueA = trueA;
    }

    public Keys[] getKeys(){
        Gson gson = new GsonBuilder().create();
        Keys[] keys = new Keys[120];
        String fileName = "src/main/resources/keysQA.json";
        Path path = new File(fileName).toPath();

        Reader reader;

        {
            try {
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                keys = gson.fromJson(reader, Keys[].class);

                Arrays.stream(keys).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

}
