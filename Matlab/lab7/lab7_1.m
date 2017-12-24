N = 1000;
n = 1 + 10/3 * log10(N)
l = 1/n;
c = 0:l:1;

m=(c(1:end-1) + c(2:end))/2; % mijloacele lor
fr = zeros(1, n);
x = rand(1, N);

for i=1:N
    ind = find(x(i) <= c, 1); % 
    fr(ind - 1) = fr(ind - 1) + 1;
end

fprintf('Index | Bl | Br | Mid | Fr | RelFr\n')
for i=1:n
    fprintf('%d [ %.5f | %.5f ] %f | %d | %f\n', i, c(i), c(i+1), m(i), fr(i), fr(i)/N)
end

figure(1)
hold on
hist(x, n)

plot(m, fr, 'Color', 'r', 'LineWidth', 5)
plot(m, fr, 'r*')

g = hist(x,n)
fr

indmax = find(fr == max(fr));
plot(m(indmax), fr(indmax), 'g^')
        

