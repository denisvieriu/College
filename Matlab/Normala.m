function y = Normala(x,mu,sig)

y = 1/(sqrt(2*pi)*sig)*exp(-(x-mu).^2/(2*sig^2));

