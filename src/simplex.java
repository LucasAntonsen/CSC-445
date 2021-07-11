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
                String line = reader.readLine();
                String[] str = line.split("\\s+");

                if(row == 0){
                    table[0][0] = 0;
                    for(int i = 0; i < str.length; i++){
                        table[row][i + 1] = Float.parseFloat(str[i]);
                    }

                }else {
                    table[row][0] = Float.parseFloat(str[str.length - 1]);

                    for (int i = 0; i < str.length - 1; i++) {
                        if(Float.parseFloat(str[i]) != 0){
                            table[row][i + 1] = -1 * Float.parseFloat(str[i]);
                        }
                    }
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

    public static void print_Table(float[][] table, String[] row_labels, String[] col_labels, int num_rows){
        for(int i = 0; i < num_rows; i++){
            System.out.print(row_labels[i] + " = ");
            for(int y = 0; y < table[i].length; y++){
                if(y == 0){
                    System.out.print(table[i][y] + " + ");
                }else if(y == table[i].length - 1){
                    System.out.print(table[i][y] + col_labels[y]);
                }else{
                    System.out.print(table[i][y] + col_labels[y] + " + ");
                }
            }
            System.out.println();
        }
    }

    public static int enter_Select(float[][] table){
        int idx = -1;
        for(int x = 1; x < table[0].length; x++){
            if(table[0][x] > 0){
                idx = x;
                break;
            }
        }
        return idx;
    }

    public static int[] gen(){
        int[] a = {1,2,3};
        return a;
    }

    public static void main(String[] args){
        File inFile = new File(args[0]);

        int rows = row_Count(inFile);
        int cols = col_Count(inFile);

        int i = 0;

        System.out.println("Number of rows: " + rows + " Number of columns: " + cols);

        float[][] table = new float[rows][];

        for(i = 0; i < rows; i++){
            table[i] = new float[cols];
        }
        //System.out.println(table[0][0]);

        fill_Table(inFile, table, rows);

        String[] col_labels = new String[cols];
        col_labels[0] = "ob";
        for(i = 1; i < cols; i++){
            col_labels[i] = "x" + i;
        }
        System.out.println(Arrays.toString(col_labels));

        String[] row_labels = new String[rows];
        row_labels[0] = "fi";
        for(i = 1; i < rows; i++){
            row_labels[i] = "w" + i;
        }
        System.out.println(Arrays.toString(row_labels));

        print_Table(table, row_labels, col_labels, rows);


        System.out.println(enter_Select(table));
    }
}
