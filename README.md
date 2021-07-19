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

fi = 2 + w1 + 3x2  
x1 = 5 - w1  
w2 = 3 - w1 - x2  

row_labels[] = {fi, x1, w2}  
col_labels[] = {obs, w1, x2, omega}  

These labels are very helpful in the method `print_soln()`:

Since we are trying to print the values of the xi, the labels 
keep track of the xi position. If we want the value of x1 like above, we can
retrieve it by finding the index of its label, 1, then accessing that index
in the table, table[1][0] = 5. x1 is greater than zero since it is basic, but
if x1 was in the col_labels array, it would be non-basic and thus zero. 

The omega label is always present, for instance, the table for the dictionary above 
is:

2  1  3 0  
5 -1  0 0  
3 -1 -1 0

The column of zeros corresponds to the coefficient of omega. Since it is not in use in this 
example, it is zero. Omega is used in the auxiliary problem.

**Bonus: Alternate Cycling Avoidance**

Using the performance oriented pivoting method, the Largest Coefficient Rule, in 
conjunction with the lexicographic method enables a fast and cycle free simplex method.

The lexicographic method is used in cases of degenerate dictionaries and compares 
degenerate leaving variables vector-wise. For instance, the vector (0, 2, 1) >L 
(0, 1, 1) since 2 > 1.

The bonus follows uses the lexicographic comparison method outlined in: 
https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-251j-introduction-to-mathematical-programming-fall-2009/lecture-notes/MIT6_251JF09_lec06.pdf.

This method selects the entering variable in a degenerate dictionary by the smallest 
lexicographic vector (row).

