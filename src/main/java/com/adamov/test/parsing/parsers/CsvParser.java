package com.adamov.test.parsing.parsers;

import com.adamov.test.parsing.models.EntityFile;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@Data
@Component
public class CsvParser implements Callable<Boolean> {
    @Autowired
    List<String> list;
    @Autowired()
    @Qualifier("csv-reader")
    FileReader fileReader;
    @Autowired()
    ModelParser modelParser;


    @Override
    public Boolean call() {
        try {
            CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            for (CSVRecord record : parser) {
                String[] array= record.toList().toArray(new String[0]);
                int line= (int) record.getRecordNumber();
                modelParser.createModelIn(array, EntityFile.CSV,line);

            }
            parser.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}