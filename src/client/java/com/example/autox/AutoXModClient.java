package com.example.autox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class AutoXModClient implements ClientModInitializer {

	// Doi thanh true/false de bat/tat tinh nang nhanh khi test.
	private static boolean enabled = true;

	// So tick (1 tick = 1/20 giay) can cho de cac mod khac (vd Inventory
	// Profiles Next) kip khoi tao xong trang thai cua GUI giao dich truoc
	// khi ta gia lap phim X. Neu van thay "luc duoc luc khong", thu tang
	// so nay len (vd 10, 15...).
	private static final int DELAY_TICKS = 2;

	// Bo dem: con bao nhieu tick nua thi gia lap phim. 0 = khong co gi cho.
	private static int pendingTicks = 0;

	@Override
	public void onInitializeClient() {
		// Lang nghe su kien: bat cu man hinh (Screen) nao duoc mo va da khoi tao xong
		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {

			// Chi xu ly khi man hinh la GUI giao dich cua dan lang (Villager Trade GUI)
			if (!enabled || !(screen instanceof MerchantScreen)) {
				return;
			}

			// Thay vi gia lap phim NGAY LAP TUC (de bi tranh chap thoi gian
			// voi cac mod khac dang cung khoi tao theo GUI nay), ta chi dat
			// bo dem va de vong lap tick ben duoi xu ly sau vai tick.
			pendingTicks = DELAY_TICKS;
		});

		// Vong lap chay moi tick cua client (20 lan/giay). Day la noi thuc
		// su gia lap phim, sau khi da doi du so tick can thiet.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (pendingTicks <= 0) {
				return;
			}

			pendingTicks--;

			if (pendingTicks == 0) {
				// Chi gia lap neu GUI giao dich VAN dang mo luc dem het gio
				// (phong truong hop nguoi choi da dong GUI truoc do).
				if (client.currentScreen instanceof MerchantScreen) {
					simulateRealKeyPressX(client);
				}
			}
		});
	}

	/**
	 * Gia lap DUNG MOT SU KIEN PHIM VAT LY X bang cach goi thang vao
	 * callback GLFW ma chinh Minecraft da dang ky voi cua so game.
	 *
	 * Day la cach gia lap manh nhat co the co: no khong di qua bat ky
	 * lop API nao cua rieng Minecraft (KeyBinding, Screen#keyPressed...)
	 * ma tai tao chinh xac con duong GLFW dung moi khi ban bam mot phim
	 * that tren ban phim vat ly. Vi vay no hoat dong voi MOI mod dang
	 * lang nghe phim - bat ke mod do bat phim kieu gi.
	 */
	private static void simulateRealKeyPressX(MinecraftClient client) {
		long windowHandle = client.getWindow().getHandle();

		// "Muon" lay callback ban phim hien tai: dang ky tam mot callback
		// rong, GLFW se tra ve callback CU (chinh la cai Minecraft/cac mod
		// khac da dang ky, co the la mot chuoi callback long nhau).
		GLFWKeyCallback previous = GLFW.glfwSetKeyCallback(windowHandle, null);

		if (previous == null) {
			AutoXMod.LOGGER.warn("[AutoX] Khong tim thay key callback hien tai, huy gia lap lan nay.");
			return;
		}

		// Tra lai callback that ngay lap tuc de khong lam mat input that cua nguoi choi.
		GLFW.glfwSetKeyCallback(windowHandle, previous);

		int scancode = GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_X);

		// Goi callback y het nhu khi GLFW tu bao "phim X vua nhan/tha" tu phan cung that.
		previous.invoke(windowHandle, GLFW.GLFW_KEY_X, scancode, GLFW.GLFW_PRESS, 0);
		previous.invoke(windowHandle, GLFW.GLFW_KEY_X, scancode, GLFW.GLFW_RELEASE, 0);

		AutoXMod.LOGGER.info("[AutoX] Da gia lap su kien GLFW that cho phim X (nhan + tha) sau " + DELAY_TICKS + " tick.");
	}

	public static void setEnabled(boolean value) {
		enabled = value;
	}

	public static boolean isEnabled() {
		return enabled;
	}
}
