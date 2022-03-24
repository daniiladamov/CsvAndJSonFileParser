package com.adamov.test.parsing.parsers;

import com.adamov.test.parsing.models.EntityFile;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@Data
@Component
public class JsonParser implements Callable<Boolean> {
    @Autowired
    List<String> list;
    @Autowired
    @Qualifier("json-reader")
    FileReader fileReader;
    @Autowired
    ModelParser modelParser;

    @Override
    public Boolean call() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(fileReader);
            String s;
            int line=1;
            while ((s = reader.readLine()) != null) {
                JSONObject jo = (JSONObject) new JSONParser().parse(s);
                String orderId = String.valueOf(jo.get("orderId"));
                String amount = String.valueOf(jo.get("amount"));
                String currency = (String) jo.get("currency");
                String comment = (String) jo.get("comment");
                String[] array = {orderId, amount, currency, comment};
                modelParser.createModelIn(array, EntityFile.JSON,line++);

            }
            reader.close();
            return true;


        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return false;

    }


}
