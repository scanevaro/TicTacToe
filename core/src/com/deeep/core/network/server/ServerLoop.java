package com.deeep.core.network.server;

import com.deeep.core.entity.World;
import com.deeep.core.entity.types.Wall;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.*;
import com.deeep.core.util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerLoop {
    /** Instance to send data trough */
    private BetterServer betterServer;
    /** Instance to manage the entities */
    private ServerManager serverManager;
    /** Instance to manage the entity id's TODO move to manager */
    private EntityIdManager entityIdManager;
    /** List of all the packet listeners to check */
    private ArrayList<PacketListener> packetListeners;
    /** Instance of the player manager, will update player specific stuff */
    private PlayerManager playerManager;
    /** Instance to log all the text to */
    private ChatLogger chatLogger;
    /** Some world */
    private World world;

    /** Starts the server and initialize all the instances. */
    public void start() {
        Logger.getInstance().system(this.getClass(), "Starting server loop. . . ");
        chatLogger = new ChatLogger();
        Logger.getInstance().system(this.getClass(), "Chat logger initialized");
        entityIdManager = new EntityIdManager(Integer.MAX_VALUE);
        Logger.getInstance().system(this.getClass(), "Entity manager set up");
        betterServer = new BetterServer();
        Logger.getInstance().system(this.getClass(), "Server initialized");
        betterServer.startListening();
        Logger.getInstance().system(this.getClass(), "Server listening");
        serverManager = new ServerManager(entityIdManager, betterServer);
        playerManager = new PlayerManager(serverManager);
        Logger.getInstance().system(this.getClass(), "Player manager made");
        serverManager.addEntity(new Wall(2,2));
        Logger.getInstance().system(this.getClass(), "Server manager set up");
        packetListeners = new ArrayList<PacketListener>();
        addListener(new PacketListener(RegisterPlayer.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                playerManager.addPlayer(receivedPacket.connection);
                for (EntityCreationPacket entityCreationPacket : serverManager.getEntityPackets()) {
                    betterServer.sendToTCP(receivedPacket.connection.getID(), entityCreationPacket);
                }
            }
        }));
        addListener(new PacketListener(ControlPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                playerManager.changeState(receivedPacket);
            }
        }));
        addListener(new PacketListener(ChatPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                chatLogger.logText(((ChatPacket) receivedPacket.containedPacket).text);
                betterServer.sendToAllTCP(receivedPacket.containedPacket);

            }
        }));
        addListener(new PacketListener(PingPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                betterServer.sendToTCP(receivedPacket.connection.getID(),receivedPacket.containedPacket);
            }
        }));
        Logger.getInstance().system(this.getClass(), "Listeners added");
    }

    /**
     * Updates all the entities and processes the packet
     *
     * @param deltaT the time that has passed
     */
    public void update(float deltaT) {
        serverManager.update(deltaT);
        playerManager.update(deltaT);
        for (int i = 0, l = betterServer.getSize(); i < l; i++) {
            actPacket(betterServer.getReceivedPacket());
        }
    }

    /**
     * This function will check for all the listeners and compare them upon arrival of one packet.
     *
     * @param receivedPacket the packet just received
     */
    public void actPacket(ReceivedPacket receivedPacket) {
        for (PacketListener packetListener : packetListeners) {
            if (packetListener.listener.isInstance(receivedPacket.containedPacket)) {
                packetListener.packetAction.action(receivedPacket);
            }
        }
    }

    /**
     * Adds a listener to check for.
     *
     * @param packetListener the listener
     */
    public void addListener(PacketListener packetListener) {
        packetListeners.add(packetListener);
    }

    /**
     * Removes a single listener with the same class. TODO make this better
     *
     * @param claz the class of which to remove the listener
     */
    public void removeListener(Class<? extends Packet> claz) {
        for (PacketListener packetListener : packetListeners) {
            if (packetListener.listener.isInstance(claz)) {
                packetListeners.remove(packetListener);
                return;
            }
        }
        Logger.getInstance().warn(this.getClass(), "No listener found with class: " + claz);
    }
}
