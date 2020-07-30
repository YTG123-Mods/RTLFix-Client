package com.ytg123.rtlfixclient.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends Screen {
    @Shadow private SelectionManager selectionManager;

    protected SignEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "charTyped(CI)Z", at = @At(value = "RETURN", target = "Lnet/minecraft/client/util/SelectionManager;insert(C)Z"))
    public void afterInsert(char chr, int code, CallbackInfoReturnable<Boolean> cir) {
        if (isRTL(chr) || (((SelectionManagerAccessor)((Object)selectionManager)).getStringGetter().get().length() > 0 && isRTL(((SelectionManagerAccessor)((Object)selectionManager)).getStringGetter().get().charAt(((SelectionManagerAccessor)((Object)selectionManager)).getStringGetter().get().length() - 1)) && Character.toString(chr).equals(" "))) {
            selectionManager.moveCursor(-1, false);
        }
    }

    private boolean isRTL(char chr) {
        return (Character.UnicodeBlock.of(chr).equals(Character.UnicodeBlock.ARABIC) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_B) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_A) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.ARABIC_SUPPLEMENT) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.ARABIC_EXTENDED_A) || Character.UnicodeBlock.of(chr)
                .equals(Character.UnicodeBlock.HEBREW));
    }
}
