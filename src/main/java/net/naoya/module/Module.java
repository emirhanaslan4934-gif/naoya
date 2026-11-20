package net.naoya.module;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    public final String name;
    private boolean enabled;

    protected Module(String name) { this.name = name; }

    public boolean isEnabled() { return enabled; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick(MinecraftClient mc) {}
}
