clear all;
close all;
clc;
alpha = 0.999;

x = [99.8 99.9 98.0 100.1 100.5 100.0 100.2; 2 5 3 4 2 2 2];

%scriem x desfasurat [99.8 99.8 ...
xnew = [];
for i = 1: length(x)
    xnew = [xnew, x(1,i) * ones(1, x(2,i))];
end

xbar = mean(xnew)
n = length(xnew);
s = var(xnew);
t = tinv(1-alpha/2, n-1);
left = xbar - s/sqrt(n) * t
right = xbar + s/sqrt(n) * t
