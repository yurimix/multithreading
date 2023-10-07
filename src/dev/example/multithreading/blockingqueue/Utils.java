package dev.example.multithreading.blockingqueue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    public static <String> DataTransferObject[] generateDataChain(int dataSize) {
        List<DataTransferObject> res =
                IntStream.
                        range(0, dataSize).
                        mapToObj(i -> new DataTransferObject<java.lang.String>(i, generateDataChain())).
                        collect(Collectors.toList());
        return res.toArray(new DataTransferObject[dataSize]);
    }

    private static String generateDataChain() {
        return "Your data could be here";
    }
}
