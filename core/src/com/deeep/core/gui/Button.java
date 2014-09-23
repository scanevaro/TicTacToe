package com.deeep.core.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/5/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Button extends GuiElement {
    private ShapeRenderer shapeRenderer;
    private TextureRegion textureRegion;
    private ButtonAction buttonAction;

    public Button(int x, int y, boolean percentage, int width, int height) {
        super(x, y, percentage, width, height);
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaT, boolean pressed, Vector2 mouseVector) {
        if (buttonAction != null) {
            if (pressed) {
                if (getHitBox().contains(mouseVector)) {
                    buttonAction.action(this);
                }
            }
        }
    }

    public void setAction(ButtonAction buttonAction) {
        this.buttonAction = buttonAction;
    }

    public void setRegion(TextureRegion region) {
        this.textureRegion = region;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (textureRegion != null) {
            spriteBatch.draw(textureRegion, x, y, width, height);
        } else {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
        }
    }
}
