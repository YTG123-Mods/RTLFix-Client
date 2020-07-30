package com.ytg123.rtlfixclient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RTLFixClient implements ModInitializer {
    public static final String MOD_ID = "rtlfix-client";
    public static final String MOD_NAME = "RTLFix Client";

    public static final Identifier clientModEnabledPacketId = new Identifier(MOD_ID, "clientmod");

    @Override public void onInitialize() {

    }
}
