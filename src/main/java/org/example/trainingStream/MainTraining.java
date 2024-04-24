package org.example.trainingStream;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTraining {
    int[ ] ints;
    Integer[ ] integers;
    public void setup() {
    ints = new Random(1).ints(1000000, 0, 1000)
            .toArray();

    integers = new Random(1).ints(1000000, 0, 1000)
            .boxed().toArray(Integer[]::new);
}
    public static void main(String[] args) {
        int[ ] ints;
        Integer[ ] integers;
        IntStream.rangeClosed(1, 1000)
                .filter(s -> s > 300)
                .forEach(System.out::println);


    }
}
