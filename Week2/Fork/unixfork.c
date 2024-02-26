#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
  pid_t pid;
  /* fork a child process */
  pid = fork();
  if (pid < 0)
  { /* error occurred */
    fprintf(stderr, "Fork Failed\n");
    return 1;
  }
  else if (pid == 0)
  { /* child process */
    execlp("/bin/ls", "ls", NULL);
    printf("this line is not reached unless there's an error\n");
  }
  else
  { /* parent process */
    /* parent will wait for the child to complete */
    wait(NULL);
    printf("Child Complete\n");
  }

  return 0;
}