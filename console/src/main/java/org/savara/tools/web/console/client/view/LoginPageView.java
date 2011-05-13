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
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import org.savara.tools.web.console.client.Build;
import org.savara.tools.web.console.client.icons.ConsoleIconBundle;
import org.savara.tools.web.console.client.presenter.LoginPresenter;

/**
 * @author: Jeff Yu
 * @date: 9/05/11
 */
public class LoginPageView extends ViewImpl implements LoginPresenter.LoginView {


    private Window window;

    private LoginPresenter presenter;

    private TextItem usernameItem;

    private PasswordItem passwordItem;

    @Inject
    public LoginPageView() {

        window = new Window();
        window.setHeaderIcon(ConsoleIconBundle.INSTANCE.savaraIcon().getURL());
        window.setTitle("console");
        window.setWidth(300);
        window.setHeight(150);
        window.setIsModal(true);
        window.setShowMinimizeButton(false);
        window.setShowCloseButton(false);
        window.centerInPage();

        DynamicForm form = new DynamicForm();
        form.setWidth(250);

        usernameItem = new TextItem();
        usernameItem.setTitle("username");

        passwordItem = new PasswordItem();
        passwordItem.setTitle("password");

        HLayout buttonLayout = new HLayout();
        IButton loginBtn = new IButton("Login");
        loginBtn.setWidth(60);
        loginBtn.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent clickEvent) {
                String name = usernameItem.getValueAsString();
                String password = passwordItem.getValueAsString();
                presenter.login(name, password);
            }
        });
        buttonLayout.addMember(loginBtn);
        buttonLayout.setAlign(Alignment.CENTER);

        form.setFields(new FormItem[]{usernameItem, passwordItem});
        form.setPadding(10);

        window.addItem(form);
        window.addItem(buttonLayout);

        HLayout versionLayout = new HLayout();
        Label versionNo = new Label(Build.VERSION);

        versionLayout.addMember(versionNo);
        versionLayout.setAlign(Alignment.RIGHT);

        window.addItem(versionLayout);
    }

    public Widget asWidget() {
        return window;
    }

    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }


    public void close() {
        window.hide();
    }

    public void show() {
        passwordItem.clearValue();
        window.show();
    }

}
