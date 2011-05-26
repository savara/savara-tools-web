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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import org.savara.tools.web.console.client.icons.ConsoleIconBundle;
import org.savara.tools.web.console.client.model.activity.ComponentActivity;
import org.savara.tools.web.console.client.presenter.DesktopPagePresenter;
import org.savara.tools.web.console.client.widget.ApplicationWindow;
import org.savara.tools.web.console.client.widget.EventViewerWidget;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 11/05/11
 */
public class DesktopPageView extends ViewImpl implements DesktopPagePresenter.DesktopView {

    private HLayout layout;

    private DesktopPagePresenter presenter;

    private ApplicationWindow componentActWindow;

    private ApplicationWindow transactionViewWindow;

    private ApplicationWindow settingsWindow;

    private ToolStrip toolstrip;

    @Inject
    public DesktopPageView() {
       layout = new HLayout(0);
       layout.setWidth("100%");
       toolstrip = new ToolStrip();
       toolstrip.setWidth("100%");

       ToolStripMenuButton menuButton = new ToolStripMenuButton(Messages.APPLICATION_LABEL, getApplicationsMenus());
       toolstrip.addMenuButton(menuButton);
       toolstrip.addSeparator();

       layout.addMember(this.toolstrip);
    }

    private Menu getApplicationsMenus() {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem logout = new MenuItem(Messages.LOGOUT_LABEL, ConsoleIconBundle.INSTANCE.logoutIcon().getURL());
        MenuItem eventDetail = new MenuItem(Messages.EVENT_VIEWER_LABEL, ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL());
        MenuItem transaction = new MenuItem(Messages.TRANSACTION_VIEW_LABEL, ConsoleIconBundle.INSTANCE.transactionViewIcon().getURL());
        MenuItem system = new MenuItem(Messages.SYSTEM_LABEL, ConsoleIconBundle.INSTANCE.systemIcon().getURL());

        MenuItemSeparator separator = new MenuItemSeparator();

        MenuItem settings = new MenuItem(Messages.SETTINGS_LABEL, ConsoleIconBundle.INSTANCE.settingsIcon().getURL());
        MenuItem console = new MenuItem(Messages.CONSOLE_LABEL, ConsoleIconBundle.INSTANCE.consoleIcon().getURL());

        Menu systemSub = new Menu();

        systemSub.setItems(settings, console);
        system.setSubmenu(systemSub);

        menu.setItems(eventDetail, transaction, separator, system, separator, logout);

        logout.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                 presenter.logout();
            }
        });


        eventDetail.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                 presenter.showEventDetailWindow();
            }
        });

        transaction.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                 presenter.showBusinessTransactionViewerWindow();
            }
        });

        settings.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent menuItemClickEvent) {
                presenter.showSettingsWindow();
            }
        });

        return menu;
    }


    public Widget asWidget() {
        return layout;
    }

    public void setPresenter(DesktopPagePresenter presenter) {
        this.presenter = presenter;
    }


    public void showEventViewer() {
            componentActWindow = new EventViewerWidget("Event Viewer", ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL(),
                                                toolstrip);
            componentActWindow.show();
    }


    public void showBizTxnWindow() {
            transactionViewWindow = new ApplicationWindow("Transaction View",
                        ConsoleIconBundle.INSTANCE.transactionViewIcon().getURL(), toolstrip);
            transactionViewWindow.show();
    }

    public void showSettingsWindow() {
            settingsWindow = new ApplicationWindow("Settings", ConsoleIconBundle.INSTANCE.settingsIcon().getURL(), toolstrip);
            settingsWindow.show();
    }


    public void closeAllWindows() {
       if (componentActWindow != null) {
          componentActWindow.close();
          componentActWindow = null;
       }
       if (transactionViewWindow != null) {
          transactionViewWindow.close();
          transactionViewWindow = null;
       }
       if (settingsWindow != null) {
           settingsWindow.close();
           settingsWindow = null;
       }

    }

}
