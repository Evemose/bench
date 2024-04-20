package findindex;

import java.util.function.Predicate;
import java.util.stream.Collector;

public class Collectors {

    public static <T> Collector<T, ?, Integer> findIndex(Predicate<? super T> predicate) {
        class Result {
            int index;
            boolean found;

            public Result(int index, boolean found) {
                this.index = index;
                this.found = found;
            }
        }
        return Collector.of(
                () -> new Result(0, false),
                (acc, element) -> {
                    if (!acc.found) {
                        if (predicate.test(element)) {
                            acc.found = true;
                        } else {
                            acc.index++;
                        }
                    }
                },
                (acc1, acc2) -> {
                    if (acc1.found) {
                        if (acc2.found) {
                            return new Result(Math.min(acc1.index, acc2.index), true);
                        } else {
                            return new Result(acc1.index, true);
                        }
                    } else {
                        return acc2;
                    }
                },
                acc -> acc.found ? acc.index : -1
        );
    }
}
