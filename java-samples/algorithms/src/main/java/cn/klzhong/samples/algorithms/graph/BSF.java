package cn.klzhong.samples.algorithms.graph;

import java.util.HashMap;
import java.util.Map;

public class BSF {

    private class SimpleGraph {
        // define points: 0 ~ (pointCount - 1)
        public int[] points;
        public int[][] edges;

        public SimpleGraph(int[] points, int[][] edges) {
            this.points = points;
            this.edges = edges;
        }
    }

    public void test() {

    }

    private void traversal(SimpleGraph g) {
        Map<Integer, Integer[][]> edgesMap = new HashMap<>();
        for (int[] edge : g.edges) {
            // TODO
        }

        // for (int p : p.points) {

        // }
    }


}
