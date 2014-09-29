package com.deeep.core.entity.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.core.system.Constants;
import com.deeep.core.util.SynchronousArrayList;
import com.deeep.core.entity.Map;
import com.deeep.core.entity.types.Wall;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Manager {
    //private ShapeRenderer shapeRenderer;
    private SynchronousArrayList<Entity> entities;
    private boolean debug = true;
    private Map map;
    private int prevSize = -1;

    public Manager() {
        map = new Map(60, 60);
        entities = new SynchronousArrayList<Entity>();
        //shapeRenderer = new ShapeRenderer();
        //shapeRenderer.setColor(Color.RED);
    }

    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> temp = new ArrayList<Entity>();
        for (Entity entity : entities) {
            temp.add(entity);
        }
        return temp;
    }

    public void addEntity(Entity entity) {
        addInternalEntity(entity);
    }

    protected void addInternalEntity(Entity entity) {
        System.out.println("Entity" + entity.getId() + " added");
        entity.setMap(map);
        entities.add(entity);
        System.out.println("Size: " + entities.size());
    }

    public void removeEntity(Entity entity) {
        entities.removeSynchronous(entity);
    }

    public void update(float deltaT) {
        for (Entity entity : entities) {
            entity.update(deltaT);
            updateSingleEntity(entity);
            if (debug) ;
        }
        updateEntities(deltaT);
        map.checkCollisions();
        entities.update();
    }

    protected abstract void updateSingleEntity(Entity entity);

    protected abstract void updateEntities(float deltaT);

    public void draw(SpriteBatch spriteBatch) {
        if (entities.size() != prevSize) {
            System.out.println("Size: " + entities.size());
            prevSize = entities.size();
        }
        for (Entity entity : entities) {
            System.out.println("entities drawn");
            entity.draw(spriteBatch);
            if (debug) {
                // if(entity instanceof Wall)
                //     shapeRenderer.setColor(Color.RED);
                // else
                //     shapeRenderer.setColor(Color.GREEN);
                // shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
                // shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                // //TODO get width and height of object and draw rect
                // shapeRenderer.end();
            }
        }
    }

    public Map getMap() {
        return map;
    }
}
