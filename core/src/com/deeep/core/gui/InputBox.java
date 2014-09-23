package com.deeep.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/13/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class InputBox extends TextBox {
    String text = "";

    public InputBox(int x, int y, boolean percentage, int width, int height) {
        super(x, y, percentage, width, height);
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keyCode) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean keyUp(int keyCode) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean keyTyped(char character) {
                if (character != '\n') {
                    text += character;
                    return true;
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean scrolled(int amount) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void update(float deltaT, boolean pressed, Vector2 mouseVector) {
        super.update(deltaT, pressed, mouseVector);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
