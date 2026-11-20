package net.naoya.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.naoya.NaoyaClient;
import net.naoya.module.Module;

public class NaoyaScreen extends Screen {
    private static final int W = 140, HEADER = 18, ROW = 16;
    private static final int BG = 0xE0121218, HEAD = 0xFF7B2FF7, ON = 0xFF2ECC71, OFF = 0xFF2A2A35, HOVER = 0xFF3A3A48;

    private static int px = 40, py = 40; // position persists between openings
    private boolean dragging;
    private double dragOffX, dragOffY;

    public NaoyaScreen() { super(Text.literal("Naoya")); }

    @Override
    public boolean shouldPause() { return false; }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);

        int rows = NaoyaClient.MODULES.size() + 1; // +1 speed row
        int h = HEADER + rows * ROW + 6;
        ctx.fill(px, py, px + W, py + h, BG);
        ctx.fill(px, py, px + W, py + HEADER, HEAD);
        ctx.drawText(textRenderer, "Naoya", px + 6, py + 5, 0xFFFFFFFF, true);

        int y = py + HEADER + 3;
        for (Module m : NaoyaClient.MODULES) {
            boolean hover = inside(mouseX, mouseY, px + 3, y, W - 6, ROW - 2);
            int color = m.isEnabled() ? ON : (hover ? HOVER : OFF);
            ctx.fill(px + 3, y, px + W - 3, y + ROW - 2, color);
            ctx.drawText(textRenderer, m.name, px + 8, y + 4, 0xFFFFFFFF, true);
            y += ROW;
        }

        // Fly speed row: [-]  Speed 1.0  [+]
        ctx.fill(px + 3, y, px + W - 3, y + ROW - 2, OFF);
        ctx.drawText(textRenderer, "-", px + 9, y + 4, 0xFFFFFFFF, true);
        String label = "Speed " + NaoyaClient.FLY.speed;
        ctx.drawText(textRenderer, label, px + (W - textRenderer.getWidth(label)) / 2, y + 4, 0xFFFFFFFF, true);
        ctx.drawText(textRenderer, "+", px + W - 14, y + 4, 0xFFFFFFFF, true);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mx = click.x(), my = click.y();
        if (click.button() == 0) {
            if (inside(mx, my, px, py, W, HEADER)) {
                dragging = true;
                dragOffX = mx - px;
                dragOffY = my - py;
                return true;
            }
            int y = py + HEADER + 3;
            for (Module m : NaoyaClient.MODULES) {
                if (inside(mx, my, px + 3, y, W - 6, ROW - 2)) { m.toggle(); return true; }
                y += ROW;
            }
            if (inside(mx, my, px + 3, y, 24, ROW - 2)) { NaoyaClient.FLY.changeSpeed(-0.5f); return true; }
            if (inside(mx, my, px + W - 27, y, 24, ROW - 2)) { NaoyaClient.FLY.changeSpeed(0.5f); return true; }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (dragging) {
            px = (int) Math.max(0, Math.min(width - W, click.x() - dragOffX));
            py = (int) Math.max(0, Math.min(height - HEADER, click.y() - dragOffY));
            return true;
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        dragging = false;
        return super.mouseReleased(click);
    }

    private static boolean inside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }
}
