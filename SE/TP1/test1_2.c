#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
    fork();

    if (fork())
    {
        fork();
    }

    printf("A avec pid = %d\n", getpid());    
    return 0;
}
