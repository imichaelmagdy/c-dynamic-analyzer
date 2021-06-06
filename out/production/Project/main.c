#include <stdio.h>

int add(int x, int y);

int main(int argc, char *argv[])
{
    int x = (int) strtol(argv[1], NULL, 10);
    int y = (int) strtol(argv[2], NULL, 10);

    int testInteger = add(x, y);

    if (testInteger >= 0)
    {
        printf("The result is a positive number!\n");
    } else {
        printf("The result is a negative number!\n");
    }

    if (testInteger >= 5)
    {
        int m = testInteger % 5;
        switch(m)
        {
            case 0:
                printf("5 divides %d", testInteger);
                break;
            default:
                printf("%d modulo 5 is equal to %d", testInteger, m);
                break;
        }
    }
    return 0;
}

int add(int x, int y)
{
    return x+y;
}