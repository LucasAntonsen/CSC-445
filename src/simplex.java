// By Lucas J. Antonsen, V00923982
import java.io.*;
import java.text.DecimalFormat;

import static java.lang.System.exit;

public class simplex {

    //create a table based on the input
    public static void fill_Table(File input, double[][] table, int num_rows) {
        int row = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {

            while (row != num_rows) {
                String line = reader.readLine();
                String[] str = line.split("\\s+");

                if(row == 0){   //treat the objective row differently. set obj val to zero
                    table[0][0] = 0;
                    for(int i = 0; i < str.length; i++){
                        table[row][i + 1] = Double.parseDouble(str[i]);
                    }

                }else {
                    table[row][0] = Double.parseDouble(str[str.length - 1]); //set constant first

                    for (int i = 0; i < str.length - 1; i++) {  //set regular coefficients
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

    //count rows of file
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

    //count columns of file
    public static int col_Count(File input){
        int num_cols = 1; //initialize count to 1 as the first row is missing a column present in all other rows

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String s = reader.readLine();
            String[] str = s.split("\\s+");
            num_cols += str.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num_cols;
    }

    //print the raw table
    public static void print_Table(double[][] table, String[] row_labels, String[] col_labels, int num_rows){
        for(int i = 0; i < num_rows; i++){
            System.out.print(row_labels[i] + " = ");
            for(int y = 0; y < table[i].length; y++){
                if(y == 0){
                    System.out.print(table[i][y] + " + "); //constants
                }else if(y == table[i].length - 1){
                    System.out.print(table[i][y] + col_labels[y]); //last column entry
                }else{
                    System.out.print(table[i][y] + col_labels[y] + " + "); //regular column entry
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    //select entering variable via Bland's rule
    public static int enter_Select(double[][] table){
        int idx = -1; //no entering variable then return -1
        for(int y = 1; y < table[0].length; y++){
            if(table[0][y] > 0){    //select the first positive value
                idx = y;
                break;
            }
        }
        return idx; //return column index of entering variable
    }

    //(bonus) select entering variable based on largest coefficient
    public static int largest_Coef_Select(double[][] table){
        int idx = -1;
        double max = 0;

        for(int y = 1; y < table[0].length; y++){
            if(table[0][y] > max){
                idx = y;
                break;
            }
        }
        return idx; //return column index of entering variable
    }

    //select leaving variable based on entering variable
    public static int leave_Select(double[][] table, int enter, int num_rows, String[] row_labels){
        double min;
        int leave = -1; //if no leaving variable is found -1 is returned, as the LP is unbounded
        double val;
        int[] neg_entry = new int[num_rows]; //keeps track of all rows with negative entries for entering variable
        int idx = 0;

        for(int x = 1; x < num_rows; x++){
            if(table[x][enter] < -1e-7){    //-1e-7 is our epsilon value, operates basically as zero to remove entries
                neg_entry[idx] = x;         //such as 1e-17, etc. (very close to zero)
                idx++;
            }
        }

        if(neg_entry[0] == 0){  //since we begin at row 1, it is impossible to get 0 as the first entry here unless
            return leave;       //there are no possible leaving variables to select
        }

        min = table[neg_entry[0]][0]/(-1*table[neg_entry[0]][enter]);   //initialize min. xi <= row constant/xi coefficient
        leave = neg_entry[0];   //initialize leave variable index

        for(int i = 1; i < idx; i++){
            val = table[neg_entry[i]][0]/(-1*table[neg_entry[i]][enter]);
            if(val < min || val == min && row_labels[neg_entry[i]].matches("omega")){ //if there is a tie between omega
                min = val;                                                                  //in the aux problem and another
                leave = neg_entry[i];                                                       //leaving variable then omega wins
            }
        }
        return leave;
    }

    //perform pivot operation based on entering and leaving variables
    public static void pivot(double[][] table, int enter_var, int leave_var, int num_rows, int num_cols,
                             String[] row_labels, String[] col_labels){

        //update values in leaving row first
        double multiplier = -1 * table[leave_var][enter_var];   //the positive coefficient of the leaving variable's entering variable
        for(int y = 0; y < num_cols; y++){
            if(y != enter_var){
                table[leave_var][y] /= multiplier;  //divide by multiplier for non-entering variable
            }else{
                table[leave_var][y] = -1 / multiplier;  //move row variable to entering variable position
            }                                           //and divided by multiplier
        }

        //update all other rows
        for(int x = 0; x < num_rows; x++) {
            if (x != leave_var) {
                multiplier = table[x][enter_var];   //multiplier for given entering variable

                for (int y = 0; y < num_cols; y++) {
                    if(y != enter_var){
                        table[x][y] += multiplier * table[leave_var][y];    //add to non-entering variables in given row

                    }else{
                        table[x][y] = multiplier * table[leave_var][y];     //revise entering variable as leaving variable
                    }
                }
            }
        }
        //swap labels of entering and leaving variables
        String copy = row_labels[leave_var];
        row_labels[leave_var] = col_labels[enter_var];
        col_labels[enter_var] = copy;
    }

    //check the feasibility of the LP initially
    public static int check_Feasibility(double[][] table, int num_rows){
        for(int x = 1; x < num_rows; x++){
            if(table[x][0] < 0){
                return -1;  //negative constant present
            }
        }
        return 1;   //no negative constants
    }

    //prints solution with minimum 7 significant figures
    public static void print_soln(double[][] table, String[] row_labels, int num_rows, String[] col_labels, int num_cols){
        int[] x_indices = new int[num_cols - 2]; //list of x indices {x1, x2, x3,...}. size is num_cols - 2 for omega and obs
        int index;

        //find all x variables in row_labels. start at 1 since obj label is not considered
        //index is label subscript - 1 since x_indices are {x1, x2, x3, x4,...}
        for(int x = 1; x < num_rows; x++){
            if(row_labels[x].matches("^x\\d+")) {
                index = Integer.parseInt(row_labels[x].replaceAll("[^0-9]", "")) - 1;
                x_indices[index] = x;   //element xi is present at index x. for instance, if we found x11 at row 7 then
            }                           //x_indices[10] = 7 (index - 1 = 11 -1)
        }

        //find all x variables in col_labels. start at 1 since obj function is not considered
        for(int y = 1; y < num_cols; y++){
            if(col_labels[y].matches("^x\\d+")){
                index = Integer.parseInt(col_labels[y].replaceAll("[^0-9]", "")) - 1;
                x_indices[index] = -y;  //set the location index (y) of element xi to a negative since it is non-basic
            }                           //and we want to differentiate it (since it will be zero)
        }

        System.out.println("optimal");

        DecimalFormat format = new DecimalFormat("#.#######");

        System.out.print(format.format(table[0][0]) + "\n");
        for(int i = 0; i < x_indices.length; i++){
            if(x_indices[i] < 0){
                if(i == x_indices.length - 1){
                    System.out.print(0);    //non-basic xn
                }else{
                    System.out.print(0 + " ");  //non-basic xi, print zero
                }
            }else if(i == x_indices.length - 1) {
                System.out.print(format.format(table[x_indices[i]][0]));    //basic xn
            }else{
                System.out.print(format.format(table[x_indices[i]][0]) + " "); //basic xi
            }
        }
    }

    //add omega and select leaving variable for aux problem
    public static int auxiliary_Select(double[][] table, int num_rows, int num_cols, String[] row_labels, String[] col_labels){
        double max = 0;
        int leave = 0;

        //overwrite objective function with fi = 0 -omega
        for(int y = 0; y < num_cols - 1; y++){
            table[0][y] = 0;
        }
        table[0][num_cols - 1] = -1; //num_cols - 1 is index of omega

        for(int x = 1; x < num_rows; x++){
            table[x][num_cols - 1] = 1; //add omega to each non-objective row

            if(table[x][0] < 0 && max < -1 * table[x][0]){  //while were looking at this row, check if row has smallest constant
                max = -1 * table[x][0];                     //return index of row with smallest constant
                leave = x;
            }
        }
        return leave;
    }

    //redefine obj function after aux problem is solved
    public static void redefine_Obj_Function(double[][] table, int num_rows, int num_cols, String[] row_labels, String[] col_labels,
                                             String[] obj_labels, double[] obj_func){
        int found;

        for(int i = 1; i < num_cols - 1; i++) {
            found = 0;

            //check if any variables in original obj function are still in the non-basis
            for (int y = 1; y < num_cols - 1; y++) {
                if (col_labels[y].matches(obj_labels[i])) { //if so, add them to current value
                    table[0][y] += obj_func[i];
                    found = 1;
                }
            }
            //if a variable is now in the basis, substitute it in
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

    //selects entering variable for the solved aux problem when omega is in the basis
    public static int special_Select(double[][] table, int leave_var, int num_cols){
        double max = 0;
        int index = -1;
        for(int y = 1; y < num_cols; y++){
            if(table[leave_var][y] > max){  //choose largest coefficient, as otherwise we divide by such a small number
                max = table[leave_var][y];  //that all the coefficients go to infinity
                index = y;
            }
        }
        return index;
    }

    //find omega location
    public static int find_Omega(String[] row_labels, String[] col_labels, int num_rows, int num_cols){
        boolean match;

        for(int y = 1; y < num_cols; y++) {
            match = col_labels[y].matches("omega");
            if (match) {
                return y;   //positive value means omega is in non-basis
            }
        }

        for(int x = 1; x < num_rows; x++) {
            match = row_labels[x].matches("omega");
            if (match) {
                return -x;  //negative value means omega is in basis
            }
        }
        return -1;  //error, should never get here
    }

    //set omega coefficients to zero
    public static void delete_Omega(double[][] table, int omega_idx, int num_rows){
        for(int x = 0; x < num_rows; x++){
            table[x][omega_idx] = 0;
        }
    }

    //create file from stdin
    public static File create_File(){
        BufferedReader br;
        File input = new File("input.txt");

        int flag = 1; //used to ensure input is identical to stdin (newlines incl.)

        try {
            FileWriter writer = new FileWriter(input);

            br = new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();

            while (str != null) {
                if(flag == 1){
                    writer.write(str);
                    flag = 0;
                }else{
                    writer.write("\n" + str);
                }
                str = br.readLine();
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return input;
    }

    //find all negative coefficients given an entering variable. if none found, the LP is unbounded
    public static int[] find_Negative_Entries(double[][] table, int enter, int num_rows){
        int[] neg_entry = new int[num_rows];    //list of rows with negative entering variable coefficients
        int idx = 0;

        //find negative coefficients and check for degeneracy
        for(int x = 1; x < num_rows; x++){
            if(table[x][enter] < -1e-7){    //check if coefficient is negative (-1e-7 operates as zero)
                if(Math.abs(table[x][0]) < 1e-7){
                    neg_entry[idx] = -x;    //degenerate row, symbolize with negation of index
                }else{
                    neg_entry[idx] = x;     //non-degenerate
                }
                idx++;
            }
        }
        return neg_entry;
    }

    public static int[] degenerate_Check(int[] leaving_cand, int num_rows){
        int count = 0;
        int[] degen_idx = null;
        int pointer = 0;

        for(int i = 0; i < num_rows; i++){
            if(leaving_cand[i] < 0){
                count++;
            }
        }

        if(count >= 2){
            degen_idx = new int[count];

            for(int t = 0; t < num_rows; t++){
                if(leaving_cand[t] < 0){
                    degen_idx[pointer++] = -leaving_cand[t];
                }
            }
        }
        return degen_idx;
    }

    public static int[] find_Max(double[][] table, int[] degen_cand, int index, int entering_var){
        double max = -Double.MAX_VALUE;
        int[] max_cand = null;
        int max_cand_idx = 0;

        for(int i = 0; i < degen_cand.length; i++){
            double val = table[degen_cand[i]][index]/table[degen_cand[i]][entering_var];
            //System.out.println(val);

            if(val > max){
                max = val;

                max_cand = new int[degen_cand.length];
                max_cand_idx = 0;
                max_cand[max_cand_idx++] = i;
            }else if(val == max){
                max_cand[max_cand_idx++] = i;
            }
        }
        //System.out.println(Arrays.toString(max_cand));
        return max_cand;
    }

    public static int lexicographic_Handler(double[][] table, int entering_var, int num_rows, int num_cols, String[] row_labels){
        int[] lexi_choice = null;

        int[] leav_candidates = find_Negative_Entries(table, entering_var, num_rows);
        if(leav_candidates[0] == 0){
            return -1;
        }
        int[] degen_var = degenerate_Check(leav_candidates, num_rows);
        if(degen_var == null){
            return leave_Select(table, entering_var, num_rows, row_labels);
        }
        //System.out.println(Arrays.toString(degen_var));
        for(int y = 1; y < num_cols; y++){
            lexi_choice = find_Max(table, degen_var, y, entering_var);
            if(lexi_choice[1] == 0){
                //System.out.println(table[lexi_choice[0]][y]);
                break;
            }
        }

        return degen_var[lexi_choice[0]];
    }

    public static void main(String[] args){
        int bonus = 0;
        if(args.length == 1){
            if(args[0].matches("bonus")){
                bonus = 1;
            }
        }

//        double[][] a = {{1,-2,3,5},
//                        {0,-2,2,7},
//                        {0,-2,2,8}};
//        int[] leaving_cand = find_Negative_Entries(a, 1, 3);
//        System.out.println(Arrays.toString(leaving_cand));
//        int[] degen = degenerate_Check(leaving_cand, 3);
//        System.out.println(Arrays.toString(degen));
//        int[] choice = find_Max(a, degen, 2, 1);
//        System.out.println(Arrays.toString(choice));
        //System.out.println(-1 <-3.5);
        //System.out.println(lexicographic_Handler(a, 1, 3, 4,null));

        //exit(0);

        //System.out.println(args.length);
        File inFile = create_File();

        int rows = row_Count(inFile);
        int cols = col_Count(inFile) + 1; //including omega place

        int i;

        int entering_var;
        int leaving_var;

        //while()

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

        //print_Table(table, row_labels, col_labels, rows);

        if(check_Feasibility(table, rows) == -1){
            double[] obj_func = new double[cols];
            String[] cur_labels = new String[cols];
            System.arraycopy(table[0], 0, obj_func, 0, cols);
            System.arraycopy(col_labels, 0, cur_labels, 0, cols);

            //System.out.println(Arrays.toString(cur_labels));
            //System.out.println(Arrays.toString(obj_func));

            entering_var = cols - 1;
            leaving_var = auxiliary_Select(table, rows, cols, row_labels, col_labels);
            //print_Table(table, row_labels, col_labels, rows);

            pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
            //Table(table, row_labels, col_labels, rows);
            //System.out.println();

            for(;;){

                if(bonus == 1){
                    entering_var = largest_Coef_Select(table);
                }else{
                    entering_var = enter_Select(table);
                }

                if(entering_var == -1){
                    break;
                }

                if(bonus == 1){
                    leaving_var = lexicographic_Handler(table, entering_var, rows, cols, row_labels);
                }else{
                    leaving_var = leave_Select(table, entering_var, rows, row_labels);
                }

                if(leaving_var == -1){
                    System.out.println("infeasible");//should it be unbounded or infeasible?
                    exit(0);
                }

                pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
            }

            //System.out.println();
            //print_Table(table, row_labels, col_labels, rows);
            if(Math.abs(table[0][0]) > 1e-7){
                System.out.println("infeasible");
                exit(0);
            }

            int omega_location = find_Omega(row_labels, col_labels, rows, cols);

            if(omega_location < 0){
                if(Math.abs(table[-omega_location][0]) > 1e-7){
                    System.out.println("infeasible");
                    exit(0);
                }

                entering_var = special_Select(table, -omega_location, cols);
                if(entering_var == -1){
                    System.out.println("infeasible");
                    exit(0);
                }
                pivot(table, entering_var, -1 * omega_location, rows, cols, row_labels, col_labels);
                //print_Table(table, row_labels, col_labels, rows);
                int new_omega_loc = find_Omega(row_labels, col_labels, rows, cols);
                delete_Omega(table, new_omega_loc, rows);
            }else{
                delete_Omega(table, omega_location, rows);
            }
            //print_Table(table, row_labels, col_labels, rows);

            redefine_Obj_Function(table, rows, cols, row_labels, col_labels, cur_labels, obj_func);
           // print_Table(table, row_labels, col_labels, rows);
            //exit(0);
        }

        for(;;){
            if(bonus == 1){
                entering_var = largest_Coef_Select(table);
            }else{
                entering_var = enter_Select(table);
            }

            if(entering_var == -1){
                //System.out.println("\nFinal table");
                //print_Table(table, row_labels, col_labels, rows);
                break;
            }

            if(bonus == 1){
                leaving_var = lexicographic_Handler(table, entering_var, rows, cols, row_labels);
            }else{
                leaving_var = leave_Select(table, entering_var, rows, row_labels);
            }

            if(leaving_var == -1){
               // print_Table(table, row_labels, col_labels, rows);
                System.out.println("unbounded");
                exit(0);
            }

//            System.out.println();
            pivot(table, entering_var, leaving_var, rows, cols, row_labels, col_labels);
           // print_Table(table, row_labels, col_labels, rows);
        }
        //print_Table(table, row_labels, col_labels, rows);
        print_soln(table, row_labels, rows, col_labels, cols);
    }
}
