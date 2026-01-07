package chapter2.taggame.Seden;

import chapter2.SteeringBehavior;
import chapter2.steering.Wander;
import chapter2.taggame.TagArena;
import chapter2.taggame.TagPlayer;
import chapter2.taggame.TagSteeringEngine;

/**
 * TODO: Implement a tag steering engine generating the steering behaviors for a tag player
 *      ! Note that you should implement SteeringBehavior classes to be generated in this class
 */
public class TSE_HW implements TagSteeringEngine {
    @Override
    public SteeringBehavior getSteeringBehavior(TagPlayer player, TagArena arena) {
        if(player.isTag()){
            return new Tagger(arena);
        }
        else{
            for(TagPlayer tagger : arena.getPlayers()){
                if(tagger.isTag()){
                    return new Runner(tagger, arena);
                }
            }
            return new Wander();
        }
    }
}
