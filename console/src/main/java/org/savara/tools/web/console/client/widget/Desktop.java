/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.savara.tools.web.console.client.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Orientation;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

/**
 * A desktop represents a desktop like application which contains a task bar,
 * start menu, and shortcuts.
 *
 * <p/>
 * Rather than adding content directly to the root panel, content should be
 * wrapped in windows. Windows can be opened via shortcuts and the start menu.
 *
 */
public class Desktop {

    protected StatusBar statusBar;
    protected DesktopWindowListener listener;
    protected VLayout viewport;
    protected DesktopTileGrid desktop;
    protected DesktopWindow activeWindow;
    protected List<Shortcut> shortcuts;
    protected List<DesktopWindow> windows;

    private Stack<DesktopWindow> activeWindows = new Stack<DesktopWindow>();

    class DesktopTileGrid extends TileGrid {
        public DesktopTileGrid() {
            super();
            setAttribute("wrapValues", true, true);
        }
    }

    @Inject
    public Desktop() {
        shortcuts = new ArrayList<Shortcut>();
        windows = new ArrayList<DesktopWindow>();

        listener = new DesktopWindowListener(this);

        statusBar = new StatusBar(this);

        viewport = new VLayout(0);
        viewport.setMargin(0);
        viewport.setWidth100();
        viewport.setHeight100();
        viewport.setBackgroundColor("transparent");

        desktop = new DesktopTileGrid();
        desktop.setShowEdges(false);
        DetailViewerField title = new DetailViewerField("title");
        DetailViewerField icon = new DetailViewerField("icon");
        icon.setType("image");
        icon.setImageSize(48);
        desktop.setFields(icon, title);
        desktop.setTileHeight(80);
        desktop.setTileWidth(60);
        desktop.setBackgroundColor("transparent");

        desktop.setOrientation(Orientation.VERTICAL);
        desktop.setHeight("*");
        desktop.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                // open window;
                final Record record = event.getRecord();
                if (record == null) {
                    return;
                }
                DesktopWindow window = ((Shortcut) record).getWindow();
                activeWindow(window);
            }
        });

        viewport.addMember(statusBar);
        viewport.addMember(desktop);

    }

    public MenuItem createApplicationMenuItem(DesktopWindow window) {
        MenuItem menuItem = new ApplicationMenuItem(window, window.getTitle(), window.getMenuIcon());
        menuItem.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
               final ApplicationMenuItem theItem = (ApplicationMenuItem)event.getItem();
               if (theItem == null) {
                   return;
               }
               DesktopWindow window = theItem.getDesktopWindow();
               activeWindow(window);
            }
        });
        return menuItem;
    }

    public void addApplicationToStartMenu(DesktopWindow win) {
        statusBar.addApplicationToStartMenu(createApplicationMenuItem(win));
    }

    public void addMenuItemToStartMenu(MenuItem item) {
        statusBar.addApplicationToStartMenu(item);
    }

    public void addSeparatorToStartMenu() {
        statusBar.addSeparatorToStartMenu();
    }

    public void activeWindow(DesktopWindow win) {
        if (win == null) {
            return;
        }
        if (!getWindows().contains(win)) {
            addWindow(win);
        }

        if (activeWindow == win) {
            return;
        }
        if (activeWindow != null) {
            addPreviousActiveWindow(activeWindow);
        }
        activeWindow = win;
        if (win.isVisible()) {
            win.bringToFront();
        } else {
            win.show();
        }
        statusBar.markActive(win);
    }

    private void addPreviousActiveWindow(DesktopWindow win) {
        while (true) {
            if (!activeWindows.remove(win)) {
                break;
            }
        }
        activeWindows.push(win);
    }

    public DesktopWindow getPreviousActiveWindow(DesktopWindow win) {
        while (true) {
            if (!activeWindows.remove(win))
                break;
        }
        if (activeWindows.size() == 0){
            return null;
        }
        return activeWindows.pop();
    }

    public void hideWindow(DesktopWindow win) {
        if (win == activeWindow) {
            activeWindow = null;
        }
        win.hide();
        statusBar.markInactive(win);
        // active previous window.
        activeWindow(getPreviousActiveWindow(win));
    }

    /**
     * Adds a shortcut to the desktop.
     *
     * @param shortcut
     *            the shortcut to add
     */
    public void addShortcut(Shortcut shortcut) {
        shortcuts.add(shortcut);
        RecordList recordList = desktop.getRecordList();
        if (recordList == null) {
            recordList = new RecordList();
            desktop.setData(recordList);
        }
        recordList.add(shortcut);
    }

    /**
     * Adds a window to the desktop.
     *
     * @param window
     *            the window to add
     */
    public void addWindow(DesktopWindow window) {
        if (windows.add(window)) {
            viewport.addChild(window);
            window.addCloseClickHandler(listener);
            window.addMinimizeClickHandler(listener);
            window.addMouseDownHandler(listener);
            statusBar.addTaskButton(window);
        }
    }

    /**
     * Returns the container of the "desktop", which is the viewport minus the
     * task bar.
     *
     * @return the desktop layout container
     */
    public TileGrid getDesktop() {
        return desktop;
    }

    /**
     * Returns a list of the desktop's shortcuts.
     *
     * @return the shortcuts
     */
    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }

    /**
     * Returns the desktop's task bar.
     *
     * @return the task bar
     */
    public StatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * Returns a list of the desktop's windows.
     *
     * @return the windows
     */
    public List<DesktopWindow> getWindows() {
        return windows;
    }

    /**
     * Removes a shortcut from the desktop.
     *
     * @param shortcut
     *            the shortcut to remove
     */
    public void removeShortcut(Shortcut shortcut) {
        shortcuts.remove(shortcut);
        desktop.getRecordList().remove(shortcut);
    }

    /**
     * Removes a window from the desktop.
     *
     * @param window
     *            the window to remove
     */
    public void removeWindow(DesktopWindow window) {
        hideWindow(window);
        if (windows.remove(window)) {
            statusBar.removeTaskButton(window.getID());
        }
    }

    public Widget asWidget() {
        return viewport;
    }

    public void logout() {
        List<DesktopWindow> allWindows = new ArrayList<DesktopWindow>();
        allWindows.addAll(windows);

        for (DesktopWindow window : allWindows) {
            removeWindow(window);
        }
    }
}