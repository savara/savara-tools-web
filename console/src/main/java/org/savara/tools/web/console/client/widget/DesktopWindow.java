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

import com.smartgwt.client.widgets.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jeff Yu
 * @date: 3/06/11
 */
public class DesktopWindow extends Window{

    public DesktopWindow() {
        setAutoCenter(true);
        setShowMinimizeButton(true);
        setShowMaximizeButton(true);
        setShowCloseButton(true);
        setCanDragResize(true);
        setKeepInParentRect(true);

        setWidth(800);
        setHeight(600);
    }

    public String getHeaderIcon() {
        return (String) getAttributeAsMap("headerIconProperties").get("src");
    }

    public String getShortcutIcon() {
        return (String) getAttributeAsMap("shortcutIconProperties").get("src");
    }

    public String getMenuIcon() {
        return getHeaderIcon();
    }

    public void setShortcutIcon(String url) {
        Map map = new HashMap<String, String>();
        map.put("src", url);
        setAttribute("shortcutIconProperties", map, true);
    }

}
