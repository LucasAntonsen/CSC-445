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

    public static void fill_Table(File input, float[][] table, int num_rows) {
        int row = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {

            while (row != num_rows) {
                //System.out.println(row);
                String line = reader.readLine();
                String[] str = line.split("\\s+");

                for(int i = 0; i < str.length; i++){
                    table[row][i] = Float.parseFloat(str[i]);
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

    public static void print_Table(float[][] table, int num_rows){
        for(int i = 0; i < num_rows; i++){
            for(int y = 0; y < table[i].length; y++){
                System.out.print(table[i][y] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        File inFile = new File(args[0]);

        int rows = row_Count(inFile);
        int cols = col_Count(inFile);

        System.out.println("Number of rows: " + rows + " Number of columns: " + cols);

        float[][] table = new float[rows][];

        for(int i = 0; i < rows; i++){
            table[i] = new float[cols];
        }
        //System.out.println(table[0][0]);

        fill_Table(inFile, table, rows);
        print_Table(table, rows);
    }
}
