main(Cocktails,Ingredients,X):-enlever_les_ingredients(Cocktails,Ingredients,Resultat_enlever_les_ingredients),
							   inferieur_a_n(Resultat_enlever_les_ingredients,X).


/*
 *  but de la fonction -> enlever_les_ingredients une liste de liste
 *
 *  C est la liste des ingrédients du cocktail
 *  Lingredient est la liste des ingredients que l on possede 
 *  GoodSizeCocktails est la liste des cocktails à la fin qui contient tout les cocktails possible avec 1 ingrédients manquants
 */

/*enlever_les_ingredients([],Ingredients,L).
enlever_les_ingredients(Cocktail,Ingredients,Reponse_recursive)
,append(Reponse,[Reponse_exist],Reponse)
*/


enlever_les_ingredients([],Ingredients,[]).
enlever_les_ingredients([Premier|Cocktails],Ingredients,[R|Rs]):-enlever_les_ingredients(Cocktails,Ingredients,Rs),
																 exist(Premier,Ingredients,Resultat_exist),
																 R=Resultat_exist.


exist(Cocktail,[],Cocktail).
exist(Cocktail,[Ldebut|Lingredient],R):-retrait_elt2(Ldebut,Cocktail,Cocktailavecelementmanquant),
										exist(Cocktailavecelementmanquant,Lingredient,R).


/*
 * but de la fonction -> dressé une liste des éléments qui manque aux cocktails
 *
 *  C est la liste des ingrédients du cocktail
 *  Lingredient est la liste des ingredients que l on possede 
 */


inferieur_a_n([],[]).
inferieur_a_n([Premier|Cocktail],[R|Rs]):-inferieur_a_n(Cocktail,Rs),length(Premier,1),Premier=R.
inferieur_a_n([Premier|Cocktail],Rs):-inferieur_a_n(Cocktail,Rs),length(Premier,Y),Y\=1.


/*
 * but de la fonction -> dressé une liste des éléments qui manque aux cocktails
 *
 *  C est la liste des ingrédients du cocktail
 *  Lingredient est la liste des ingredients que l on possede 
 */



%calcul_iteration([],[]).
%calcul_iteration([Premier|Cocktails],[R|Rs]):-calcul_iteration(Cocktails,Rs),R=Y,not(member([Ing,L],R)),flatten([Premier,1],Y),next(Premier,Ing,Num).
%,member([Ing,L],R)
%calcul_iteration([Premier|Cocktails],R,VraiRep):-calcul_iteration(Cocktails,Resultat,VraiRep),recherche(Premier,R,R,N,Newlist),ajoutplus1(Premier,Newlist,N,Resultat).


recherche(C,Occurence,[R|Rs],Number,T):-retrait_elt2(R,Occurence,T),next(R,X,Y),[X]=C,Number=Y.
recherche(C,Occurence,[R|Rs],Number2,T2):-recherche(C,Occurence,Rs,Number,T),next(R,X,Y),[X]\=C.
next([Ing|Num],X,Y):-X=Ing,Y=Num.

ajoutplus1(C,R,Number,Resultat):-append([Buff],R,Resultat),append(C,Number,Buff).


/*
Utilitaire
*/

%flatten trouvé sur internet
flatten([],[]).
flatten([X|Xs],Y) :-
   flatten(X,XF),
   flatten(Xs,XsF),
   append(XF,XsF,Y).
flatten(X,[X]).

%on vous pique votre retrait_elt mr rueher
retrait_elt2(J,[],[]).
retrait_elt2(J,[J|L],L).
retrait_elt2(J,[K|L],[K|M]):-retrait_elt2(J,L,M).

/*

main([[ia,ib,ic],[ib,ic],[ia,ic],[ia,ib,id],[ia,id],[ia,ib],[ia,ib,id,ie]],[ia,ib],L). 


enlever_les_ingredients([[ia,ib,ic],[ib,ic],[ia,ic],[ia,ib,id],[ia,id],[ia,ib],[ia,ib,id,ie]],[ia,ib],L). 

enlever_les_ingredients([ia,ib,ic],[ia,ib],L).

exist([a,b,c],[a,b],R).

inferieur_a_n([[c],[c],[c],[d],[d],[],[d,e]],X).

calcul_iteration([[c],[c],[c],[d],[d]],L).

recherche([c],[[c,1],[d,2]],[[c,1],[d,2]],Y,T).
-----> 1



recherche([d],[[d,2],[c,1]],[[d,2],[c,1]],Y,T).
-------> 2

recherche([d],[[e,2],[c,1]],[[d,2],[c,1]],Y,T).



ajoutplus1([c],[[d,2]],[2],R).
*/