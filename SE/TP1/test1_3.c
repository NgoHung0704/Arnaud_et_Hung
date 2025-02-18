#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void)
{
    printf("A\n");
    execl("./rebours","./rebours", "5", NULL);
    printf("A\n");
    return 0;
}