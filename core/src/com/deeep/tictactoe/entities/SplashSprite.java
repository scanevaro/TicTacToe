package com.deeep.tictactoe.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by scanevaro on 26/09/2014.
 */
public class SplashSprite extends Actor {
    private Sprite sprite;

    public SplashSprite(Sprite sprite) {
        this.sprite = sprite;

        setActions();
    }

    private void setActions() {
        SequenceAction secAction = new SequenceAction();
        secAction.addAction(Actions.fadeIn(0.5f));
        secAction.addAction(Actions.delay(2.5f));
        secAction.addAction(Actions.fadeOut(0.5f));
        addAction(secAction);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        sprite.setColor(color);

        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void act(float delta) {
        super.act(delta);


    }
}