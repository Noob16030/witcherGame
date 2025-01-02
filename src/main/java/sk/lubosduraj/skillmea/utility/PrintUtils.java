package sk.lubosduraj.skillmea.utility;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Hero;

import java.util.Map;

public class PrintUtils {
    public static void printAbilities(GameCharacter character){
        System.out.println("Your abilities:");
        for (Map.Entry<Ability, Integer> entry : character.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
    }

    public static void printDivider(){
        System.out.println("---------------------------------------------");
    }
}
