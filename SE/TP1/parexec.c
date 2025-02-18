#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char* argv[])
{
    printf("Bonjour je suis '%s'\n",argv[0]);


    int nb_prog = argc - 2;

    for (int i = 0; i < nb_prog; i++)
    {
        int x = fork();
        if (!x)
        {
            execl("./rebours","./rebours", argv[i+2], NULL);
        }
    }
    for (int i = 0; i < nb_prog; i++)
    {
        wait(NULL);
    }
    return 0;
}
