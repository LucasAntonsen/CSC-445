import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class simplex {
    //check it works on linux csc!!!!!!!!!

    public static void input_parse(File input, float[][] table) {
        int num_lines = 1;
        int num_col = 1;
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String s = reader.readLine();
            String[] str = s.split("\\s+");
            num_col += str.length;

            while (reader.readLine() != null) {
                num_lines++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("num_lines:" + num_lines + " num_col:" + num_col);


        table = new float[num_lines][];

        for(i = 0; i < num_lines; i++){
            table[i] = new float[num_col];
        }

        try (BufferedReader reader2 = new BufferedReader(new FileReader(input))) {
            String a = reader2.readLine();
            int row = 0;

            while (a != null) {
                String[] str2 = a.split("\\s+");
                for(i = 0; i < num_col; i++){
                    table[row][i] = Float.parseFloat(str2[i]);
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static int row_Count(File input){
        int num_rows = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while (reader.readLine() != null) {
                num_rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num_rows;
    }

    public static int col_Count(File input){
        int num_cols = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String s = reader.readLine();
            String[] str = s.split("\\s+");
            num_cols += str.length;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return num_cols;
    }

    //public static void print_Table(float[][] table){
//        for(int i = 0; i <
//    }

    public static void main(String[] args){
        File inFile = null;
        inFile = new File(args[0]);

        float[][] table;

        System.out.println("Number of rows: " + row_Count(inFile) + " Number of columns: " + col_Count(inFile));

        //input_parse(inFile, table);
    }
}
