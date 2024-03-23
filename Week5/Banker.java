package Week5;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Implementations of Banker's algorithm for finding out whether a system is in
 * a safe state, determining whether requests for resources can be safely
 * granted and detecting deadlocks.
 */
public class Banker {
    /**
     * Figures out whether or not a system is in a safe state.
     * 
     * @param n          the number of processes in the system.
     * @param m          the number of resource types.
     * @param available  the number of available resources of each type.
     * @param allocation the number of resources of each type currently allocated to
     *                   each process.
     * @param need       the remaining resource need of each process.
     * @return true if the system is in a safe state and false otherwise.
     */
    public static boolean checkSafety(int n, int m, int[] available, int[][] allocation, int[][] need) {
        // Initialization
        int[] work = Arrays.copyOf(available, available.length);
        boolean[] finish = new boolean[n];
        Arrays.fill(finish, false);

        // for storing the sequence satisfying the safety criteria
        int[] sequence = new int[n];
        int cur = 0;

        while (true) {
            boolean finishableProcessExists = false;
            // find an index i such that finish[i] == false and need[i] <= work
            for (int i = 0; i < n; ++i) {
                if (!finish[i] && isLessThanOrEqualTo(m, need[i], work)) {
                    finishableProcessExists = true;
                    // work = work + allocation[i]
                    for (int j = 0; j < m; ++j) {
                        work[j] += allocation[i][j];
                    }
                    finish[i] = true;
                    sequence[cur++] = i;
                }
            }
            // stop if no such i exists
            if (!finishableProcessExists) {
                break;
            }
        }

        // if finish[i] == true for all i then the system is in a safe state
        for (boolean finished : finish) {
            if (!finished) {
                return false;
            }
        }

        System.out.println("The following sequence satisfies the safety criteria:");
        for (int process : sequence) {
            System.out.print(process + " ");
        }
        System.out.println();

        return true;
    }

    /**
     * Determines whether a request for resources can be safely granted.
     * 
     * @param n          the number of processes in the system.
     * @param m          the number of resources types.
     * @param available  the number of available resources of each type.
     * @param allocation the number of resources of each type currently allocated to
     *                   each process.
     * @param need       the remaining resource need of each process.
     * @param process    the process requesting resources.
     * @param request    the number of requested resources of each type.
     * @return true if the request can be safely fulfilled and false otherwise.
     */
    public static boolean canSafelyGrantResources(int n, int m, int[] available, int[][] allocation,
            int[][] need,
            int process, int[] request) {
        if (!isLessThanOrEqualTo(m, request, need[process])) {
            System.out.printf("Process #%d has exceeded its maximum claim.\n", process);
            return false;
        }

        if (!isLessThanOrEqualTo(m, request, available)) {
            System.out.printf("Process #%d must wait, since the resources are not available.\n", process);
            return false;
        }

        int[] availableCopy = Arrays.copyOf(available, available.length);
        int[][] allocationCopy = Arrays.stream(allocation).map(int[]::clone).toArray(int[][]::new);
        int[][] needCopy = Arrays.stream(need).map(int[]::clone).toArray(int[][]::new);

        // have the system pretend to have allocated the requested resources to the
        // requesting process by modifying the state as follow:
        // available = available - request
        // allocation[process] += request
        // need[process] += request
        for (int i = 0; i < m; ++i) {
            availableCopy[i] -= request[i];
            allocationCopy[process][i] += request[i];
            needCopy[process][i] -= request[i];
        }

        return checkSafety(n, m, availableCopy, allocationCopy, needCopy);
    }

