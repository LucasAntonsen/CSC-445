# Simplex Method Solver

In the UVic course, CSC 445: Operations Research: Linear Programming, I developed the program simplex.java for solving a linear program.

The program takes a linear program like so:

max.&nbsp; &nbsp; &nbsp; $x$ &nbsp; $+$ &nbsp; &nbsp; $y$  
s.t.&nbsp; &nbsp; $-2x$ &nbsp; $+$ &nbsp; &nbsp; $y$ &nbsp; $\le$ &nbsp; $1$  
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $x$ &nbsp; $+$ &nbsp; $3y$ &nbsp; $\le$ &nbsp; $8$  
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $3x$ &nbsp; $+$ &nbsp; &nbsp; $y$ &nbsp; $\le$ &nbsp; $12$  
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; $x,y$ &nbsp; $\ge$ &nbsp; $0$

Converts it into a dictionary:

$\zeta~$ &nbsp; $=$ &nbsp; $0$ &nbsp; &nbsp; $+$ &nbsp; &nbsp; $x$ &nbsp; $+$ &nbsp; &nbsp; $y$  
<ins>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </ins>  
$w_{1}$&nbsp; $=$ &nbsp; $1$ &nbsp; &nbsp; $+$ &nbsp; $2x$ &nbsp; $-$ &nbsp; &nbsp; $y$  
$w_{2}$&nbsp; $=$ &nbsp; $8$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $x$ &nbsp; $-$ &nbsp; $3y$  
$w_{3}$&nbsp; $=$ &nbsp; $12$ &nbsp; $-$ &nbsp; $3x$ &nbsp; $-$ &nbsp; &nbsp; $y$  

