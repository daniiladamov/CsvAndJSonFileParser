package com.adamov.test.parsing;

import com.adamov.test.parsing.configs.SpringConfig;
import com.adamov.test.parsing.services.ParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SpringBootApplication
public class ParsingApplication {

    public static void main(String[] args) throws InterruptedException {
        if (args.length<2 ||args.length>2) {
            System.err.println("Проверьте количество входных параметров");
            System.exit(0);
        }


        createProperties(args);
        SpringApplication.run(ParsingApplication.class, args);
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(SpringConfig.class);

        ParserService parserService=context.getBean("parserService", ParserService.class);
        List<String> list=context.getBean("list",List.class);
        parserService.execute();
        list.stream().parallel().forEach(System.out::println);
        deleteTempFile();
    }

    private static void deleteTempFile() {
        File folder=new File( "C:\\testForJava");
        File file=new File(folder,"application.properties");
        file.delete();
        folder.delete();
    }
    private static void createProperties(String[] args){
        try {
            File folder=new File("C:\\testForJava");
            folder.mkdir();
            File file=new File("C:\\testForJava\\application.properties");
            if (file.exists())file.delete();
            else {
                file.createNewFile();
            }
            PrintWriter printWriter=new PrintWriter(file);
            if (args[0].matches(".json")){
                printWriter.println("json="+args[0]);
                printWriter.println("csv="+args[1]);}
            else{
                printWriter.println("csv="+args[0]);
                printWriter.println("json="+args[1]);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
