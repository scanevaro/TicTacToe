package com.deeep.core.controlls;

import com.badlogic.gdx.Gdx;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.packets.TouchPacket;
import com.deeep.core.system.Constants;
import com.deeep.core.util.Logger;
import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;

/**
 * Created by scanevaro on 24/09/2014.
 */
public class NetworkTouchController {
    public static final float SEND_INTERVAL = 0.1f;
    private float timer = 0;
    private ClientLoop clientLoop;
    private NetGestures netController;

    public NetworkTouchController(ClientLoop clientLoop) {
        this.clientLoop = clientLoop;
        netController = new NetGestures();
    }

    public void update(float deltaT) {
        if (timer >= SEND_INTERVAL) {
            netController.update();
            if (netController.isChanged()) {
                clientLoop.getClient().sendTcp(netController.getTouchPacket());
            }
            timer -= SEND_INTERVAL;
        }
        timer += deltaT;
    }

    public class NetGestures {
        private boolean changed = false;
        private TouchPacket touchPacket;
        private TouchPacket previousTouchPacket = new TouchPacket();

        public NetGestures() {
            touchPacket = new TouchPacket();
        }

        public void update() {
            previousTouchPacket.touch = touchPacket.touch;
            previousTouchPacket.x = touchPacket.x;
            previousTouchPacket.y = touchPacket.y;

            if (Gdx.input.isTouched()) {
                touchPacket.touch = true;
                touchPacket.y = Gdx.input.getY() * Constants.VIRTUAL_HEIGHT / Gdx.graphics.getHeight();
                touchPacket.x = Gdx.input.getX() * Constants.VIRTUAL_WIDTH / Gdx.graphics.getWidth();
            } else {
                touchPacket.touch = false;
            }
            changed = false;
            if (!previousTouchPacket.equals(touchPacket)) {
                changed = true;
            }
        }

        public boolean isChanged() {
            return changed;
        }

        public TouchPacket getTouchPacket() {
            return touchPacket;
        }
    }
}
