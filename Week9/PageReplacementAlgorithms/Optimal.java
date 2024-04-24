import java.io.FileNotFoundException;
import java.util.Arrays;

public class Optimal extends PageReplacement {

    @Override
    public int run(int[] referenceString, int numberOfFrames) {
        int[] memory = new int[numberOfFrames];
        Arrays.fill(memory, -1);
        int pageFaults = 0;
        for (int i = 0; i < referenceString.length; ++i) {
            int page = referenceString[i];

            if (!loaded(page, memory)) {
                ++pageFaults;
                // check if there is a free frame available
                int freeFrame = firstFreeFrame(memory);
                if (freeFrame != -1) {
                    memory[freeFrame] = page;
                } else {
                    // index of the frame where we load the new page
                    int toReplace = 0;
                    // maximum number of references before the next use of a page
                    int currentMax = -1;
                    // find the page that will not be used for the longest period of time
                    for (int j = 0; j < numberOfFrames; ++j) {
                        int frame = memory[j];
                        // find the next occurence of page at current frame
                        // which is equivalent to the next usage of the page
                        // nextUsage = length of reference string means the page is not going to be used
                        int nextUsage = referenceString.length;
                        for (int k = i + 1; k < referenceString.length; ++k) {
                            if (referenceString[k] == frame) {
                                nextUsage = k;
                                break;
                            }
                        }
                        if (nextUsage > currentMax) {
                            currentMax = nextUsage;
                            toReplace = j;
                        }
                    }
                    memory[toReplace] = page;
                }

                printMemory(memory);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Optimal optimal = new Optimal();
        optimal.demo();
    }
}
