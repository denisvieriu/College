%x^j

close all
e = exp(1);
for c = 1:3
    if c == 1
        x = 0:0.1:2;
        F = zeros(length(x),4);
    elseif c == 2
        x = 0:0.1:2 * pi;
        F = zeros(length(x),4);
    elseif c == 3
         x = -2:0.1:2;
         F = zeros(length(x),4);
    end
       
figure(c);
    for j = 1:4
        if c == 1
            y = x.^j;   
            F(:,j) = y;
        elseif c == 2
            y = sin(x./j);   
            F(:,j) = y;
        elseif c == 3
            y = x.^j;
            F(:,j) = y;
        end

        
        %%plot(x,y, 'LineWidth', j + 1, 'LineStyle', ':')    
    end
    hold on
    plot(x, F)
    
    if c == 1
        legend('x^1', 'x^2', 'x^3', 'x^4');
    elseif c == 2
        legend('sin(x/1)', 'sin(x/2)', 'sin(x/3)', 'sin(x/4)');
    elseif c == 3
        legend('e^((-1) * x^2)', 'e^((-2) * x^2)', 'e^((-3) * x^2)', 'e^((-4) * x^2)')
    end
end



