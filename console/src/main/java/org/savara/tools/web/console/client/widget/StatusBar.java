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

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jeff Yu
 * @date: 3/06/11
 */
/**
 * Displays the start menu button, followed by a list of open windows.
 */
public class StatusBar extends HLayout {

    protected TasksButtonsPanel tbPanel;
    private Desktop desktop;
    private Menu startMenu;

    public StatusBar(Desktop desktop) {
        this.desktop = desktop;
        tbPanel = new TasksButtonsPanel();
        //tbPanel.setStyleName("ToolStrip x-task-button-panel");
        setHeight(60);

        startMenu = new Menu();
        startMenu.setShowShadow(true);
        startMenu.setShadowDepth(3);

        ToolStripMenuButton startMenus = new ToolStripMenuButton(AppMessages.APPLICATION_LABEL, startMenu);
        tbPanel.addMenuButton(startMenus);
        tbPanel.addSeparator();

        addMember(tbPanel);
    }

    /**
     * Adds a button.
     *
     * @param win
     *            the window
     * @return the new task button
     */
    public TaskButton addTaskButton(DesktopWindow win) {
        return tbPanel.addButton(win);
    }

    /**
     * Returns the bar's buttons.
     *
     * @return the buttons
     */
    public List<TaskButton> getButtons() {
        return tbPanel.getItems();
    }

    /**
     * Removes a button.
     *
     * @param id
     *            the button to remove
     */
    public void removeTaskButton(String id) {
        tbPanel.removeButton(id);
    }

    /**
     * Sets the active button.
     *
     * @param btn
     *            the button
     */
    public void setActiveButton(TaskButton btn) {
        tbPanel.setActiveButton(btn);
    }

    public void addApplicationToStartMenu(MenuItem item) {
        startMenu.addItem(item);
    }

    public void addSeparatorToStartMenu() {
        startMenu.addItem(new MenuItemSeparator());
    }

    public class TaskButton extends ToolStripButton implements ClickHandler {

        private DesktopWindow win;

        TaskButton(DesktopWindow win) {
            this.win = win;
            setActionType(SelectionType.RADIO);
            setRadioGroup("TASK_BUTTON");
            setTitle(win.getTitle());
            setIcon(win.getHeaderIcon());
            addClickHandler(this);
        }

        public void onClick(ClickEvent event) {
            if (win.isVisible() && (win == desktop.activeWindow) ) {
                desktop.hideWindow(win);
            } else {
                desktop.activeWindow(win);
            }
        }
    }

    class TasksButtonsPanel extends ToolStrip {
        private Map<String, TaskButton> items;

        TasksButtonsPanel() {
            items = new HashMap<String, TaskButton>();
            setWidth("100%");
        }

        public TaskButton addButton(DesktopWindow win) {
            TaskButton btn = new TaskButton(win);
            items.put(win.getID(), btn);
            addButton(btn);
            setActiveButton(btn);
            return btn;
        }

        public List<TaskButton> getItems() {
            return new ArrayList<TaskButton>(items.values());
        }

        public void removeButton(String wid) {
            removeMember(items.get(wid));
            items.remove(wid);
        }

        public void setActiveButton(TaskButton btn) {
            btn.setSelected(true);
        }
    }

    public void markActive(DesktopWindow win) {
        TaskButton button = tbPanel.items.get(win.getID());
        if (button != null) {
            button.setSelected(true);
        }
    }

    public void markInactive(DesktopWindow win) {
        TaskButton button = tbPanel.items.get(win.getID());
        if (button != null) {
            button.setSelected(false);
        }
    }
}
