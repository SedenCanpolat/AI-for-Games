package chapter2.taggame.Seden;

import chapter2.*;
import chapter2.steering.Wander;
import chapter2.taggame.TagArena;
import chapter2.taggame.TagPlayer;
import math.geom2d.Vector2D;

import java.awt.geom.Rectangle2D;

public class Runner implements SteeringBehavior{

    public final double runRadius = 200;
    public TagPlayer tagger;
    TagArena tagArena;

    double maxAcceleration = 5;
    double wanderAcceleration = 2;
    double cornerThreshold = 50;

    Vector2D cornerAvoidance = new Vector2D(0, 0);
    Vector2D finalDirection;
    Wander wander= new Wander();

    public Runner(TagPlayer tagger, TagArena tagArena){
        this.tagger = tagger;
        this.tagArena = tagArena;
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {
        Vector2D[] corners = getVector2DS();

        for(Vector2D corner : corners){
            Vector2D cornerDirection = staticInfo.getPos().minus(corner);
            double cornerDistance = cornerDirection.norm();
            if(cornerDistance < cornerThreshold){
                cornerAvoidance = cornerAvoidance.plus(cornerDirection.normalize().times((cornerThreshold - cornerDistance) / cornerThreshold * maxAcceleration));
            }
        }

        Vector2D taggerPosition = tagger.getStaticInfo().getPos();
        Vector2D direction = staticInfo.getPos().minus(taggerPosition);
        double distance = direction.norm();

        if (distance > runRadius || tagger == null) {
            Acceleration wanderSteering = wander.getSteering(staticInfo, velocity);
            finalDirection = wanderSteering.getLinear().normalize().times(wanderAcceleration);
        }
        else {
            finalDirection = direction;
        }

        finalDirection = finalDirection.plus(cornerAvoidance);

        if (finalDirection.norm()>maxAcceleration)
            finalDirection = finalDirection.normalize().times(maxAcceleration);

        return new Acceleration(finalDirection,0, AccelerationType.Dynamic);

    }

    private Vector2D[] getVector2DS() {
        Rectangle2D arenaBoundaries = tagArena.getBoundaries();
        Vector2D topLeft = new Vector2D(arenaBoundaries.getMinX(), arenaBoundaries.getMaxY());
        Vector2D topRight = new Vector2D(arenaBoundaries.getMaxX(), arenaBoundaries.getMaxY());
        Vector2D bottomLeft = new Vector2D(arenaBoundaries.getMinX(), arenaBoundaries.getMinY());
        Vector2D bottomRight = new Vector2D(arenaBoundaries.getMaxX(), arenaBoundaries.getMinY());
        return new Vector2D[]{topLeft, topRight, bottomLeft, bottomRight};
    }

}
