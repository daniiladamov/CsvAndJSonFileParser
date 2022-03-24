package com.adamov.test.parsing.services;

import com.adamov.test.parsing.parsers.CsvParser;
import com.adamov.test.parsing.parsers.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Data
@Component
@AllArgsConstructor
public class ParserService {
    JsonParser jsonParser;
    CsvParser csvParser;


    public void execute() throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();

        Future<Boolean> secondResult=exec.submit(csvParser);
        Future<Boolean> firstResult=exec.submit(jsonParser);
        try {

            secondResult.get();
            firstResult.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        exec.shutdown();

    }

}