import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

public class Button {
    private float x;
    private float y;
    private float width;
    private float height;
    private PApplet pApplet;
    private int buttonState;
    private String buttonName;
    private boolean isClicked;

    public Button(PApplet pApplet, float x, float y, float width, float height, int buttonState) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pApplet = pApplet;
        this.buttonState = buttonState;
        if (buttonState == 0) {
            buttonName = "바위";
        } else if (buttonState == 1) {
            buttonName = "가위";
        } else if (buttonState == 2) {
            buttonName = "보";
        }
    }

    public void render() {
        pApplet.image(SpriteManager.getImage(buttonState, 0), x, y, width, height);
    }

    public String getButtonName() {
        return buttonName;
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsClicked() {
        this.isClicked = false;
    }


    public void mouseClicked(int mouseX, int mouseY) {
        if (isCollision(mouseX, mouseY)) {
            isClicked = true;
        }
    }


    public boolean isCollision(int mouseX, int mouseY) {
        return mouseX > x
                && mouseX < x + width
                && mouseY > y
                && mouseY < y + height;
    }
}
