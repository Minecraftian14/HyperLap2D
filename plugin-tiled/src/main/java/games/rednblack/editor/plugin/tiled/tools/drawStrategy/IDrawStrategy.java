package games.rednblack.editor.plugin.tiled.tools.drawStrategy;

import com.badlogic.ashley.core.Entity;

public interface IDrawStrategy {
    Entity drawTile(float x, float y, int row, int column);
    void updateTile(Entity entity);
}
