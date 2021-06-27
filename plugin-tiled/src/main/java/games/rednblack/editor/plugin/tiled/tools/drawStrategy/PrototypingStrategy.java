package games.rednblack.editor.plugin.tiled.tools.drawStrategy;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import games.rednblack.editor.plugin.tiled.TiledPlugin;
import games.rednblack.editor.plugin.tiled.prototyping.TileSetExistentialCrisisResolver;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TextureRegionComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.h2d.common.MsgAPI;
import games.rednblack.h2d.common.command.ReplaceRegionCommandBuilder;
import games.rednblack.h2d.common.factory.IFactory;

import java.util.HashSet;
import java.util.Set;

import static games.rednblack.editor.plugin.tiled.tools.drawStrategy.TilePosition.*;

public class PrototypingStrategy extends BasicDrawStrategy {

    private final ComponentMapper<MainItemComponent> MICM = ComponentMapper.getFor(MainItemComponent.class);
    private final ComponentMapper<TransformComponent> TRAM = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<TextureRegionComponent> TRCM = ComponentMapper.getFor(TextureRegionComponent.class);

    TileSetExistentialCrisisResolver tileSet = new TileSetExistentialCrisisResolver(tiledPlugin);

    private final Array<Entity> tiles = new Array<>();
    private final IntArray types = new IntArray();

    public PrototypingStrategy(TiledPlugin plugin) {
        super(plugin);
    }

    @Override
    public Entity drawTile(float x, float y, int row, int column) {
        Entity underneathTile = tiledPlugin.getPluginEntityWithParams(row, column);
        if (underneathTile != null) {
            if (!tiles.contains(underneathTile, false))
                tiles.add(underneathTile);
            return underneathTile;
        }
        if (isThereATileOfConcern(row, column))
            return null;

        IFactory itemFactory = tiledPlugin.getAPI().getItemFactory();
        temp.set(x, y);
        if (itemFactory.createSimpleImage(tiledPlugin.getSelectedTileName(), temp)) {
            Entity imageEntity = itemFactory.getCreatedEntity();
            postProcessEntity(imageEntity, x, y, row, column);
            tiles.add(imageEntity);
            return imageEntity;
        }
        return null;
    }

    private boolean isThereATileOfConcern(float row, float col) {
        for (int i = 0; i < tiles.size; i++) {

            MainItemComponent comp = MICM.get(tiles.get(i));

            int row_n = comp.customVariables.getIntegerVariable(TiledPlugin.ROW);
            int col_n = comp.customVariables.getIntegerVariable(TiledPlugin.COLUMN);

            if (row == row_n && col == col_n) return true;
        }
        return false;
    }

    @Override
    public void updateTile(Entity entity) {
        throw new RuntimeException("Wait what? You should not be having the need to update prototyping tiles!!");
    }

    public void updateTiles() {
        calculateTypes();

        for (int i = 0; i < tiles.size; i++) {
            Entity entity = tiles.get(i);
            int type = types.get(i);

            TextureRegionComponent textureComp = TRCM.get(entity);

            if (textureComp == null || textureComp.regionName == null)
                continue;

//            if (textureComp.regionName.equals(tiledPlugin.getSelectedTileName()))
//                continue;

            if (!tiledPlugin.getAPI().getSceneLoader().getRm().hasTextureRegion(tiledPlugin.getSelectedTileName()))
                continue;

            tileSet.transform(type,entity);

//            String region = tileSet.transform(type, entity);
//
//            replaceRegionCommandBuilder.begin(entity);
//            replaceRegionCommandBuilder.setRegion(tiledPlugin.getAPI().getSceneLoader().getRm().getTextureRegion(region));
//            replaceRegionCommandBuilder.setRegionName(region);
//            replaceRegionCommandBuilder.execute(tiledPlugin.facade);
        }
    }

    private void calculateTypes() {
        types.clear();
        for (int i = 0; i < tiles.size; i++) {
            Entity tile = tiles.get(i);

            int number = 0;
            if (isThereATileOfConcern(tile, 0, 1)) number |= Tp;
            if (isThereATileOfConcern(tile, 1, 1)) number |= TR;
            if (isThereATileOfConcern(tile, 1, 0)) number |= CR;
            if (isThereATileOfConcern(tile, 1, -1)) number |= BR;
            if (isThereATileOfConcern(tile, 0, -1)) number |= Bo;
            if (isThereATileOfConcern(tile, -1, -1)) number |= BL;
            if (isThereATileOfConcern(tile, -1, 0)) number |= CL;
            if (isThereATileOfConcern(tile, -1, 1)) number |= TL;
            types.add(number);
        }
    }

    public boolean isThereATileOfConcern(Entity entity, int ofx, int ofy) {
        MainItemComponent comp = MICM.get(entity);

        int row = comp.customVariables.getIntegerVariable(TiledPlugin.ROW) + ofy;
        int col = comp.customVariables.getIntegerVariable(TiledPlugin.COLUMN) + ofx;

        for (int i = 0; i < tiles.size; i++) {
            Entity tile = tiles.get(i);

            if (tile.equals(entity)) continue;

            MainItemComponent comp_n = MICM.get(tile);

            int row_n = comp_n.customVariables.getIntegerVariable(TiledPlugin.ROW);
            int col_n = comp_n.customVariables.getIntegerVariable(TiledPlugin.COLUMN);

            if (row == row_n && col == col_n) return true;
        }

        return false;
    }

    Set<Entity> items = new HashSet<>();

    public void deleteEntity(Entity entity) {
        deleteTile(entity);
        if (tiledPlugin.isTile(entity) && tiledPlugin.isOnCurrentSelectedLayer(entity)) {
            items.clear();
            items.add(entity);
            tiledPlugin.facade.sendNotification(MsgAPI.ACTION_SET_SELECTION, items);
            tiledPlugin.facade.sendNotification(MsgAPI.ACTION_DELETE);
        }
    }

    private void deleteTile(Entity entity) {
        MainItemComponent comp = MICM.get(entity);

        int row = comp.customVariables.getIntegerVariable(TiledPlugin.ROW);
        int col = comp.customVariables.getIntegerVariable(TiledPlugin.COLUMN);

        int index = -1;

        for (int i = 0; i < tiles.size; i++) {
            Entity tile = tiles.get(i);

            if (tile.equals(entity)) {
                index = i;
                break;
            }

            MainItemComponent comp_n = MICM.get(tile);

            int row_n = comp_n.customVariables.getIntegerVariable(TiledPlugin.ROW);
            int col_n = comp_n.customVariables.getIntegerVariable(TiledPlugin.COLUMN);

            if (row == row_n && col == col_n) {
                index = i;
                break;
            }
        }

        if (index != -1)
            tiles.removeIndex(index);
    }
}
