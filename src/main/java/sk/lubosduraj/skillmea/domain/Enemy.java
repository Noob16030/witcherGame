package sk.lubosduraj.skillmea.domain;

import sk.lubosduraj.skillmea.ability.Ability;

import java.util.Map;

public class Enemy extends GameCharacter{

    public Enemy(String name, Map<Ability, Integer> abilities) {
        super(name, abilities);
    }
}
