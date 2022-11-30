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

$x,y,w_{1},w_{2},w_{3} \ge 0$

And uses the dictionary to perform operations that rearranges the basic variables (rows of the dictionary below $\zeta$) and non-basic variables (columns of dictionary, ie. $x,y$) such that $\zeta$ is maximized, like so:

$\zeta~$ &nbsp; $=$ &nbsp; &nbsp; $5$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{1}{4}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{1}{4}w_{3}$  
<ins>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </ins>  
$w_{1}$&nbsp; $=$ &nbsp; $\frac{13}{2}$&nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{5}{8}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{7}{8}w_{3}$  
$y$ &nbsp; &nbsp; $=$ &nbsp; $\frac{3}{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{3}{8}w_{2}$ &nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{1}{8}w_{3}$  
$x$ &nbsp; &nbsp; $=$ &nbsp; $\frac{7}{2}$ &nbsp; &nbsp; $+$ &nbsp; &nbsp; $\frac{1}{8}w_{2}$ &nbsp; &nbsp; $-$ &nbsp; &nbsp; $\frac{3}{8}w_{3}$

Since the non-basic variables are all being subtracted in $\zeta$, the maximum value they can have is $0$ from above, thus $\zeta = 5$, $w_{1} = \frac{13}{2}$, $w_{2} = w_{3} = 0$, $y = \frac{3}{2}$ and $x = \frac{7}{2}$.

