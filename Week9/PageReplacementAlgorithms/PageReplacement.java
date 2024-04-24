import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class PageReplacement {
    public abstract int run(int[] referenceString, int numberOfFrames);

    public void printMemory(int[] memory) {
        for (int page : memory) {
            System.out.print(page + " ");
        }
        System.out.println();
    }

    public void demo() throws FileNotFoundException {
        demo(", ");
    }

    public void demo(String sep) throws FileNotFoundException {
        File file = new File(".\\Week9\\PageReplacementAlgorithms\\input.txt");
        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();
        String[] tokens = line.split(sep);
        int[] referenceString = new int[tokens.length];
        for (int i = 0; i < tokens.length; ++i) {
            referenceString[i] = Integer.parseInt(tokens[i]);
        }
        int numberOfFrames = scanner.nextInt();
        scanner.close();

        int pageFaults = run(referenceString, numberOfFrames);
        System.out.println("Number of page faults: " + pageFaults);
    }

    public boolean loaded(int page, int[] memory) {
        // check if page has been loaded into memory
        for (int frame : memory) {
            if (frame == page)
                return true;
        }
        return false;
    }

    // return the index of the frame containing the page
    // and -1 if page is not loaded in memory
    public int pageFrame(int page, int[] memory) {
        for (int i = 0; i < memory.length; ++i) {
            if (memory[i] == page) {
                return i;
            }
        }
        return -1;
    }

    // return the index of the first free frame, -1 if none available
    public int firstFreeFrame(int[] memory) {
        for (int i = 0; i < memory.length; ++i) {
            if (memory[i] == -1) {
                return i;
            }
        }
        return -1;
    }
}
