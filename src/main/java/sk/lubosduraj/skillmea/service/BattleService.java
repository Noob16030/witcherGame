package sk.lubosduraj.skillmea.service;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.Immunity;
import sk.lubosduraj.skillmea.constant.Constant;
import sk.lubosduraj.skillmea.domain.Monster;
import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Witcher;
import sk.lubosduraj.skillmea.utility.InputUtils;
import sk.lubosduraj.skillmea.utility.PrintUtils;

import java.util.Map;
import java.util.Random;

public class BattleService {
    private final Random random;

    public BattleService() {
        this.random = new Random();
    }

    public boolean battle(Witcher hero, Monster enemy) throws InterruptedException {
        final Map<Ability, Integer> heroAbilities = hero.getAbilities();
        final Map<Ability, Integer> enemyAbilities = enemy.getAbilities();

        System.out.println("You surprise monster and start the battle!");
        PrintUtils.printDivider();

        boolean isHeroTurn = true;
        boolean printedRecovery = true;
        boolean printedRecoveryFromSlow = true;
        boolean printedMagicalShield = true;
        int roundsRemaining = 0;
        int roundsSlowed = 0;
        int roundsMagicalShield = 0;
        int basicMonsterAttack = 0;
        int basicMonsterParry = 0;
        int basicWitcherParry = 0;


        while (true) {
            final int heroLife = heroAbilities.get(Ability.HEALTH);
            final int enemyLife = enemyAbilities.get(Ability.HEALTH);

            if (heroLife <= 0) {
                return false;
            } else if (enemyLife <= 0) {
                return true;
            }

            System.out.println("Your life: " + heroLife);
            System.out.println("Enemy life: " + enemyLife);

            if (isHeroTurn) {
                System.out.println("0. Run away");
                System.out.println("1. Attack with steel sword");
                System.out.println("2. Attack with silver sword");
                System.out.println("3. Use sign");
                final int choice = InputUtils.readInt();
                switch (choice){
                    case 0 -> {
                        return false;
                    }
                    case 1 -> this.huntRound(hero, enemy, isHeroTurn, Immunity.STEEL);
                    case 2 -> this.huntRound(hero, enemy, isHeroTurn, Immunity.SILVER);
                    case 3 -> {
                        final Sign sign = this.huntRoundSign(hero);
                        if (sign == null) {
                            continue;
                        }
                        if (sign == Sign.AARD){
                            System.out.println("You stunned " + enemy.getName() + ". You have now some time for your attacks.");
                            roundsRemaining += 2;
                            hero.setManaPoints(hero.getManaPoints() - 1);
                            printedRecovery = false;
                            continue;
                        }
                        if (sign == Sign.IGNI){
                            System.out.println("You fired IGNI against " + enemy.getName() + ".");
                            enemy.receiveDamage(20);
                            hero.setManaPoints(hero.getManaPoints() - 1);
                        }
                        if (sign == Sign.YRDEN){
                            System.out.println(enemy.getName() + " is slowed down for some time.");
                            roundsSlowed += 3;
                            basicMonsterAttack = enemy.getAbilities().get(Ability.ATTACK);
                            basicMonsterParry = enemy.getAbilities().get(Ability.PARRY);
                            enemy.slowCharacter(hero.getCurrentLevel());
                            hero.setManaPoints(hero.getManaPoints() - 1);
                            printedRecoveryFromSlow = false;
                        }
                        if(sign == Sign.QUEN){
                            System.out.println("You cast magical shield around you!");
                            roundsMagicalShield += hero.getCurrentLevel();
                            basicWitcherParry = hero.getAbilities().get(Ability.PARRY);
                            hero.raiseParry(hero.getCurrentLevel() * 3);
                            hero.setManaPoints(hero.getManaPoints() - 1);
                            printedMagicalShield = false;
                        }
                        if(sign == Sign.AXII){
                            if(enemy instanceof Monster){
                                System.out.println("AXII against monsters do not have visible effect!");
                                hero.setManaPoints(hero.getManaPoints() - 1);
                            }
                        }
                    }
                }
                if (roundsRemaining > 0){
                    roundsRemaining--;
                } else {
                    if(!printedRecovery) {
                        System.out.println(enemy.getName() + " is back on legs.");
                        printedRecovery = true;
                    }
                    isHeroTurn = false;
                }

                if (roundsSlowed > 0){
                    roundsSlowed--;
                } else {
                    if(!printedRecoveryFromSlow) {
                        System.out.println(enemy.getName() + " is recovered from YRDEN.");
                        enemy.returnCharacterStats(basicMonsterAttack, basicMonsterParry);
                        printedRecoveryFromSlow = true;
                    }
                }

                if (roundsMagicalShield > 0){
                    roundsMagicalShield--;
                } else {
                    if(!printedMagicalShield){
                        System.out.println("Magical shield was broken!");
                        hero.returnParry(basicWitcherParry);
                        printedMagicalShield = true;
                    }
                }
            } else {
                this.huntRound(enemy, hero, isHeroTurn, Immunity.STEEL);
                isHeroTurn = true;
            }

            Thread.sleep(Constant.BATTLE_DELAY_MILIS);

        }
    }

