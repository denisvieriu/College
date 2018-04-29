function TestZ(x,m0,sigma,alpha,tiptest)
    xbar = mean(x);
    n = length(x)
    z = (xbar - m0) * sqrt(n) / sigma;
    switch tiptest
        case 'bilateral'
            q = norminv(1-alpha/2,0,1);
            if abs(z) < q
                fprintf('H0')
            else
                fprontf('H1')
            end
        case 'right'
            q = norminv(1-alpha,0,1);
            if z < q
                fprintf('H0')
            else
                fprintf('H1')
            end
        case 'left'
            q = norminv(alpha,0,1);
            if z > q
                fprintf('H0')
            else
                fprintf('H1')
            end
    end
end

        