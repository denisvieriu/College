function [Pa,Pb,Pc,Pd,Pe,Pf]=Normal2(x0,x1,x2,alpha,beta,mu,sig)
Pa=normcdf(x0,mu,sig);
Pb=1-Pa;
Pc=normcdf(x2,mu,sig)-normcdf(x1,mu,sig);
Pd=1-Pc;
Pe=norminv(alpha,mu,sig);
Pf=norminv(1-beta,mu,sig);