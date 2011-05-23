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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jeff Yu
 * @date: 19/05/11
 */
public class BootstrapContext implements ApplicationProperties{

    private Map<String, String> ctx = new HashMap<String, String>();

    @Inject
    public BootstrapContext() {
         String svcUrl = GWT.isScript() ? getBaseUrl() + "savara-console-server" : "http://127.0.0.1:8888/app/proxy";
         setProperty(SERVICE_URL, svcUrl);

         Log.info("Restful Service URL is: " + svcUrl);
    }


    private String getBaseUrl() {
        // extract host
        String base = GWT.getHostPageBaseURL();
        String protocol = base.substring(0, base.indexOf("//")+2);
        String remainder = base.substring(base.indexOf(protocol)+protocol.length(), base.length());
        String host = remainder.indexOf(":")!=-1 ?
                remainder.substring(0, remainder.indexOf(":")) :
                remainder.substring(0, remainder.indexOf("/"));

        // default url
        return protocol + host + ":8080/";

    }

    public void setProperty(String key, String value) {
        ctx.put(key, value);
    }

    public String getProperty(String key) {
        return ctx.get(key);
    }

    public boolean hasProperty(String key) {
        return ctx.containsKey(key);
    }

    public void removeProperty(String key) {
        ctx.remove(key);
    }
}
