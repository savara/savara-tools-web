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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import org.savara.tools.web.console.client.icons.ConsoleIconBundle;
import org.savara.tools.web.console.client.presenter.DesktopPagePresenter;
import org.savara.tools.web.console.client.widget.ApplicationWindow;

/**
 * @author: Jeff Yu
 * @date: 11/05/11
 */
public class DesktopPageView extends ViewImpl implements DesktopPagePresenter.DesktopView {

    private HLayout layout;

    private DesktopPagePresenter presenter;

    private ApplicationWindow eventDetailWindow;

    private ApplicationWindow transactionViewWindow;

    private ApplicationWindow settingsWindow;

    private ToolStrip toolstrip;

    @Inject
    public DesktopPageView() {
       layout = new HLayout(0);
       layout.setWidth("100%");

       toolstrip = new ToolStrip();
       toolstrip.setWidth("100%");

       ToolStripMenuButton menuButton = new ToolStripMenuButton("Applications", getApplicationsMenus());
       toolstrip.addMenuButton(menuButton);
       toolstrip.addSeparator();

       layout.addMember(toolstrip);
    }

    private Menu getApplicationsMenus() {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem logout = new MenuItem("Logout", ConsoleIconBundle.INSTANCE.logoutIcon().getURL());
        MenuItem eventDetail = new MenuItem("Event Detail", ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL());
        MenuItem transaction = new MenuItem("Transaction Viewer", ConsoleIconBundle.INSTANCE.transactionViewIcon().getURL());
        MenuItem settings = new MenuItem("Settings", ConsoleIconBundle.INSTANCE.settingsIcon().getURL());

        menu.setItems(eventDetail, transaction, settings, logout);

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


    public void showEventDetailWindow() {
            eventDetailWindow = new ApplicationWindow("Event Detail", ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL(),
                                                toolstrip);

            DynamicForm form = new DynamicForm();
            TextItem activityItem = new TextItem();
            activityItem.setTitle("Activity ID");

            form.setItems(activityItem);

            eventDetailWindow.getWindow().addItem(form);
            eventDetailWindow.show();

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
       if (eventDetailWindow != null) {
          eventDetailWindow.close();
          eventDetailWindow = null;
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
