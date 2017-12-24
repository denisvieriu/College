clear all;
close all;

x = -6:0.01:6;
y01 = Normala(x, 0, 1);
y31 = Normala(x, 3, 1);
y02 = Normala(x, 0, 2);

figure(1)
hold on; box on;
plot(x, y01, 'b');
plot(x, y31, 'r');

figure(2)
hold on; box on;
plot(x, y01, 'b');
plot(x, y02, 'r');

% intre -sig, sig -> 68%
% intre -2*sig, 2*sig -> 95%
% intre -3*sig, 3*sig -> 98%

%quad integral pentru integrale 
%integral pentru integrale improprii -inf , inf

sigma = 1;
a01 = quad(@(x) Normala(x,0,1), -10, 10)
% aria intre -1 si 1

a02 = quad(@(x) Normala(x,0,2),-20, 20)