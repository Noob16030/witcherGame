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
                final int choice = InputUtils.readInt();
                switch (choice){
                    case 0 -> {
                        return false;
                    }
                    case 1 -> this.huntRound(hero, enemy, isHeroTurn, Immunity.STEEL);
                    case 2 -> this.huntRound(hero, enemy, isHeroTurn, Immunity.SILVER);
                }
                isHeroTurn = false;
            } else {
                this.huntRound(enemy, hero, isHeroTurn, Immunity.STEEL);
                isHeroTurn = true;
            }

            Thread.sleep(Constant.BATTLE_DELAY_MILIS);

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
