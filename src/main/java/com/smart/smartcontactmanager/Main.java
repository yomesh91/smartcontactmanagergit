package com.smart.smartcontactmanager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("newfile.text");
        FileWriter fileWriter = new FileWriter("newfile.text");
        FileReader fileReader = new FileReader("newfile.text");
        char array[] = new char[90];
        try {
            boolean value = file.createNewFile();
            if (value) {
                System.out.println("The new file is created.");
            }
            else {
                System.out.println("The file already exists.");
            }
            for(int i=0;i<=10;i++) { fileWriter.write("Yomesh" + i+"\n");}
            System.out.println("Data is written to the file.");
            fileWriter.close();

            fileReader.read(array);
            System.out.println("Data in the file:");
            System.out.println(array);
            fileReader.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }


        /*try{
            FileWriter fileWriter = new FileWriter("newfile.text");
            for(int i=0;i<=10;i++) { fileWriter.write("Yomesh" + i+"\n");}
            System.out.println("Data is written to the file.");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
