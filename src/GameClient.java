import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends PApplet {
    private static final int ROCK = 0;
    private static final int SCISSORS = 1;
    private static final int PAPER = 2;
    private List<Button> buttons;
    private Client client;
    private byte[] bytes;
    private int len;
    private boolean gamePlaying = true;
    private KeyEventManager keyEventManager;
    private String playResult;
    private String[] strings = new String[10];
    private int serverResult;
    private int clientResult;

    {
        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void settings() {
        size(960, 640);
        loadImage();
    }

    public void setup() {
        bytes = new byte[128];
        buttons = new ArrayList<>();
        buttons.add(new Button(this, 250, 500, 100, 100, ROCK));
        buttons.add(new Button(this, 400, 500, 100, 100, SCISSORS));
        buttons.add(new Button(this, 550, 500, 100, 100, PAPER));
        keyEventManager = new KeyEventManager();
        keyEventManager.addPressListener(32, (isOnPress, duration) -> {

        });
        keyEventManager.addReleaseListener(32, duration -> {
            gamePlaying = true;
            for (Button btn : buttons) {
                btn.setIsClicked();
            }
        });
    }

    public void draw() {
        background(255);
        keyEventManager.update();

        fill(0, 70, 80);
        textSize(50);
        text("server : ", 100, 100);
        text("player : ", 100, 300);

        for (Button btn : buttons) {
            btn.render();
            if (btn.getIsClicked()) {
                try {
                    if (gamePlaying)
                        client.getOs().write(btn.getButtonName().getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (gamePlaying) {
                        len = client.getIs().read(bytes);
                        if (len == -1)
                            break;
                        playResult = new String(bytes, 0, len);
                        strings = playResult.split(",");

                        clientResult = setImageState(btn.getButtonName());
                        serverResult = setImageState(strings[1]);
                        System.out.println(strings[0]);
                    }
                    image(SpriteManager.getImage(clientResult, 0), 350, 250, 100, 100);
                    image(SpriteManager.getImage(serverResult, 0), 350, 50, 100, 100);
                    fill(255, 0, 0);
                    textSize(50);
                    text(strings[0], 600, 200);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                gamePlaying = false;
            }
        }


    }

    public void keyPressed() {
        keyEventManager.setPress(keyCode);
    }

    public void keyReleased() {
        keyEventManager.setRelease(keyCode);
    }

    public void mouseClicked() {
        for (Button btn : buttons) {
            if (!btn.getIsClicked())
                btn.mouseClicked(mouseX, mouseY);
        }

    }

    public void loadImage() {
        SpriteManager.loadImage(this, ROCK, "./data/rock.jpg", 0, 0, 240, 220);
        SpriteManager.loadImage(this, PAPER, "./data/rock.jpg", 0, 1, 220, 220);
        SpriteManager.loadImage(this, SCISSORS, "./data/rock.jpg", 0, 2, 215, 220);
    }

    public int setImageState(String person) {
        if (person.equals("가위")) return SCISSORS;
        else if (person.equals("바위")) return ROCK;
        else return PAPER;
    }
}
