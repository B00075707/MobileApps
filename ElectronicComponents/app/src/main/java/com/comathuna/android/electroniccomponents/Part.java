package com.comathuna.android.electroniccomponents;

/**
 * Created by comathuna on 25/10/2016.
 */
public class Part {
    private String name;
    private String description;
    private int imageResourceId;

    public static final Part[] sParts = {
            new Part("Red LED", "Glows red", R.drawable.led),
            new Part(new String("180"+(char)0x03A9), "180 ohm resistor", R.drawable.resistor),
            new Part(new String("4k"+(char)0x03A9), "4k ohm resistor", R.drawable.resistor),
            new Part(new String("40k"+(char)0x03A9), "40k ohm resistor", R.drawable.resistor),
            new Part("NE555", "Timer IC", R.drawable.ic),
            new Part("CD4060", "Counter", R.drawable.ic),
    };

    //Each Part has a name, description, and an image resource
    private Part(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
