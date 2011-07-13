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
package org.savara.tools.web.console.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import org.savara.tools.web.console.client.presenter.DesktopPagePresenter;
import org.savara.tools.web.console.client.widget.*;

/**
 * @author: Jeff Yu
 * @date: 11/05/11
 */
public class DesktopPageView extends ViewImpl implements DesktopPagePresenter.DesktopView {

    private Desktop desktop;

    private DesktopPagePresenter presenter;

    @Inject
    public DesktopPageView() {
       desktop = new Desktop();

       DesktopWindow eventViewer = new EventViewerWindow();
       desktop.addShortcut(new Shortcut(eventViewer));
       desktop.addApplicationToStartMenu(eventViewer);

       DesktopWindow transactionViewer = new TransactionViewerWindow();
       desktop.addShortcut(new Shortcut(transactionViewer));
       desktop.addApplicationToStartMenu(transactionViewer);

       MenuItem system = new MenuItem(AppMessages.SYSTEM_LABEL, AppImages.SYSTEM_ICON);

       DesktopWindow settingWindow = new SettingWindow();
       DesktopWindow consoleWindow = new ConsoleWindow();
       Menu systemSub = new Menu();
       systemSub.setItems(desktop.createApplicationMenuItem(settingWindow), desktop.createApplicationMenuItem(consoleWindow));
       system.setSubmenu(systemSub);

       desktop.addSeparatorToStartMenu();
       desktop.addMenuItemToStartMenu(system);

       MenuItem logout = new MenuItem(AppMessages.LOGOUT_LABEL, AppImages.LOGOUT_ICON);
       logout.addClickHandler(new ClickHandler() {
           public void onClick(MenuItemClickEvent event) {
               presenter.logout();
           }
       });
       desktop.addMenuItemToStartMenu(logout);

    }

    public void setPresenter(DesktopPagePresenter presenter) {
        this.presenter = presenter;
    }

    public void closeAllWindows() {
        desktop.logout();
    }

    public Widget asWidget() {
        return desktop.asWidget();
    }

}
