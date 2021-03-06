package models;



import javafx.scene.Group;
import javafx.scene.image.Image;
import sprites.Cookie;
import sprites.Ghost;
import sprites.Pacman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Maze {

    public Set<Cookie> getCookies() {
        return cookies;
    }
    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    private Set<BarObstacle> obstacles;
    private Set<Cookie> cookies;
    private Set<Ghost> ghosts;
    private GameManager gameManager;

    private String[] ghostSprites = new String[]{"file:BestPacmanEverV5/src/resources/images/ghost1.gif", "file:BestPacmanEverV5/src/resources/images/ghost2.gif", "file:BestPacmanEverV5/src/resources/images/ghost3.gif", "file:BestPacmanEverV5/src/resources/images/ghost4.gif"};

    Maze(GameManager gameManager) {
        obstacles = new HashSet<>();
        cookies = new HashSet<>();
        ghosts = new HashSet<>();
        this.gameManager = gameManager;
    }

    /**
     * Checks if point is touching obstacles
     * @param x geting X for initialisation
     * @param y geting Y for initialisation
     * @param padding Padding is used to keep sprites at a safe distance to avoid bugs
     * @return return either True of False to signal if its Touching
     */
    public Boolean isTouching(double x, double y, double padding) {
        for (BarObstacle barObstacle:obstacles) {
            if (
                x >= barObstacle.getX() - padding &&
                x <= barObstacle.getX() + padding + barObstacle.getWidth() &&
                y >= barObstacle.getY() - padding &&
                y <= barObstacle.getY() + padding + barObstacle.getHeight())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * lets you know if there's an obstacle in the current coordinate
     * @param fromX Current X Value of the object
     * @param toX The Y position the object wants to go
     * @param fromY Current Y Value of the object
     * @param toY The Y position the object wants to go
     * @return Return whether or not its touching
     */
    public Boolean hasObstacle(double fromX,  double toX, double fromY, double toY) {
        boolean isTouching = false;
        for (double i = fromX; i < toX; i++) {
            for (double j = fromY; j < toY; j++) {
                if (this.isTouching(i, j, 0)) isTouching = true;
            }
        }
        return isTouching;
    }

    /**
     * Draws the maze
     * @param root This is the anchor for where everything will be located
     * @param pacMap This is the map I want to generate (Being fed in from the map selector in controller
     * I changed this method quite drastically by implementing a load by file method which is a lot easier to use and maintain
     */
    public void CreateMaze(Group root, String pacMap) {
            // Internal object - the reader for the input filer
            BufferedReader br;
            String line;
            int x, y = 0, i=0;

            try
            {
                br = new BufferedReader(new FileReader(pacMap));
                while ((line = br.readLine()) != null) {
                    for (x = 0; x < line.length(); x++) {
                        if (line.charAt(x) == '@') {
                            obstacles.add(new BarObstacle(x * BarObstacle.THICKNESS, y * BarObstacle.THICKNESS, "horizontal", 1));
                        }
                        if (line.charAt(x) == '*'){
                            cookies.add(new Cookie((x + 0.5) * BarObstacle.THICKNESS, (y + 0.5) * BarObstacle.THICKNESS));
                        }
                        if (line.charAt(x) == 'G'){
                            ghosts.add(new Ghost((x - 0.5) * BarObstacle.THICKNESS, (y - 0.5) * BarObstacle.THICKNESS, new Image(ghostSprites[i]), this, gameManager));
                            i++;
                        }
                        if (line.charAt(x) == 'P'){
                            Pacman.xPos = (x + 0.5) * BarObstacle.THICKNESS;
                            Pacman.yPos = (y + 0.5) * BarObstacle.THICKNESS;
                        }

                    }
                    y++;
                }
                br.close();
            }
            catch(Exception e )
            {
                return;
            }

            root.getChildren().addAll(obstacles);
            root.getChildren().addAll(cookies);
            root.getChildren().addAll(ghosts);

            return;
        }
}
