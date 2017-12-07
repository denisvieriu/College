inserare([],E,[E]).
inserare([H|T],E,[E,H|T]).
inserare([H|T],E,[H|RT]):-
    inserare(T,E,RT).


allSolutions(L,E,RL):-
    findall(Ri,inserare(L,E,Ri),RL).
