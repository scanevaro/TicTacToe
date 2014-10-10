package com.deeep.tictactoe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.ChatPacket;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.network.mutual.packets.WinPacket;
import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.system.ClientCore;
import com.deeep.core.system.Constants;
import com.deeep.core.util.Logger;

/**
 * Created by Elmar on 9/26/2014.
 */
public class ClientGame extends ClientCore {
    private ShapeRenderer shapeRenderer;

    public ClientGame(String ip, String gameId, String name) {
        super(ip, gameId, name);
        clientLoop.addListener(new PacketListener(ChatPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                Logger.getInstance().debug(this.getClass(), "Received from server: " + ((ChatPacket) receivedPacket.containedPacket).text);
            }
        }));
        clientLoop.addListener(new PacketListener(WinPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                clientLoop.winPacket = (WinPacket) receivedPacket.containedPacket;
            }
        }));
    }

    @Override
    public void onGDXLoad() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void clientUpdate(float deltaTime) {
        //TODO particle effects
    }

    @Override
    public void clientDraw(SpriteBatch spriteBatch) {
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i < 4; i++) {
            shapeRenderer.line(i * (Constants.VIRTUAL_HEIGHT / 3), 0, i * (Constants.VIRTUAL_HEIGHT / 3), Constants.VIRTUAL_HEIGHT);
            shapeRenderer.line(0, i * (Constants.VIRTUAL_HEIGHT / 3), Constants.VIRTUAL_HEIGHT, i * (Constants.VIRTUAL_HEIGHT / 3));
        }
        shapeRenderer.end();
    }
}
