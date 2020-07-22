/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.view.menu;

import java.util.ArrayList;

import com.kotcrab.vis.ui.widget.MenuItem;
import games.rednblack.editor.event.MenuItemListener;
import games.rednblack.editor.view.ui.widget.CustomMenuBar;

public class HyperLap2DMenuBar extends CustomMenuBar {

    public static final String prefix = "games.rednblack.editor.view.HyperLap2DMenuBar";

    public static final String RECENT_LIST_MODIFIED = prefix + ".RECENT_LIST_MODIFIED";

    private static final String TAG = HyperLap2DMenuBar.class.getCanonicalName();
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final WindowMenu windowMenu;
    private final HelpMenu helpMenu;

    public HyperLap2DMenuBar() {
        fileMenu = new FileMenu();
        editMenu = new EditMenu();
        windowMenu = new WindowMenu();
        helpMenu = new HelpMenu();

        addMenu(fileMenu);
        addMenu(editMenu);
        addMenu(windowMenu);
        addMenu(helpMenu);
        setProjectOpen(false);
    }

    public void reInitRecent(ArrayList<String> paths) {
        fileMenu.reInitRecent(paths);
    }

    public void setProjectOpen(boolean open) {
        fileMenu.setProjectOpen(open);
        editMenu.setProjectOpen(open);
        windowMenu.setProjectOpen(open);
        helpMenu.setProjectOpen(open);
    }

    public void addMenuItem(String menu, String subMenuName, String notificationName) {
        if(menu.equals(FileMenu.FILE_MENU)) {
            fileMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
        if(menu.equals(EditMenu.EDIT_MENU)) {
            editMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
        if(menu.equals(WindowMenu.WINDOW_MENU)) {
            windowMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
        if(menu.equals(HelpMenu.HELP_MENU)) {
            helpMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
    }
}
