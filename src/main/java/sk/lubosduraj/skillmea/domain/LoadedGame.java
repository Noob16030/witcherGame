package sk.lubosduraj.skillmea.domain;

public class LoadedGame {
    private final Witcher hero;
    private final int level;


    public Witcher getHero() {
        return hero;
    }

    public int getLevel() {
        return level;
    }

    public LoadedGame(Witcher hero, int level) {
        this.hero = hero;
        this.level = level;
    }
}
