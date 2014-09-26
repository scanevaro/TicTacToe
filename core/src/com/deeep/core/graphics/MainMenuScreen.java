package com.deeep.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.core.system.Constants;
import com.deeep.core.system.Core;

/**
 * Created by scanevaro on 26/09/2014.
 */
public class MainMenuScreen implements Screen {
    private Core core;
    private Stage stage;

    public MainMenuScreen(Core core) {
        stage = new Stage(new StretchViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        Gdx.input.setInputProcessor(stage);

        prepareMenu();
    }

    private void prepareMenu() {
        TextButton hostButton = new TextButton("Host", Assets.getAssets().getSkin());
        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton joinButton = new TextButton("Join", Assets.getAssets().getSkin());
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton spectateButton = new TextButton("Spectate", Assets.getAssets().getSkin());
        spectateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton twoPlayersutton = new TextButton("Two Players", Assets.getAssets().getSkin());
        twoPlayersutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        Table table = new Table(Assets.getAssets().getSkin());
        table.setFillParent(true);
        table.add(hostButton).align(Align.center);
        table.row().pad(10);
        table.add(joinButton).align(Align.center);
        table.row();
        table.add(spectateButton).align(Align.center);
        table.row().pad(10);
        table.add(twoPlayersutton).align(Align.center);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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