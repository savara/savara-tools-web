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
package org.savara.tools.web.console.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import org.savara.tools.web.console.client.auth.CurrentUser;
import org.savara.tools.web.console.client.presenter.DesktopPagePresenter;
import org.savara.tools.web.console.client.presenter.LoginPresenter;

/**
 * @author: Jeff Yu
 * @date: 10/05/11
 */
@GinModules(ConsoleModule.class)
public interface ConsoleUI extends Ginjector {

    BootstrapContext getBootstrapContext();

    PlaceManager getPlaceManager();

    EventBus getEventBus();

    ProxyFailureHandler getProxyFailureHandler();

    Provider<LoginPresenter> getLoginPresenter();

    Provider<DesktopPagePresenter> getDesktopPagePresenter();

    @DefaultGatekeeper
    Gatekeeper getLoggedInGateKeeper();

    CurrentUser getCurrentUser();
}
