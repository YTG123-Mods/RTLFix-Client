package com.ytg123.rtlfixclient.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin extends AbstractButtonWidget {
    public TextFieldWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Shadow public abstract void moveCursor(int offset);

    @Shadow private String text;

    @Inject(method = "charTyped(CI)Z", at = @At(value = "RETURN", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;write(Ljava/lang/String;)V"))
    public void beforeWrite(char chr, int keyCode, CallbackInfoReturnable<Boolean> cir) {
        if (isRTL(chr) || (text.length() > 0 && isRTL(text.charAt(text.length()-1)) && Character.toString(chr).equals(" "))) {
            moveCursor(-1);
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