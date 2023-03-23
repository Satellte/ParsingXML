package Parcer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Parcer.Parser.parseTags;

public class Main {
    public static void main(String[] args) {
        File inputText = new File("./src/Parcer/test.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputText);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл");
        }
        int levelOfKey = 0;
        StringBuilder inputString = new StringBuilder();
        while(scanner.hasNextLine()) {
            inputString.append(scanner.nextLine().replace("\n","").replace("    ",""));
        }
        scanner.close();
        System.out.println(inputString);
        //parseTags(inputString.toString());
    }
}