    /**
     * Determines whether the system is in a deadlocked state.
     * 
     * @param n          the number of processes in the system.
     * @param m          the number of resources types.
     * @param available  the number of available resources of each type.
     * @param allocation the number of resources of each type currently allocated to
     *                   each process.
     * @param request    the number of requested resources of each type.
     * @return true if the system is in a deadlocked state, and false otherwise.
     */
    public static boolean detectDeadlocks(int n, int m, int[] available, int[][] allocation, int[][] request) {
        // Initialization
        int[] work = Arrays.copyOf(available, available.length);
        boolean[] finish = new boolean[n];
        Arrays.fill(finish, false);

        // for storing the sequence satisfying the safety criteria
        int[] sequence = new int[n];
        int cur = 0;

        while (true) {
            boolean satisfiableRequestExists = false;

            for (int i = 0; i < n; ++i) {
                if (!finish[i] && isLessThanOrEqualTo(m, request[i], work)) {
                    satisfiableRequestExists = true;
                    for (int j = 0; j < m; ++j) {
                        work[j] += allocation[i][j];
                    }
                    finish[i] = true;
                    sequence[cur++] = i;
                }
            }

            if (!satisfiableRequestExists) {
                break;
            }
        }

        boolean isDeadlocked = false;

        for (int i = 0; i < n; ++i) {
            if (!finish[i]) {
                isDeadlocked = true;
                System.out.printf("Process #%d is deadlocked.\n", i);
            }
        }

        if (!isDeadlocked) {
            System.out.println("The following sequence satisfies the deadlock-free criteria:");
            for (int process : sequence) {
                System.out.print(process + " ");
            }
            System.out.println();
        }

        return isDeadlocked;
    }

    /**
     * Determines whether a <= b.
     * We say that a <= b if and only if a[i] <= b[i] for all i = 0, 1, ..., m - 1.
     * 
     * @param m the dimension of each array.
     * @param a first array.
     * @param b second array.
     * @return true if a <= b and false otherwise.
     */
    private static boolean isLessThanOrEqualTo(int m, int[] a, int[] b) {
        for (int i = 0; i < m; ++i) {
            if (a[i] > b[i]) {
                return false;
            }
        }
        return true;
    }

    private static int[][] readArray(int n, int m, Scanner scanner) {
        int[][] array = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                array[i][j] = scanner.nextInt();
            }
        }
        return array;
    }

    private static void testSafetyAlgorithm() throws Exception {
        File file = new File(".\\Week5\\sample_input_0.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] allocation = readArray(n, m, scanner);
        int[][] need;

        // if given need
        // need = readArray(n, m, scanner);

        // if given max only
        int[][] max = readArray(n, m, scanner);
        need = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        int[] available = new int[m];
        for (int i = 0; i < m; ++i) {
            available[i] = scanner.nextInt();
        }

        boolean safety = checkSafety(n, m, available, allocation, need);

        if (safety) {
            System.out.println("The system is in a safe state.");
        } else {
            System.out.println("The system is in an unsafe state.");
        }
    }

    private static void testResourceRequestAlgorithm(int process, int[] request) throws Exception {
        File file = new File(".\\Week5\\sample_input_0.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] allocation = readArray(n, m, scanner);
        int[][] need;

        // if given need
        // need = readArray(n, m, scanner);

        // if given max only
        int[][] max = readArray(n, m, scanner);
        need = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        int[] available = new int[m];
        for (int i = 0; i < m; ++i) {
            available[i] = scanner.nextInt();
        }

        boolean grantable = canSafelyGrantResources(n, m, available, allocation, need, process, request);

        if (grantable) {
            System.out.printf("Process #%d's request can be granted.\n", process);
        } else {
            System.out.printf("Process #%d's request cannot be granted.\n", process);
        }
    }

    private static void testDeadlockDetectionAlgorithm() throws Exception {
        File file = new File(".\\Week5\\homework_input.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] allocation = readArray(n, m, scanner);
        int[][] request = readArray(n, m, scanner);
        int[] available = new int[m];
        for (int i = 0; i < m; ++i) {
            available[i] = scanner.nextInt();
        }

        boolean isDeadlocked = detectDeadlocks(n, m, available, allocation, request);

        if (isDeadlocked) {
            System.out.println("The system is in a deadlocked state.");
        } else {
            System.out.println("The system is deadlock-free.");
        }
    }

    public static void main(String[] args) throws Exception {
        testSafetyAlgorithm();
        testResourceRequestAlgorithm(1, new int[] { 1, 0, 2 });
        testDeadlockDetectionAlgorithm();
    }
}