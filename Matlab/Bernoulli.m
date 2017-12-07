function y = Bernoulli(P, S)
U = rand(1, S);
y = U < P; % generam un vector cu numere intre 0 si 1