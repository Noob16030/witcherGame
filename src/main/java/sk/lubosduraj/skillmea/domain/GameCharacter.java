package sk.lubosduraj.skillmea.domain;

import sk.lubosduraj.skillmea.ability.Ability;

import java.util.Map;

public abstract class GameCharacter {
    protected String name;
    protected Map<Ability, Integer> abilities;

    public GameCharacter(String name, Map<Ability, Integer> abilities) {
        this.name = name;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public void receiveDamage(int damage){
        abilities.put(Ability.ACTUAL_HEALTH, Math.max(0, abilities.get(Ability.ACTUAL_HEALTH) - damage));
    }

    public void slowCharacter(int value){
        abilities.put(Ability.ATTACK, Math.max(0, abilities.get(Ability.ATTACK) - value));
        abilities.put(Ability.PARRY, Math.max(0, abilities.get(Ability.PARRY) - value));
    }

    public void returnCharacterStats(int valueAttack, int valueParry){
        abilities.put(Ability.ATTACK, valueAttack);
        abilities.put(Ability.PARRY, valueParry);
    }

    public void raiseParry(int value){
        abilities.put(Ability.PARRY, Math.max(0, abilities.get(Ability.PARRY) + value));
    }

    public void returnParry(int value){
        abilities.put(Ability.PARRY, value);
    }

}
