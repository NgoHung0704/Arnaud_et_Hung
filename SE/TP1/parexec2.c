#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char* argv[]) {
    printf("Bonjour je suis '%s'\n",argv[0]);
    
    int seuil = atoi(argv[2]);
    int n = seuil-1;
    for (int i = 3; i < argc; ) {
        if (n < seuil) {
            if (!fork()) {
                execl("./rebours","./rebours", argv[i], NULL);
            }
            i++;
            n--;
        } else {
            wait(NULL);
            n--;
        }
        if (n < 0) n = seuil;
    }

    for (int i = 0; i < (argc+1)/2; i++) {
        wait(NULL);
    }
    return EXIT_SUCCESS;
}
