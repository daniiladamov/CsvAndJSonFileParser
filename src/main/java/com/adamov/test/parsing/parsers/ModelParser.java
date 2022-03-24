package com.adamov.test.parsing.parsers;

import com.adamov.test.parsing.models.EntityFile;
import com.adamov.test.parsing.models.ModelOut;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ModelParser {
    @Autowired
    @Qualifier("csv")
    String fileCsv;
    @Autowired
    @Qualifier("json")
    String FileJson;
    @Autowired
    @Qualifier("list")
    List<String> response;

    public synchronized void createModelIn(String[] array, EntityFile entity, int line) {
        StringBuilder result=new StringBuilder();
        int orderId;
        double amount;
        if (array[0].matches("[1-9][0-9]*")) orderId=Integer.parseInt(array[0]);
        else {orderId=-1;
            result.append("номер заказа orderId="+array[0]+" должен передаваться в формате положительно целого числа;");
        }
        if (array[1].matches("[1-9][0-9]*") || array[1].matches("[1-9][0-9]*\\.[0-9]{1,2}"))
            amount=Double.parseDouble(array[1]);
        else {amount=-1.0;
            result.append("стоимость amount="+array[1]+" должна передаваться в формате положительных целого или " +
                    "вещественного числа");
        }

        ModelOut modelOut=new ModelOut();
        modelOut.setOrderId(orderId);
        modelOut.setAmount(amount);
        modelOut.setCurrency(array[2]);
        modelOut.setComment(array[3]);

        if (entity==EntityFile.CSV) modelOut.setFilename(fileCsv);
        else modelOut.setFilename(FileJson);
        modelOut.setLine(line);
        if(!result.toString().isEmpty()) modelOut.setResult(result.toString());
        else modelOut.setResult("OK");

        try {
            String json=new ObjectMapper().writeValueAsString(modelOut);
            response.add(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
