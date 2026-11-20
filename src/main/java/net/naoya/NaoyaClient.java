package net.naoya;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.naoya.gui.NaoyaScreen;
import net.naoya.module.AutoTotemModule;
import net.naoya.module.FlyModule;
import net.naoya.module.FullbrightModule;
import net.naoya.module.Module;
import net.naoya.module.XrayModule;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class NaoyaClient implements ClientModInitializer {
    public static final FlyModule FLY = new FlyModule();
    public static XrayModule XRAY = new XrayModule();
    public static final List<Module> MODULES = List.of(FLY, XRAY, new FullbrightModule(), new AutoTotemModule());

    private static KeyBinding openGui;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("naoya", "main"));
        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.naoya.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, category));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openGui.wasPressed()) mc.setScreen(new NaoyaScreen());
            for (Module m : MODULES) if (m.isEnabled()) m.onTick(mc);
        });
    }
}
