inserare([],E,[E]).
inserare([H|T],E,[E,H|T]).
inserare([H|T],E,[H|RT]):-
    inserare(T,E,RT).

perm([],[]).
perm([H|T],RP):-
    perm(T,TR),
    inserare(TR,H,RP).

getElem([],_,_,0).
getElem([H|_],CPos,Pos,H):-
    CPos=:=Pos,
    !.

getElem([_|T],CPos,Pos,R):-
    X is CPos+1,
    getElem(T,X,Pos,R).

getElemMain(L,Pos,R):-
    getElem(L,1,Pos,R).

maxCount([],R,R).
maxCount([_|T],R,RL):-
    X is R+1,
    maxCount(T,X,RL).

maxCountMain(L,RL):-
    maxCount(L,0,RL).

absM(Nr,Nr):-
    Nr>0.

absM(Nr,R):-
    R is Nr*(-1).

traversal(_,I,_,N):-
    I>N,
    false,
    !.

traversal(L,I,J,N):-
    I=<N,
    J<I,
    getElemMain(L,I,Ri),
    getElemMain(L,J,Rj),
    C is Ri-Rj,
    absM(C,RC),
    RC=:=1,
    true,
    !.

traversal(L,I,J,N):-
    I=<N,
    J<I,
    getElemMain(L,I,Ri),
    getElemMain(L,J,Rj),
    C is Ri-Rj,
    absM(C,RC),
    RC=\=1,
    JJ is J+1,
    traversal(L,I,JJ,R,R).

