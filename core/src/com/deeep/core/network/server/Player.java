package com.deeep.core.network.server;

import com.deeep.core.network.mutual.ControlsKeyId;
import com.deeep.core.entity.abstraction.Manager;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/1/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    private PlayerControlHandler playerControlHandler;
    private float moveTimer = 0;
    private float speed = 0.2f;
    private boolean previousAction = false;
    private Connection connection;
    private Manager manager;

    public Player(Connection connection, Manager manager) {
        playerControlHandler = new PlayerControlHandler();
        this.connection = connection;
        this.manager = manager;
    }

    public void update(float deltaT) {
        if (playerControlHandler.isPressed()) {
            if (playerControlHandler.isKeyPressed(ControlsKeyId.UP)) {

            }
            if (playerControlHandler.isKeyPressed(ControlsKeyId.DOWN)) {

            }
            if (playerControlHandler.isKeyPressed(ControlsKeyId.LEFT)) {

            }
            if (playerControlHandler.isKeyPressed(ControlsKeyId.RIGHT)) {

            }
        }
        moveTimer += deltaT;
        if (moveTimer >= speed) {

            moveTimer -= speed;
        }
        if (!previousAction && playerControlHandler.isKeyPressed(ControlsKeyId.ACTION)) {

        }
        previousAction = playerControlHandler.isKeyPressed(ControlsKeyId.ACTION);
    }

    public PlayerControlHandler getPlayerControlHandler() {
        return playerControlHandler;
    }
}
