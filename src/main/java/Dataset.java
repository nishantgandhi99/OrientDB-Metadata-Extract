

import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dataset {
    String database;
    String entity;
    String name;
    HashMap<String, String> properties = new HashMap<String, String>();

    public Dataset(String database, String entity, String name)
    {
        this.database = database;
        this.entity = entity;
        this.name = name;
    }

    public void addProperty(String name, String value){
        properties.put(name, value);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String jsonProperties = gson.toJson(properties);

        return this.database + " | " + this.entity + " | " + this.name + " | " + jsonProperties;
    }
}
