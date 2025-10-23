package Level;

import java.awt.Color;
import java.util.ArrayList;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Mouse;
import GameObject.GameObject;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Players.Alex;
import Utils.Direction;
import GameObject.PlayerBullet;

import GameObject.Bullet;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected int interactionRange = 1;
    protected Direction currentWalkingXDirection;
    protected Direction currentWalkingYDirection;
    protected Direction lastWalkingXDirection;
    protected Direction lastWalkingYDirection;

    // values used to handle player movement
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected Direction lastMovementDirection;

    private static final int PLAYER_BULLET_DAMAGE = 1;

    // health system
    protected int currentHealth = 6;  // Starting health (3 hearts)
    protected int maxHealth = 6;      // Max health (3 hearts)

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();

    protected Key Dodge = Key.SPACE;
    protected Key MOVE_LEFT_KEY = Key.A;
    protected Key MOVE_RIGHT_KEY = Key.D;
    protected Key MOVE_UP_KEY = Key.W;
    protected Key MOVE_DOWN_KEY = Key.S;

    protected Key INTERACT_KEY = Key.E;

    protected boolean isLocked = false;

    // Dodge system variables
    private boolean isDodging = false;
    private long dodgeStartTime = 0;
    private long lastDodgeTime = 0;
    

    private static final long DODGE_DURATION = 300; // milliseconds (0.3s)
    private static final long DODGE_COOLDOWN = 5000; // milliseconds (1s)
    private static final float DODGE_SPEED = 3.0f; // speed multiplier during dodge

    // store direction at start of dodge
    private float dodgeDirX = 0;
    private float dodgeDirY = 0;

    private double dodgeSpeed = 8.0;
    private double dodgeVelX = 0;
    private double dodgeVelY = 0;

    // Track last facing direction
    private double lastDirectionX = 1;
    private double lastDirectionY = 0;

    public boolean isDodging() {
    return isDodging;
    }

    protected Mouse mouse;

    // Shooting
    private static final float STEP_DT = 1f / 60f;
    private static final int FIRE_INTERVAL = 60;
    private static final float MUZZLE_OFFSET = 10f;
    private static final int BURST_COUNT = 1;
    private static final int BULLET_SIZE = 6;

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private int fireCooldown = 0;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        this.affectedByTriggers = true;
    }

    public void update() {
        double speed = 2.3;
        double dx = 0;
        double dy = 0;
        long currentTime = System.currentTimeMillis();
        
        
        if (isDodging && currentTime - dodgeStartTime > DODGE_DURATION) {
            isDodging = false;
        }
        // --- Handle dodge movement ---
        if (isDodging) {
            x += dodgeVelX;
            y += dodgeVelY;
            return; // skip normal input while dodging
        }
         if (Keyboard.isKeyDown(MOVE_UP_KEY)) dy -= 2.3;
        if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) dy += 2.3;
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) dx -= 2.3;
        if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) dx += 2.3;

        if (dx != 0 && dy != 0) {
        dx *= 0.7071;
        dy *= 0.7071;
        }

        if (!isLocked) {
            // Store movement for collision-based movement
            moveAmountX = (float)dx;
            moveAmountY = (float)dy;

            // if player is currently playing through level (has not won or lost)
            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
        }

        // Shooting: attempt to fire
        updateFiringAndBullets();

        handlePlayerAnimation();

        updateLockedKeys();

        super.update();
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case DODGING:
                startDodge();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        // if a walk key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            playerState = PlayerState.WALKING;
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
            currentWalkingXDirection = Direction.LEFT;
            lastWalkingXDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
            currentWalkingXDirection = Direction.RIGHT;
            lastWalkingXDirection = Direction.RIGHT;
        }
        else {
            currentWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyDown(MOVE_UP_KEY)) {
            moveAmountY -= walkSpeed;
            currentWalkingYDirection = Direction.UP;
            lastWalkingYDirection = Direction.UP;
        }
        else if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            moveAmountY += walkSpeed;
            currentWalkingYDirection = Direction.DOWN;
            lastWalkingYDirection = Direction.DOWN;
        }
        else {
            currentWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingXDirection == Direction.RIGHT || currentWalkingXDirection == Direction.LEFT) && currentWalkingYDirection == Direction.NONE) {
            lastWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingYDirection == Direction.UP || currentWalkingYDirection == Direction.DOWN) && currentWalkingXDirection == Direction.NONE) {
            lastWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(MOVE_UP_KEY) && Keyboard.isKeyUp(MOVE_DOWN_KEY)) {
            playerState = PlayerState.STANDING;
        }
        if (isDodging) {
        // Move faster in dodge direction
        x += dodgeDirX * DODGE_SPEED;
        y += dodgeDirY * DODGE_SPEED;
        return; // skip normal movement while dodging
        }
        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
        startDodge();
        keyLocker.lockKey(Key.SPACE);
        }

        if (Keyboard.isKeyUp(Key.SPACE)) {
        keyLocker.unlockKey(Key.SPACE);
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(INTERACT_KEY) && !isLocked) {
            keyLocker.unlockKey(INTERACT_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        if (playerState == PlayerState.STANDING) {
            // sets animation to a STAND animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
        }
        else if (playerState == PlayerState.WALKING) {
            // sets animation to a WALK animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        }
        else if (playerState == PlayerState.DODGING){
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "DODGE_RIGHT" : "DODGE_LEFT";
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Rectangle getInteractionRange() {
        return new Rectangle(
                getBounds().getX1() - interactionRange,
                getBounds().getY1() - interactionRange,
                getBounds().getWidth() + (interactionRange * 2),
                getBounds().getHeight() + (interactionRange * 2));
    }

    public Key getInteractKey() { return INTERACT_KEY; }
    public Direction getCurrentWalkingXDirection() { return currentWalkingXDirection; }
    public Direction getCurrentWalkingYDirection() { return currentWalkingYDirection; }
    public Direction getLastWalkingXDirection() { return lastWalkingXDirection; }
    public Direction getLastWalkingYDirection() { return lastWalkingYDirection; }

    // Health system methods
    public int getHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
        if (this.currentHealth <= 0) {
            // Player died - you can add death logic here later
        }
    }

    public void takeDamage(int damage) {
        setHealth(currentHealth - damage);
    }

    public void heal(int amount) {
        setHealth(currentHealth + amount);
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public void lock() {
        isLocked = true;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    public void unlock() {
        isLocked = false;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    // Shooting

    protected boolean isFireHeld() {
    return mouse != null && mouse.isMouseDown();
}
    // Player center - same as EnemyBasic
    protected float[] getPlayerCenterWorld() {
        Rectangle pb = this.getBounds();
        float cx = pb.getX() + pb.getWidth() * 0.5f;
        float cy = pb.getY() + pb.getHeight() * 0.5f;
        return new float[] { cx, cy };
    }

    // Aim toward the mouse cursor, with fallback if mouse not injected yet
    protected float[] getAimTargetWorld() {
        if (mouse != null && map != null && map.getCamera() != null) {
            float wx = mouse.getMouseX() + map.getCamera().getX();
            float wy = mouse.getMouseY() + map.getCamera().getY();
            return new float[] { wx, wy };
        }
        // Fallback: aim in facing direction
        float[] c = getPlayerCenterWorld();
        final float OFF = 100f;
        float dx = 0f, dy = 0f;
        switch (this.facingDirection) {
            case RIGHT: dx =  OFF; break;
            case LEFT:  dx = -OFF; break;
            case UP:    dy = -OFF; break;
            case DOWN:  dy =  OFF; break;
            default:    dx =  OFF; break;
        }
        return new float[] { c[0] + dx, c[1] + dy };
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
    
    // Getter for bullets to allow Map class to access them
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    // Shooting
    private void updateFiringAndBullets() {
        if (!isLocked && isFireHeld() && fireCooldown <= 0) {
            float[] c = getPlayerCenterWorld();
            float[] t = getAimTargetWorld();

            float dx = t[0] - c[0];
            float dy = t[1] - c[1];
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < 1e-4f) dist = 1f;

            float nx = dx / dist;
            float ny = dy / dist;

            float bx = c[0] + nx * MUZZLE_OFFSET;
            float by = c[1] + ny * MUZZLE_OFFSET;

            for (int i = 0; i < BURST_COUNT; i++) {
                PlayerBullet pb = new PlayerBullet(1001, bx, by, nx, ny, PLAYER_BULLET_DAMAGE);
                if (map != null) {
                    pb.setMap(map);
                    map.addNPC(pb);  // Map owns lifecycle
                }
            }
            fireCooldown = FIRE_INTERVAL;
        } else {
            if (fireCooldown > 0) fireCooldown--;
        }
    }

    /*
    private boolean isOffMap(Bullet b) {
        if (map == null) return false;
        Rectangle rb = b.getBounds();
        float bx = rb.getX() + rb.getWidth() / 2f;
        float by = rb.getY() + rb.getHeight() / 2f;

        int w = map.getWidthPixels();
        int h = map.getHeightPixels();

        if (bx < -BULLET_SIZE || bx > (w + BULLET_SIZE)) return true;
        if (by < -BULLET_SIZE || by > (h + BULLET_SIZE)) return true;
        return false;
    }
    */

    // Drawing
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    protected void drawPlayerBullets(GraphicsHandler g) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
    }

    public void startDodge() {
        playerState = PlayerState.DODGING;
        long currentTime = System.currentTimeMillis();
    // Check cooldown
    if (currentTime >= DODGE_COOLDOWN && !isDodging) {
        dodgeDirX = 0;
        dodgeDirY = 0;
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)){
              moveAmountX -= 2.5;
        }
        if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
              moveAmountX += 2.5;
        }
        if (Keyboard.isKeyDown(MOVE_UP_KEY)){
              moveAmountY -= 2.5;
        }
        if (Keyboard.isKeyDown(MOVE_DOWN_KEY)){
              moveAmountY += 2.5;
        }

        if (dodgeDirX != 0 || dodgeDirY != 0) {
            isDodging = true;
            dodgeStartTime = currentTime;
            lastDodgeTime = currentTime;
            if (hasAnimationLooped == true){
            playerState = PlayerState.STANDING;
        }

        }
        // Handle dodge movement
        if (isDodging) {
            // Move faster in dodge direction
            x += dodgeDirX * DODGE_SPEED;
            y += dodgeDirY * DODGE_SPEED;
            return; 
        }
        
        if (hasAnimationLooped == true){
            playerState = PlayerState.STANDING;
        }
    }
}
        public void handlePlayerInput() {
        long currentTime = System.currentTimeMillis();

    
    if (isDodging && currentTime - dodgeStartTime >= DODGE_DURATION) {
        isDodging = false;
    }

    
    if (isDodging) {
        x += dodgeDirX * DODGE_SPEED;
        y += dodgeDirY * DODGE_SPEED;
        return; 
    }

    moveAmountX = 0;
    moveAmountY = 0;

    if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
        moveAmountX -= walkSpeed;
        currentWalkingXDirection = Direction.LEFT;
    } else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
        moveAmountX += walkSpeed;
        currentWalkingXDirection = Direction.RIGHT;
    }

    if (Keyboard.isKeyDown(MOVE_UP_KEY)) {
        moveAmountY -= walkSpeed;
        currentWalkingYDirection = Direction.UP;
    } else if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
        moveAmountY += walkSpeed;
        currentWalkingYDirection = Direction.DOWN;
    }

    // Apply movement
    x += moveAmountX;
    y += moveAmountY;

    
    if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
        startDodge();
        keyLocker.lockKey(Key.SPACE);
    }

    if (Keyboard.isKeyUp(Key.SPACE)) {
        keyLocker.unlockKey(Key.SPACE);
    }
}

    

    // Uncomment this to have game draw player's bounds to make it easier to visualize
    /* 
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    */
}