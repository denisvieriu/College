vale([H1,H2|_],F):-
    F=:=(-1),
    H1<H2,
    fail.

vale([H1,H2|T],F):-
    F=:=(-1),
    H1>H2,
    vale([H2|T],0).

vale([H1,H2|T],F):-
    F=:=0,
    H1>H2,
    vale([H2|T],0).

vale([H1,H2|T],F):-
    F=:=0,
    H1<H2,
    vale([H2|T],1).

vale([H1,H2|_],F):-
    F=:=1,
    H1>H2,
    fail.

vale([H1,H2|T],F):-
    F=:=1,
    H1<H2,
    vale([H2|T],1).

vale([H1,H2],F):-
    F=:=1,
    H1<H2,
    true.

vale([H1,H2],F):-
    F=:=0,
    H1<H2,
    true.

vale([_],_):-
    false.
    
valeTest():-
    vale([10,8,6,9,11,13],-1),
    \+ vale([10,8,6,5],-1),
    \+ vale([10,8,6,8,10,8,6,8,10],-1),
    vale([10,8,6,9],-1).
    
    

