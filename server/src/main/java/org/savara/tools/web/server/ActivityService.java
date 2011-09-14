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
    @Path("all")
    @Produces("application/json")
    public List<Activity> getAllActivities() throws Exception {
       List<Activity> result = new ArrayList<Activity>();
       Activity act = new Activity();
       act.setId("2053");
       act.setDescriptionCode("Activity Test 1");
       act.setTimestamp(new Date());
       
       ComponentId cid = new ComponentId();
       cid.setApplication("Web application");
       cid.setComponent("web");
       cid.setComponentType("Component Type");
       cid.setInstanceId("instanceId1");
       cid.setLocation("localhost");
       cid.setLocationType("IP Adress");
       
       act.setSource(cid);
       
       InteractionActivity ia = new InteractionActivity();
       ia.setOperationName("operationName");
       act.setType(ia);

       result.add(act);

       Activity act2 = new Activity();
       act2.setId("20531");
       act2.setDescriptionCode("Activity Test 2");
       act2.setTimestamp(new Date());
       
       ComponentId cid2 = new ComponentId();
       cid2.setApplication("Web application2");
       cid2.setComponent("web2");
       cid2.setComponentType("Component Type2");
       cid2.setInstanceId("instanceId2");
       cid2.setLocation("localhost");
       cid2.setLocationType("IP Adress");
       
       act2.setSource(cid2);
       
       InteractionActivity ia2 = new InteractionActivity();
       ia2.setOperationName("operationName2");
       act2.setType(ia2);

       result.add(act2);

       return result;
    }
}
