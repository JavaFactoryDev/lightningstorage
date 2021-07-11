package de.leonhard.storage.util;

import lombok.experimental.UtilityClass;
import lombok.val;
import lombok.var;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Utility Class to take benchmarks
 */
@UtilityClass
@SuppressWarnings("unused")
public class LagCatcher {

    private final HashMap<String, Long> startTimes = new HashMap<>();
    private final HashMap<String, Long> stopTimes = new HashMap<>();

    public void start(final String name) {
        if (LagCatcher.startTimes.containsKey(name))
        {
            throw new IllegalStateException(("Test is already running for '" + name + "'"));
        }
        val nanoTime = System.nanoTime();
        LagCatcher.startTimes.put(name, nanoTime);
    }

    public void stop(final String name) {
        if (LagCatcher.stopTimes.containsKey(name)) throw new IllegalStateException(("No test running for '" + name + "'"));
        LagCatcher.stopTimes.put(name, System.nanoTime());
    }

    private void show(final String name) {
        if (!LagCatcher.startTimes.containsKey(name) || !LagCatcher.stopTimes.containsKey(name))
        {
            throw new IllegalStateException(("No results found for '" + name + "'"));
        }
        val value = LagCatcher.startTimes.get(name);
        if (value == null) {
            return;
        }
        final long start = value;
        val value2 = LagCatcher.stopTimes.get(name);
        if (value2 != null)
        {
            final long end = value2;
            final long took = end - start;
            System.out.println((Object)
                    ("Test '"
                            + name
                            + "' took "
                            + TimeUnit.NANOSECONDS.toMicros(took)
                            + " micro-seconds. That's "
                            + TimeUnit.NANOSECONDS.toMillis(took)
                            + " ms."));
            LagCatcher.stopTimes.remove(name);
            LagCatcher.startTimes.remove(name);
        }
    }

    public void stopAndShow(final String name) {
        stop(name);
        show(name);
    }

    public void runMultipleTimes(final int cycles, final Runnable runnable)
    {
        var nanosTook = 0L;
        for (int i = 0; i < cycles; ++i)
        {
            val nanoTime = System.nanoTime();
            runnable.run();
            nanosTook += System.nanoTime() - nanoTime;
        }
        System.out.println((Object)
                ("Average time: "
                        + TimeUnit.NANOSECONDS.toMicros(nanosTook / cycles)
                        + " micros - "
                        + TimeUnit.NANOSECONDS.toMillis(nanosTook / cycles)
                        + " ms."));
        System.out.println((Object)
                ("Test took: "
                        + TimeUnit.NANOSECONDS.toMicros(nanosTook)
                        + " micros "
                        + "- "
                        + TimeUnit.NANOSECONDS.toMillis(nanosTook)
                        + " ms"));
    }
}
