package sk.lubosduraj.skillmea.service;
import sk.lubosduraj.skillmea.ability.Ability;
import sk.lubosduraj.skillmea.domain.Hero;
import sk.lubosduraj.skillmea.domain.LoadedGame;
import sk.lubosduraj.skillmea.utility.InputUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileService {

    public void saveGame(Hero hero, int currentLevel) {
        while(true){
            System.out.println("How do you want to name your save?");
            final String name = InputUtils.readString();

            final String path = "saved-games/" + name + ".txt";

            if (new File(path).exists()){
                System.out.println("Game with this name is already saved.");
                continue;
            }

            try{

                Files.writeString(Path.of(path), this.heroDataToString(hero, currentLevel));
                System.out.println("Game saved.");
            } catch (IOException e) {
                System.out.println("Error while saving game.");
                System.out.println("Really want to save?");
                System.out.println("0. Yes");
                System.out.println("1. No");

                final int myChoice = InputUtils.readInt();

                switch (myChoice){
                    case 0 -> {
                        continue;
                    }
                    case 1 -> {
                        break;
                    }
                    default -> {
                        System.out.println("Wrong input!");
                    }
                }



            } catch (InvalidPathException e) {
                System.out.println("Invalid characters in file name.");
                System.out.println("Really want to save?");
                System.out.println("0. Yes");
                System.out.println("1. No");

                final int myChoice = InputUtils.readInt();

                switch (myChoice){
                    case 0 -> {
                        continue;
                    }
                    case 1 -> {
                        break;
                    }
                    default -> {
                        System.out.println("Wrong input!");
                    }
                }
            }
            break;
        }
    }

    public LoadedGame loadGame(){
        while (true){
            final File[] savedFiles = new File ("saved-games").listFiles();
            if (savedFiles == null || savedFiles.length == 0){
                System.out.println("No saved games found.");
                return null;
            }

            System.out.println("Enter name of save you want to load.");
            for (int i = 0; i < savedFiles.length; i++){
                System.out.println(i + ". " + savedFiles[i].getName().replace(".txt", ""));
            }

            final int choice = InputUtils.readInt();
            if (choice < 0 || choice >= savedFiles.length){
                System.out.println("Invalid choice.");
                continue;
            }

            final String loadGameFile = savedFiles[choice].getName();
            final String filepath = "saved-games/" + loadGameFile;

            try{
                final String heroData = Files.readString(Path.of(filepath));
                System.out.println("Game loaded.");
                return this.stringToHeroData(heroData);
            } catch (IOException e) {
                System.out.println("Error while loading game!");
            } catch (InvalidPathException e) {
                System.out.println("Invalid characters in file name!");
            }
        }
    }

    private LoadedGame stringToHeroData(String heroData){
        final String[] lines = heroData.split("\n");
        final int currentLevel = Integer.parseInt(lines[0]);
        final String heroName = lines[1];
        final int heroAvailablePoints = Integer.parseInt(lines[2]);
        final Map<Ability, Integer> abilities = new HashMap<>();
        for (int i = 3; i < 3 + Ability.values().length; i++){
            final String[] abilityData = lines[i].split(":");
            final Ability ability = Ability.valueOf(abilityData[0]);
            final int value = Integer.parseInt(abilityData[1]);
            abilities.put(ability, value);
        }
        return new LoadedGame(new Hero(heroName, abilities, heroAvailablePoints), currentLevel);
    }

    private String heroDataToString(Hero hero, int currentLevel){
        final StringBuilder sb = new StringBuilder();
        sb.append(currentLevel).append("\n");
        sb.append(hero.getName()).append("\n");
        sb.append(hero.getHeroAvailablePoints()).append("\n");
        for (Ability ability: Ability.values()){
            sb.append(ability).append(":").append(hero.getAbilities().get(ability)).append("\n");
        }
        return sb.toString();
    }
}
