package com.deeep.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/5/13
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Canvas {
    private ArrayList<GuiElement> guiElements;
    private int width, height;
    private Vector2 mouseVector;

    public Canvas(int width, int height) {
        guiElements = new ArrayList<GuiElement>();
        this.width = width;
        this.height = height;
        this.mouseVector = new Vector2(0, 0);
    }

    public void addElement(GuiElement guiElement) {
        guiElements.add(guiElement);
    }

    public void update(float deltaT) {
        mouseVector.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        for (GuiElement guiElement : guiElements) {
            guiElement.update(deltaT, Gdx.input.isTouched(), mouseVector);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (GuiElement guiElement : guiElements) {
            guiElement.draw(spriteBatch);
        }
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        for (GuiElement guiElement : guiElements) {
            guiElement.resize(width, height);
        }
    }
}

