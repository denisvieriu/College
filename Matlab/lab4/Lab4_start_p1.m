clear all
close all
x0=2; x1=3; x2=5;
mu=0;
sig=1;
alpha=0.8;
beta=0.75;
n=3;

[Pa,Pb,Pc,Pd,Pe,Pf]=Normal2(x0,x1,x2,alpha,beta,mu,sig);
[Pa,Pb,Pc,Pd,Pe,Pf]=Student(x0,x1,x2,alpha,beta,n)