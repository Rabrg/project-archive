package me.rabrg.bankstander.herblore;

public enum Herb {

    AVANTOE(103, 211, 261),
    CADANTINE(107, 215, 265),
    DWARF_WEED(109, 217, 267),
    GUAM(91, 199, 249),
    Harralander(97, 205, 255),
    IRIT(97, 209, 259),
    KWUARM(105, 213, 265),
    LANTADYME(2483, 2485, 2481),
    MARRENTILL(93, 201, 251),
    RANARR(99, 207, 257),
    SNAPDRAGON(3004, 3051, 3000),
    TARROMIN(95, 203, 253),
    TOADFLAX(3002, 3049, 2998),
    TORSTOL(111, 219, 269);

    private final int unfId;
    private final int grimyId;
    private final int cleanId;

    Herb(final int unfId, final int grimyId, final int cleanId) {
        this.unfId = unfId;
        this.grimyId = grimyId;
        this.cleanId = cleanId;
    }

    public int getUnfId() {
        return unfId;
    }

    public int getGrimyId() {
        return grimyId;
    }

    public int getCleanId() {
        return cleanId;
    }
}
