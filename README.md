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

The solver follows the normal procedure for the simplex method. The program 
checks for feasibility of the linear program, and if it is feasible it runs
the simplex method. Otherwise, if the linear program is initially infeasible 
then the program solves the auxiliary problem. If the auxiliary problem is 
solvable then the simplex method is performed with the objective function 
restored.

**Solver procedure:**

1. Check for feasibility of linear program

2. If infeasible, run auxiliary problem

    2.1 If a dictionary is unbounded, return infeasible
    
    2.2 If the final dictionary has a non-zero optimal value or omega, return
    infeasible
    
    2.3 Otherwise, use final dictionary and restore original objective function
    
3. If feasible, or now feasible after auxiliary problem, run simplex method

    3.1 If a dictionary is unbounded, return unbounded
    
    3.2 Otherwise, return optimal objective value and corresponding xi values
    
For the regular solver, it uses