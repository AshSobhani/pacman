package sprites;



import models.GameManager;
import models.Maze;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public class Ghost extends Rectangle implements Runnable {

    String direction;
    GameManager gameManager;
    Maze maze;
    AnimationTimer animation;
    int timesWalked;
    private double xPos;
    private double yPos;

    /**
     *
     * @param x Used to set X coordinate
     * @param y Used to set Y coordinate
     * @param ghostPic I use this to give my Pacman a better look, I have currently implemented a GiF which this ststem
     * @param maze The Maze the ghost is on
     * @param gameManager Just carrying through a game parameter
     */
    public Ghost(double x, double y, Image ghostPic, Maze maze, GameManager gameManager) {

        xPos = x;
        yPos = y;
        this.setX(x);
        this.setY(y);
        this.maze = maze;
        this.gameManager = gameManager;
        this.setHeight(50);
        this.setWidth(47.5);
        this.setFill(new ImagePattern(ghostPic, 0, 0, 1, 1, true));
        this.timesWalked = 0;
        this.direction = "down";
        this.createAnimation();
    }

    /**
     *
     * @param exclude1 Used to define things the ghosts categorically shouldn't do
     * @param exclude2 Used to define things the ghosts categorically shouldn't do
     * @return Return a String which gives a random direction
     */
    private String getRandomDirection(String exclude1, String exclude2) {
        String[] directions = {"left", "right", "up", "down"};
        int rnd = new Random().nextInt(directions.length);
        while (directions[rnd].equals(exclude1) || directions[rnd].equals(exclude2)) {
            rnd = new Random().nextInt(directions.length);
        }
        return directions[rnd];
    }

    /**
     * Gets the animation for the ghost
     * @return returns an animation timer
     */
    public AnimationTimer getAnimation() {
        return animation;
    }

    /**
     * When moving the Ghost will be looking for paths to take (This can be optimised)
     * @param direction This is used to keep the Ghosts on track
     */
    private void checkIftheresPathToGo(String direction) {
        double rightEdge, leftEdge, topEdge, bottomEdge;
        switch (direction) {
            case "down":
                leftEdge = getX() - 10;
                bottomEdge = getY() + getHeight() + 15;
                rightEdge = getX() + getWidth() + 10;
                if (!maze.hasObstacle(leftEdge, rightEdge, bottomEdge - 1, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "up":
                leftEdge = getX() - 10;
                rightEdge = getX() + getWidth() + 10;
                topEdge = getY() - 15;
                if (!maze.hasObstacle(leftEdge, rightEdge, topEdge - 1, topEdge)) {
                    this.direction = direction;
                }
                break;
            case "left":
                leftEdge = getX() - 15;
                bottomEdge = getY() + getHeight() + 10;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(leftEdge - 1, leftEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                    this.setScaleX(-1);
                }
                break;
            case "right":
                bottomEdge = getY() + getHeight() + 10;
                rightEdge = getX() + getWidth() + 15;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(rightEdge - 1, rightEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                    this.setScaleX(1);
                }
                break;
        }
    }

    /**
     *
     * @param whereToGo Where the ghost is going
     * @param whereToChangeTo Which direction the ghost will go when he cant keep moving
     * @param leftEdge Needed for wben the edge of the ghost is clipping onto the obstacles
     * @param topEdge Needed for wben the edge of the ghost is clipping onto the obstacles
     * @param rightEdge Needed for wben the edge of the ghost is clipping onto the obstacles
     * @param bottomEdge Needed for wben the edge of the ghost is clipping onto the obstacles
     * @param padding Padding is used to keep the Ghost from getting to close to the obstacles and glitching out
     */
    private void moveUntilYouCant(String whereToGo, String whereToChangeTo, double leftEdge, double topEdge, double rightEdge, double bottomEdge, double padding) {
        double step = 5;
        switch (whereToGo) {
            case "left":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setX(leftEdge - step);
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setX(getX() + 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "right":
                if (!maze.isTouching(rightEdge, topEdge, padding)) {
                    setX(leftEdge + step);
                } else {
                    while (maze.isTouching(getX() + getWidth(), getY(), padding)) {
                        setX(getX() - 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "up":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setY(topEdge - step);
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setY(getY() + 1);
                    }
                    direction = "left";
                }
                break;
            case "down":
                if (!maze.isTouching(leftEdge, bottomEdge, padding)) {
                    setY(topEdge + step);
                } else {
                    while (maze.isTouching(getX(), getY() + getHeight(), padding)) {
                        setY(getY() - 1);
                    }
                    direction = "right";
                }
                break;
        }

    }

    /**
     * Creates an animation of the ghost
     * I've implemented the portal using the x, y coordinets and setting boundries that will teleport the user if he hits them
     * The padding and the walkAtLeast try to optimise the ghosts moving accuracy
     */
    public void createAnimation() {

        this.animation = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gameManager.checkGhostCoalition();
                double leftEdge = getX();
                double topEdge = getY();
                double rightEdge = getX() + getWidth();
                double bottomEdge = getY() + getHeight();
                double padding = 11.5;
                timesWalked++;
                int walkAtLeast = 3;

                if (getX() < -5) {
                    setX(1230);
                }
                if (getX() > 1230) {
                    setX(-5);
                }

                switch (direction) {
                    case "left":
                        moveUntilYouCant("left", "down", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("left", "right"));
                            timesWalked = 0;
                        }
                        break;
                    case "right":
                        moveUntilYouCant("right", "up", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("left", "right"));
                             timesWalked = 0;
                        }
                        break;
                    case "up":
                        moveUntilYouCant("up", "left", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                    case "down":
                        moveUntilYouCant("down", "right", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                }
            }
        };
    }

    /**
     * I made this little commmand to placeGhosts anywhere on the map
     * Similar to my Pacman place method
     */
    public void placeGhosts() {
        this.setX(xPos);
        this.setY(yPos);
    }

    @Override
    public void run() {
        this.animation.start();
    }
}
