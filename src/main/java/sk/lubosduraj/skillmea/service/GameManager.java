package sk.lubosduraj.skillmea.service;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.HeroAbilityManager;
import sk.lubosduraj.skillmea.constant.Constant;
import sk.lubosduraj.skillmea.domain.Enemy;
import sk.lubosduraj.skillmea.domain.Hero;
import sk.lubosduraj.skillmea.domain.LoadedGame;
import sk.lubosduraj.skillmea.utility.EnemyGenerator;
import sk.lubosduraj.skillmea.utility.InputUtils;
import sk.lubosduraj.skillmea.utility.PrintUtils;

import java.util.Map;

public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;
    private final FileService fileService;
    private final Map<Integer, Enemy> enemiesByLevel;
    private final BattleService battleService;

    public GameManager(){
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
        this.enemiesByLevel = EnemyGenerator.createEnemies();
        this.battleService = new BattleService();
    }

    public void startGame() throws InterruptedException {
        this.initGame();
        while (this.currentLevel <=  this.enemiesByLevel.size()){
            final Enemy enemy = this.enemiesByLevel.get(this.currentLevel);
            System.out.println("0. Fight " + enemy.getName() + " (Level " + this.currentLevel + ")");
            System.out.println("1. Upgrade abilities (" + hero.getHeroAvailablePoints() + " points to spend)");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");

            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> {
                    if(this.battleService.isHeroReadyToBattle(this.hero, enemy)){
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.HEALTH);

                        final boolean hasHeroWon = this.battleService.battle(this.hero, enemy);
                        if (hasHeroWon){
                            PrintUtils.printDivider();
                            System.out.println("You have won this battle! You have gained " + this.currentLevel + " ability points.");
                            this.hero.updateAvailablePoints(this.currentLevel);
                            this.currentLevel++;
                        } else {
                            System.out.println("You have lost.");
                        }

                        // restore health
                        this.hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                        System.out.println("You have full health now.");
                        PrintUtils.printDivider();
                    }
                }
                case 1 -> {
                    this.upgradeAbilities();
                }
                case 2 -> {
                    this.fileService.saveGame(this.hero, this.currentLevel);
                }
                case 3 ->{
                    System.out.println("Are you sure?");
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    final int exitChoice = InputUtils.readInt();
                    switch (exitChoice){
                        case 0 -> {
                            System.out.println("Continuing...");
                            PrintUtils.printDivider();
                        }
                        case 1 -> {
                            System.out.println("Bye!");
                            return;
                        }
                        default -> System.out.println("Wrong input!");

                    }
                }
                default -> System.out.println("Wrong input!");
            }
        }
        System.out.println("You won the game!");
    }

    private void upgradeAbilities(){
        System.out.println("Your abilities are:");
        PrintUtils.printAbilities(this.hero);

        System.out.println("0. Go back");
        System.out.println("1. Spend points (" + hero.getHeroAvailablePoints() + " points to spend)");
        System.out.println("2. Remove points");

        final  int choice = InputUtils.readInt();
        switch (choice){
            case 0 -> {}
            case 1 -> this.heroAbilityManager.spendAvailablePoints();
            case 2 -> this.heroAbilityManager.removeAvailablePoints();
            default -> System.out.println("Invalid index.");

        }
    }

    public void initGame(){
        System.out.println("\n" +
                " ________ __ __         __                    __                        __               __                                 \n" +
                "|  |  |  |__|  |_.----.|  |--.-----.----.    |  |_.-----.----.--------.|__|.-----.---.-.|  |    .-----.---.-.--------.-----.\n" +
                "|  |  |  |  |   _|  __||     |  -__|   _|    |   _|  -__|   _|        ||  ||     |  _  ||  |    |  _  |  _  |        |  -__|\n" +
                "|________|__|____|____||__|__|_____|__|      |____|_____|__| |__|__|__||__||__|__|___._||__|    |___  |___._|__|__|__|_____|\n" +
                "                                                                                                |_____|                     \n");
        System.out.println("Welcome young Witcher.");
        boolean initialized = false;
        while (!initialized){
            System.out.println("0. Start new game");
            System.out.println("1. Load game");
            System.out.println("3. Exit game");
            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> {
                    System.out.println("Hunt begins!");
                    initialized = true;
                }
                case 1 -> {
                        final LoadedGame loadGame = fileService.loadGame();
                        if (loadGame != null){
                            this.hero = loadGame.getHero();
                            this.currentLevel = loadGame.getLevel();
                            initialized = true;
                            return;
                        }
                }
                case 3 -> {
                    System.out.println("Bye");
                    this.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }


        System.out.println("Enter your name: ");
        final String name = InputUtils.readString();
        this.hero.setName(name);
        System.out.println("Hello " + hero.getName() + ". Begin your journey as a true Witcher!");
        PrintUtils.printDivider();
        PrintUtils.printAbilities(hero);
        System.out.println();
        this.heroAbilityManager.spendAvailablePoints();
    }

    public void exit(int status){
        System.exit(status);
    }

}
