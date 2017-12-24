
close all
x=-10:0.025:10;
mu=0;
sig=1;
y1=Normal(x,mu,sig);
z=normpdf(x,mu,sig);
w=normcdf(x,mu,sig);

%mu=3;
%sig=1;
%y2=Normal(x,mu,sig);

%mu=0;
%sig=5;
%y3=Normal(x,mu,sig);

n=10000000;
st1=tpdf(x,n);
st2=tcdf(x,n);
figure(1)
hold on

figure(2)
hold on


figure(1)
hold on
plot(x,z,'b*');
plot(x,st1,'g*');

figure(2)
hold on
plot(x,w,'r');
plot(x,st2,'g');


%plot(x,y1)
%plot(x,y2)
%plot(x,y3)
%legend('y1','y2','y3');
