/*
 * Copyright Denys Lebediev
 */
package flatgui.samples;

import flatgui.run.FGServerRunner;
import flatgui.util.resourceserver.FGResourceServicePublisher;

/**
 * @author Denis Lebedev
 */
public class FGTextField2DemoServer
{
    private static final int PORT = 13100;

    public static void main(String[] args) throws Exception
    {
        FGServerRunner.runApplication(FGTextField2Demo.CONTAINER_NS, FGTextField2Demo.CONTAINER_VAR_NAME, PORT,
                null,
                server -> server.setCustomServlet("media", new FGResourceServicePublisher(null)));
    }
}
