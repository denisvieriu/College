function y = Pascal(p, r, N)
y = zeros(1,N);
for i = 1:N
    y(i) = sum(Geometric(p,r));
end
