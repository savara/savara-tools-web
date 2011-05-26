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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import org.fusesource.restygwt.client.*;
import org.savara.tools.web.console.client.ApplicationProperties;
import org.savara.tools.web.console.client.ConsoleEntryPoint;
import org.savara.tools.web.console.client.NameTokens;
import org.savara.tools.web.console.client.model.activity.Activity;
import org.savara.tools.web.console.client.model.activity.ComponentActivity;
import org.savara.tools.web.console.client.svc.ActivityService;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 11/05/11
 */
public class DesktopPagePresenter extends Presenter<DesktopPagePresenter.DesktopView,
                                                    DesktopPagePresenter.DesktopProxy>{


    private PlaceManager placeManager;

    private ActivityService activityService;

    @Inject
    public DesktopPagePresenter(EventBus eventBus, DesktopView view, DesktopProxy proxy,
                                PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;

        Defaults.setServiceRoot(ConsoleEntryPoint.MODULE.getBootstrapContext().getProperty(ApplicationProperties.SERVICE_URL));
        activityService = GWT.create(ActivityService.class);
    }

    public interface DesktopView extends View {

        public void setPresenter(DesktopPagePresenter presenter);

        public void showEventViewer();

        public void showBizTxnWindow();

        public void showSettingsWindow();

        public void closeAllWindows();
    }


    @ProxyCodeSplit
    @NameToken(NameTokens.DESKTOP_WINDOW)
    public interface DesktopProxy extends ProxyPlace<DesktopPagePresenter> {}


    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }


    @Override
    public void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }


    public void logout() {
        getView().closeAllWindows();
        PlaceRequest loginPlace = new PlaceRequest(NameTokens.LOGIN_VIEW);
        placeManager.revealPlace(loginPlace);
    }

    public void showEventDetailWindow() {
        getView().showEventViewer();
    }

    public void showBusinessTransactionViewerWindow() {
        getView().showBizTxnWindow();
    }


    public void showSettingsWindow() {
        getView().showSettingsWindow();
    }

    private void getData() {

        activityService.getAllActivities(new MethodCallback<List<Activity>>() {
            public void onFailure(Method method, Throwable throwable) {
                Log.error("error getAllActivities :=> " + method.getResponse().getText());
            }

            public void onSuccess(Method method, List<Activity> activities) {
                Log.info("====> " + activities.size());
            }
        });
    }

}
