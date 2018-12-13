package controller;



import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sprites.Cookie;
import sprites.Ghost;
import sprites.Pacman;

public class GameManager {

    private Pacman pacman;
    private Group root;
    private AnimationTimer leftPacmanAnimation;
    private AnimationTimer rightPacmanAnimation;
    private AnimationTimer upPacmanAnimation;
    private AnimationTimer downPacmanAnimation;
    private Maze maze;
    private int lifes;
    private int score;
    private Score scoreBoard;
    private boolean gameEnded;
    private int cookiesEaten;
    private String map;

    /**
     * Constructor
     */
    GameManager(Group root, String map) {
        this.root = root;
        this.maze = new Maze(this);
        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS);
        this.leftPacmanAnimation = this.createAnimation("left");
        this.rightPacmanAnimation = this.createAnimation("right");
        this.upPacmanAnimation = this.createAnimation("up");
        this.downPacmanAnimation = this.createAnimation("down");
        this.lifes = 3;
        this.score = 0;
        this.cookiesEaten = 0;
        this.map = map;
    }

    /**
     * Set one life less
     */
    private void lifeLost() {
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : maze.getGhosts()) {
            ghost.getAnimation().stop();
        }
        this.pacman.placePacMan();
        this.pacman.placePacMan();
        lifes--;
        score -= 10;
        this.scoreBoard.lifes.setText("Lifes: " + this.lifes);
        this.scoreBoard.score.setText("Score: " + this.score);
        if (lifes == 0) {
            this.endGame();
        }
    }

    /**
     * Ends the game
     */
    private void endGame() {
        this.gameEnded = true;
        root.getChildren().remove(pacman);
        for (Ghost ghost : maze.getGhosts()) {
            root.getChildren().remove(ghost);
        }
        javafx.scene.text.Text endGame = new javafx.scene.text.Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Avenir Next Heavy", 40));
        endGame.setFill(Color.ROYALBLUE);
        root.getChildren().remove(this.scoreBoard.score);
        root.getChildren().remove(this.scoreBoard.lifes);
        root.getChildren().add(endGame);
    }

    /**
     * Restart the game
     * @param event
     */
    public void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            maze.getCookies().clear();
            maze.getGhosts().clear();
            this.drawBoard();
            this.pacman.placePacMan();
            this.pacman.placePacMan();
            this.lifes = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    public void drawBoard() {
        this.maze.CreateMaze(root, "BestPacmanEverV5/src/resources/maps/" + map);

        root.getChildren().add(this.pacman);
        this.pacman.placePacMan();
        this.scoreBoard = new Score(root);
    }

    /**
     * Moves the pacman
     * Moves constantly until either a obstacle is hit or user changes PacMans direction
     * @param event
     */
    public void movePacman(KeyEvent event) {
        for (Ghost ghost : maze.getGhosts()) {
            ghost.run();
        }
        this.downPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        switch(event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.start();
                pacman.setRotate(0);
                break;
            case LEFT:
                this.leftPacmanAnimation.start();
                pacman.setRotate(180);
                break;
            case UP:
                this.upPacmanAnimation.start();
                pacman.setRotate(270);
                break;
            case DOWN:
                this.downPacmanAnimation.start();
                pacman.setRotate(90);
                break;
        }
    }

    /**
     * Creates an animation of the movement.
     * @param direction
     * @return
     */
    private AnimationTimer createAnimation(String direction) {
        double step = 5;
        return new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if (pacman.getCenterX() < -5) {
                    pacman.setCenterX(1230);
                }
                if (pacman.getCenterX() > 1230) {
                    pacman.setCenterX(-5);
                }
            switch (direction) {
                case "left":
                    if (!maze.isTouching(pacman.getCenterX() - pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() - step);
                        checkCookieCollection(pacman, "x");
                        checkGhostCoalition();
                    }

                    break;
                case "right":
                    if (!maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() + step);
                        checkCookieCollection(pacman, "x");
                        checkGhostCoalition();
                    }
                    break;
                case "up":
                    if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
                        pacman.setCenterY(pacman.getCenterY() - step);
                        checkCookieCollection(pacman, "y");
                        checkGhostCoalition();
                    }
                    break;
                case "down":
                   if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
                       pacman.setCenterY(pacman.getCenterY() + step);
                       checkCookieCollection(pacman, "y");
                       checkGhostCoalition();
                   }
                   break;
            }
            }
        };
    }

    /**
     * Checks if the Pacman touches cookies.
     * @param pacman
     * @param axis
     */
    private void checkCookieCollection(Pacman pacman, String axis) {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Cookie cookie:maze.getCookies()) {
            double cookieCenterX = cookie.getCenterX();
            double cookieCenterY = cookie.getCenterY();
            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
            double cookieRightEdge = cookieCenterX + cookie.getRadius();
            double cookieTopEdge = cookieCenterY - cookie.getRadius();
            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
            if (axis.equals("x")) {
                // pacman goes right
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
            }
            this.scoreBoard.score.setFont(Font.font("Avenir Next Heavy", 30));
            this.scoreBoard.score.setText("Score: " + this.score);
            if (this.cookiesEaten == maze.getCookies().size()) {
                this.endGame();
            }
        }
    }

    /**
     * Checks if pacman is touching a ghost
     */
    public void checkGhostCoalition() {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Ghost ghost : maze.getGhosts()) {
            double ghostLeftEdge = ghost.getX();
            double ghostRightEdge = ghost.getX() + ghost.getWidth();
            double ghostTopEdge = ghost.getY();
            double ghostBottomEdge = ghost.getY() + ghost.getHeight();
            if ((pacmanLeftEdge <= ghostRightEdge && pacmanLeftEdge >= ghostLeftEdge) || (pacmanRightEdge >= ghostLeftEdge && pacmanRightEdge <= ghostRightEdge)) {
                if ((pacmanTopEdge <= ghostBottomEdge && pacmanTopEdge >= ghostTopEdge) || (pacmanBottomEdge >= ghostTopEdge && pacmanBottomEdge <= ghostBottomEdge)) {
                    lifeLost();
                }
            }
        }
    }

}
