package com.ytg123.rtlfixclient.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(BookEditScreen.PageContent.class)
public interface BookEditScreenPageContentMixinAccessor {
    @Accessor String getPageContent();
}
