##x = [0:0.01:5];
##y = exp(-1.5*x).*sin(10*x);
##plot(x, y);

clearvars

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