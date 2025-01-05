package sk.lubosduraj.skillmea.service;

import sk.lubosduraj.skillmea.domain.GameCharacter;
import sk.lubosduraj.skillmea.domain.Witcher;
import sk.lubosduraj.skillmea.domain.Monster;
import sk.lubosduraj.skillmea.utility.MonsterGenerator;

import java.io.IOException;
import java.util.Map;

public class QuestService {
    boolean active;
    String name;
    int pointsReceived;
    Witcher witcher;
    private final Map<Integer, GameCharacter> enemiesByLevel;
    FileService fileService;

    public QuestService(Witcher witcher) throws IOException {
        this.witcher = witcher;
        this.enemiesByLevel = MonsterGenerator.createEnemies();
        this.fileService = new FileService();
        this.pointsReceived = this.witcher.getCurrentLevel();
        this.name = fileService.readQuestName(String.valueOf(this.witcher.getCurrentLevel()));
    }

    public GameCharacter startQuest() throws IOException {
        final GameCharacter monster = this.enemiesByLevel.get(this.witcher.getCurrentLevel());
        this.active = true;
        fileService.readQuest(String.valueOf(this.witcher.getCurrentLevel()));
        return monster;
    }

    public int endQuest(boolean finished) {
        this.active = false;
        if (finished) {
            return 5;
        } else {
            return 0;
        }
    }

    public int getPointsReceived() {
        return pointsReceived;
    }

    public String getName(){
        return name;
    }
}
