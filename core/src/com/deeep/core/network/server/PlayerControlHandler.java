package com.deeep.core.network.server;

import com.deeep.core.network.mutual.ControlsKeyId;
import com.deeep.core.network.mutual.packets.ControlPacket;
import com.deeep.core.util.UpdateAble;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerControlHandler implements UpdateAble {
    private ArrayList<Integer> registeredKeys = new ArrayList<Integer>();
    private HashMap<Integer, Boolean> keyToPressed = new HashMap<Integer, Boolean>();
    private boolean keyPressed = false;

    public PlayerControlHandler() {
        registeredKeys.add(ControlsKeyId.UP);
        registeredKeys.add(ControlsKeyId.LEFT);
        registeredKeys.add(ControlsKeyId.RIGHT);
        registeredKeys.add(ControlsKeyId.DOWN);
        registeredKeys.add(ControlsKeyId.ACTION);
        for (Integer integer : registeredKeys) {
            keyToPressed.put(integer, false);
        }
    }

    @Override
    public void update(float deltaT) {
    }

    public void updateKeyState(ControlPacket controlPacket) {
        for (Integer integer : registeredKeys) {
            keyToPressed.put(integer, false);
        }
        keyPressed = false;
        for (Integer integer : controlPacket.pressedKeys) {
            for (Integer key : registeredKeys) {
                if (integer.equals(key)) {
                    keyPressed = true;
                    keyToPressed.put(key, true);
                }
            }
        }
    }

    public boolean isKeyPressed(int id) {
        return keyToPressed.get(id);
    }

    public boolean isPressed() {
        return keyPressed;
    }


}