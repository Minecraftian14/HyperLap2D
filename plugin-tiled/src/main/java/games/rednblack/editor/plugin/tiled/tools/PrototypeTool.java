package games.rednblack.editor.plugin.tiled.tools;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import games.rednblack.editor.plugin.tiled.TiledPlugin;
import games.rednblack.editor.plugin.tiled.data.TileVO;
import games.rednblack.editor.plugin.tiled.tools.drawStrategy.PrototypingStrategy;
import games.rednblack.h2d.common.MsgAPI;
import games.rednblack.h2d.common.view.tools.Tool;
import games.rednblack.h2d.common.vo.CursorData;
import org.puremvc.java.interfaces.INotification;

import java.util.HashSet;
import java.util.Set;

public class PrototypeTool implements Tool {

    private static final CursorData CURSOR = new CursorData("tile-cursor", 14, 14);
    public static final String NAME = "PROTOTYPE_TOOL";

    private TiledPlugin tiledPlugin;
    private float gridWidth;
    private float gridHeight;

    private boolean isShiftDown = false;

    private final PrototypingStrategy prototypingStrategy;

    public PrototypeTool(TiledPlugin tiledPlugin) {
        this.tiledPlugin = tiledPlugin;
        prototypingStrategy = new PrototypingStrategy(tiledPlugin);
    }

    @Override
    public void initTool() {
        Texture cursorTexture = tiledPlugin.pluginRM.getTexture(CURSOR.region);
        tiledPlugin.getAPI().setCursor(CURSOR, new TextureRegion(cursorTexture));
    }

    @Override
    public String getShortcut() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Prototype Tool";
    }

    @Override
    public boolean stageMouseDown(float x, float y) {
        if (isShiftDown) return true;
        initGridThings();
        drawTile(x, y);
        return true;
    }

    @Override
    public void stageMouseUp(float x, float y) {
        if (isShiftDown) return;
        prototypingStrategy.updateTiles();
    }

    @Override
    public void stageMouseDragged(float x, float y) {
        if (isShiftDown) deleteEntityWithCoordinate(x, y);
        else drawTile(x, y);
    }

    @Override
    public void stageMouseDoubleClick(float x, float y) {

    }

    @Override
    public boolean stageMouseScrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean itemMouseDown(Entity entity, float x, float y) {
        if(isShiftDown) return true;
        initGridThings();
        if (entity == null)
            drawTile(x, y);
        return true;
    }

    @Override
    public void itemMouseUp(Entity entity, float x, float y) {
    }

    @Override
    public void itemMouseDragged(Entity entity, float x, float y) {
        if (isShiftDown) deleteEntityWithCoordinate(x, y);
        else drawTile(x, y);
    }

    @Override
    public void itemMouseDoubleClick(Entity entity, float x, float y) {
    }

    @Override
    public String getName() {
        return NAME;
    }



    @Override
    public void handleNotification(INotification notification) {

    }

    @Override
    public void keyDown(Entity entity, int keycode) {
        isShiftDown = keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT;
    }

    @Override
    public void keyUp(Entity entity, int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT)
            isShiftDown = false;
    }

    private void initGridThings() {
        gridWidth = tiledPlugin.dataToSave.getParameterVO().gridWidth;
        gridHeight = tiledPlugin.dataToSave.getParameterVO().gridHeight;
    }


    private void drawTile(float x, float y) {
        if (tiledPlugin.getSelectedTileName().equals("")) return;

        float newX = MathUtils.floor(x / gridWidth) * gridWidth + tiledPlugin.getSelectedTileGridOffset().x;
        float newY = MathUtils.floor(y / gridHeight) * gridHeight + tiledPlugin.getSelectedTileGridOffset().y;
        int row = MathUtils.floor(newY / gridHeight);
        int column = MathUtils.round(newX / gridWidth);

        prototypingStrategy.drawTile(newX, newY, row, column);
    }

    private void deleteEntityWithCoordinate (float x, float y) {
        Entity entity = tiledPlugin.getPluginEntityWithCoords(x, y);
        if (entity != null) {
            prototypingStrategy.deleteEntity(entity);
        }
    }
}
