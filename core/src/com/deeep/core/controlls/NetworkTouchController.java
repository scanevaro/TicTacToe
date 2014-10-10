package com.deeep.core.controlls;

import com.badlogic.gdx.Gdx;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.packets.TouchPacket;
import com.deeep.core.system.Constants;

/**
 * Created by scanevaro on 24/09/2014.
 */
public class NetworkTouchController {
    private ClientLoop clientLoop;
    private NetGestures netGestures;

    public NetworkTouchController(ClientLoop clientLoop) {
        this.clientLoop = clientLoop;
        netGestures = new NetGestures();
    }

    public void update(float deltaT) {
        netGestures.update();
        if (netGestures.isChanged()) {
            clientLoop.getClient().sendTcp(netGestures.getTouchPacket());
        }

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
