package sk.lubosduraj.skillmea.service;

import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.ability.HeroAbilityManager;
import sk.lubosduraj.skillmea.constant.Constant;
import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Monster;
import sk.lubosduraj.skillmea.domain.Witcher;
import sk.lubosduraj.skillmea.domain.LoadedGame;
import sk.lubosduraj.skillmea.utility.MonsterGenerator;
import sk.lubosduraj.skillmea.utility.InputUtils;
import sk.lubosduraj.skillmea.utility.PrintUtils;

import java.io.IOException;
import java.util.Map;

public class GameManager {
    private Witcher hero;
    private HeroAbilityManager heroAbilityManager;
    private final FileService fileService;
    private final Map<Integer, GameCharacter> enemiesByLevel;
    private final BattleService battleService;

    public GameManager(){
        this.hero = new Witcher("");
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.fileService = new FileService();
        this.enemiesByLevel = MonsterGenerator.createEnemies();
        this.battleService = new BattleService();
    }

    public void startGame() throws InterruptedException, IOException {
        this.initGame();
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
        while (hero.getCurrentLevel() <=  this.enemiesByLevel.size()){
            QuestService quest = new QuestService(this.hero);
            //System.out.println("0. Fight " + enemy.getName() + " (Level " + this.currentLevel + ")");
            PrintUtils.printActualHealth(this.hero);
            System.out.println("0. Start quest: " + quest.getName());
            System.out.println("1. Upgrade abilities (" + hero.getHeroAvailablePoints() + " points to spend)");
            System.out.println("2. Meditate for 5 coins (You have " + hero.getCoins()+ " coins)");
            System.out.println("3. Save game");
            System.out.println("4. Exit game");

            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> {
                   final GameCharacter monster = quest.startQuest();
                    PrintUtils.printDivider();

                    if(this.battleService.isHeroReadyToBattle(this.hero, monster)){
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.ACTUAL_HEALTH);
                        final boolean hasHeroWon = this.battleService.battle(this.hero, monster);
                        if (hasHeroWon){
                            PrintUtils.printDivider();
                            System.out.println("You have won this hunt! You have gained " + quest.getPointsReceived() + " ability points.");
                            this.hero.updateAvailablePoints(quest.getPointsReceived());
                            this.hero.setCurrentLevel(this.hero.getCurrentLevel() + 1);
                            this.hero.setManaPoints(this.hero.getManaPoints() +1);
                            hero.setCoins(hero.getCoins() + quest.endQuest(true));
                        } else {
                            System.out.println("You have lost. You are severally damaged. Need to meditate.");
                            quest.endQuest(false);
                        }
                    }
                }
                case 1 -> this.upgradeAbilities();

                case 2 -> heroAbilityManager.meditate(this.hero);

                case 3 -> this.fileService.saveGame(this.hero, hero.getCurrentLevel());

                case 4 ->{
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
        System.out.println("You finish all quests for now withcer!");
    }

    private void upgradeAbilities(){
        System.out.println("Your abilities are:");
        PrintUtils.printAbilitiesWithoutNumbers(this.hero);

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

    public void initGame() throws IOException {
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
            System.out.println("2. Exit game");
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
                            this.hero.setCurrentLevel(loadGame.getLevel());
                            initialized = true;
                            return;
                        }
                }
                case 2 -> {
                    System.out.println("Bye");
                    this.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }


        System.out.println("Enter your name: ");
        final String name = InputUtils.readString();
        this.hero.setName(name);
        fileService.readText("Intro");
        System.out.println(hero.getName() + " begin your journey as a true Witcher!");
        PrintUtils.printDivider();
        this.heroAbilityManager.spendAvailablePoints();
        this.hero.setAbility(Ability.ACTUAL_HEALTH, this.hero.getAbilities().get(Ability.MAX_HEALTH));
    }

    public void exit(int status){
        System.exit(status);
    }

}
