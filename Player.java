import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
    boolean facingLeft = false;

    Vec2 pos;
    Vec2 posLastFrame;

    float movementSpeed;

    int numberAnimationStates = 0;
    int displayedAnimationState = 0;
    int moveCounter = 0;

    // Tiles for movement animation
    protected ArrayList<BufferedImage> tilesWalk;

    BoundingBox bBox;

    Level l;

    Player(Level l) {
        this.pos = new Vec2(0, 0);
        this.posLastFrame = new Vec2(0, 0);
        this.movementSpeed = 7.5f;

        this.bBox = new BoundingBox(this.pos);

        this.l = l;
        tilesWalk = new ArrayList<BufferedImage>();
        try {
            // Tiles for movement animation
            BufferedImage imageWalk;
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk01.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk02.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk03.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk04.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk05.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk06.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk07.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk08.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk09.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk10.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk11.png"));
            tilesWalk.add(imageWalk);

        } catch (IOException e) {
            e.printStackTrace();
        }
        numberAnimationStates = tilesWalk.size();
    }

    public void move(int deltaX, int deltaY) {
        if (deltaX < 0) {
            facingLeft = true;
            pos.x = pos.x - movementSpeed;
            bBox.max.x = bBox.max.x - movementSpeed;
        } else if (deltaX > 0) {
            pos.x = pos.x + movementSpeed;
            bBox.max.x = bBox.max.x + movementSpeed;
            facingLeft = false;
        }

        if (deltaY < 0) {
            pos.y = pos.y - movementSpeed;
            bBox.max.y = bBox.max.y - movementSpeed;
        } else if (deltaY > 0) {
            pos.y = pos.y + movementSpeed;
            bBox.max.y = bBox.max.y + movementSpeed;
        }

        updateBBox();
        checkCollision();
        getNextImage();
    }

    public BufferedImage getPlayerImage() {
        BufferedImage b = getNextImage();
        if (facingLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-b.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            b = op.filter(b, null);
        }
        return b;
    }

    private BufferedImage getNextImage() {
        moveCounter++;
        if (moveCounter >= 3) {
            displayedAnimationState++;
            moveCounter = 0;
        }
        if (displayedAnimationState > numberAnimationStates - 1) {
            displayedAnimationState = 0;
        }
        return tilesWalk.get(displayedAnimationState);
    }

    public void updateBBox() {
        bBox.min.x = pos.x;
        bBox.max.x = pos.x + 72;
        bBox.min.y = pos.y;
        bBox.max.y = pos.y + 97;
        System.out.println("Playerbox: " + bBox.toString());
    }

    public void checkCollision() {
        for (Tile t : l.tileList) {
            if (bBox.intersect(t.bBox)) {
                Vec2 overlapVec = bBox.overlapSize(t.bBox);
                if (overlapVec.y > overlapVec.x) {
                    if (bBox.max.x > t.bBox.max.x) { // Kollision von rechts
                        System.out.println("Kollision  von rechts mit " + t.bBox.toString());
                    }
                    else { // Kollision von links
                        System.out.println("Kollision  von links mit " + t.bBox.toString());
                    }
                } else {
                    if (bBox.max.y > t.bBox.max.y) { // Kollision von unten
                        System.out.println("Kollision  von unten mit " + t.bBox.toString());;
                    } else { // Kollision von oben
                        System.out.println("Kollision  von oben mit " + t.bBox.toString());
                    }
                }
            }
        }
    }
}