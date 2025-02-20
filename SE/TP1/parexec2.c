#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char* argv[])
{
    printf("Bonjour je suis '%s'\n",argv[0]);


    int nb_prog = argc - 3;
    int nb_enfant = 0;
    int seuil = atoi(argv[2]);

    for (int i = 0; i < nb_prog; i++)
    {
        int x = fork();
        nb_enfant++;
        if (!x)
        {
            execl("./rebours","./rebours", argv[i+3], NULL);
        }

        if (nb_enfant >= seuil)
        {
            wait(NULL);
            nb_enfant--;
        }
    }

    while (nb_enfant > 0) {
        wait(NULL);
        nb_enfant--;
    }
    return 0;
}
