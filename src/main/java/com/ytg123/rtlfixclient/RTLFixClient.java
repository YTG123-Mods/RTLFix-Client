package com.ytg123.rtlfixclient;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RTLFixClient implements ModInitializer {
    public static final String MOD_ID = "rtlfix-client";
    public static final String MOD_NAME = "RTLFix Client";

    public static final Logger logger = LogManager.getLogger();

    public static final Identifier clientModEnabledQuestionPacketID = new Identifier("rtlfix", "isclientmodenabled");
    public static final Identifier clientModEnabledAnswerPacketID = new Identifier("rtlfix", "clientmodenabled");

    @Override public void onInitialize() {
        ClientSidePacketRegistry.INSTANCE.register(clientModEnabledQuestionPacketID, (packetContext, attachedData) -> {
            log(Level.INFO, "Server Asked Question");
            packetContext.getTaskQueue().execute(() -> {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true);
                ClientSidePacketRegistry.INSTANCE.sendToServer(clientModEnabledAnswerPacketID, passedData);
                log(Level.INFO, "Sent Answer");
            });
        });
    }

    public static void log(Level level, String message) {
        logger.log(level, "[" + MOD_NAME + "] " + message);
    }
}
