function [y] = Normal(x,mu,sig)
y=(1/sig*sqrt(2*pi)) * exp((-(x - mu).^2)/(2*sig^2));