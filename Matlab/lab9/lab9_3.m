clear all; close all; clc

alpha = 0.05;

x = [1.48 1.26 1.52 1.56 1.48 1.46
1.30 1.28 1.43 1.43 1.55 1.57
1.51 1.53 1.68 1.37 1.47 1.61
1.49 1.43 1.64 1.51 1.60 1.65
1.60 1.64 1.51 1.51 1.53 1.74];

x = x(:)';

s = std(x);
n = length(x);
c1 = chi2inv(1 - alpha/2, n-1);
c2 = chi2inv(alpha/2, n-1);

left = (n-1)*s^2/c1;
right = (n-1)*s^2/c2;
[left s^2 right]