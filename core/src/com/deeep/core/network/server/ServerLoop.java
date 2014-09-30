package com.deeep.core.network.server;

import com.deeep.core.entity.World;
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
public class ServerLoop implements Runnable {
    /**
     * Tick time in ms, once every tickTime everything will be updated. Or later. But never sooner
     */
    public static int tickTime = 40;
    /**
     * Instance to send data trough
     */
    private BetterServer betterServer;
    /**
     * Instance to manage the entities
     */
    private ServerManager serverManager;
    /**
     * Instance to manage the entity id's TODO move to manager
     */
    private EntityIdManager entityIdManager;
    /**
     * List of all the packet listeners to check
     */
    private ArrayList<PacketListener> packetListeners;
    /**
     * Instance of the player manager, will update player specific stuff
     */
    private PlayerManager playerManager;
    /**
     * Instance to log all the text to
     */
    private ChatLogger chatLogger;
    /**
     * Some world
     */
    //private World world;
    /**
     * Whether the server is running, or stopped
     */
    private boolean running = false;
    /**
     * The thread containing the server. Not sure why this is needed TODO
     */
    private Thread thread = null;


    private ConnectionListeners connectionListeners;

    /**
     * Starts the server and initialize all the instances.
     */
    public void start() {
        Logger.getInstance().system(this.getClass(), "Starting server loop. . . ");
        chatLogger = new ChatLogger();
        Logger.getInstance().system(this.getClass(), "Chat logger initialized");
        entityIdManager = new EntityIdManager(Integer.MAX_VALUE);
        Logger.getInstance().system(this.getClass(), "Entity manager set up");
        betterServer = new BetterServer();
        connectionListeners = new ConnectionListeners();
        Logger.getInstance().system(this.getClass(), "Server initialized");
        betterServer.startListening();
        Logger.getInstance().system(this.getClass(), "Server listening");
        serverManager = new ServerManager(entityIdManager, betterServer);
        Logger.getInstance().system(this.getClass(), "Server manager set up");
        playerManager = new PlayerManager(serverManager);
        Logger.getInstance().system(this.getClass(), "Player manager made");
        packetListeners = new ArrayList<PacketListener>();
        addListener(new PacketListener(RegisterPlayer.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                playerManager.addPlayer(receivedPacket.connection);
                for (EntityCreationPacket entityCreationPacket : serverManager.getEntityPackets()) {
                    betterServer.sendToTCP(receivedPacket.connection.getID(), entityCreationPacket);
                }
                if (connectionListeners.connection != null) {
                    connectionListeners.connection.onConnect(receivedPacket.connection);
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
                betterServer.sendToTCP(receivedPacket.connection.getID(), receivedPacket.containedPacket);
            }
        }));
        Logger.getInstance().system(this.getClass(), "Listeners added");
        running = true;
        thread = new Thread(this);
        thread.start();
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

    public ServerManager getServerManager() {
        return serverManager;
    }

    public ConnectionListeners getConnectionListeners() {
        return connectionListeners;
    }

    public BetterServer getBetterServer() {
        return betterServer;
    }

    @Override
    public void run() {
        long delayTime = 0;
        Logger.getInstance().system(this.getClass(), "Server thread started");
        while (running) {
            long startTime = System.currentTimeMillis();
            serverManager.update(tickTime);
            playerManager.update(tickTime);
            for (int i = 0, l = betterServer.getSize(); i < l; i++) {
                actPacket(betterServer.getReceivedPacket());
            }
            if ((delayTime = tickTime - (startTime - System.currentTimeMillis())) > 0) {
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}