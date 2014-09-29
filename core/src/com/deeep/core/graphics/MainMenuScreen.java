package com.deeep.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    private TextButton hostButton;
    private TextButton joinButton;
    private TextButton spectateButton;
    private TextButton twoPlayersutton;

    public MainMenuScreen(Core core) {
        this.core = core;

        //Instanciate Stage
        stage = new Stage(new StretchViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        //Set input processor
        Gdx.input.setInputProcessor(stage);

        setActors();
        setListeners();
        setLayout();
    }

    private void setActors() {
        hostButton = new TextButton("Host", Assets.getAssets().getSkin());
        joinButton = new TextButton("Join", Assets.getAssets().getSkin());
        spectateButton = new TextButton("Spectate", Assets.getAssets().getSkin());
        twoPlayersutton = new TextButton("Two Players", Assets.getAssets().getSkin());
    }

    private void setListeners() {
        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        spectateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        twoPlayersutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
    }

    private void setLayout() {
        //create table with skin
        Table table = new Table(Assets.getAssets().getSkin());
        //set fill parent
        table.setFillParent(true);
        //add game title, align center, set pad, and add new row
        table.add(new Image(Assets.getAssets().getTitle()));
        table.align(Align.center);
        table.pad(15);
        table.row();
        //add button and align center
        table.add(hostButton).align(Align.center);
        //add a pad of 10 pixels
        table.row().pad(10);
        table.add(joinButton).align(Align.center);
        //add new row
        table.row();
        table.add(spectateButton).align(Align.center);
        table.row().pad(10);
        table.add(twoPlayersutton).align(Align.center);

        //add table actor to stage
        stage.addActor(table);
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