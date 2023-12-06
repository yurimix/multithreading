package dev.example.multithreading.blockingqueue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;

public class Utils {
    public static List<DataTransferObject<String>> generateDataChain(int dataSize) {
        return IntStream.range(1, dataSize + 1).mapToObj(Utils::generateDataItem).toList();
    }

    private static DataTransferObject<String> generateDataItem(int idx) {
        return new DataTransferObject<String>(idx, randomUUID().toString());
    }
}
