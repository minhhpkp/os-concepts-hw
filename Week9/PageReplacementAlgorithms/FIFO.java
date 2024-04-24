import java.io.FileNotFoundException;
import java.util.Arrays;

public class FIFO extends PageReplacement {
    @Override
    public int run(int[] referenceString, int numberOfFrames) {
        int[] memory = new int[numberOfFrames];
        Arrays.fill(memory, -1);
        int cur = 0;
        int pageFaults = 0;
        for (int page : referenceString) {
            if (!loaded(page, memory)) {
                ++pageFaults;
                memory[cur++] = page;
                if (cur >= memory.length)
                    cur = 0;
                printMemory(memory);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FIFO fifo = new FIFO();
        fifo.demo();
    }
}