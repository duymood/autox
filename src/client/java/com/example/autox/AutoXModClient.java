package com.example.autox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AutoXModClient implements ClientModInitializer {

	// Doi thanh true/false de bat/tat tinh nang nhanh khi test.
	private static boolean enabled = true;

	// Ma phim vat ly cho X, dung chung cho ca 2 cach gia lap ben duoi.
	private static final InputUtil.Key KEY_X = InputUtil.fromKeyCode(GLFW.GLFW_KEY_X, 0);

	@Override
	public void onInitializeClient() {
		// Lang nghe su kien: bat cu man hinh (Screen) nao duoc mo va da khoi tao xong
		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {

			// Chi xu ly khi man hinh la GUI giao dich cua dan lang (Villager Trade GUI)
			if (!enabled || !(screen instanceof MerchantScreen)) {
				return;
			}

			// Doi mot nhip de dam bao GUI (va mod khac dang lang nghe man hinh nay)
			// da khoi tao hoan toan truoc khi gia lap phim.
			client.execute(() -> {

				// Cach 1: cap nhat trang thai KeyBinding toan cuc, y het khi GLFW bao
				// "phim X vua duoc nhan/tha". Cach nay hoat dong voi cac mod dung
				// KeyBinding.wasPressed() / isPressed() (kiem tra moi tick) - day la
				// cach pho bien nhat cho cac phim tat QoL trong game.
				KeyBinding.setKeyPressed(KEY_X, true);

				// Cach 2: goi truc tiep keyPressed cua chinh man hinh dang mo.
				// Hoat dong voi cac mod bat phim ngay trong Screen#keyPressed
				// (thuong qua Mixin vao HandledScreen/MerchantScreen).
				screen.keyPressed(GLFW.GLFW_KEY_X, 0, 0);

				// Tha phim ra ngay sau do, mo phong dung mot lan bam-tha hoan chinh.
				KeyBinding.setKeyPressed(KEY_X, false);

				AutoXMod.LOGGER.info("[AutoX] Da gia lap bam va tha phim X khi mo GUI giao dich.");
			});
		});
	}

	public static void setEnabled(boolean value) {
		enabled = value;
	}

	public static boolean isEnabled() {
		return enabled;
	}
}

