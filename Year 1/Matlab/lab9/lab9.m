%lab9  1

x = [7 7 4 5 9 9
4 12 8 1 8 7
3 13 2 1 17 7
12 5 6 2 1 13
14 10 2 4 9 11
3 5 12 6 10 7];

% luam de la noi
% cu cat intervalul mai mic, cu atat mai multe val
alpha = 0.01;
x = x(:)';
xbar = mean(x);
sigma = 5;
n = length(x);

z = norminv(alpha/2,0,1);

left = (xbar + (sigma / (sqrt(n))) * (z))
right = (xbar - (sigma / (sqrt(n))) * (z)) 


