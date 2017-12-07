function x = Geometric(p,N)
x = zeros(1,N);
for i = 1:N
    while rand >= p
        x(i) = x(i) + 1;
    end
end
