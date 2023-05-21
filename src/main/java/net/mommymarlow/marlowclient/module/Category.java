package net.mommymarlow.marlowclient.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    MISC("Misc");

    public String name;

    Category(String name) {
        this.name = name;
    }
}
