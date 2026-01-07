package chapter3.pathfinding.alg.astar.hw2.Seden;

import chapter3.pathfinding.Connection;
import chapter3.pathfinding.visibility.GeomUtils;
import chapter3.pathfinding.visibility.Graph2D;
import chapter3.pathfinding.visibility.LineConnection;
import math.geom2d.Point2D;
import math.geom2d.polygon.Polygon2D;

import java.util.ArrayList;
import java.util.List;

public class VisibilityGraphHW implements Graph2D {

    List<Point2D> points;
    List<Polygon2D> polygons;
    List<Connection<Point2D>> connections;
    // TODO: You can define necessary additional members here



    public VisibilityGraphHW(List<Point2D> points, List<Polygon2D> polygons) {
        this.points = points;
        this.polygons = polygons;
        this.connections = new ArrayList<>();
        //addPolygonVertices(polygons);
        buildGraph();
    }

    /**
     * TODO: you can do whatever you want to do initially here
     */

    private void buildGraph() {
        connectPolygonVertices(polygons);
        List<Point2D> allPoints = getNodes();

        for (int i = 0; i < allPoints.size(); i++) {
            for (int j = i + 1; j < allPoints.size(); j++) {
                Point2D point1 = allPoints.get(i);
                Point2D point2 = allPoints.get(j);
                if(GeomUtils.isVisible(point1, point2, polygons)){
                    connectPoints(point1, point2);
                    connectPoints(point2, point1);

                }
            }
        }

    }


    private void connectPolygonVertices(List<Polygon2D> polygons) {
        for (Polygon2D polygon : polygons){
            for (int i = 0; i < polygon.vertexNumber(); i++){
                if (i < polygon.vertexNumber() - 1){
                    connectPoints(polygon.vertex(i + 1), polygon.vertex(i));
                    connectPoints(polygon.vertex(i), polygon.vertex(i + 1));
                }
                else {
                    connectPoints(polygon.vertex(0), polygon.vertex(i));
                    connectPoints(polygon.vertex(i), polygon.vertex(0));
                }
            }
        }
    }

    private void connectPoints(Point2D point1, Point2D point2) {
        LineConnection polygonLineConnection = new LineConnection(point1, point2);
        if(!connections.contains(polygonLineConnection)){
            connections.add(polygonLineConnection);
        }
    }


    @Override
    public List<? extends Connection<Point2D>> getConnections() {
        return connections;
    }

    /**
     * TODO: Implement properly
     * @return
     */
    @Override
    public List<Point2D> getNodes() {
        List<Point2D> allPoints = new ArrayList<>(points);
        for(var polygon : polygons){
            allPoints.addAll(polygon.vertices());
        }
        return allPoints;
    }

    /**
     * TODO:Implement properly
     * @return
     */
    @Override
    public List<Polygon2D> getPolygons() {
        return polygons;
    }

    /**
     * TODO:Implement properly
     * @param from
     * @return
     */
    @Override
    public List<Connection<Point2D>> connectionsOf(Point2D from) {
        List<Connection<Point2D>> connectionOf = new ArrayList<>();

        for (Connection<Point2D> connection : connections) {
            if(connection.from().equals(from)){
                connectionOf.add(connection);
            }
        }

        return connectionOf;
    }
}
