package Level;

import java.awt.Color;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.GameObject;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Direction;

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

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
   protected Key MOVE_LEFT_KEY = Key.A;
protected Key MOVE_RIGHT_KEY = Key.D;
protected Key MOVE_UP_KEY = Key.W;
protected Key MOVE_DOWN_KEY = Key.S;
protected Key Dodge = Key.SPACE;

    protected Key INTERACT_KEY = Key.E;

    protected boolean isLocked = false;

    // Dodge system variables
    private boolean isDodging = false;
    private long dodgeStartTime = 0;
    private long lastDodgeTime = 0;
    private boolean invincible = false;

    private static final long DODGE_DURATION = 300; // milliseconds (0.3s)
    private static final long DODGE_COOLDOWN = 1000; // milliseconds (1s)
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

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        this.affectedByTriggers = true;
    }

    public void update() {
        double speed = 3.0;
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
         if (Keyboard.isKeyDown(MOVE_UP_KEY)) dy -= 3;
        if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) dy += 3;
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) dx -= 3;
        if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) dx += 3;

        if (dx != 0 && dy != 0) {
        dx *= 0.7071;
        dy *= 0.7071;
        }

       
        if (!isLocked) {
            moveAmountX = 0;
            moveAmountY = 0;

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

        handlePlayerAnimation();

        updateLockedKeys();

        // update player's animation
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

    // used by other files or scripts to force player to stand
    public void stand(Direction direction) {
        playerState = PlayerState.STANDING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    // used by other files or scripts to force player to walk
    public void walk(Direction direction, float speed) {
        playerState = PlayerState.WALKING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "WALK_LEFT";
        }
        if (direction == Direction.UP) {
            moveY(-speed);
        }
        else if (direction == Direction.DOWN) {
            moveY(speed);
        }
        else if (direction == Direction.LEFT) {
            moveX(-speed);
        }
        else if (direction == Direction.RIGHT) {
            moveX(speed);
        }
    }

    public void startDodge() {
        playerState = PlayerState.DODGING;
        long currentTime = System.currentTimeMillis();
    // Check cooldown
    if (currentTime - lastDodgeTime >= DODGE_COOLDOWN && !isDodging) {
        dodgeDirX = 0;
        dodgeDirY = 0;
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)){
              moveAmountX -= 1.5;
        }
        if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
              moveAmountX += 1.5;
        }
        if (Keyboard.isKeyDown(MOVE_UP_KEY)){
              moveAmountY -= 1.5;
        }
        if (Keyboard.isKeyDown(MOVE_DOWN_KEY)){
              moveAmountY += 1.5;
        }

        if (dodgeDirX != 0 || dodgeDirY != 0) {
            isDodging = true;
            invincible = true;
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
