# G1
##x = [0:0.01:1];
##f = exp(10 * x .* (x - 1)).*sin(12 * pi * x); 
##subplot(1,2,1)
##plot(x, f),xlabel('x'),ylabel('exp(10*x*(x-1))*sin(12*pi*x)')
##f = 3 * exp(5 * x.^2 - 1).*cos(12 * pi * x);
##subplot(1,2,2);
##plot(x, f),xlabel('x'),ylabel('3*exp(5*x^2-1)*cos(12*pi*x)') 

#G2
##a = 3; b = 1;
##t = [0:0.1:10*pi];
##x = (a + b) * cos(t) - b * cos((a/b + 1) * t);
##y = (a + b) * sin(t) - b * sin((a/b + 1) * t);
##plot(x, y);

#G3
##x = [0:0.01:2 * pi];
##f1 = cos(x);
####plot(x, f1, '--'), legend('Cos(x)')
##
####hold on
##f2 = sin(x);
####plot(x, f2, '-'), legend('Sin(x)')
##
####hold on
##f3 = cos(2 * x);
##hold off
##plot(x, f1, x, f2, x, f3), legend('Cos(x)', 'Sin(x)', 'Cos(2x)');

#G4
##x = [0 : 1 : 50];
##f = [0 : 1 : 50];
##f(51)
###logical index
##idx = mod(x, 2) == 1;
##
##for c = 1:51
##  if (mod(c, 2) == 1)
##    f(c) = 3 * c + 1;
##  else
##    f(c) = c / 2;
##  end
##end
##
##plot(x, f);

#G5 done a function for it

#G6
x=[-2:0.01:2];
y=[-4:0.01:4];
[X,Y] = meshgrid(x,y);

F = -1 * exp((X - 1/2).^2 + (Y - 1/2).^2);

surf(X,Y,F);
