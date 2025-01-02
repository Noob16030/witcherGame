package sk.lubosduraj.skillmea;
import sk.lubosduraj.skillmea.service.GameManager;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        final GameManager gameManager = new GameManager();
        gameManager.startGame();
    }

}