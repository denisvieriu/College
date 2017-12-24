x = [20,21,22,23,24,25,26,27;2,1,3,6,5,9,2,2];
y = [75,76,77,78,79,80,81,82;3,2,2,5,8,8,1,1];
xnew = [];
ynew = [];


Nx = sum(x(2,:));
Ny = sum(y(2,:));

for i=1:length(x(1,:))
     xnew = [xnew, x(1,i)*ones(1,x(2,i))];
end

for i=1:length(y(1,:))
    ynew = [ynew, y(1,i)*ones(1,y(2,i))];
end

mx = mean(xnew);
my = mean(ynew);

vx = var(xnew,1); % 1 - ca sa imparta cu 1/n
vy = var(ynew,1);

covarianta = cov(xnew, ynew, 1);

clasic = sum((xnew - mx) .* (ynew - my))/Nx;
[covarianta(1,2) clasic]

Ro = corrcoef(xnew, ynew);
RoClassic = clasic / sqrt(vx * vy);

x = 20:27;
y = my + RoClassic * sqrt (vy/vx) * (x - mx);
figure(1)
hold on; box on;
plot(x, y, 'r')
plot(xnew, ynew, 'b*')
