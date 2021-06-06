#include <stdio.h>

FILE *DAOutputPTR;

int main(int argc, char *argv[])
{
    fclose(fopen("../test-cases/temp/prime.txt", "w"));
	DAOutputPTR = fopen("../test-cases/temp/prime.txt", "a");

    fprintf(DAOutputPTR, "0\n");
    int n, i, flag = 0;
    n = (int) strtol(argv[1], NULL, 10);

    for (i = 2; i <= n / 2; ++i) {
    // condition for non-prime
        fprintf(DAOutputPTR, "1\n");
        if (n % i == 0) {
            fprintf(DAOutputPTR, "2\n");
            flag = 1;
            break;
        }
    }

    if (n == 1) {
        fprintf(DAOutputPTR, "3\n");
        printf("1 is neither prime nor composite.");
    } else {
        fprintf(DAOutputPTR, "4\n");
        if (flag == 0)
        {
            fprintf(DAOutputPTR, "5\n");
            printf("%d is a prime number.", n);
        }
        else
        {
            fprintf(DAOutputPTR, "6\n");
            printf("%d is not a prime number.", n);
        }
    }
    fclose(DAOutputPTR);
    return 0;
}