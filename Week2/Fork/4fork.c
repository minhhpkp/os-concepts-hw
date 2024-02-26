#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
  pid_t pid;
  pid = fork();
  pid = fork();
  pid = fork();
  pid = fork();

  if (pid < 0) { /* error occurred */
    fprintf(stderr, "Fork Failed\n");
    return 1;
  } else if (pid == 0) { /* child process */
    printf("child, pid: %d, ppid: %d\n", getpid(), getppid());
    // printf("%d %d\n", getppid(), getpid());
  } else { /* parent process */
    /* parent will wait for the child to complete */
    wait(NULL);
    sleep(1);
    // printf("%d %d\n", getppid(), getpid());
    printf("parent, pid: %d, ppid: %d\n", getpid(), getppid());
  }

  return 0;
}