package sk.lubosduraj.skillmea.domain;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {
    private int heroAvailablePoints;


    public void setName(String name) {
        this.name = name;
    }

    public int getHeroAvailablePoints() {
        return heroAvailablePoints;
    }

    public void setAbility(Ability ability, int value){
        abilities.put(ability, value);
    }


    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.heroAvailablePoints = Constant.INITIAL_ABILITY_POINTS;
    }

    public Hero(String name, Map<Ability, Integer> abilities, int heroAvailablePoints) {
        super(name, abilities);
        this.heroAvailablePoints = heroAvailablePoints;
    }

    public void updateAvailablePoints(int delta){
        this.heroAvailablePoints += delta;
    }

    public void updateAbility(Ability ability, int delta){
        if (ability.equals(Ability.HEALTH)){
            this.abilities.put(ability, this.abilities.get(ability) + delta * Constant.HEALTH_OF_ONE_POINT);
        } else {
            this.abilities.put(ability, this.abilities.get(ability) + delta);
        }
    }

    private Map<Ability, Integer> getInitialAbilities(){
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.PARRY, 1,
                Ability.DEXTERITY, 1,
                Ability.SKILL, 1,
                Ability.LUCK, 1,
                Ability.HEALTH, 50
        ));
    }

}
