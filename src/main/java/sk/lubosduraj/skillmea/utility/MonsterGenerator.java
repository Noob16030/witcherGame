package sk.lubosduraj.skillmea.utility;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;
import sk.lubosduraj.skillmea.domain.Monster;

import java.util.HashMap;
import java.util.Map;

public class MonsterGenerator {
    public static Map<Integer, Monster> createEnemies(){
        Map<Integer, Monster> enemies = new HashMap<>();
        enemies.put(
                1, new Monster("Drowner", new HashMap<>(Map.of(
                        Ability.ATTACK, 8,
                        Ability.PARRY, 7,
                        Ability.DEXTERITY, 6,
                        Ability.HEALTH, 40
                )), new HashMap<>(Map.of(
                        Immunity.SILVER, false,
                        Immunity.STEEL, true
                )))
        );
        /*enemies.put(
                2, new Monster("Orc", new HashMap<>(Map.of(
                        Ability.ATTACK, 3,
                        Ability.PARRY, 1,
                        Ability.DEXTERITY, 4,
                        Ability.SKILL, 3,
                        Ability.LUCK, 1,
                        Ability.HEALTH, 35
                )))
        );
        enemies.put(
                3, new Monster("Golem", new HashMap<>(Map.of(
                        Ability.ATTACK, 4,
                        Ability.PARRY, 4,
                        Ability.DEXTERITY, 1,
                        Ability.SKILL, 2,
                        Ability.LUCK, 5,
                        Ability.HEALTH, 50
                )))
        );
        enemies.put(
                4, new Monster("Troll", new HashMap<>(Map.of(
                        Ability.ATTACK, 5,
                        Ability.PARRY, 5,
                        Ability.DEXTERITY, 1,
                        Ability.SKILL, 1,
                        Ability.LUCK, 1,
                        Ability.HEALTH, 60
                )))
        );
        enemies.put(
                5, new Monster("Dragon", new HashMap<>(Map.of(
                        Ability.ATTACK, 5,
                        Ability.PARRY, 5,
                        Ability.DEXTERITY, 5,
                        Ability.SKILL, 5,
                        Ability.LUCK, 5,
                        Ability.HEALTH, 60
                )))
        );*/
        return enemies;
    }
}

