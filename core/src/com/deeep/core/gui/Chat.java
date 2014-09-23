package com.deeep.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/13/13
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class Chat extends GuiElement {
    ChatBox chat;
    InputBox input;
    boolean active = true;
    boolean previousActive;

    public Chat(int x, int y, boolean percentage, int width, int lines) {
        super(x, y, percentage, width, 0);
        input = new InputBox(x, y, percentage, width, 1);
        chat = new ChatBox(x, (int) (y + input.getHeight()), percentage, width, lines);
    }

    @Override
    public void update(float deltaT, boolean pressed, Vector2 mouseVector) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && !previousActive) {
            active = !active;
            previousActive = true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            previousActive = false;
        }
        if (active) {
            input.update(deltaT, pressed, mouseVector);
        }
        //chat.update(deltaT, pressed, mouseVector);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        chat.draw(spriteBatch);
        if (active) {
            input.draw(spriteBatch);
        }
    }
}
