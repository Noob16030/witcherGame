package sk.lubosduraj.skillmea.domain;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;
import sk.lubosduraj.skillmea.constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class Witcher extends GameCharacter {
    private int heroAvailablePoints;
    private int currentLevel;
    private int manaPoints;

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeroAvailablePoints() {
        return heroAvailablePoints;
    }

    public void setAbility(Ability ability, int value){
        abilities.put(ability, value);
    }


    public Witcher(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.heroAvailablePoints = Constant.INITIAL_ABILITY_POINTS;
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.manaPoints = Constant.INITIAL_MANA_POINTS;
    }

    public Witcher(String name, Map<Ability, Integer> abilities, int heroAvailablePoints, int manaPoints) {
        super(name, abilities);
        this.heroAvailablePoints = heroAvailablePoints;
        this.manaPoints = manaPoints;
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
