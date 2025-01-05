package sk.lubosduraj.skillmea.ability;

import sk.lubosduraj.skillmea.constant.Constant;
import sk.lubosduraj.skillmea.domain.Witcher;
import sk.lubosduraj.skillmea.utility.InputUtils;
import sk.lubosduraj.skillmea.utility.PrintUtils;

public class HeroAbilityManager {
    private final Witcher hero;

    public HeroAbilityManager(Witcher hero){
        this.hero = hero;
    }

    public void removeAvailablePoints(){
        while (true) {
            System.out.println("Which ability do you want to remove?");
            System.out.println("0. I am done");
            PrintUtils.printAbilitiesNew(hero);

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
                case 6 -> ability = Ability.MAX_HEALTH;
                default -> {
                    System.out.println("Invalid index.");
                    continue;
                }
            }

            if (ability == Ability.MAX_HEALTH && this.hero.getAbilities().get(Ability.MAX_HEALTH) <= 50){
                System.out.println("You cannot remove points from this ability!");
                PrintUtils.printDivider();
                continue;
            }

            if (this.hero.getAbilities().get(ability) == 1) {
                System.out.println("You cannot remove points from this ability!");
                PrintUtils.printDivider();
            } else {
                this.hero.updateAbility(ability, -1);
                this.hero.updateAvailablePoints(1);
                System.out.println("You have removed 1 point from " + ability);
                PrintUtils.printDivider();
            }
        }
    }

    public void spendAvailablePoints(){
        int availablePoints = hero.getHeroAvailablePoints();

        if (availablePoints == 0){
            System.out.println("You have no points to spent.");
            PrintUtils.printDivider();
            return;
        }

        while (availablePoints > 0){
            System.out.println("You have " + availablePoints + " points to spend. Choose wisely.");
            System.out.println("0. Explain abilities");
            PrintUtils.printAbilitiesNew(hero);

            final int abilityIndex = InputUtils.readInt();
            Ability ability;
            switch (abilityIndex){
                case 0 -> {
                    for (Ability a: Ability.values()){
                        System.out.println(a + ": " + a.getDescription());
                    }
                    System.out.println();
                    if (availablePoints > 1){
                        PrintUtils.printDivider();
                    }
                    continue;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.PARRY;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.MAX_HEALTH;
                default -> {
                    System.out.println("Invalid index.");
                    continue;
                }
            }
            hero.updateAbility(ability, 1);
            System.out.println("You have updated " + ability);
            hero.updateAvailablePoints(-1);
            PrintUtils.printDivider();

            availablePoints --;
        }
        System.out.println("You spent all your talent points.");
        PrintUtils.printAbilitiesWithoutNumbers(hero);
        PrintUtils.printDivider();
    }

    public void meditate(Witcher witcher) throws InterruptedException {
        if (witcher.getAbilities().get(Ability.ACTUAL_HEALTH) == witcher.getAbilities().get(Ability.MAX_HEALTH)){
            System.out.println("Your health is full");
        } else if (witcher.getCoins() < 5){
            System.out.println("You do not have enough coins.");
        } else {
            // restore health
            PrintUtils.printDivider();
            System.out.print("Meditating.");
            for(int i = 0; i <= 4; i++){
                Thread.sleep(Constant.RESTING_DELAY_MILIS);
                System.out.print(".");
            }
            System.out.println("");
            PrintUtils.printDivider();
            witcher.setAbility(Ability.ACTUAL_HEALTH, witcher.getAbilities().get(Ability.MAX_HEALTH));
            witcher.setCoins(witcher.getCoins() - 5);
            System.out.println("You are full rested for now.");
            PrintUtils.printDivider();
        }
    }
}
