Linear Programming Project  
---
##### By Lucas J. Antonsen, V00923982

#### How to Run

The solver is entirely contained within the program _simplex.java_.

To compile, use the following command:

`javac simplex.java`

To run, use the following command:

`java simplex < lp_file.txt`

To run the bonus, use the following command:

`java simplex bonus < lp_file.txt`

#### Solver Architecture

**Solver procedure:**

1. Check for feasibility of linear program

2. If infeasible, run auxiliary problem

    2.1 If a dictionary is unbounded, return infeasible
    
    2.2 If the final dictionary has a non-zero optimal value or omega, return
    infeasible
    
    2.3 Otherwise, take final dictionary and restore original objective function
    
3. If feasible, or now feasible after auxiliary problem, run simplex method

    3.1 If a dictionary is unbounded, return unbounded
    
    3.2 Otherwise, return optimal objective value and corresponding xi values
    
To avoid cycling the program uses Bland's Rule to select an entering variable.

**Solver Procedure (Bonus)**

Follows same procedure as above.

To avoid cycling the program uses the lexicographic selection rule to select a
leaving variable. This rule is necessary since the bonus uses the largest coefficient
rule for selecting a entering variable.

#### Notable Methods and Features

`create_File()`: Creates a file, _input.txt_, that is an identical copy of the
standard input. This method enables reading input multiple times for row and 
column counting and for filling of initial dictionary.

`fill_table()`: Fills the table in the form:

row 1: constant x1 x2 . . . xn  
row 2: constant x1 x2 . . . xn  
.  
.  
.  
row m: constant x1 x2 ... xn  

Each entry is a coefficient of the given variable.

Row and Column labeling: At any given time, the program keeps track of all
basic and non-basic variables. For a dictionary like below:

fi = 2 + x1 + 3x2
w1 = 5 - x1
w2 = 3 - x1 - x2

row_labels[] = {fi, w1, w2}
col_labels[] = {obs, x1, x2, omega}

These labels are very helpful in the following methods.