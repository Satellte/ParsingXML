package Parcer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//import static Parcer.Parser2.*;
import static Parcer.Parser.*;


public class Main {
    public static void main(String[] args) {
        File inputText = new File("./src/Parcer/test.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputText);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл");
        }
        StringBuilder inputString = new StringBuilder();
        while(scanner.hasNextLine()) {
            inputString.append(scanner.nextLine().replace("\n","").replace("    ",""));
        }
        scanner.close();
        ArrayList<String> parentPath = new ArrayList<>();
        System.out.println(parseTags(String.valueOf(inputString), parentPath));
    }
}
