package chapter2.taggame.Seden;

import chapter2.*;
import chapter2.taggame.TagArena;
import chapter2.taggame.TagPlayer;
import math.geom2d.Vector2D;

public class Tagger  implements SteeringBehavior {
    double maxAcceleration = 5;
    TagArena tagArena;

    public Tagger(TagArena tagArena) {
        this.tagArena = tagArena;
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {
        double shortestDistance = Double.MAX_VALUE;
        TagPlayer closestPlayer = null;

        for(TagPlayer player: tagArena.getPlayers()){
            if(!player.isTag()){
                double distance = staticInfo.getPos().minus(player.getStaticInfo().getPos()).norm();

                if(distance < shortestDistance){
                    shortestDistance = distance;
                    closestPlayer = player;
                }
            }
        }

        if (closestPlayer != null) {
            Vector2D direction = closestPlayer.getStaticInfo().getPos().minus(staticInfo.getPos()).normalize();
            if (direction.norm()>maxAcceleration) {
                direction = direction.normalize().times(maxAcceleration);
            }
            return new Acceleration(direction,0, AccelerationType.Dynamic);
        }
        return Acceleration.NoAcceleration;
    }
}
