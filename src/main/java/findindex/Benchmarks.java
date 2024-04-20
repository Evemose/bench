package findindex;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Benchmarks {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexCollector_LastElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getLast();
        blackhole.consume(list.stream().collect(Collectors.findIndex(predicate)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexCollector_FirstElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getFirst();
        blackhole.consume(list.stream().collect(Collectors.findIndex(predicate)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexCollector_MiddleElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.get(list.size() / 2);
        blackhole.consume(list.stream().collect(Collectors.findIndex(predicate)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexCollector_NotFound(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == Integer.MAX_VALUE;
        blackhole.consume(list.stream().collect(Collectors.findIndex(predicate)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexGatherer_LastElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getLast();
        blackhole.consume(list.stream().gather(Gatherers.findIndex(predicate)).findAny());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexGatherer_FirstElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getFirst();
        blackhole.consume(list.stream().gather(Gatherers.findIndex(predicate)).findAny());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexGatherer_MiddleElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.get(list.size() / 2);
        blackhole.consume(list.stream().gather(Gatherers.findIndex(predicate)).findAny());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexGatherer_NotFound(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == Integer.MAX_VALUE;
        blackhole.consume(list.stream().gather(Gatherers.findIndex(predicate)).findAny());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexNaive_LastElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getLast();
        blackhole.consume(list.findIndex(predicate));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexNaive_FirstElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.getFirst();
        blackhole.consume(list.findIndex(predicate));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexNaive_MiddleElement(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == list.get(list.size() / 2);
        blackhole.consume(list.findIndex(predicate));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testFindIndexNaive_NotFound(BenchmarkState state, Blackhole blackhole) {
        var list = state.list;
        var predicate = (Predicate<Integer>) i -> i == Integer.MAX_VALUE;
        blackhole.consume(list.findIndex(predicate));
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"10", "100", "1000", "10000", "100000"})
        public int listSize;
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        @Setup
        public void setup() {
            list = random.ints(listSize, Integer.MIN_VALUE, Integer.MAX_VALUE).boxed().toList();
        }
    }
}
