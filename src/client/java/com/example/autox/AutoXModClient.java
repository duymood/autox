package com.example.autox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;

public class AutoXModClient implements ClientModInitializer {

    // Doi thanh true/false de bat/tat tinh nang.
    private static boolean enabled = true;

    // Phim X theo API ban phim cua Minecraft 1.21.11.
    private static final KeyInput KEY_INPUT_X = new KeyInput(GLFW.GLFW_KEY_X, 0, 0);

    // Cho GUI khoi tao xong. Lenh bam X se duoc thuc hien o tick ke tiep (~50ms).
    private static boolean pendingAutoX = false;
    private static boolean alreadyPressed = false;

    @Override
    public void onInitializeClient() {
        // Khi GUI dan lang mo xong, chi dat co. Khong bam X ngay trong AFTER_INIT.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!enabled || !(screen instanceof MerchantScreen)) {
                return;
            }

            alreadyPressed = false;
            pendingAutoX = true;
        });

        // Tick ke tiep sau khi GUI mo: bam X mot lan.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!enabled || !pendingAutoX || alreadyPressed) {
                return;
            }

            if (!(client.currentScreen instanceof MerchantScreen)) {
                // GUI da dong truoc khi toi tick xu ly -> huy lenh.
                pendingAutoX = false;
                return;
            }

            pendingAutoX = false;
            alreadyPressed = true;

            client.currentScreen.keyPressed(KEY_INPUT_X);
            AutoXMod.LOGGER.info("[AutoX] Da bam X khi mo GUI giao dich.");
        });
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        if (!value) {
            pendingAutoX = false;
            alreadyPressed = false;
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
