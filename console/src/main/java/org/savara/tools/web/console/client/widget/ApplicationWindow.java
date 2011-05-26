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

import com.allen_sauer.gwt.log.client.Log;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.*;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * This class takes care of minimizing the window, clicking the toolstrip button and close window event handler.
 * This will be base class for the each application that will be pop up.
 * @author: Jeff Yu
 * @date: 14/05/11
 */
public class ApplicationWindow {

     protected Window window;

     protected ToolStripButton toolBarBtn;

     protected ToolStrip strip;

     public ApplicationWindow(final String title, final String iconUrl, final ToolStrip strip) {
         window = new Window();
         window.setTitle(title);
         window.setHeaderIcon(iconUrl);
         window.setWidth("80%");
         window.setHeight(600);
         window.setCanDragResize(true);
         window.setCanDragReposition(true);
         window.setShowMaximizeButton(true);

         window.addMinimizeClickHandler(new MinimizeClickHandler() {
             public void onMinimizeClick(MinimizeClickEvent minimizeClickEvent) {
                 window.hide();
             }
         });


         window.addCloseClickHandler(new CloseClickHandler() {
             public void onCloseClick(CloseClientEvent event) {
                 strip.removeMember(toolBarBtn);
                 window.destroy();
                 window = null;
             }
         });

         window.centerInPage();

         toolBarBtn = new ToolStripButton();
         toolBarBtn.setTitle(title);
         toolBarBtn.setIcon(iconUrl);
         toolBarBtn.setWidth("60");

         toolBarBtn.addClickHandler(new ClickHandler() {
             public void onClick(ClickEvent clickEvent) {
                 if (window.isVisible()) {
                     window.hide();
                 } else {
                     window.restore();
                     window.show();
                 }
             }
         });

         this.strip = strip;

     }


    public Window getWindow() {
        return window;
    }


    public ToolStripButton getToolStripButton() {
        return toolBarBtn;
    }

    public void show() {
        strip.addButton(toolBarBtn);
        window.show();
    }

    public void close(){
        if (strip != null && strip.hasMember(toolBarBtn)) {
            strip.removeMember(toolBarBtn);
        }
        if (window != null) {
            window.destroy();
        }
        window = null;
        toolBarBtn = null;
    }

}
