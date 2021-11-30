package scene;


import utils.CommandSolver;
import utils.GameKernel;

public abstract class Scene implements GameKernel.PaintInterface, GameKernel.UpdateInterface {
    public abstract void sceneBegin();

    public abstract void sceneEnd();

    public abstract CommandSolver.MouseListener mouseListener();

    public abstract CommandSolver.KeyListener keyListener();
}
