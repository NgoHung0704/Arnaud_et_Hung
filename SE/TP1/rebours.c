#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
    (void) argc;
    int pid = getpid();
    int n = atoi(argv[1]);
    assert(n > 0);

    printf("%d : debut\n", pid);

    for (int i = 0; i < n; i++)
    {
        printf("%d : %d\n", pid, n-i);
        sleep(1);
    }

    printf("%d : fin\n", pid);

    return EXIT_SUCCESS;
}
