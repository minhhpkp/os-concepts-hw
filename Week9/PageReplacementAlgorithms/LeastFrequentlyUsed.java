import java.io.FileNotFoundException;
import java.util.Arrays;

public class LeastFrequentlyUsed extends PageReplacement {

    @Override
    public int run(int[] referenceString, int numberOfFrames) {
        int[] memory = new int[numberOfFrames];
        int[] frequency = new int[numberOfFrames];
        Arrays.fill(memory, -1);
        int pageFaults = 0;
        for (int i = 0; i < referenceString.length; ++i) {
            int page = referenceString[i];
            int frame = pageFrame(page, memory);
            // if page is already in memory at the determined frame
            if (frame != -1) {
                // update the page's usage frequency
                ++frequency[frame];
            } else {
                ++pageFaults;
                // check if there is a free frame available
                int freeFrame = firstFreeFrame(memory);
                if (freeFrame != -1) {
                    memory[freeFrame] = page;
                    frequency[freeFrame] = 1;
                } else {
                    // the frame to which the page will be loaded
                    int toReplace = 0;
                    // the current least usage frequency
                    int currentMin = i + 2;
                    for (int j = 0; j < numberOfFrames; ++j) {
                        if (frequency[j] < currentMin) {
                            toReplace = j;
                            currentMin = frequency[j];
                        }
                    }
                    memory[toReplace] = page;
                    frequency[toReplace] = 1;
                }
                printMemory(memory);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        LeastFrequentlyUsed leastFrequentlyUsed = new LeastFrequentlyUsed();
        leastFrequentlyUsed.demo();
    }
}
