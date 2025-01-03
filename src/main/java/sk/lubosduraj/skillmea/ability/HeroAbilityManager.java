package sk.lubosduraj.skillmea.ability;

import sk.lubosduraj.skillmea.domain.Hero;
import sk.lubosduraj.skillmea.utility.InputUtils;
import sk.lubosduraj.skillmea.utility.PrintUtils;

public class HeroAbilityManager {
    private final Hero hero;

    public HeroAbilityManager(Hero hero){
        this.hero = hero;
    }

    public void removeAvailablePoints(){
        while (true) {
            System.out.println("Which ability do you want to remove?");
            System.out.println("0. I am done");
            System.out.println("1. Attack");
            System.out.println("2. Defence");
            System.out.println("3. Dexterity");
            System.out.println("4. Skill");
            System.out.println("5. Luck");
            System.out.println("6. Health");

            final int abilityIndex = InputUtils.readInt();
            Ability ability;
            switch (abilityIndex){
                case 0 -> {
                    return;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.PARRY;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid index.");
                    continue;
                }
            }
            if (this.hero.getAbilities().get(ability) == 1 || this.hero.getAbilities().get(Ability.HEALTH) == 50) {
                System.out.println("You cannot remove points from this ability!");
            } else {
                this.hero.updateAbility(ability, -1);
                this.hero.updateAvailablePoints(1);
                System.out.println("You have removed 1 point from " + ability);
                PrintUtils.printAbilities(this.hero);
                PrintUtils.printDivider();
            }
        }
    }

    public void spendAvailablePoints(){
        int availablePoints = hero.getHeroAvailablePoints();

        if (availablePoints == 0){
            System.out.println("You have no points to spent.");
            return;
        }

        while (availablePoints > 0){
            System.out.println("You have " + availablePoints + " points to spend. Choose wisely.");
            System.out.println("0. Explain abilities");
            System.out.println("1. Attack");
            System.out.println("2. Parry");
            System.out.println("3. Dexterity");
            System.out.println("4. Skill");
            System.out.println("5. Luck");
            System.out.println("6. Health");

            final int abilityIndex = InputUtils.readInt();
            Ability ability;
            switch (abilityIndex){
                case 0 -> {
                    for (Ability a: Ability.values()){
                        System.out.println(a + ": " + a.getDescription());
                    }
                    System.out.println();
                    if (availablePoints > 1){
                        PrintUtils.printAbilities(hero);
                    }
                    continue;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.PARRY;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid index.");
                    continue;
                }
            }
            hero.updateAbility(ability, 1);
            System.out.println("You have updated " + ability);
            hero.updateAvailablePoints(-1);
            if (availablePoints > 1){
                PrintUtils.printAbilities(hero);
            }
            availablePoints --;
        }
        System.out.println("You spent all your talent points.");
        PrintUtils.printAbilities(hero);
    }
}
