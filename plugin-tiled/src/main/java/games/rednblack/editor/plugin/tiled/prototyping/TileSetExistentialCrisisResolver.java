package games.rednblack.editor.plugin.tiled.prototyping;

import com.badlogic.ashley.core.Entity;
import games.rednblack.editor.plugin.tiled.TiledPlugin;
import games.rednblack.h2d.common.command.ReplaceRegionCommandBuilder;

import static games.rednblack.editor.plugin.tiled.tools.drawStrategy.TilePosition.*;

public class TileSetExistentialCrisisResolver {

    private final TiledPlugin plugin;
    private final ReplaceRegionCommandBuilder replaceRegionCommandBuilder = new ReplaceRegionCommandBuilder();

    public TileSetExistentialCrisisResolver(TiledPlugin rm) {
        this.plugin = rm;
    }

    String region;

    public void transform(int type, Entity entity) {

        System.out.println(type);


        if ((type & (Tp | CR)) == 0) {
            topRight(type, entity);
        } else if ((type & (Bo | CR)) == 0) {
            bottomRight(type, entity);
        } else if ((type & (Bo | CL)) == 0) {
            bottomLeft(type, entity);
        } else if ((type & (Tp | CL)) == 0) {
            topLeft(type, entity);
        } else if ((type & Tp) == 0) {
            top(type, entity);
        } else if ((type & Bo) == 0) {
            bottom(type, entity);
        } else if ((type & CR) == 0) {
            right(type, entity);
        } else if ((type & CL) == 0) {
            left(type, entity);
        } else {
            center(type, entity);
        }

        replaceRegionCommandBuilder.begin(entity);
        replaceRegionCommandBuilder.setRegion(plugin.getAPI().getSceneLoader().getRm().getTextureRegion(region));
        replaceRegionCommandBuilder.setRegionName(region);
        replaceRegionCommandBuilder.execute(plugin.facade);
    }

    private void top(int type, Entity entity) {
        region = plugin.getSelectedTileName();
    }

    private void topRight(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "tr";
    }

    private void topLeft(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "tl";
    }

    private void right(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "r";
    }

    private void center(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "c";
    }

    private void left(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "l";
    }

    private void bottom(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "b";
    }

    private void bottomRight(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "br";
    }

    private void bottomLeft(int type, Entity entity) {
        region = plugin.getSelectedTileName() + "bl";
    }


//    private void bottom(int type, Entity entity) {
//        TransformCommandBuilder commandBuilder = new TransformCommandBuilder();
//        commandBuilder.begin(entity);
//
//        commandBuilder.setRotation(180);
//
//        commandBuilder.execute(plugin.facade);
//    }
//
//    private void right(int type, Entity entity) {
//        TransformCommandBuilder commandBuilder = new TransformCommandBuilder();
//        commandBuilder.begin(entity);
//
//        commandBuilder.setRotation(90);
//
//        commandBuilder.execute(plugin.facade);
//    }
//
//    private void left(int type, Entity entity) {
//        TransformCommandBuilder commandBuilder = new TransformCommandBuilder();
//        commandBuilder.begin(entity);
//
//        commandBuilder.setRotation(-90);
//
//        commandBuilder.execute(plugin.facade);
//    }


}
