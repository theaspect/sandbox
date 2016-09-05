package me.blzr.fp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static me.blzr.fp.ZCommon.print;

public class IFileRead {

    public static void main(String[] args) {
        // Обработка файла в императивном подходе
        String f = "file.txt";
        BufferedReader br = null;
        List<String> results = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 2) {
                    results.add(line.toUpperCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //Do nothing
                }
            }
        }

        print(results);

        // Обработка файла в функциональном подходе
        String f2 = "file.txt";
        try (Stream<String> lines = Files.lines(Paths.get(f2))) {
            lines
                    .filter(l -> l.length() > 2)
                    .map(String::toUpperCase)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
