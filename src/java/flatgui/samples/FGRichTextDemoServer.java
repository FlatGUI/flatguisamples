/*
 * Copyright Denys Lebediev
 */
package flatgui.samples;

import flatgui.run.FGServerRunner;
import flatgui.util.resourceserver.FGResourceServicePublisher;

/**
 * @author Denis Lebedev
 */
public class FGRichTextDemoServer
{
    private static final int PORT = 13100;

    public static void main(String[] args) throws Exception
    {
        FGServerRunner.runApplication(FGRichTextDemo.CONTAINER_NS, FGRichTextDemo.CONTAINER_VAR_NAME, PORT,
                null,
                server -> server.setCustomServlet("media", new FGResourceServicePublisher(null)));
    }
}