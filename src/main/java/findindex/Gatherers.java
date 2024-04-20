package findindex;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class Gatherers {

    public static <T> Gatherer<T, ?, Integer> findIndex(Predicate<? super T> predicate) {
        Gatherer.Integrator<AtomicInteger, T, Integer> integrator = (idx, element, downstream) -> {
            if (predicate.test(element)) {
                downstream.push(idx.getAndIncrement());
                return false;
            }
            idx.incrementAndGet();
            return true;
        };
        return Gatherer.ofSequential(AtomicInteger::new, integrator);
    }
}
