import clock.Clock;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RequestPerMinuteStatistic implements EventsStatistic{
    private static final int MINUTES_IN_HOUR = 60;

    private final Clock clock;
    private final Queue<Pair<String, Instant>> eventQueue;
    private final Map<String, Integer> eventCounters;

    public RequestPerMinuteStatistic(Clock clock) {
        this.clock = clock;
        eventQueue = new ArrayDeque<>();
        eventCounters = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        Instant now = clock.now();
        Instant threshold = getThreshold(now);
        removeOutdatedEvents(threshold);
        eventQueue.add(Pair.of(name, now));
        eventCounters.merge(name, 1, Integer::sum);
    }

    @Override
    public double getEventStatisticByName(String name) {
        removeOutdatedEvents(getThreshold(clock.now()));
        int eventCount = eventCounters.getOrDefault(name, 0);
        return (double) eventCount / MINUTES_IN_HOUR;
    }

    @Override
    public List<Pair<String, Double>> getAllEventStatistic() {
        removeOutdatedEvents(getThreshold(clock.now()));
        return eventCounters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> Pair.of(e.getKey(), (double)e.getValue() / MINUTES_IN_HOUR))
                .collect(Collectors.toList());
    }

    @Override
    public void printStatistic() {
        List<Pair<String, Double>> allEventStatistic = getAllEventStatistic();
        allEventStatistic.forEach(event ->
                System.out.println(event.getLeft() + ":" + event.getRight() + "rmp"));
    }

    private void removeOutdatedEvents(Instant threshold) {
        Pair<String, Instant> event;
        while (!eventQueue.isEmpty()) {
            event = eventQueue.peek();
            if (event.getRight().isBefore(threshold)) {
                String eventName = event.getLeft();
                eventQueue.remove();
                eventCounters.computeIfPresent(eventName, (e, v) -> v - 1);
                eventCounters.remove(eventName, 0);
            } else {
                break;
            }
        }
    }

    private Instant getThreshold(Instant now) {
        return now.minus(1, ChronoUnit.HOURS);
    }
}
