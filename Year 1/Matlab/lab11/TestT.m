function TestT(x, m0, alpha, tiptest)
    xbar = mean(x);
    n = length(x);
    sn = std(x); % deviatia standard
    t = (xbar - m0) * sqrt(n) / sn;
    switch tiptest
        case 'bilateral'
            q = tinv(1 - aplha/2, n - 1);
            if abs(t) < q
                fprintf('H0')
            else
                fprintf('H1')
            end
        case 'right'
            q = tinv(1-alpha, n-1);
            if t < q
                fprintf('H0')
            else
                fprintf('H1')
            end
        case 'left'
            q = tinv(alpha,n-1);
            if t > q
                fprintf('H0')
            else
                fprintf('H1')
            end
    end
end

            
    