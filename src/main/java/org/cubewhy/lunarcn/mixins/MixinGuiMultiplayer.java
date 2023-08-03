package org.cubewhy.lunarcn.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.gui.GuiProxyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {
    @Inject(method = "initGui", at = @At("RETURN"))
    public void initGui(CallbackInfo ci) {
        String text = "ProxySettings";
        int textWidth = fontRendererObj.getStringWidth(text);
        this.buttonList.add(new GuiButton(114514, this.width - textWidth - 20, 10, textWidth + 10, fontRendererObj.FONT_HEIGHT + 10, text));
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void actionPerformed(GuiButton button, CallbackInfo ci) {
        switch (button.id) {
            case 114514:
                mc.displayGuiScreen(new GuiProxyConfig(mc.currentScreen));
                break;
        }
    }
}
