package com.ytg123.rtlfixclient.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SelectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(SelectionManager.class)
public interface SelectionManagerAccessor {
    @Accessor
    Supplier<String> getStringGetter();
}
