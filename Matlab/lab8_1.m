X = [20,21,22,23,24,25,26,27;2,1,3,6,5,9,2,2]
Y = [75,76,77,78,79,80,81,82;3,2,2,5,8,8,1,1]

xnew = []
ynew = []
for i =1 : max(size(x))
    xnew = [xnew, X(1,i) * ones(1, X(2,i))];
    ynew = [ynew, Y(1,i) * ones(1, Y(2,i))];
end

m1 = mean(xnew)
m2 = mean(ynew)

N1 = sum(X(2,:));
N2 = sum(Y(2,:));

m1c = sum(X(1,:).*X(2,:))/N1
m2c = sum(Y(1,:).*Y(2,:))/N2

v1 = sum((xnew-m1).^2)/N1
v1m = var(xnew, 1)

v2 = sum((ynew-m2).^2)/N2
v2m = var(ynew, 1)

v1c = X(2,:).*(X(1,:) - m1).^2
v2c = Y(2,:).*(Y(1,:) - m2).^2