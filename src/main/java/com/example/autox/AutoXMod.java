package com.example.autox;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoXMod implements ModInitializer {
	public static final String MOD_ID = "autox";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[AutoX] Mod da duoc nap.");
	}
}

