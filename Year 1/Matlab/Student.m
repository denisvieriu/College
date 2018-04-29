function [Pa,Pb,Pc,Pd,Pe,Pf]=Student(x0,x1,x2,alpha,beta,n)
Pa=tcdf(x0,n);
Pb=1-Pa;
Pc=tcdf(x2,n)-tcdf(x1,n);
Pd=1-Pc;
Pe=tinv(alpha,n);
Pf=tinv(1-alpha,n);