/*
 Implementation of class StateGraph for the bloc world problem
 Copyright (C) 2023 Christine Solnon
 Ce programme est un logiciel libre ; vous pouvez le redistribuer et/ou le modifier au titre des clauses de la Licence Publique Générale GNU, telle que publiée par la Free Software Foundation. Ce programme est distribué dans l'espoir qu'il sera utile, mais SANS AUCUNE GARANTIE ; sans même une garantie implicite de COMMERCIABILITE ou DE CONFORMITE A UNE UTILISATION PARTICULIERE. Voir la Licence Publique Générale GNU pour plus de détails.
 */
#include <iostream>
using namespace std;

class StateGraph{
public:
    StateGraph();
    // Creates a state-transition problem
    
    State initialState() const;
    // Return the initial state
    
    bool isFinal(const State &s) const;
    // Return true if s is a final state

    int searchActions(const State &s);
    // Return the number of all possible actions from state s
    
    int getCost(const State &s, int i) const;
    // Precondition: 0<=i<searchActions(s)
    // Return the cost of performing ith action on state s
    
    State transition(const State &s, int i);
    // Precondition: 0<=i<searchActions(s)
    // Return the state obtained when performing ith action on state s
    
    void print(const State &s, const State &s_succ);
    // Display the action used to go from s to s_succ
    
    int heuristic(const State &s, int heuristic) const;
    // Return a lower bound of the length of the shortest path from s to a final state

private:
    vector<int> actions;
    int nbBlocs; // Number of blocs
    int nbStacks; // Number of stacks
};


StateGraph::StateGraph(){
    // Constructor that creates a bloc world planning problem
    cout << "Enter the number of stacks: ";
    cin >> nbStacks;
    cout << "Enter the number of blocs: ";
    cin >> nbBlocs;
    if (nbStacks <= 0 || nbBlocs <= 0) {
        cout << "The number of stacks and the number of blocs must be positive numbers" << endl;
        exit(0);
    }
}

State StateGraph::initialState() const{
    // Return the initial state
    return State(nbStacks, nbBlocs);
}

bool StateGraph::isFinal(const State &s) const{
    // Return true if s is a final state
    for (int i=0; i<nbStacks-1; i++)
        if (s.getNbBlocs(i)>0) return false;
    for (int i=0; i<nbBlocs; i++)
        if (s.getBloc(nbStacks-1, i) != 'a'+nbBlocs-1-i) return false;
    return true;

}

int StateGraph::searchActions(const State &s){
    // Return the number of all possible actions from state s
    // initialize actions with all possible actions from state s
    // An action is an integer value a such that a/nbStacks is the stack from which a bloc is removed and a%nbStacks is the stack on which the bloc is added
    actions.clear();
    for (int i=0; i<nbStacks; i++){
        if (s.getNbBlocs(i)>0){
            for (int j=0; j<nbStacks; j++){
                if (i != j) actions.push_back(i*nbStacks + j);
            }
        }
    }
    return actions.size();
}

int StateGraph::heuristic(const State &s, int heuristic) const {
    // return a lower bound of the length of the shortest path from s to a final state
    int c = 0;
    // Insert your code here to implement a more informed heuristic!
    switch (heuristic) {
    case 1:
        // Heuristic h1 is the number of blocs that are not in the last stack
        return c = nbBlocs - s.getNbBlocs(nbStacks-1);
    case 2:
        // Heuristic h2 is the number of blocs that are (not on the last stack) + (not in the right order x2)
        c = nbBlocs - s.getNbBlocs(nbStacks-1);

        for (int i=0; i<s.getNbBlocs(nbStacks-1); i++){
            if (s.getBloc(nbStacks-1, i) != 'a'+nbBlocs-1-i) {
                c+=  2*(s.getNbBlocs(nbStacks-1) - i);
                break;
            }
        }

        for (int i=0; i<nbStacks-1; i++){
            if (s.getNbBlocs(i)>0){
                int max = s.getBloc(i, 0);
                for (int j=1; j<s.getNbBlocs(i); j++){
                    if (s.getBloc(i, j) < max) c+=1;
                    else max = s.getBloc(i, j);
                }
            }
        }

        return c;
    default:
        return 0; // No heuristic
    }   
}

State StateGraph::transition(const State &s, int i){
    // Return the state obtained when performing ith action on state s
    return State(s, actions[i]/nbStacks, actions[i]%nbStacks);
}

int StateGraph::getCost(const State &s, int i) const{
    // Precondition: 0<=i<searchActions(s)
    // Return the cost of performing ith action on state s
    return 1;
}

void StateGraph::print(const State &s, const State &s_succ){
    static State s0 = initialState();
    if (s == s0){
        printf("Init: ");
        s.print();
    }
    // Print the action that has been used to go from s to s_succ
    for (int i=0; i<nbStacks; i++){
        if (s.getNbBlocs(i)>0){
            for (int j=0; j<nbStacks; j++){
                if (i != j && State(s, i, j) == s_succ){
                    printf("%d->%d: ", i, j);
                    s_succ.print();
                    return;
                }
            }
        }
    }
}
