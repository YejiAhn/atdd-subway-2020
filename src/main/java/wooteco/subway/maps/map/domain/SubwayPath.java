package wooteco.subway.maps.map.domain;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubwayPath {

    public static final int MINIMUM_FARE = 1250;
    public static final int BASE_DISTANCE = 10;
    private List<LineStationEdge> lineStationEdges;
    private int extraFare = 0;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists
                .newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare() {
        int overFareDistance = Math.max(calculateDistance() - BASE_DISTANCE, 0);
        return MINIMUM_FARE + calculateOverFareByDistance(overFareDistance) + extraFare;
    }

    private int calculateOverFareByDistance(int distance) {
        if (distance == 0) {
            return 0;
        }
        if (distance <= 40) {
            return (int) ((Math.ceil((distance - 1) / 5) + 1) * 100);
        }
        return (int) ((Math.ceil((distance - 1) / 8) + 1) * 100);
    }

    public Set<Long> getAllLineIds() {
        return lineStationEdges.stream().map(LineStationEdge::getLineId).collect(Collectors.toSet());
    }

    public void addExtraFareByLine(Set<Integer> extraFaresInPath) {
        System.out.println(extraFaresInPath.toString());
        int maximumExtraFare = Collections.max(extraFaresInPath);
        System.out.println(maximumExtraFare + "#########");
        extraFare = maximumExtraFare;
    }

}
