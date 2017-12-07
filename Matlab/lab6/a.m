close all
clear all
N=10000;
p=0.35;
x=Geometric(p,N);
T=tabulate(x);

figure(1)
hold on;
box on;
plot(T(:,1), T(:,2) / N, 'b*');
% 
% r=3;
% y=Pascal(p,r,N);
% Ty=tabulate(y);
% figure(2)
% hold on;
% plot(Ty(:,1),Ty(:,2)/N, 'r*');

figure(2)
for r = 1 : 40
    y = Pascal(p,r,N);
    Ty=tabulate(y);
    plot(Ty(:,1),Ty(:,2)/N,'r*')
    pause(1)
end