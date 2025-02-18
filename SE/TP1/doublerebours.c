#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
    (void) argc;
    (void) argv;
    
    int x = fork();
    if (x)
    {
        execl("./rebours","./rebours", "1", NULL);
    } else {
        execl("./rebours","./rebours", "5", NULL);
    }

    return EXIT_SUCCESS;
}
