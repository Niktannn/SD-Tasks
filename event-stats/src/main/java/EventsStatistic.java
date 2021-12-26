import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface EventsStatistic {
    void incEvent(String name);
    double getEventStatisticByName(String name);
    List<Pair<String, Double>> getAllEventStatistic();
    void printStatistic();
}
