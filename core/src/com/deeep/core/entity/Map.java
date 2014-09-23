package com.deeep.core.entity;

import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.network.mutual.packets.PositionPacket;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Map {
    /** Array containing all the entities. NOT DYNAMIC, ISNT MEANT TO BE EITHER */
    private ArrayList<Entity> entities[][];
    /** The entity changes, to check for collisions */
    private ArrayList<MapChange> mapChanges;
    /** The entity changes, this one is used to get everything to send */
    private ArrayList<PositionPacket> positionPackets;

    /** Default constructor. Initializes everything. */
    public Map(int width, int height) {
        positionPackets = new ArrayList<PositionPacket>();
        entities = new ArrayList[height][width];
        mapChanges = new ArrayList<MapChange>();
        for (int y = 0; y < entities.length; y++) {
            for (int x = 0; x < entities[y].length; x++) {
                entities[y][x] = new ArrayList<Entity>();
            }
        }
    }

    /** Checks for collisions on all the changed cells */
    public void checkCollisions() {
        if (mapChanges.size() > 0) {
            for (MapChange mapChange : mapChanges) {
                if (entities[mapChange.y][mapChange.x].size() > 1) {
                    for (Entity entity : entities[mapChange.y][mapChange.x]) {
                        entity.collide(entities[mapChange.y][mapChange.x]);
                    }
                }
            }
        }
        mapChanges.clear();
        positionPackets.clear();
    }

    /**
     * Override of the default tostring function. Usefull for debugging. returns a string containing the sizes
     * of the array in each cell.
     *
     * @return string containing the cells, and the entity sizes of that cell
     */
    public String toString() {
        String string = "--------+\n";
        for (int y = 0; y < entities.length; y++) {
            for (int x = 0; x < entities[y].length; x++) {
                string += entities[y][x].size() + ", ";
            }
            string += "\n";
        }
        string += "--------";
        return string;
    }

    /**
     * Moves the entity to a new position.
     *
     * @param entity the entity to be moved
     * @param oldX   the old position
     * @param oldY   the new position
     */
    public void move(Entity entity, int oldX, int oldY) {
        mapChanges.add(new MapChange(entity.getId(), entity.getMapX(), entity.getMapY()));
        mapChanges.add(new MapChange(entity.getId(), oldX, oldY));
        entities[oldY][oldX].remove(entity);
        entities[entity.getMapY()][entity.getMapX()].add(entity);
        PositionPacket positionPacket = new PositionPacket();
        positionPacket.id = entity.getId();
        positionPacket.x = (int) entity.getX();
        positionPacket.y = (int) entity.getY();
        positionPacket.direction = entity.getDirection();
        if (entity.getId() != -1) {    //If initialized
            positionPackets.add(positionPacket);
        }
    }

    /**
     * getter for the map
     *
     * @return the map array
     */
    public ArrayList<Entity>[][] getMap() {
        return entities;
    }

    public ArrayList<PositionPacket> getPositionPackets() {
        return positionPackets;
    }

    /** Simple class used to keep track of the map changes */
    public class MapChange {
        public int x, y;
        public int id;

        MapChange(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
