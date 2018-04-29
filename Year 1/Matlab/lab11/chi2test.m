function chi2test(x, sigma, alpha, tiptest)
    n = length(x);
    sn = std(x);
    c = (n-1)*sn^2/sigma^2;
    switch tiptest
        case 'bilateral'
            q1 = chi2inv(alpha/2, n-1);
            q2 = chi2inv(1-alpha/2, n-1);
            if (q1 < c) & ( c < q2)
                fprintf('H0')
            else
                fprintf('H1')
            end
            
        case 'right'
            q = chi2inv(1-alpha,n-1);
            if c < q
                fprintf('H0')
            else
                fprintf('H1')
            end
        case 'left'
            q = chi2inv(alpha, n-1);
            if c > q
                fprintf('H0')
            else
                fprintf('H1')
            end
    end
end
