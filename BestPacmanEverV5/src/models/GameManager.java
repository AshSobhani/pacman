package models;



import controllers.GameController;
import controllers.UserScoreController;
import controllers.SoundController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private SoundController pacSound;
    private UserScoreController scoreCon;
    private GameController game;

    /**
     * Constructor used to preset everything for the day
     * @param root A group of objects
     * @param map Map is where the directory for the map will be held
     */
    public GameManager(Group root, String map) {
        this.pacSound = new SoundController();
        this.game = new GameController();
        this.scoreCon = new UserScoreController();
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
     * It places the ghosts back to their orignal spawning position due to a placeGhost method
     */
    private void lifeLost() {
        pacSound.playPacDeath();
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : maze.getGhosts()) {
            ghost.getAnimation().stop();
            ghost.placeGhosts();
        }
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
     * Removes everything before ending the game to reduce unnecessary background work
     */
    private void endGame() {
        this.gameEnded = true;
        root.getChildren().remove(pacman);
        for (Ghost ghost : maze.getGhosts()) {
            root.getChildren().remove(ghost);
        }
        Text endGame = new Text("ESC - Restart  |  SPACE - Menu  |  ENTER - Add High Score");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Avenir Next Heavy", 30));
        endGame.setFill(Color.ROYALBLUE);

        scoreCon.getScore(score);
        root.getChildren().remove(this.scoreBoard.score);
        root.getChildren().remove(this.scoreBoard.lifes);
        root.getChildren().add(endGame);
    }

    /**
     * Restart the game
     * Upon ending the game the user can either Restart the game, go to main menu or save their score.
     * @param event This event looks out for any keys being pressed to trigger the method to run.
     */
    public void restartGameOrMenu(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            maze.getCookies().clear();
            maze.getGhosts().clear();
            this.drawBoard();
            this.pacman.placePacMan();
            this.lifes = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
        if (event.getCode() == KeyCode.SPACE && gameEnded) {
            try {
                root.getChildren().clear();
                maze.getCookies().clear();
                maze.getGhosts().clear();
                gameEnded = true;
                game.backToMain(event);
            } catch (Exception Failed) {
                System.out.print("Failed To Load Menu");
            };
        }
        if (event.getCode() == KeyCode.ENTER && gameEnded) {
            try {
                root.getChildren().clear();
                maze.getCookies().clear();
                maze.getGhosts().clear();
                gameEnded = true;
                GameController.newUserScore(event);
            } catch (Exception Failed) {
                System.out.print("Failed To User Input Screen");
            };
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     * pacSound is used to play audio, in this case its used for the level start music
     */
    public void drawBoard() {
        this.maze.CreateMaze(root, "BestPacmanEverV5/src/resources/maps/" + map);
        pacSound.playPacStart();

        root.getChildren().add(this.pacman);
        this.pacman.placePacMan();
        this.scoreBoard = new Score(root);
    }

    /**
     * Moves the pacman
     * Moves constantly until either a obstacle is hit or user changes PacMans direction
     * @param event this event just looks out for keys pressed to change the Pacmans animation
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
                break;
            case LEFT:
                this.leftPacmanAnimation.start();
                break;
            case UP:
                this.upPacmanAnimation.start();
                break;
            case DOWN:
                this.downPacmanAnimation.start();
                break;
        }
    }

    /**
     * Creates an animation of the movement.
     * @param direction By having direction we can unsure that Pacman rotates to face the way he is moving
     * @return The Pacmans X, Y is used to implement the portal feature, I did this by setting triggers which will move the sprite
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
                        pacman.setRotate(180);
                    }

                    break;
                case "right":
                    if (!maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() + step);
                        checkCookieCollection(pacman, "x");
                        checkGhostCoalition();
                        pacman.setRotate(0);
                    }
                    break;
                case "up":
                    if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
                        pacman.setCenterY(pacman.getCenterY() - step);
                        checkCookieCollection(pacman, "y");
                        checkGhostCoalition();
                        pacman.setRotate(270);
                    }
                    break;
                case "down":
                   if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
                       pacman.setCenterY(pacman.getCenterY() + step);
                       checkCookieCollection(pacman, "y");
                       checkGhostCoalition();
                       pacman.setRotate(90);
                   }
                   break;
            }
            }
        };
    }

    /**
     * Checks if the Pacman touches cookies.
     * @param pacman Parameter is necessary to detect if the Pacman eats a cookie
     * @param axis Used to check if Pacman is facing right
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
     * If he is touching a ghost it triggers the lifeLost method which will prompt Pacman to respawn with 1 life less
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
