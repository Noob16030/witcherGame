package sk.lubosduraj.skillmea.domain;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;

import java.util.Map;

public class Humanoid extends GameCharacter{
    private Map<Immunity, Boolean> immunities;

    public Map<Immunity, Boolean> getImmunities() {
        return immunities;
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public Humanoid(String name, Map<Ability, Integer> abilities, Map<Immunity, Boolean> immunities) {
        super(name, abilities);
        this.immunities = immunities;
    }

    public String getName() {
        return name;
    }
}
