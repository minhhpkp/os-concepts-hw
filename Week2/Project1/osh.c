#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define MAX_LINE 80 /* The maximum length command */
#define HISTORY_SIZE 10

struct HistoryItem {
  int id;
  char *command[MAX_LINE / 2 + 1];
  int numTokens;
};

struct HistoryItem history[HISTORY_SIZE];
int head = -1, tail = 0;
char *args[MAX_LINE / 2 + 1]; /* command line arguments */
int numTokens;

void stringcopy(char **dest, char *src) {
  // Allocate memory for the dest string
  (*dest) = malloc(strlen(src) + 1);
  if ((*dest) == NULL) {
    perror("Memory allocation failed");
    exit(EXIT_FAILURE);
  }

  // Copy the src string into the allocated memory
  strcpy(*dest, src);
}

/**
 * add a command to history.
 */
void add(void) {
  int prevhead = head;
  head = (head + 1) % HISTORY_SIZE;

  if (prevhead == -1) {
    history[head].id = 1;
  } else {
    history[head].id = history[prevhead].id + 1;
  }
  history[head].numTokens = numTokens;

  // if head reaches tail and the history is not empty
  if (head == tail && prevhead != -1) {
    // Free the tail item
    for (int i = 0; i < history[tail].numTokens; ++i) {
      // Free memory for each token
      free(history[tail].command[i]);
    }
    tail = (tail + 1) % HISTORY_SIZE;
  }

  for (int i = 0; i < numTokens; ++i) {
    stringcopy(&(history[head].command[i]), args[i]);
  }
  history[head].command[numTokens] = NULL;
}

// print history item at specified index
void printHistoryItem(int index) {
  printf("%d", history[index].id);
  for (int i = 0; i < history[index].numTokens; ++i) {
    printf(" %s", history[index].command[i]);
  }
  printf("\n");
}

// load history item at specified index
void loadHistoryItem(int index) {
  numTokens = history[index].numTokens;
  for (int i = 0; i < history[index].numTokens; ++i) {
    stringcopy(&(args[i]), history[index].command[i]);
  }
}

int main(void) {
  int should_run = 1; /* flag to determine when to exit program */

  // for reading lines
  char line[256];
  char sen[256];

  while (should_run) {
    printf("osh>");
    fflush(stdout);

    /**
     * After reading user input, the steps are:
     * (1) fork a child process using fork()
     * (2) the child process will invoke execvp()
     * (3) if command included &, parent will invoke wait()
     */
    if (fgets(line, sizeof(line), stdin) == 0) continue;
    if (sscanf(line, "%255[^\n]", sen) != 1) continue;
    // printf("Found: %s\n", sen);

    // Tokenize the input string
    char *token = strtok(sen, " \t\n");  // Split by space, tab, and newline

    // handle exit command
    if (strcmp(token, "exit") == 0) return 0;

    // handle history command
    if (strcmp(token, "history") == 0) {
      if (head == -1) {
        printf("No commands in history.\n");
      } else {
        for (int i = head;; i = (i - 1 + HISTORY_SIZE) % HISTORY_SIZE) {
          printHistoryItem(i);
          if (i == tail) break;
        }
      }
      continue;
    }

    int doTokenize = 1;
    if (strcmp(token, "!!") == 0) {
      if (head == -1) {
        printf("No commands in history.\n");
        continue;
      } else {
        printHistoryItem(head);
        loadHistoryItem(head);
        doTokenize = 0;
      }
    } else if (token[0] == '!') {
      if (head == -1) {
        printf("No commands in history.\n");
        continue;
      } else {
        int isNumber = 1;
        for (int i = 1; i < strlen(token); ++i) {
          if (token[i] < '0' || token[i] > '9') {
            isNumber = 0;
            break;
          }
        }
        if (isNumber) {
          int id = 0;
          for (int i = 1; i < strlen(token); ++i) {
            id = id * 10 + (token[i] - '0');
          }
          if (id >= history[tail].id && id <= history[head].id) {
            int index = (tail + id - history[tail].id) % HISTORY_SIZE;
            printHistoryItem(index);
            loadHistoryItem(index);
            doTokenize = 0;
          } else {
            printf("No such command in history.\n");
            continue;
          }
        }
      }
    }

    if (doTokenize) {
      numTokens = 0;
      while (token != NULL) {
        stringcopy(&(args[numTokens]), token);
        numTokens++;

        // Get the next token
        token = strtok(NULL, " \t\n");
      }
    }

    add();

    int runChildBackground = 0;
    if (strcmp(args[numTokens - 1], "&") == 0) {
      numTokens--;
      free(args[numTokens]);
      runChildBackground = 1;
    }
    args[numTokens] = NULL;

    pid_t pid;
    pid = fork();
    if (pid < 0) { /* error occurred */
      fprintf(stderr, "Fork Failed\n");
      return 1;
    } else if (pid == 0) {
      /* child process */
      execvp(args[0], args);
      return 0;
    } else {
      /* parent process */
      /**
       * if user does not indicate to run the child in background with &
       * then the parent will wait for the child to complete
       */
      if (!runChildBackground) wait(NULL);
    }

    for (int i = 0; i < numTokens; ++i) {
      free(args[i]);
    }
  }
  return 0;
}