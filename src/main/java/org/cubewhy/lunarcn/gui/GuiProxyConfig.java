package org.cubewhy.lunarcn.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.cubewhy.lunarcn.ProxyManager;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.net.Proxy;

public class GuiProxyConfig extends GuiScreen {
    private final static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private final GuiScreen base;
    private GuiTextField addressInput;

    public GuiProxyConfig(GuiScreen base) {
        this.base = base;
    }

    public GuiProxyConfig() {
        this.base = mc.currentScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        addressInput = new GuiTextField(0, fontRenderer, width / 2 - 100, 60, 200, 20);
        addressInput.setText(ProxyManager.getInstance().getProxyAddress());
        addressInput.setMaxStringLength(100);
        addressInput.setFocused(true);
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, getStateText())); // proxyState
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 120, getProxyTypeText())); // proxyType
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 144, "Back"));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawCenteredString(fontRenderer, "ProxyManager", width / 2, 35, new Color(255, 255, 255).getRGB());
        addressInput.drawTextBox();

        if (addressInput.getText().isEmpty() && !addressInput.isFocused()) {
            drawString(fontRenderer, "Type proxy address...", addressInput.xPosition, addressInput.yPosition + 6, new Color(255, 255, 255).getRGB());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: // 返回
                ProxyManager.getInstance().setAddress(addressInput.getText());
                mc.displayGuiScreen(this.base);
                break;
            case 1: // 切换状态
                ProxyManager.getInstance().state = !ProxyManager.getInstance().state;
                button.displayString = getStateText();
                break;
            case 2: // 切换代理模式
                ProxyManager.getInstance().proxyType = ProxyManager.getInstance().proxyType == Proxy.Type.HTTP ? Proxy.Type.SOCKS : Proxy.Type.HTTP;
                button.displayString = getProxyTypeText();
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            ProxyManager.getInstance().setAddress(addressInput.getText());
            mc.displayGuiScreen(this.base);
            return;
        }

        if (addressInput.isFocused()) {
            addressInput.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    private String getStateText() {
        return "State: " + (ProxyManager.getInstance().state ? "On" : "Off");
    }

    private String getProxyTypeText() {
        return "Type: " + (ProxyManager.getInstance().proxyType == Proxy.Type.HTTP ? "Http" : "Socks");
    }
}
