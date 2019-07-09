package test.algo;

import test.common.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Find the maximum number of concurrent sessions in the following data with the first value representing start time and last value end time. The input is not necessarily sorted.
 * Input: (2,5), (3,6), (8,10),(10,12),(9,20)
 * Output: 3 (from 8 to 20)
 * Input: (2,5), (3,6), (8,10),(9,12),(12,20)
 * Output: 2 (from 8 to 12 or 2 to 6)
 */
public class Shopify {
    public static void main(String[] args) {
        List<Range> ranges = new ArrayList<Range>();
        ranges.add(new Range(2, 5));
        ranges.add(new Range(3, 6));
        ranges.add(new Range(8, 10));
        ranges.add(new Range(10, 12));
        ranges.add(new Range(9, 20));
        /*ranges.add(new Range(2, 5));
        ranges.add(new Range(3, 6));
        ranges.add(new Range(8, 10));
        ranges.add(new Range(9, 12));
        ranges.add(new Range(12, 20));*/
        System.out.println(new Shopify().getMaxConcurrentSessions(ranges));
    }

    public int getMaxConcurrentSessions(List<Range> ranges) {
        List<Pair<Range, Integer>> lst = new ArrayList<>();
        for (Range r : ranges) {
            Pair<Range, Integer> existing = null;
            for (Pair<Range, Integer> e : lst) {
                if (e.first().isBetween(r)) {
                    existing = e;
                    break;
                }
            }
            if (existing == null) {
                lst.add(new Pair<>(r, 1));
            } else {
                existing.first(merge(r, existing.first()));
                existing.second(existing.second() + 1);
            }
            System.out.println(r + " -> " + lst);
            //balance lst
            lst = balance(lst);
        }
        int max = 0;
        for (Pair<Range, Integer> e : lst) {
            if (e.second() > max) {
                max = e.second();
            }
        }
        System.out.println(lst);
        return max;
    }

    private List<Pair<Range, Integer>> balance(List<Pair<Range, Integer>> lst) {
        if (lst != null && lst.size() > 0) {
            Pair<Range, Integer> head = lst.get(0);
            Pair<Range, Integer> existing = null;
            int idx = 0;
            for (int i = 1; i < lst.size(); i++) {
                Pair<Range, Integer> e = lst.get(i);
                if (e.first().isBetween(head.first())) {
                    existing = e;
                    idx = i;
                    break;
                }
            }
            if (existing != null) {
                head.first(merge(head.first(), existing.first()));
                head.second(existing.second() + head.second());
                lst.remove(idx);
                lst = balance(lst);
            }
        }
        return lst;
    }

    private Range merge(Range r1, Range r2) {
        return new Range(Math.min(r1.getStart(), r2.getStart()), Math.max(r1.getEnd(), r2.getEnd()));
    }
}

class Range {
    Integer start;
    Integer end;

    public Range(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    public void setEnd(Integer end) {
        this.end = end;
    }
    public Integer getStart() {
        return start;
    }
    public Integer getEnd() {
        return end;
    }

    public boolean isBetween(Range that) {
        return (this.start <= that.start && that.start < this.end) || (this.start < that.end && that.end <= this.end);
    }

    @Override
    public String toString() {
        return "<" + start + " -> " + end + ">";
    }
}