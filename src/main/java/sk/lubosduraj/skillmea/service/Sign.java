package sk.lubosduraj.skillmea.service;

public enum Sign {
    AARD("Aard is used for telekinetic thrust that can stun, repel, knock down, or disarm opponents, as well as remove barriers and other objects."),
    IGNI("Igni is used for pyrokinetic burst that can repel and ignite opponents, as well as start fires."),
    YRDEN("If a hostile being enters the area affected by Sign, it is visibly slowed down. Not very effective against humanoids."),
    QUEN("When Quen is casted magical shield protecting the witcher for next attacks, depending on witcher level."),
    AXII("Axii can calm down people, manipulate their minds or be used to hex enemies. Not very effective against monsters.");

    private final String description;

    Sign(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
