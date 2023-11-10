package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage () {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i<gp.maxWorldRow; i++) {
                String line = br.readLine();
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    String[] numbers = line.split(" ");
                    mapTileNum[i][j] = Integer.parseInt(numbers[j]);
                }
            }

            br.close();

        }catch(Exception ignored) {

        }
     }

    public void draw(Graphics2D g2) {

        for (int i = 0; i<gp.maxWorldRow; i++){
            for (int j = 0; j<gp.maxWorldCol; j++){
                int tileNum = mapTileNum[i][j];
                int screenX = gp.tileSize*j - gp.player.worldX + gp.player.screenX;
                int screenY = gp.tileSize*i - gp.player.worldY + gp.player.screenY;
                if (screenX > -gp.tileSize && screenX < gp.screenWidth && screenY > -gp.tileSize && screenY < gp.screenHeight) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
