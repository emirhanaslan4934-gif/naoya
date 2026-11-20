package net.naoya.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class FlyModule extends Module {
    public float speed = 1.0f; // 1.0 = vanilla creative flight speed

    public FlyModule() { super("Fly"); }

    @Override
    public void onTick(MinecraftClient mc) {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;
        p.getAbilities().allowFlying = true;
        p.getAbilities().flying = true;
        p.getAbilities().setFlySpeed(0.05f * speed);
    }

    @Override
    public void onDisable() {
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        if (p == null) return;
        p.getAbilities().setFlySpeed(0.05f);
        if (!p.isCreative() && !p.isSpectator()) {
            p.getAbilities().allowFlying = false;
            p.getAbilities().flying = false;
        }
    }

    public void changeSpeed(float delta) {
        speed = Math.max(0.5f, Math.min(10.0f, Math.round((speed + delta) * 10f) / 10f));
    }
}
