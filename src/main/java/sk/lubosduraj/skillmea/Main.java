package sk.lubosduraj.skillmea;
import sk.lubosduraj.skillmea.service.GameManager;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        final GameManager gameManager = new GameManager();
        gameManager.startGame();
    }

}