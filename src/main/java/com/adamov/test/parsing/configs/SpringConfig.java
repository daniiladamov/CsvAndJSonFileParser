package com.adamov.test.parsing.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import java.io.*;
import java.util.*;

@Configuration
@ComponentScan("com.adamov.test.parsing")
public class SpringConfig {

    @Bean(name = "list")
    public List<String> getList(){
        return Collections.synchronizedList(new ArrayList<String>());
    }

    @Bean(name="csv-reader")
    public FileReader getFileReaderCsv() {
        try {
            return new FileReader(fileNameCsv());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name= "json-reader")
    public FileReader getFileReaderJson() {
        try {
            return new FileReader(fileNameJson());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Map<String, String > getMapProperties(){
        Map <String, String >map=new HashMap<>();
        File fileTemp=new File("C:\\testForJava\\application.properties");
        try {
            BufferedReader reader=new BufferedReader(new FileReader(fileTemp));
            String s;
            while((s=reader.readLine())!=null){
                String[] arr=s.split("=");
                map.put(arr[0], arr[1]);}
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "json")
    public String fileNameJson(){
        return getMapProperties().get("json");
    }

    @Bean(name = "csv")
    public String fileNameCsv(){
        return getMapProperties().get("csv");
    }

}