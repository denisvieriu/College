count(_,R,R).
count([_|T],R,RR):-
    X is R+1,
    count(T,X,RR).


countM(L,R):-
    count(L,0,R).

listf([],_,0,[]).

listf([H|T],E,R,[HR|TR]):-
    listf(T,E,R,TR),
    RES is E*H,
    RES2 is RES+R,
    RR is RES2 mod 10,
    DR is RES2/10,
    R is DR,
    HR is RR.























