#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>

typedef struct{
    int n; // number of vertices in the graph
    int* nbSucc; // for each 0<=i<n, nbSucc[i] = number of successors of i
    int** succ; // for each 0<=i<n and each 0<=j<nbSucc[i], succ[i][j]=jth successor of i
} DIGRAPH;


DIGRAPH* readDigraph(FILE *fp){
    // return the DIGRAPH contained in file fp
    DIGRAPH *g = (DIGRAPH*)malloc(sizeof(DIGRAPH));
    if (fscanf(fp, "%d\n", &(g->n)) != 1 || g->n <= 0){
        printf("erreur de lecture du fichier\n");
        exit(1);
    }
    g->nbSucc = (int*)malloc(g->n*sizeof(int));
    g->succ = (int**)malloc(g->n*sizeof(int*));
    int succ[g->n];
    for (int i=0; i<g->n; i++){
        g->nbSucc[i] = 0;
        while (1){
            if (fscanf(fp, "%d", &(succ[g->nbSucc[i]])) != 1 || succ[g->nbSucc[i]] >= g->n){
                printf("erreur de lecture du fichier\n");
                exit(1);
            }
            if (succ[g->nbSucc[i]]<0) break;
            g->nbSucc[i]++;
        };
        g->succ[i] = (int*)malloc(g->nbSucc[i]*sizeof(int));
        memcpy(g->succ[i], succ, g->nbSucc[i]*sizeof(int));
    }
    return g;
}

void printDigraph(DIGRAPH *g){
    // For each vertex of g, display the list of its successors
    for (int i=0; i<g->n; i++){
        printf("Vertex %d has %d successors: ", i, g->nbSucc[i]);
        for (int j=0; j<g->nbSucc[i]; j++)
            printf("%d ", g->succ[i][j]);
        printf("\n");
    }
}

void delete(DIGRAPH *g){
    // free the memory allocated for g
    free(g->nbSucc);
    for (int i=0; i<g->n; i++) free(g->succ[i]);
    free(g->succ);
    free(g);
}

int main(){
    FILE* fp  = fopen("exemple2.txt", "r");
    DIGRAPH* g = readDigraph(fp);
    fclose(fp);
    printDigraph(g);

    double e = 1.0e-10;
    double a = 0.9;

    double s[g->n];
    for (int i=0; i<g->n; i++) s[i] = 1.0 / g->n;

    int absorbant[g->n];
    int h = 0;
    for (int i=0; i<g->n; i++) {
        if (g->nbSucc[i] == 0) absorbant[h++] = i;
    }

    printf("s0 = ( \t");
    double somme = 0;
    for (int i=0; i<g->n; i++) {
        printf("%.6f ", s[i]);
        somme += s[i];
    }
    printf(") ");
    printf("somme = %.10f\n", somme);

    int i = 1;
    while (1) {
        double tmp[g->n];
        for (int j=0; j<g->n; j++) {
            tmp[j] = (1.0-a)/g->n;
            for (int k=0; k < h; k++) tmp[j] += a*s[absorbant[k]]/g->n;
        }

        for (int j=0; j<g->n; j++){
            if (g->nbSucc[j] != 0) {
                double score = a*s[j]/g->nbSucc[j];
                for (int k=0; k<g->nbSucc[j]; k++){
                    tmp[g->succ[j][k]] += score;
                }
            }
        }

        printf("s%d = ( \t", i);
        double somme = 0;
        int stop = 1;
        for (int j=0; j<g->n; j++){
            if (fabs(tmp[j] - s[j]) > e) {
                stop = 0;
            }

            s[j] = tmp[j];
            somme += s[j];
            printf("%.6f ", s[j]);
        }
        printf(") somme = %.10f\n", somme);

        if (stop) {
            printf("\nIterration : %d \n", i-1);
            break;
        }
        i++;
    }

    ////////////////////////////
    // Create an array of indices
    int sommets[g->n];
    for (int j = 0; j < g->n; j++) {
        sommets[j] = j;
    }

    // Sort sommets based on the values in s
    for (int j = 0; j < g->n - 1; j++) {
        for (int k = j + 1; k < g->n; k++) {
            if (s[sommets[j]] < s[sommets[k]]) {
                int temp = sommets[j];
                sommets[j] = sommets[k];
                sommets[k] = temp;
            }
        }
    }

    // Print the sommets of the first 5 maximum values in s
    printf("\n\nThe sommets with the first 5 maximum values in s are: ");
    for (int j = 0; j < 6 && j < g->n; j++) {
        printf("%d ", sommets[j]);
    }

    printf("\n");
    ///////////////////////////

    delete(g);

    return 0;
}
