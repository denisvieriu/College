function z = Binomial(p, n, S)
for i = 1 : S
    z(i) = sum(Bernoulli(p, n));
end
