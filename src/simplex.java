import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import static java.lang.System.exit;

public class simplex {
    //check it works on linux csc!!!!!!!!!
    //make sure it runs from standard input!!!!!!!!!!!!!

    public static void fill_Table(File input, double[][] table, int num_rows) {
        int row = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {

            while (row != num_rows) {
                String line = reader.readLine();
                String[] str = line.split("\\s+");

                if(row == 0){
                    table[0][0] = 0;
                    for(int i = 0; i < str.length; i++){
                        table[row][i + 1] = Double.parseDouble(str[i]);
                    }

                }else {
                    table[row][0] = Double.parseDouble(str[str.length - 1]);

                    for (int i = 0; i < str.length - 1; i++) {
                        if(Double.parseDouble(str[i]) != 0){
                            table[row][i + 1] = -1 * Double.parseDouble(str[i]);
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

    public static void print_Table(double[][] table, String[] row_labels, String[] col_labels, int num_rows){
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
        System.out.println();
    }

    public static int enter_Select(double[][] table){
        int idx = -1;
        for(int y = 1; y < table[0].length; y++){
            if(table[0][y] > 0){
                idx = y;
                break;
            }
        }
        return idx;
    }

    public static int largest_Coef_Select(double[][] table){
        int idx = -1;
        double max = 0;

        for(int y = 1; y < table[0].length; y++){
            if(table[0][y] > max){
                idx = y;
                break;
            }
        }
        return idx;
    }

    //bounds
    public static int leave_Select(double[][] table, int enter, int num_rows, String[] row_labels){
        double min;
        int leave = -1;
        double val;
        int[] neg_entry = new int[num_rows];
        int idx = 0;

        for(int x = 1; x < num_rows; x++){
            if(table[x][enter] < -1e-7){
                neg_entry[idx] = x;
                idx++;
            }
        }
        //System.out.println(Arrays.toString(neg_entry));
       // System.out.println("idx " + idx);

        if(neg_entry[0] == 0){
            return leave;
        }

        min = table[neg_entry[0]][0]/(-1*table[neg_entry[0]][enter]);
        //System.out.println("min " + min);
        leave = neg_entry[0];

        for(int i = 1; i < idx; i++){
            val = table[neg_entry[i]][0]/(-1*table[neg_entry[i]][enter]);
            //System.out.println("val " + val);
            if(val < min || val == min && row_labels[neg_entry[i]].matches("omega")){
                min = val;
                leave = neg_entry[i];
            }
        }

        return leave;
    }

    public static void pivot(double[][] table, int enter_var, int leave_var, int num_rows, int num_cols,
                             String[] row_labels, String[] col_labels){

        double multiplier = -1 * table[leave_var][enter_var];
        for(int y = 0; y < num_cols; y++){
            if(y != enter_var){
                table[leave_var][y] /= multiplier;

//                if(Math.abs(table[leave_var][y]) < .0000000001){
//                    table[leave_var][y] = 0;
//                }

            }else{
                table[leave_var][enter_var] = -1 / multiplier;

//                if(Math.abs(table[leave_var][enter_var]) < .000000001){
//                    table[leave_var][enter_var] = 0;
//                }
            }
        }

        for(int x = 0; x < num_rows; x++) {
            if (x != leave_var) {

                multiplier = table[x][enter_var];

                for (int y = 0; y < num_cols; y++) {
                    if(y != enter_var){
                        //System.out.println("x: " + x + " y: " + y + " factor: " + multiplier + "*" + table[leave_var][y]);
                        table[x][y] += multiplier * table[leave_var][y];

//                        if(Math.abs(table[x][y]) < .000000000001){//5 zeroes is pretty good, 6 zeroes is bad, 4 zeroes is bad infeasible
//                            table[x][y] = 0;
//                        }

                    }else{
                        table[x][y] = multiplier * table[leave_var][y];

//                        if(Math.abs(table[x][y]) < .00000000001){
//                            table[x][y] = 0;
//                        }
                    }
                }
            }
        }
        String copy = row_labels[leave_var];
        row_labels[leave_var] = col_labels[enter_var];
        col_labels[enter_var] = copy;
    }

    public static int check_Feasibility(double[][] table, int num_rows){
        for(int x = 1; x < num_rows; x++){
            if(table[x][0] < 0){
                return -1;
            }
        }
        return 1;
    }

    public static void print_soln(double[][] table, String[] row_labels, int num_rows, String[] col_labels, int num_cols){
        int[] x_indices = new int[num_cols - 2]; //-2 for omega and obs
        int index;

        for(int x = 1; x < num_rows; x++){
            if(row_labels[x].matches("^x\\d+")){
                index = Integer.parseInt(row_labels[x].replaceAll("[^0-9]", "")) - 1;
                x_indices[index] = x;
            }
        }

        for(int y = 1; y < num_cols; y++){
            if(col_labels[y].matches("^x\\d+")){
                index = Integer.parseInt(col_labels[y].replaceAll("[^0-9]", "")) - 1;
                x_indices[index] = -y;
            }
        }
        //System.out.println(Arrays.toString(x_indices));

        System.out.println("optimal");

        DecimalFormat format = new DecimalFormat("#.#######");

        System.out.print(format.format(table[0][0]) + "\n");
        for(int i = 0; i < x_indices.length; i++){
            if(x_indices[i] < 0){
                System.out.print(0 + " ");
            }else if(i == x_indices.length - 1) {
                System.out.print(format.format(table[x_indices[i]][0]));
            }else{
                System.out.print(format.format(table[x_indices[i]][0]) + " ");
            }
        }
        //System.out.printf(format.format(3000000.12356788));
    }

    public static int auxiliary_Select(double[][] table, int num_rows, int num_cols, String[] row_labels, String[] col_labels){
        double max = 0;
        int leave = 0;

        for(int y = 0; y < num_cols - 1; y++){
            table[0][y] = 0;
        }
        table[0][num_cols - 1] = -1;

        for(int x = 1; x < num_rows; x++){
            table[x][num_cols - 1] = 1;

            if(table[x][0] < 0 && max < -1 * table[x][0]){
                max = -1 * table[x][0];
                leave = x;
            }
        }
        System.out.println(leave + " " + max);

        //print_Table(table, row_labels, col_labels, num_rows);
        //System.out.println();
        return leave;
    }

    public static void redefine_Obj_Function(double[][] table, int num_rows, int num_cols, String[] row_labels, String[] col_labels,
                                             String[] obj_labels, double[] obj_func){
        int found;

        for(int i = 1; i < num_cols - 1; i++) {
            found = 0;

            for (int y = 1; y < num_cols - 1; y++) {
                if (col_labels[y].matches(obj_labels[i])) {
                    table[0][y] += obj_func[i];
                    found = 1;
                }
            }
            if(found != 1){
                for(int x = 1; x < num_rows; x++){
                    if(row_labels[x].matches(obj_labels[i])){

                        for(int idx = 0; idx < num_cols; idx++){
                            table[0][idx] += obj_func[i] * table[x][idx];
                        }
                    }
                }
            }

        }
    }

    public static int special_Select(double[][] table, int enter_var, int leave_var, int num_rows, int num_cols,
                                      String[] row_labels, String[] col_labels){
        double max = Double.MAX_VALUE;
        int index = -1;
        for(int y = 1; y < num_cols; y++){
            if(table[leave_var][y] < max && table[leave_var][y] < 0){
                max = table[leave_var][y];
                index = y;
            }
        }
        return index;
    }

    public static void main(String[] args){
        File inFile = new File(args[0]);

        int rows = row_Count(inFile);
        int cols = col_Count(inFile) + 1; //including omega place

        int i;

        int entering_var;
        int leaving_var;
        int omega = 0;
        boolean match_omega;

        //System.out.println("Number of rows: " + rows + " Number of columns: " + cols);

        double[][] table = new double[rows][];

        for(i = 0; i < rows; i++){
            table[i] = new double[cols]; //
        }
        //System.out.println(table[0][0]);

        fill_Table(inFile, table, rows);

        String[] col_labels = new String[cols];
        col_labels[0] = "ob";
        for(i = 1; i < cols; i++){
            col_labels[i] = "x" + i;
        }
        col_labels[cols - 1] = "omega";
        //System.out.println(Arrays.toString(col_labels));

        String[] row_labels = new String[rows];
        row_labels[0] = "fi";
        for(i = 1; i < rows; i++){
            row_labels[i] = "w" + i;
        }
        //System.out.println(Arrays.toString(row_labels));

        print_Table(table, row_labels, col_labels, rows);

        if(check_Feasibility(table, rows) == -1){
            double[] obj_func = new double[cols];
            String[] cur_labels = new String[cols];
            System.arraycopy(table[0], 0, obj_func, 0, cols);
            System.arraycopy(col_labels, 0, cur_labels, 0, cols);

            //System.out.println(Arrays.toString(cur_labels));
            //System.out.println(Arrays.toString(obj_func));

            entering_var = cols - 1;
            leaving_var = auxiliary_Select(table, rows, cols, row_labels, col_labels);
            print_Table(table, row_labels, col_labels, rows);

            pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
            print_Table(table, row_labels, col_labels, rows);
            //System.out.println();

            for(;;){
                if(Math.abs(table[0][0]) < 1e-7){
                    for(int y = 1; y < cols; y++) {
                        match_omega = col_labels[y].matches("omega");
                        if (match_omega) {
                            omega = y;
                            break;
                        }
                    }
                    print_Table(table, row_labels, col_labels, rows);
                    exit(0);
                }


                entering_var = largest_Coef_Select(table);

                if(entering_var == -1){
                    //System.out.println();
                    print_Table(table, row_labels, col_labels, rows);
                    break;
                }

                leaving_var = leave_Select(table, entering_var, rows, row_labels);

                System.out.println("entering variable and leaving variable " + entering_var + "    " + leaving_var);

                if(leaving_var == -1){
                    print_Table(table, row_labels, col_labels, rows);
                    System.out.println("unbounded");//should it be unbounded or infeasible?
                    exit(0);
                }

                pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
            }

            if(table[0][0] < 0){
                for(int y = 1; y < cols; y++) {
                    match_omega = col_labels[y].matches("omega");
                    if (match_omega) {
                        omega = y;
                    }
                }
                if(omega == 0) {
                    for (int x = 1; x < rows; x++) {
                        match_omega = row_labels[x].matches("omega");
                        if (match_omega) {
                            omega = -x;
                        }
                    }
                }
            }else{
                System.out.println("infeasible");
                exit(0);
            }

            if(omega > 0){
                for(int x = 0; x < rows; x++){
                    table[x][omega] = 0;
                }
            }else{
                for(int y = 1; y < cols; y++){
                    if(table[0][y] < 0){
                        double max = table[-omega][y];
                        pivot(table, y, -omega, rows, cols, row_labels, col_labels);
                        break;
                    }
                }
                print_Table(table, row_labels, col_labels, rows);
                exit(0);
                for(int y = 1; y < cols; y++) {
                    match_omega = col_labels[y].matches("omega");
                    if (match_omega) {
                        omega = y;
                    }
                }
                for(int x = 0; x < rows; x++){
                    table[x][-omega] = 0;
                }
            }
            System.out.println();
            print_Table(table, row_labels, col_labels, rows);
//
//            System.out.println(Arrays.toString(cur_labels));
//            System.out.println(Arrays.toString(obj_func));
//            System.out.println("feasible");
            redefine_Obj_Function(table, rows, cols, row_labels, col_labels, cur_labels, obj_func);
            print_Table(table, row_labels, col_labels, rows);
            //exit(0);
        }

        for(;;){
            entering_var = largest_Coef_Select(table);

            if(entering_var == -1){
                //System.out.println("\nFinal table");
                //print_Table(table, row_labels, col_labels, rows);
                break;
            }

            leaving_var = leave_Select(table, entering_var, rows, row_labels);

            if(leaving_var == -1){
                print_Table(table, row_labels, col_labels, rows);
                System.out.println("unbounded");
                exit(0);
            }

//            System.out.println();
            pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
            print_Table(table, row_labels, col_labels, rows);
        }
        print_soln(table, row_labels, rows, col_labels, cols);
    }
}