    private Sign huntRoundSign(Witcher witcher) {
        if (witcher.getManaPoints() > 0) {
            while (true) {
                System.out.println("0. Back");
                System.out.println("1. Explain signs");
                System.out.println("2. AARD");
                System.out.println("3. IGNI");
                System.out.println("4. YRDEN");
                System.out.println("5. QUEN");
                System.out.println("6. AXII");

                final int choice = InputUtils.readInt();

                switch (choice) {
                    case 0 -> {
                        return null;
                    }
                    case 1 -> {
                        PrintUtils.printSigns();
                        PrintUtils.printDivider();
                    }
                    case 2 -> {
                        return Sign.AARD;
                    }
                    case 3 -> {
                        return Sign.IGNI;
                    }
                    case 4 -> {
                        return Sign.YRDEN;
                    }
                    case 5 -> {
                        return Sign.QUEN;
                    }
                    case 6 -> {
                        return Sign.AXII;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("You do not have power to sign!");
            return null;
        }
    }

    private void huntRound(GameCharacter attacker, GameCharacter defender, boolean isWitcher, Immunity immunity){
        final Map<Ability, Integer> attackerAbilities = attacker.getAbilities();
        final Map<Ability, Integer> defenderAbilities = defender.getAbilities();
        final int minAttack;
        final int maxAttack;
        final int attackPower;
        final int minDefence;
        final int maxDefence;
        final int defencePower;
        int damage;

        // calculate witcher attack power
        if (isWitcher) {
            minAttack = attackerAbilities.get(Ability.ATTACK);
            maxAttack = minAttack + attackerAbilities.get(Ability.DEXTERITY) + attackerAbilities.get(Ability.SKILL);
            if ((((Monster) defender).getImmunities().get(Immunity.SILVER) && immunity == Immunity.SILVER) || (((Monster) defender).getImmunities().get(Immunity.STEEL) && immunity == Immunity.STEEL)) {
                attackPower = random.nextInt(maxAttack - minAttack + 1) + minAttack;
            } else {
                attackPower = random.nextInt(maxAttack - minAttack + 1) + minAttack + 10;
            }

            // calculate defence power
            minDefence = defenderAbilities.get(Ability.PARRY);
            maxDefence = minDefence + defenderAbilities.get(Ability.DEXTERITY);
            defencePower = random.nextInt(maxDefence - minDefence + 1) + minDefence;


            // calculate damage
            damage = Math.max(0, attackPower - defencePower);
            final boolean isCriticalHit = (random.nextInt(101) + 1) < attackerAbilities.get(Ability.LUCK);
            if (isCriticalHit) {
                System.out.println("Critical hit!");
                damage *= Constant.CRITICAL_HIT_MULTIPLIER;
            }
        } else {
            minAttack = attackerAbilities.get(Ability.ATTACK);
            maxAttack = minAttack + attackerAbilities.get(Ability.DEXTERITY);
            attackPower = random.nextInt(maxAttack - minAttack + 1) + minAttack;

            // calculate defence power
            minDefence = defenderAbilities.get(Ability.PARRY);
            maxDefence = minDefence + defenderAbilities.get(Ability.DEXTERITY);
            defencePower = random.nextInt(maxDefence - minDefence + 1) + minDefence;

            // calculate damage
            damage = Math.max(0, attackPower - defencePower);
        }

        System.out.println(attacker.getName() + " attacks " + defender.getName() + " with " + damage + " damage!");
        defender.receiveDamage(damage);
        System.out.println(defender.getName() + " has " + defenderAbilities.get(Ability.HEALTH) +  " health.");
        PrintUtils.printDivider();

    }

    public boolean isHeroReadyToBattle(Witcher hero, Monster monster){
        System.out.println(hero.getName() + " VS " + monster.getName());
        PrintUtils.printDivider();
        System.out.println("View your abilities:");
        PrintUtils.printAbilitiesWithoutNumbers(hero);
        PrintUtils.printNumberOfSignsReady(hero);
        PrintUtils.printDivider();
        System.out.println("View monster abilities:");
        PrintUtils.printMonsterProperties(monster);

        System.out.println("Are you ready to hunt?");
        System.out.println("0. No");
        System.out.println("1. Yes");

        final int choice = InputUtils.readInt();
        switch (choice){
            case 0 -> {
                System.out.println("You have return to nearest tawern.");
                return false;
            }
            case 1 -> {
                System.out.println("Let the hunt begin.");
                return true;
            }
            default -> {
                System.out.println("Invalid choice!");
                return false;
            }
        }
    }
}
