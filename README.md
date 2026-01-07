# Artificial Intelligence for Games – Homework Projects

This repository contains two different homework assignments developed for the Artificial Intelligence for Games course.

## Project Structure

### Homework 1 Package
`chapter2.taggame.Seden`

- **TSE_HW.java** — Tag Steering Engine  
- **Tagger.java** — Pursuit behavior  
- **Runner.java** — Evasion behavior  

### Homework 2 Package
`chapter3.pathfinding.alg.astar.hw2.Seden`

- **VisibilityGraphHW.java** — Visibility graph generator  
- **AStarHW.java** — A* pathfinding algorithm  
- **EuclideanDistanceHeuristic.java** — Heuristic function  

## Homework 1: Tag Game Steering Behaviors

### TSE_HW (Tag Steering Engine)

Dynamically assigns steering behaviors based on the current player state:

- **Tagger** behavior for the player designated as *“it”*  
- **Runner** behavior for non-tagged players  
- Fallback to **Wander** behavior when no tagger exists  

### Tagger Behavior

Implements pursuit steering to chase the closest non-tagged player:

- Scans all players and calculates distances using vector mathematics  
- Applies normalized directional acceleration (maximum 5.0) toward the target  
- Updates direction vectors in real time to track moving targets  

### Runner Behavior

Implements evasion behavior combined with environmental awareness:

- **Threat Detection**  
  Activates evasion when the tagger is within a 200-unit radius  

- **Corner Avoidance**  
  - Detects proximity to the four corners of the `Rectangle2D` arena (50-unit threshold)  
  - Calculates weighted repulsion vectors inversely proportional to distance  
  - Accumulates forces to handle multiple nearby corners  

- **Behavioral Blending**  
  Combines evasion and avoidance vectors, transitioning to wander behavior when safe  
  - Maximum acceleration: 5.0 for evasion, 2.0 for wandering  

## Homework 2: Pathfinding with Visibility Graphs and A*

### VisibilityGraphHW

Generates navigable paths around polygonal obstacles:

- Aggregates all points (custom points and polygon vertices)  
- Tests line-of-sight visibility using `GeomUtils.isVisible()`  
- Creates bidirectional connections between visible points  
- Automatically connects adjacent polygon vertices to preserve obstacle boundaries  
- Includes a duplicate-check mechanism when adding `LineConnection` objects  

### AStarHW (A* Algorithm)

Implements a standard A* pathfinding algorithm with optimal results:

- Uses `PathfindingList` as a priority queue for the open list  
- Uses `HashMap<T, AStarNode<T>>` for the explored set  
- Calculates f-cost (g-cost + h-cost) for informed search prioritization  
- Updates node costs when better paths are found  
- Reconstructs the final path by backtracking through connection references  
- Handles edge cases such as `start == end` and no-solution scenarios  

### EuclideanDistanceHeuristic

Implements an admissible heuristic using straight-line distance:

```
√[(x₂ − x₁)² + (y₂ − y₁)²]
```

- Never overestimates the actual path cost  
- Guarantees optimality of the A* algorithm  

## Key Technical Implementations

- **Vector Mathematics**  
  2D operations including normalization, scalar multiplication, and vector addition  

- **Multi-Vector Blending**  
  Weighted combination of evasion and corner-avoidance forces  

- **Graph Construction**  
  Visibility testing and bidirectional edge creation for navigable graphs  

- **Informed Search**  
  Heuristic-guided pathfinding with cost optimization  

- **Framework Integration**  
  Proper use of provided utilities such as `GeomUtils`, `PathfindingList`, and `LineConnection`  


