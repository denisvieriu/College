clear all
N = 20;
X=randi([0,1],3,N);
S=sum(X);
%T=tabulate(S);
%T=[T T(:,2)/N [1/8 3/8 3/8 1/8]']
Su=unique(S);
for i=1:length(Su)
    T(i)=sum(S==Su(i));
end

[Su' T' T'/N]