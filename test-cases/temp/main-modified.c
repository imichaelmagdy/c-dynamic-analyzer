#include <stdio.h>

int add(int x, int y);

FILE *DAOutputPTR;

int main(int argc, char *argv[])
{
    fclose(fopen("../test-cases/temp/main.txt", "w"));
	DAOutputPTR = fopen("../test-cases/temp/main.txt", "a");

    fprintf(DAOutputPTR, "0\n");
    int x = (int) strtol(argv[1], NULL, 10);
    int y = (int) strtol(argv[2], NULL, 10);

    int testInteger = add(x, y);

    if (testInteger >= 0)
    {
        fprintf(DAOutputPTR, "1\n");
        printf("The result is a positive number!\n");
    } else {
        fprintf(DAOutputPTR, "2\n");
        printf("The result is a negative number!\n");
    }

    if (testInteger >= 5)
    {
        fprintf(DAOutputPTR, "3\n");
        int m = testInteger % 5;
        switch(m)
        {
            fprintf(DAOutputPTR, "4\n");
            case 0:
                fprintf(DAOutputPTR, "5\n");
                printf("5 divides %d", testInteger);
                break;
            default:
                fprintf(DAOutputPTR, "6\n");
                printf("%d modulo 5 is equal to %d", testInteger, m);
                break;
        }
    }
    fclose(DAOutputPTR);
    return 0;
}

int add(int x, int y)
{
    fprintf(DAOutputPTR, "7\n");
    return x+y;
}