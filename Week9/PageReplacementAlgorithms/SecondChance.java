import java.io.FileNotFoundException;
import java.util.Arrays;

public class SecondChance extends PageReplacement {

    @Override
    public int run(int[] referenceString, int numberOfFrames) {
        int[] memory = new int[numberOfFrames];
        int[] referenceBits = new int[numberOfFrames];
        // -1 means free frame
        Arrays.fill(memory, -1);
        int pointer = 0;
        int pageFaults = 0;

        for (int page : referenceString) {
            System.out.println("page = " + page);
            int index = pageFrame(page, memory);
            // if page is loaded into memory
            if (index != -1) {
                referenceBits[index] = 1;
                System.out.print("reference bits (after): ");
                printMemory(referenceBits);
            } else {
                ++pageFaults;

                // free frame available
                if (memory[pointer] == -1) {
                    memory[pointer] = page;
                    referenceBits[pointer] = 1;
                    pointer = (pointer + 1) % numberOfFrames;
                } else {
                    System.out.println("pointer (before): " + pointer);
                    System.out.print("reference bits (before): ");
                    printMemory(referenceBits);
                    while (referenceBits[pointer] == 1) {
                        referenceBits[pointer] = 0;
                        pointer = (pointer + 1) % numberOfFrames;
                    }
                    memory[pointer] = page;
                    referenceBits[pointer] = 1;
                    pointer = (pointer + 1) % numberOfFrames;
                    System.out.println("pointer (after): " + pointer);
                    System.out.print("reference bits (after): ");
                    printMemory(referenceBits);
                }

                printMemory(memory);

            }
        }

        return pageFaults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SecondChance secondChance = new SecondChance();
        secondChance.demo();
    }
}
