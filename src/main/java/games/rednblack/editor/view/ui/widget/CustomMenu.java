package games.rednblack.editor.view.ui.widget;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;
import games.rednblack.h2d.common.view.ui.widget.H2DPopupMenu;

/**
 * Created by hayk on 5/22/2015.
 */
public class CustomMenu extends H2DPopupMenu {
    private CustomMenuBar menuBar;
    public VisTextButton openButton;

    private String title;

    public CustomMenu (String title) {
        this.title = title;

        openButton = new VisTextButton(title, new VisTextButton.VisTextButtonStyle(VisUI.getSkin().get("menu-bar", VisTextButton.VisTextButtonStyle.class)));
        openButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (menuBar.getCurrentMenu() == CustomMenu.this) {
                    menuBar.closeMenu();
                    return true;
                }

                switchMenu();
                event.stop();
                return true;
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (menuBar.getCurrentMenu() != null) switchMenu();
            }
        });
    }

    public String getTitle () {
        return title;
    }

    private void switchMenu () {
        if (menuBar.getCurrentMenu() != this) {
            menuBar.closeMenu();
            showMenu();
        }
    }

    private void showMenu () {
        Vector2 pos = openButton.localToStageCoordinates(new Vector2(0, 0));
        super.showMenu(openButton.getStage(), pos.x, pos.y);
        menuBar.setCurrentMenu(this);
    }

    @Override
    public boolean remove () {
        if (menuBar == null)
            return false;
        boolean result = super.remove();
        menuBar.setCurrentMenu(null);
        return result;
    }

    /** Called by MenuBar when this menu is added to it */
    void setMenuBar (CustomMenuBar menuBar) {
        if (this.menuBar != null) throw new IllegalStateException("Menu was already added to MenuBar");
        this.menuBar = menuBar;
        this.menuBar.defaultStyle   =   openButton.getStyle().up;
    }

    TextButton getOpenButton () {
        return openButton;
    }
}
