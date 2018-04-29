% sa generam numere cu probl p
% restul 1 - p

close all;
clear all;
S = 50000;
P = 0.65;
y = Bernoulli(P, S);
fr1 = sum(y)/S;
fr0 = 1-fr1;
[fr1 P];


n = 20;
z = Binomial(P, n, S);
uz = unique(z);
lz = length(uz);
nz = hist(z, lz);
frz = nz / S;

for k = 0:n
    Teor(k+1) = nchoosek(n,k) * P^k * (1-P)^(n-k);
end
Teor
frz
hist(z)