package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;


        screenX = gp.screenWidth /2 - (gp.tileSize/2);
        screenY = gp.screenHeight /2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = gp.tileSize/6;
        solidArea.y = gp.tileSize/3;
        solidArea.width = gp.tileSize/3*2;
        solidArea.height = gp.tileSize/3*2;
        System.out.println(solidArea);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_0.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_2.png")));
            down0= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_0.png")));
            down1= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_2.png")));
            left0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_0.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_2.png")));
            right0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_0.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_2.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        } else {
            spriteCounter = 0;
            spriteNum = 1;
        }


        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> {
                        worldY -= speed;
                    }
                    case "down" -> {
                        worldY += speed;
                    }
                    case "left" -> {
                        worldX -= speed;
                    }
                    case "right" -> {
                        worldX += speed;
                    }
                }
            }
        }

        spriteCounter++;
        if (spriteCounter > 8) {
            spriteNum++;
            if (spriteNum > 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up" -> image = switch (spriteNum) {
                case 1, 3 -> up0;
                case 2 -> up1;
                case 4 -> up2;
                default -> null;
            };
            case "down" -> image = switch (spriteNum) {
                case 1, 3 -> down0;
                case 2 -> down1;
                case 4 -> down2;
                default -> null;
            };
            case "left" -> image = switch (spriteNum) {
                case 1, 3 -> left0;
                case 2 -> left1;
                case 4 -> left2;
                default -> null;
            };
            case "right" -> image = switch (spriteNum) {
                case 1, 3 -> right0;
                case 2 -> right1;
                case 4 -> right2;
                default -> null;
            };
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
