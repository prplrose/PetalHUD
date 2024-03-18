package com.github.prplrose.petalhud.settings.screen.widget;

import com.github.prplrose.petalhud.Color;
import com.github.prplrose.petalhud.PetalHUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class ColorWidget extends ClickableWidget {

    Color color;
    boolean ctrlPressed = false;
    boolean lockSnapped = false;
    double mouseLockX;
    double mouseLockY;
    boolean editable = false;
    String input = "";
    int selectionStart = -1;
    int selectionEnd = -1;
    int cursorIndex = 0;
    MinecraftClient client = MinecraftClient.getInstance();
    TextRenderer textRenderer = client.textRenderer;

    public ColorWidget(int x, int y, int width, int height, Text message, Color color) {
        super(x, y, width, height, message);
        this.color = color.opaque();
        this.setInput(color.toString());
    }

    public static Builder builder(Text message) {
        return new Builder(message);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_CONTROL) {
            this.ctrlPressed = true;
        }

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            String input = getInput();
            int length = getInput().length();
            if (length > 0)
                setInput(getInput().substring(0, length - 1));
        }

        if (Screen.isSelectAll(keyCode)) {
            setSelectionStart(0);
            setSelectionEnd(this.input.length());
            setCursorIndex(0);
            return true;
        }
        if (Screen.isCopy(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.color.toString());
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            setInput(MinecraftClient.getInstance().keyboard.getClipboard());
            return true;
        }
        if (Screen.isCut(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.color.toString());
            this.setInput("");
            return true;
        }
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_CONTROL) {
            this.ctrlPressed = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.mouseLockX = mouseX * ((double) this.client.getWindow().getWidth()) / ((double) this.client.getWindow().getScaledWidth());
        this.mouseLockY = mouseY * ((double) this.client.getWindow().getHeight()) / ((double) this.client.getWindow().getScaledHeight());
        setEditable(true);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (lockSnapped) {
            this.lockSnapped = false;
            return;
        }
        Double sensitivity = this.client.options.getMouseSensitivity().getValue();
        updateColor(deltaX * sensitivity, deltaY * sensitivity);

        InputUtil.setCursorParameters(this.client.getWindow().getHandle(), InputUtil.GLFW_CURSOR_NORMAL, this.mouseLockX, this.mouseLockY);
        this.lockSnapped = true;

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    void updateColor(double deltaX, double deltaY) {

        final double stepMultiplier = 0.005;
        deltaX *= stepMultiplier;
        deltaY *= stepMultiplier;

        float[] hsb = this.color.hsbFA();
        if (this.ctrlPressed) {
            hsb[1] = (float) MathHelper.clamp(hsb[1] + deltaX, 0, 1);
            hsb[2] = (float) MathHelper.clamp(hsb[2] + deltaY, 0, 1);
        } else {
            hsb[0] = (float) MathHelper.floorMod(hsb[0] + deltaX, 1.0);
        }
        setInput(Color.fromHSB(hsb).toString());
        //setColor(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));

    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = x1 + this.getWidth();
        int y2 = y1 + this.getHeight();
        context.fill(x1, y1, x2, y2, this.getColor().rgb());

        if (!isFocused()) {
            this.drawMessage(context, this.textRenderer);
            return;
        }

        context.drawCenteredTextWithShadow(textRenderer, Text.literal(getInput()), this.getX() + this.width / 2, this.getY() + (this.getHeight() - textRenderer.fontHeight) / 2 + 1, 0xffffffff);

    }

    void drawMessage(DrawContext context, TextRenderer textRenderer) {
        this.drawScrollableText(context, textRenderer, 2, 0xffffffff);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if ((chr >= '0' && chr <= '9') || (chr >= 'a' && chr <= 'f') || (chr >= 'A' && chr <= 'F') || chr == '#' || chr == '-') {
            if (isFocused()) {
                this.write(Character.toString(chr));
            }
            return true;
        }
        return false;
    }

    void write(String string) {
        setInput(this.getInput() + string);
    }

    void clear() {
        this.setInput("");
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
        Color newColor;
        if (input.length() == 0) {
            return;
        }

        try {
            newColor = Color.fromString(input);
            if (newColor != null)
                setColor(newColor.opaque());
        } catch (Exception e) {
            PetalHUD.LOGGER.error("Exception: " + e.getMessage() + " at " + Arrays.stream(e.getStackTrace()).findFirst());
        }
    }

    public int getSelectionStart() {
        return selectionStart;
    }

    public void setSelectionStart(int selectionStart) {
        this.selectionStart = selectionStart;
    }

    public int getSelectionEnd() {
        return selectionEnd;
    }

    public void setSelectionEnd(int selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void setCursorIndex(int cursorIndex) {
        this.cursorIndex = cursorIndex;
    }

    public static class Builder {

        private final Text message;
        private Color color = new Color(0x7f7f7f);
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message) {
            this.message = message;
        }

        public ColorWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public ColorWidget.Builder width(int width) {
            this.width = width;
            return this;
        }

        public ColorWidget.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public ColorWidget.Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public ColorWidget.Builder color(Color color) {
            this.color = color;
            return this;
        }

        public ColorWidget build() {
            return new ColorWidget(this.x, this.y, this.width, this.height, this.message, this.color.opaque());
        }
    }
}
