package com.deeep.tictactoe;

import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.network.mutual.packets.TouchPacket;
import com.deeep.core.network.server.ConnectionListeners;
import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.system.Constants;
import com.deeep.tictactoe.entities.MoveCross;
import com.deeep.tictactoe.entities.MoveZero;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created by Elmar on 9/25/2014.
 */
public class ServerGame {
    public static int cross_id = -1;
    public static int zero_id = -1;
    int[][] field;
    int playerTurn;
    boolean finished = false;
    boolean crossWin = false;
    boolean zeroWin = false;
    boolean draw = false;
    private ServerLoop serverLoop;

    public ServerGame(final ServerLoop serverLoop) {
        field = new int[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                field[x][y] = -1;
            }
        }
        this.serverLoop = serverLoop;
        serverLoop.getConnectionListeners().connection = new ConnectionListeners.ConnectionInterface() {
            @Override
            public void onConnect(Connection connection) {
                if (cross_id == -1) {
                    cross_id = connection.getID();
                    playerTurn = cross_id;
                } else if (zero_id == -1) {
                    zero_id = connection.getID();
                }
            }
        };
        serverLoop.addListener(new PacketListener(TouchPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                //calculate x and y
                if (cross_id != -1 && zero_id != -1 && receivedPacket.connection.getID() == playerTurn) {
                    TouchPacket touchPacket = (TouchPacket) receivedPacket.containedPacket;
                    if (touchPacket.x <= Constants.VIRTUAL_HEIGHT) {
                        int x = 0, y = 2;
                        while (touchPacket.x > Constants.VIRTUAL_HEIGHT / 3) {
                            x++;
                            touchPacket.x -= Constants.VIRTUAL_HEIGHT / 3;
                        }
                        while (touchPacket.y > Constants.VIRTUAL_HEIGHT / 3) {
                            y--;
                            touchPacket.y -= Constants.VIRTUAL_HEIGHT / 3;
                        }
                        if (field[x][y] != -1)
                            return;
                        if (playerTurn == cross_id) {
                            playerTurn = zero_id;
                            serverLoop.getServerManager().addEntity(new MoveCross(x, y));
                        } else {
                            playerTurn = cross_id;
                            serverLoop.getServerManager().addEntity(new MoveZero(x, y));

                        }
                        field[x][y] = receivedPacket.connection.getID();
                        winCondition();
                    }
                }
            }
        }));
    }

    public void winCondition() {
        if (!finished) {
            crossWin = checkPlayer(cross_id);
            zeroWin = checkPlayer(zero_id);
            if (!crossWin && !zeroWin) {
                draw = true;
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (field[x][y] == -1)
                            draw = false;
                    }
                }
            }
            if (crossWin | draw | zeroWin) {
                finished = true;
                System.out.println(crossWin + " " + draw + " " + zeroWin + "");
            }
        }
        System.out.println(this);
    }

    private boolean checkPlayer(int player) {
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == player && field[i][1] == player && field[i][2] == player) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i] == player && field[1][i] == player && field[2][i] == player) {
                return true;
            }
        }
        if (field[0][0] == player && field[1][1] == player && field[2][2] == player)
            return true;
        if (field[2][0] == player && field[1][1] == player && field[0][2] == player)
            return true;
        return false;
    }

    public String toString() {
        String map = "";
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                map += field[x][y] + " ";
            }
            map += '\n';
        }
        return map;
    }
}
