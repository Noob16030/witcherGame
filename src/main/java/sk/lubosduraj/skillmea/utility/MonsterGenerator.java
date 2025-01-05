package sk.lubosduraj.skillmea.utility;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;
import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Humanoid;
import sk.lubosduraj.skillmea.domain.Monster;

import java.util.HashMap;
import java.util.Map;

public class MonsterGenerator {
    public static Map<Integer, GameCharacter> createEnemies(){
        Map<Integer, GameCharacter> enemies = new HashMap<>();
        enemies.put(
                1, new Monster("Drowner", new HashMap<>(Map.of(
                        Ability.ATTACK, 3,
                        Ability.PARRY, 2,
                        Ability.DEXTERITY, 3,
                        Ability.ACTUAL_HEALTH, 40
                )), new HashMap<>(Map.of(
                        Immunity.SILVER, false,
                        Immunity.STEEL, true
                )))
        );
        enemies.put(
                2, new Monster("Pack of Wolves", new HashMap<>(Map.of(
                        Ability.ATTACK, 8,
                        Ability.PARRY, 1,
                        Ability.DEXTERITY, 3,
                        Ability.ACTUAL_HEALTH, 70
                )), new HashMap<>(Map.of(
                        Immunity.SILVER, true,
                        Immunity.STEEL, false
                )))
        );
        enemies.put(
                3, new Humanoid("Bandit", new HashMap<>(Map.of(
                        Ability.ATTACK, 10,
                        Ability.PARRY, 3,
                        Ability.DEXTERITY, 3,
                        Ability.ACTUAL_HEALTH, 50
                )), new HashMap<>(Map.of(
                        Immunity.SILVER, true,
                        Immunity.STEEL, false
                )))
        );
        enemies.put(
                4, new Monster("Kiki Mora", new HashMap<>(Map.of(
                        Ability.ATTACK, 11,
                        Ability.PARRY, 7,
                        Ability.DEXTERITY, 4,
                        Ability.ACTUAL_HEALTH, 80
                )), new HashMap<>(Map.of(
                        Immunity.SILVER, false,
                        Immunity.STEEL, true
                )))
        );
        /*enemies.put(
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

