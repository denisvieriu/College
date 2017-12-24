n=200;
p=0.3;
k=0:n;
y=binopdf(k,n,p);
figure(1)
hold on
plot(k,y,'b*')