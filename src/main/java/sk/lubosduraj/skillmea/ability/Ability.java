package sk.lubosduraj.skillmea.ability;

public enum Ability {
    ATTACK("Attack is the ability for raw damage. Final damage is also affected by dexterity, skill and used sword."),
    PARRY("Parry is the ability to defend enemy's damage. Final damage is also affected by dexterity."),
    DEXTERITY("Dexterity is important for both attack and defence. It affects final damage and final damage reduction."),
    SKILL("Skill is important for attack and also for critical hit chance."),
    LUCK("Luck is important for critical hit chance."),
    ACTUAL_HEALTH("Health is the amount of damage you can take before you die. After each battle, health can be restored to full."),
    MAX_HEALTH("Max amount of health, before taken any damage");


    private final String description;

    Ability(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

