close all

%prob. calculate
X = [0 1 2 3; 1/8 3/8 3/8 1/8];
F = [1/8, 1/2, 7/8, 1];

figure(1)
hold on
plot(X(1,:),X(2,:),'b*')
axis equal
figure(2)
bar(X(2,:))
figure(3)
stairs(X(1,:),F)


p=0.5;
for i = 0:3
    P(i+1)=nchoosek(3,i).*p.^i.*(1-p).^(3-i);
end
figure(1)
hold on
plot(X(1,:),P,'r.');

n = 3;
for i = 0:n
   Pa(i+1)=binopdf(i,n,p); 
end    
figure(1)
hold on
plot(X(1,:),Pa,'gs');
[X(2,:);P;Pa]

x = 0:0.01:3;
y = binocdf(x,n,p);
figure(3)
hold on
plot(x,y,'r*')