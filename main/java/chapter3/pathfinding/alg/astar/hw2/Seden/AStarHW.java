package chapter3.pathfinding.alg.astar.hw2.Seden;

import chapter3.pathfinding.*;
import chapter3.pathfinding.alg.PathfindingAlgorithm;
import chapter3.pathfinding.alg.PathfindingList;
import chapter3.pathfinding.alg.PathfindingNode;
import chapter3.pathfinding.alg.astar.AStarHeuristic;
import chapter3.pathfinding.alg.astar.AStarNode;

import java.util.*;

public class AStarHW<T> implements PathfindingAlgorithm<T> {

    PathfindingList<T> openList;
    HashMap<T, AStarNode<T>> closedList;
    AStarHeuristic<T> heuristic;
    private T start;
    private T end;
    private T current;
    private Graph<T> graph;

    private Path<T> path;

    public AStarHW(AStarHeuristic<T> heuristic) {
        openList = new PathfindingList<>();
        closedList = new HashMap<>();
        this.heuristic= heuristic;
    }

    /**
     * TODO: Implement A* algorithm.
     * @param graph
     * @param start
     * @param end
     * @return
     */
    @Override
    public Path<T> findPath(Graph<T> graph, T start, T end) {
        if (start.equals(end)) {
            return new ListPath<>(new ArrayList<>(), 0.0);
        }

        init(graph, start, end);
        while (!openList.isEmpty() && path==null){
            AStarNode<T> currentNode = (AStarNode<T>) openList.removeMin();

            if(currentNode.getNode().equals(end)){
                path = buildPath(currentNode, start, closedList);
                return path;
            }

            List<Connection<T>> neighbors = graph.connectionsOf(currentNode.getNode());
            for(Connection<T>  connection : neighbors){
                T neighbor = connection.to();

                if(closedList.containsKey(neighbor)){
                    continue;
                }

                double heuristicCost = heuristic.estimate(graph, neighbor, end);
                double gCostSoFar = currentNode.getCostSoFar()+connection.cost();
                double fTotalCost = heuristicCost + gCostSoFar;

                AStarNode<T> newRecord = new AStarNode<>(neighbor, gCostSoFar, fTotalCost, connection);
                if(openList.contains(neighbor)){
                    if (openList.get(neighbor).getEstimatedCost() > newRecord.getEstimatedCost() )
                        openList.update(newRecord);
                }
                else{
                    openList.insert(newRecord);
                }
            }

            closedList.put(currentNode.getNode(), currentNode);

        }

        return path==null ? new ListPath<T>(Collections.emptyList(),0):path;
    }






    @Override
    public Path<T> getPath() {
        return path;
    }




    private void init(Graph<T> graph,T start, T end) {

        this.start = start;
        this.end = end;
        this.graph= graph;
        path= null;
        openList.init();
        closedList.clear();
        PathfindingNode<T> dNode = new AStarNode<>(start,0,heuristic.estimate(graph,start,end),null);

        openList.insert(dNode);
    }

    private Path<T> buildPath(AStarNode<T> currentDNode, T start, HashMap<T,AStarNode<T>> closedList) {

        List<Connection<T>> path = new ArrayList<>();
        double weight =0;

        while (!currentDNode.getNode().equals(start))
        {
            path.add(currentDNode.getConnection());
            weight += currentDNode.getConnection().cost();
            currentDNode = closedList.get(currentDNode.getConnection().from());


        }

        Collections.reverse(path);




        return new ListPath<>(path, weight);
    }




}
