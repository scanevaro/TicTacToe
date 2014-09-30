package com.deeep.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.core.system.Constants;
import com.deeep.tictactoe.entities.SplashSprite;

/**
 * Created by scanevaro on 26/09/2014.
 */
public class SplashScreen implements Screen {
    private static final float DURATION = 4f; //Duration of the SplashScreen
    private Stage stage;
    private SplashSprite splashSprite;

    public SplashScreen() {

        stage = new Stage(new StretchViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        splashSprite = new SplashSprite(new Sprite(Assets.getAssets().getLogo()));
        splashSprite.setColor(1, 1, 1, 0);
        stage.addActor(splashSprite);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                //core.setScreen(new MainMenuScreen(core));
            }
        }, DURATION);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        splashSprite.setPosition(width / 2, height / 2);
        splashSprite.getSprite().setSize(width, height);
        splashSprite.getSprite().setPosition(width / 2 - splashSprite.getSprite().getWidth() / 2, height / 2 - splashSprite.getSprite().getHeight() / 2);
        splashSprite.setSize(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}