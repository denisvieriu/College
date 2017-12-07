cmmdcM(A,B,A):-
    B =:= 0.

cmmdcM(A,B,R):-
    C is B mod A,
    cmmdcM(B,C,R).

%cmmdcList([R],[R]).
%cmmdcList([H|T],R):-
%    cmmdcList(T,RR),
%    cmmdcM(H,RR,R).
