package sk.lubosduraj.skillmea.ability;

public enum Sign {
    AARD("Aard is used for telekinetic thrust that can stun, repel, knock down, or disarm opponents, as well as remove barriers and other objects."),
    IGNI("Igni is used for pyrokinetic burst that can repel and ignite opponents, as well as start fires."),
    YRDEN("Yrden blocks the monsters from getting closer, scaring them off. If a hostile being enters the area affected by Sign, it is visibly slowed down"),
    QUEN("When Quen is casted magical shield protecting the witcher for next one attack."),
    AXII("Axii can calm down people and creatures, manipulate their minds or be used to hex enemies.");

    private final String description;

    Sign(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
