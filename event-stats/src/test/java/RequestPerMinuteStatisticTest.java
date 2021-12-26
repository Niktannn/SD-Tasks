import clock.SettableClock;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.HOURS;

public class RequestPerMinuteStatisticTest {
    private static final int MINUTES_IN_HOUR = (int) HOURS.getDuration().toMinutes();
    private static final double EPS = Double.MIN_VALUE;

    private final SettableClock clock = new SettableClock(Instant.now());

    @Test
    public void testNoOutdated() {
        List<Integer> eventsSeq = new ArrayList<>(List.of(3, 4, 1, 0, 2, 1, 1, 4, 2, 3));
        int lastOutdatedPos = -1;
        testGeneral(eventsSeq, lastOutdatedPos);
    }

    @Test
    public void testSomeOutdated() {
        List<Integer> eventsSeq = new ArrayList<>(List.of(3, 4, 1, 0, 2, 1, 1, 4, 2, 3));
        int lastOutdatedPos = 2;
        testGeneral(eventsSeq, lastOutdatedPos);
    }

    @Test
    public void testAllOutdated() {
        List<Integer> eventsSeq = new ArrayList<>(List.of(3, 4, 1, 0, 2, 1, 1, 4, 2, 3));
        int lastOutdatedPos = eventsSeq.size() - 1;
        testGeneral(eventsSeq, lastOutdatedPos);
    }


    @Test
    public void testRandom() {
        Random random = new Random();
        int maxEvent = random.nextInt(10);
        int eventsCount = random.nextInt(50) + 1;
        int lastOutdatedPos = random.nextInt(eventsCount);
        List<Integer> eventsSeq = random.ints(eventsCount, 0, maxEvent + 1)
                .boxed().collect(Collectors.toList());
        testGeneral(eventsSeq, lastOutdatedPos);
    }

    private void testGeneral(List<Integer> eventsSeq, int lastOutdatedPos) {
        EventsStatistic eventStatistic = new RequestPerMinuteStatistic(clock);
        Instant startTime = Instant.now();

        Set<Integer> events = new HashSet<>(eventsSeq);

        for (int i = 0; i < eventsSeq.size(); ++i) {
            clock.setNow(startTime.plus(i, ChronoUnit.SECONDS));
            eventStatistic.incEvent(String.valueOf(eventsSeq.get(i)));
        }

        clock.setNow(startTime.plus(1, HOURS).plus(lastOutdatedPos + 1, ChronoUnit.SECONDS));
        final List<Integer> leftEventSeq = eventsSeq.subList(lastOutdatedPos + 1, eventsSeq.size());

        for (Integer event : events) {
            Assert.assertEquals((double)(Collections.frequency(leftEventSeq, event)) / MINUTES_IN_HOUR,
                    eventStatistic.getEventStatisticByName(String.valueOf(event)), EPS);
        }

        events = new HashSet<>(leftEventSeq);

        Assert.assertEquals(events.stream().map(i -> Pair.of(String.valueOf(i),
                                (double)(Collections.frequency(leftEventSeq, i)) / MINUTES_IN_HOUR))
                        .collect(Collectors.toList()),
                eventStatistic.getAllEventStatistic());
    }
}
