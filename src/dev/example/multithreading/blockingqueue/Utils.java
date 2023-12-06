package dev.example.multithreading.blockingqueue;

import static java.util.UUID.randomUUID;

import java.util.List;
import java.util.stream.IntStream;

public class Utils {
    public static List<DataTransferObject<String>> generateDataChain(int dataSize) {
        return IntStream.range(1, dataSize + 1).mapToObj(Utils::generateDataItem).toList();
    }

    private static DataTransferObject<String> generateDataItem(int idx) {
        return new DataTransferObject<String>(idx, randomUUID().toString());
    }
}
