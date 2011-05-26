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
package org.savara.tools.web.server;

import org.savara.activity.model.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 17/05/11
 */
@Path("/activity")
public class ActivityService {

    @GET
    @Path("components")
    @Produces("application/json")
    public List<ComponentActivity> getAllComponentActivity() throws Exception {
       List<ComponentActivity> acts = new ArrayList<ComponentActivity>();
       ComponentActivity act = new ComponentActivity();
       act.setComponentId("componentId");
       act.setComponentName("component name");
       act.setId("2053");
       act.setInstanceId("1");
       act.setStatus(Status.STARTED);
       act.setTimestamp(new Date());
       acts.add(act);


       ComponentActivity act2 = new ComponentActivity();
       act2.setComponentName("component name2");
       act2.setComponentId("Id2");
       act2.setInstanceId("instance Id 2");
       act2.setStatus(Status.FINISHED);

       acts.add(act2);

       return acts;
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public List<Activity> getAllActivities() throws Exception {
       List<Activity> result = new ArrayList<Activity>();
       ComponentActivity act = new ComponentActivity();
       act.setComponentId("componentId");
       act.setComponentName("component name");
       act.setId("2053");
       act.setInstanceId("1");
       act.setStatus(Status.STARTED);
       act.setTimestamp(new Date());

       result.add(act);

       InteractionActivity interactionAct = new InteractionActivity();
       interactionAct.setId("interaction act Id");
       interactionAct.setId("2054");
       interactionAct.setTimestamp(new Date());
       interactionAct.setExchangeType(ExchangeType.UNDEFINED);

       result.add(interactionAct);


       ComponentActivity act2 = new ComponentActivity();
       act2.setComponentId("componentId");
       act2.setComponentName("component name");
       act2.setId("20532");
       act2.setInstanceId("2");
       act2.setStatus(Status.STARTED);
       act2.setTimestamp(new Date());
       result.add(act2);

       return result;
    }
}
