package controller;

import gameobj.GameObject;
import scene.Scene;
import utils.CommandSolver;
import utils.GameKernel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SceneController
        implements GameKernel.UpdateInterface, GameKernel.PaintInterface,
        CommandSolver.KeyListener, CommandSolver.MouseListener {

    private Scene currentScene;
    private static SceneController sceneController;

    public static SceneController getInstance() {
        if (sceneController == null) {
            sceneController = new SceneController();
        }
        return sceneController;
    }

    private SceneController() {
        super();

    }

    public void change(final Scene scene) {

        if (currentScene != null) {
            this.currentScene.sceneEnd();
        }

        if (scene != null) {
            scene.sceneBegin();
        }
        this.currentScene = scene;
    }

    @Override
    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if (currentScene != null && currentScene.keyListener() != null) {
            currentScene.keyListener().keyPressed(commandCode, trigTime);
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        if (currentScene != null && currentScene.keyListener() != null) {
            currentScene.keyListener().keyReleased(commandCode, trigTime);
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        if (currentScene != null && currentScene.keyListener() != null) {
            currentScene.keyListener().keyTyped(c, trigTime);
        }
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        if (currentScene != null && currentScene.mouseListener() != null) {
            currentScene.mouseListener().mouseTrig(e, state, trigTime);
        }

    }

    @Override
    public void paint(Graphics g) {
        if (this.currentScene != null) {
            this.currentScene.paint(g);
        }
    }
}
