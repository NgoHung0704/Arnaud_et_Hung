#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
    (void) argc;
    (void) argv;
    int pid = getpid();

    printf("%d : hello world\n", pid);

    int x = fork();
    if (x)
    {
        printf("%d : je suis le parent de %d\n", pid, x);
    } else {
        int pid = getpid();
        printf("%d : je suis l'enfant\n", pid);
    }

    return EXIT_SUCCESS;
}
