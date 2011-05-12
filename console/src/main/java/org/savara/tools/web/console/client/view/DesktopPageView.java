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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import org.savara.tools.web.console.client.presenter.DesktopPagePresenter;

/**
 * @author: Jeff Yu
 * @date: 11/05/11
 */
public class DesktopPageView extends ViewImpl implements DesktopPagePresenter.DesktopView {

    private HLayout layout;

    private DesktopPagePresenter presenter;

    private Window eventDetailWindow;

    private Window bizTransactionWindow;

    @Inject
    public DesktopPageView() {
       layout = new HLayout(0);
       layout.setWidth("99.5%");

       ToolStrip toolstrip = new ToolStrip();
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

        MenuItem logout = new MenuItem("Logout", "/images/buttons/logout.png");
        MenuItem eventDetail = new MenuItem("Event Detail", "/images/buttons/event_detail.png");
        MenuItem transaction = new MenuItem("Transaction Viewer", "/images/buttons/transaction_view.png");

        menu.setItems(eventDetail, transaction, logout);

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

        return menu;
    }


    public Widget asWidget() {
        return layout;
    }

    public void setPresenter(DesktopPagePresenter presenter) {
        this.presenter = presenter;
    }


    public void showEventDetailWindow() {
        eventDetailWindow = new Window();

        eventDetailWindow.setHeaderIcon("/images/buttons/event_detail.png");
        eventDetailWindow.setTitle("Event Detail");
        eventDetailWindow.setWidth("60%");
        eventDetailWindow.setHeight(500);

        DynamicForm form = new DynamicForm();
        TextItem activityItem = new TextItem();
        activityItem.setTitle("Activity ID");

        form.setItems(activityItem);

        eventDetailWindow.addItem(form);

        eventDetailWindow.show();

        eventDetailWindow.centerInPage();

    }


    public void showBizTxnWindow() {
        bizTransactionWindow = new Window();
        bizTransactionWindow.setHeaderIcon("/images/buttons/transaction_view.png");
        bizTransactionWindow.setTitle("Transaction Viewer");
        bizTransactionWindow.setWidth("60%");
        bizTransactionWindow.setHeight(500);

        bizTransactionWindow.show();
        bizTransactionWindow.centerInPage();
    }

}
