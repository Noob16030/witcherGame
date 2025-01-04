package sk.lubosduraj.skillmea.utility;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;
import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Monster;
import sk.lubosduraj.skillmea.domain.Witcher;
import sk.lubosduraj.skillmea.service.Sign;

import javax.sound.midi.Soundbank;

public class PrintUtils {
    public static void printAbilitiesWithoutNumbers(GameCharacter character) {
        System.out.println("ATTACK: " + character.getAbilities().get(Ability.ATTACK));
        System.out.println("Parry: " + character.getAbilities().get(Ability.PARRY));
        System.out.println("Dexterity: " + character.getAbilities().get(Ability.DEXTERITY));
        System.out.println("Skill: " + character.getAbilities().get(Ability.SKILL));
        System.out.println("Luck: " + character.getAbilities().get(Ability.LUCK));
        System.out.println("Health: " + character.getAbilities().get(Ability.HEALTH));
    }

    public static void printDivider(){
        System.out.println("---------------------------------------------");
    }

    public static void printAbilitiesNew(Witcher hero){
        System.out.println("1. ATTACK: " + hero.getAbilities().get(Ability.ATTACK));
        System.out.println("2. Parry: " + hero.getAbilities().get(Ability.PARRY));
        System.out.println("3. Dexterity: " + hero.getAbilities().get(Ability.DEXTERITY));
        System.out.println("4. Skill: " + hero.getAbilities().get(Ability.SKILL));
        System.out.println("5. Luck: " + hero.getAbilities().get(Ability.LUCK));
        System.out.println("6. Health: " + hero.getAbilities().get(Ability.HEALTH));

    }

    public static void printMonsterProperties(Monster monster){
        System.out.println("ATTACK: " + monster.getAbilities().get(Ability.ATTACK));
        System.out.println("Parry: " + monster.getAbilities().get(Ability.PARRY));
        System.out.println("Dexterity: " + monster.getAbilities().get(Ability.DEXTERITY));
        System.out.println("Health: " + monster.getAbilities().get(Ability.HEALTH));
        if (monster.getImmunities().get(Immunity.SILVER) == true){
            System.out.println(monster.getName() + " is immune against silver sword.");
        } else {
            System.out.println(monster.getName() + " is weak against silver sword.");
        }

        if (monster.getImmunities().get(Immunity.STEEL) == true){
            System.out.println(monster.getName() + " is immune against steel sword.");
        } else {
            System.out.println(monster.getName() + " is weak against steel sword.");
        }
    }

    public static void printSigns() {
        System.out.println("AARD: " + Sign.AARD.getDescription());
        System.out.println("IGNI: " + Sign.IGNI.getDescription());
        System.out.println("YRDEN: " + Sign.YRDEN.getDescription());
        System.out.println("QUEN: " + Sign.QUEN.getDescription());
        System.out.println("AXII: " + Sign.AXII.getDescription());
    }

    public static void printNumberOfSignsReady(Witcher witcher){
        if (witcher.getManaPoints() <= 0) {
            System.out.println("You have not any power for signs!");
        } else if (witcher.getManaPoints() == 1) {
             System.out.println("In next hunt you can use only one sign!");
        } else {
            System.out.println("You can use " + witcher.getManaPoints() + " times a sign!");
        }
    }
}
