package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum =  new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldmap01.txt"); //to load different map, add txt file to 'res/maps/' and update arg
    }

    public void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass-tile.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall-tile.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water-tile.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree-tile.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand-tile.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt-tile.png"));

            //add additional tile textures here and increase tile array length (line 22) as needed

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int worldCol = 0, row = 0;

            while(worldCol < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while(worldCol < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[worldCol]);

                    mapTileNum[worldCol][row] = num;
                    worldCol++;
                }
                if (worldCol == gp.maxWorldCol) {
                    worldCol = 0;
                    row++;
                }
            }
            br.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0, worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize; // x coordinate of tile on world map
            int worldY = worldRow * gp.tileSize; // y coordinate of tile on world map

            /*
            gp.player.world X/Y reference the players location on the MAP.
            gp.player.screen X/Y reference players location on the SCREEN.
            in this case, the player location is fixed at x=360 y=264 (middle of the window)

            local screenX and screenY variables reference where on the screen we must render the tile

            by subtracting the players world location from the tiles world location, we can determine the tile's
            offset from the player. from there we add the player's x/y screen coordinates in order to place that tile
            correctly relative to the PLAYER.

            for example:

            if the tile is 100px to the right of the player on the world map, where is that on the screen?
            player.screenX(360) + 100. now the tile is placed correctly in relation to the player.

            we add the player screen location to offset the placement of the tile. otherwise the tile would be
            placed relative to (0,0).

            */
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
