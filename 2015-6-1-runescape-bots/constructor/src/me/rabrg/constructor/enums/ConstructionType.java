package me.rabrg.constructor.enums;

import java.awt.*;

public enum ConstructionType {

    CRUDE_WOODEN_CHAIR("Crude wooden chair", Constants.CHAIR_SPACE, 6752, Constants.CHAIR_WIDGET,
            new Rectangle(40, 66, 20, 20), Constants.PLANK, 2, 2),
    WOODEN_CHAIR("Wooden chair", Constants.CHAIR_SPACE, -1, Constants.CHAIR_WIDGET,
            new Rectangle(40, 135, 20, 20), Constants.PLANK, 3, 3), // TODO: id
    ROCKING_CHAIR("Rocking chair", Constants.CHAIR_SPACE, -1, Constants.CHAIR_WIDGET,
            new Rectangle(40, 196, 20, 20), Constants.PLANK, 3, 3), // TODO: id
    OAK_CHAIR("Oak chair", Constants.CHAIR_SPACE, -1, Constants.CHAIR_WIDGET,
            new Rectangle(40, 264, 20, 30), Constants.OAK_PLANK, 2, 0), // TODO: id
    OAK_ARMCHAIR("Oak armchair", Constants.CHAIR_SPACE, -1, Constants.CHAIR_WIDGET,
            new Rectangle(253, 73, 20, 20), Constants.OAK_PLANK, 3, 0); // TODO: id

    private final String name;
    private final String unbuiltObjectName;
    private final int builtObjectId;
    private final int widgetId;
    private final Rectangle clickRectangle;
    private final String plank;
    private final int plankAmount;
    private final int nailAmount;

    ConstructionType(final String name, final String unbuiltObjectName, final int builtObjectId, final int widgetId,
                     final Rectangle clickRectangle, final String plank, final int plankAmount,
                     final int nailAmount) {
        this.name = name;
        this.unbuiltObjectName = unbuiltObjectName;
        this.builtObjectId = builtObjectId;
        this.widgetId = widgetId;
        this.clickRectangle = clickRectangle;
        this.plank = plank;
        this.plankAmount = plankAmount;
        this.nailAmount = nailAmount;
    }

    public String getName() {
        return name;
    }

    public String getSpaceObjectName() {
        return unbuiltObjectName;
    }

    public int getBuiltObjectId() {
        return builtObjectId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public Rectangle getClickRectangle() {
        return clickRectangle;
    }

    public String getPlank() {
        return plank;
    }

    public int getPlankAmount() {
        return plankAmount;
    }

    public int getNailAmount() {
        return nailAmount;
    }

    private static final class Constants { // TODO

        private static final String CHAIR_SPACE = "Chair space";
        private static final String PLANK = "Plank";
        private static final String OAK_PLANK = "Oak plank";

        private static final int CHAIR_WIDGET = 396;
    }
}
