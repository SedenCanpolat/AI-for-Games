package chapter3.pathfinding.alg.astar.hw2.Seden;

import chapter3.pathfinding.Graph;
import chapter3.pathfinding.alg.astar.AStarHeuristic;
import math.geom2d.Point2D;

public class EuclideanDistanceHeuristic <T extends Point2D> implements AStarHeuristic<T> {

    @Override
    public double estimate(Graph<T> graph, T node, T target) {
        //d =√[(x2 — x1)^2 + (y2 — y1)^2]
        double xValues = node.x() - target.x();
        double yValues = node.y()- target.y();
        return Math.sqrt(xValues * xValues + yValues * yValues);
    }
}
