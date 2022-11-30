# Simplex Method Solver

In the UVic course, CSC 445: Operations Research: Linear Programming, I developed the program _simplex.java_ for solving a linear program.

In brief, the program takes a linear program like so:

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

And uses the dictionary to perform operations that rearranges the variables and equations such that $\zeta$ is maximized like so:

$\zeta~$ &nbsp; $=$ &nbsp; &nbsp; $5$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{1}{4}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{1}{4}w_{3}$  
<ins>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </ins>  
$w_{1}$&nbsp; $=$ &nbsp; $\frac{13}{2}$&nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{5}{8}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{7}{8}w_{3}$  
$y$ &nbsp; &nbsp; $=$ &nbsp; $\frac{3}{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{3}{8}w_{2}$ &nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{1}{8}w_{3}$  
$x$ &nbsp; &nbsp; $=$ &nbsp; $\frac{7}{2}$ &nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{1}{8}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{3}{8}w_{3}$
