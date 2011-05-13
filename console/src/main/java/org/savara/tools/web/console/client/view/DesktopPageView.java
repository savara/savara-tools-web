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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import org.savara.tools.web.console.client.icons.ConsoleIconBundle;
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

    private ToolStrip toolstrip;

    private boolean destroyed = false;

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
        if (eventDetailWindow != null && !destroyed) {
            eventDetailWindow.show();
        } else {
            eventDetailWindow = new Window();

            eventDetailWindow.setHeaderIcon(ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL());
            eventDetailWindow.setTitle("Event Detail");
            eventDetailWindow.setWidth("60%");
            eventDetailWindow.setHeight(500);
            eventDetailWindow.addMinimizeClickHandler(new MinimizeClickHandler() {
                public void onMinimizeClick(MinimizeClickEvent minimizeClickEvent) {
                   eventDetailWindow.hide();
                    final ToolStripButton button = new ToolStripButton();
                    button.setIcon(ConsoleIconBundle.INSTANCE.eventDetailIcon().getURL());
                    button.setTitle("Event Detail");
                    button.setWidth("60");

                    button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
                        public void onClick(ClickEvent clickEvent) {
                            eventDetailWindow.restore();
                            eventDetailWindow.show();
                            eventDetailWindow.centerInPage();
                            toolstrip.removeMember(button);
                        }
                    });

                    toolstrip.addButton(button);
                }
            });

            DynamicForm form = new DynamicForm();
            TextItem activityItem = new TextItem();
            activityItem.setTitle("Activity ID");

            form.setItems(activityItem);

            eventDetailWindow.addItem(form);

            eventDetailWindow.show();

            eventDetailWindow.centerInPage();

        }
    }


    public void showBizTxnWindow() {
        if (bizTransactionWindow != null && !destroyed) {
            bizTransactionWindow.show();
        } else {
            bizTransactionWindow = new Window();
            bizTransactionWindow.setHeaderIcon(ConsoleIconBundle.INSTANCE.transactionViewIcon().getURL());
            bizTransactionWindow.setTitle("Transaction Viewer");
            bizTransactionWindow.setWidth("60%");
            bizTransactionWindow.setHeight(500);

            bizTransactionWindow.show();
            bizTransactionWindow.centerInPage();


            bizTransactionWindow.addMinimizeClickHandler(new MinimizeClickHandler() {
                public void onMinimizeClick(MinimizeClickEvent minimizeClickEvent) {
                    bizTransactionWindow.hide();
                    final ToolStripButton button = new ToolStripButton();
                    button.setIcon(ConsoleIconBundle.INSTANCE.transactionViewIcon().getURL());
                    button.setTitle("Transaction Viewer");
                    button.setWidth("60");

                    button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
                        public void onClick(ClickEvent clickEvent) {
                            bizTransactionWindow.restore();
                            bizTransactionWindow.show();
                            bizTransactionWindow.centerInPage();
                            toolstrip.removeMember(button);
                        }
                    });

                    toolstrip.addButton(button);
                }
            });
        }
    }


    public void closeAllWindows() {
        if (eventDetailWindow != null) {
            eventDetailWindow.destroy();
        }
        if (bizTransactionWindow != null) {
            bizTransactionWindow.destroy();
        }
        destroyed = true;
    }

}
