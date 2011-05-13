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
package org.savara.tools.web.console.client.presenter;


import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import org.savara.tools.web.console.client.NameTokens;
import org.savara.tools.web.console.client.auth.CurrentUser;

/**
 * @author: Jeff Yu
 * @date: 9/05/11
 */
public class LoginPresenter extends Presenter<LoginPresenter.LoginView,
                                              LoginPresenter.LoginProxy> {


    private PlaceManager placeManager;

    private CurrentUser currentUser;

    @Inject
    public LoginPresenter(EventBus eventBus, LoginView loginview, LoginProxy loginProxy,
                          PlaceManager placeManager, CurrentUser user) {
        super(eventBus, loginview, loginProxy);
        this.placeManager = placeManager;
        this.currentUser = user;
    }


    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    public interface LoginView extends View {

        public void setPresenter(LoginPresenter presenter);

        public void close();

        public void show();

    }

    @ProxyStandard
    @NameToken(NameTokens.LOGIN_VIEW)
    @NoGatekeeper
    public interface LoginProxy extends ProxyPlace<LoginPresenter> {}

    @Override
    public void onBind() {
       super.onBind();
       getView().setPresenter(this);
    }

    @Override
    public void onReveal() {
        getView().show();
    }

    public void login(String name, String password) {
        //TODO: validate it from backend server;
        getView().close();

        currentUser.setUserName(name);
        currentUser.setLoggedIn(true);

        PlaceRequest desktopPlace = new PlaceRequest(NameTokens.DESKTOP_WINDOW);
        placeManager.revealPlace(desktopPlace);
    }

}
