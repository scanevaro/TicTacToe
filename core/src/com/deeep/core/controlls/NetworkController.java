package com.deeep.core.controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.packets.ControlPacket;

import com.deeep.core.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/1/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkController {
    public static final float SEND_INTERVAL = 0.1f;
    private float timer = 0;
    private ClientLoop clientLoop;
    private NetController netController;

    public NetworkController(ClientLoop clientLoop) {
        this.clientLoop = clientLoop;
        netController = new NetController();
    }

    public void update(float deltaT) {
        if (timer >= SEND_INTERVAL) {
            netController.update();
            if (netController.isChanged())
                clientLoop.getClient().sendTcp(netController.getControlPacket());
            timer -= SEND_INTERVAL;
        }
        timer += deltaT;
    }

    public void draw(){

    }

    public class NetController {
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
        public static final int LEFT = 4;
        public static final int ACTION = 5;
        private boolean changed = false;
        private ControlPacket controlPacket;
        private ControlPacket previousControlPacket = new ControlPacket();

        public NetController() {
            controlPacket = new ControlPacket();
        }

        public void update() {
            previousControlPacket.pressedKeys.clear();
            previousControlPacket.pressedKeys.addAll(controlPacket.pressedKeys);
            controlPacket.pressedKeys.clear();
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                controlPacket.pressedKeys.add(UP);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                controlPacket.pressedKeys.add(DOWN);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                controlPacket.pressedKeys.add(LEFT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                controlPacket.pressedKeys.add(RIGHT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                controlPacket.pressedKeys.add(ACTION);
            }
            changed = false;
            if (!previousControlPacket.pressedKeys.containsAll(controlPacket.pressedKeys) || !controlPacket.pressedKeys.containsAll(previousControlPacket.pressedKeys)) {
                changed = true;
                Logger.getInstance().debug(this.getClass(),"changed packet!");
            }
        }

        public boolean isChanged() {
            return changed;
        }

        public ControlPacket getControlPacket() {
            return controlPacket;
        }
    }
}
