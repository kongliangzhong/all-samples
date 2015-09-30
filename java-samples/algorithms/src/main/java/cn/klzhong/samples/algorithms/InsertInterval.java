package cn.klzhong.samples.algorithms;

import java.util.List;
import java.util.ArrayList;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Example 1:
 * Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
 * Example 2:
 * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */

public class InsertInterval {

    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public void test() {

    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null) return intervals;
        List<Interval> resultList = new java.util.ArrayList<Interval>();
        Interval merged = newInterval;
        boolean isMergedAdd = false;
        for (Interval i : intervals) {
            if (isMergedAdd) {
                resultList.add(i);
            } else {
                if (isNotCross(i, merged)) {
                    if (notCrossCompare(i, merged) > 0) {
                        resultList.add(merged);
                        isMergedAdd = true;
                    }
                    resultList.add(i);
                } else {
                    merged = merge(merged, i);
                }
            }
        }

        if (!isMergedAdd) {
            resultList.add(merged);
        }

        return resultList;
    }

    private int notCrossCompare(Interval a, Interval b) {
        return a.end - b.start;
    }

    private Interval merge(Interval a, Interval b) {
        int start = a.start < b.start ? a.start : b.start;
        int end = a.end > b.end ? a.end : b.end;
        return new Interval(start, end);
    }

    private boolean isNotCross(Interval a, Interval b) {
        return a.start > b.end || a.end < b.start;
    }
}
