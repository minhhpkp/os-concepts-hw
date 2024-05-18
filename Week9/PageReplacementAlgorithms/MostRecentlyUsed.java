import java.io.FileNotFoundException;
import java.util.Arrays;

public class MostRecentlyUsed extends PageReplacement {
    @Override
    public int run(int[] referenceString, int numberOfFrames) {
        int[] memory = new int[numberOfFrames];
        int[] lastUse = new int[numberOfFrames];
        Arrays.fill(memory, -1);
        int pageFaults = 0;
        for (int i = 0; i < referenceString.length; ++i) {
            int page = referenceString[i];
            int index = pageFrame(page, memory);
            if (index != -1) {
                lastUse[index] = i;
            } else {
                ++pageFaults;
                // check if there is a free frame available
                int freeFrame = firstFreeFrame(memory);
                if (freeFrame != -1) {
                    memory[freeFrame] = page;
                    lastUse[freeFrame] = i;
                } else {
                    // index of the frame to which the page will be loaded
                    int toReplace = 0;
                    // the most recent use of a page
                    int currentMax = -1;
                    for (int j = 0; j < numberOfFrames; ++j) {
                        if (lastUse[j] > currentMax) {
                            toReplace = j;
                            currentMax = lastUse[j];
                        }
                    }
                    memory[toReplace] = page;
                    lastUse[toReplace] = i;
                }
                printMemory(memory);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        MostRecentlyUsed mostRecentlyUsed = new MostRecentlyUsed();
        mostRecentlyUsed.demo();
    }
}
