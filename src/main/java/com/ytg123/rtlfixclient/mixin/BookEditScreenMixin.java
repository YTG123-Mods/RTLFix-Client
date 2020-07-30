package com.ytg123.rtlfixclient.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BookEditScreen.class)
public abstract class BookEditScreenMixin extends Screen {
    @Shadow protected abstract void moveCursorToBottom();

    @Shadow @Final private SelectionManager field_24269;

    @Shadow protected abstract BookEditScreen.PageContent getPageContent();

    protected BookEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "charTyped(CI)Z", at = @At(value = "RETURN", target = "Lnet/minecraft/client/util/SelectionManager;insert(Ljava/lang/String;)V"))
    public void beforeWrite(char chr, int keyCode, CallbackInfoReturnable<Boolean> cir) {
        if (isRTL(chr) || (isRTL(((BookEditScreenPageContentMixinAccessor)((Object)getPageContent())).getPageContent().charAt(((BookEditScreenPageContentMixinAccessor)((Object)getPageContent())).getPageContent().length()-1)) && ((BookEditScreenPageContentMixinAccessor)((Object)getPageContent())).getPageContent().length() > 0) && Character.toString(chr).equals(" ")) {
            field_24269.moveCursor(-1, false);
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
