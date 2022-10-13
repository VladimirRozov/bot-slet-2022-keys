import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Brigade {

    public  String _id;
    public  String name;
    public  String group;
    public  String quota;

    public Brigade(){}

    public Brigade(String _id, String name, String group, String quota){
        this._id = _id;
        this.name = name;
        this.group = group;
        this.quota = quota;
    }

    public Brigade[] getBrigades(){
        Gson gson = new GsonBuilder().create();
        Brigade[] brigades = new Brigade[120];
        String fileName = "src/main/resources/brigades.json";
        Path path = new File(fileName).toPath();

        Reader reader;

        {
            try {
                reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
                brigades = gson.fromJson(reader, Brigade[].class);

                Arrays.stream(brigades).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return brigades;
    }

}
