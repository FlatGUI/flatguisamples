/*
 * Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file LICENSE at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 */
package flatgui.samples;

import flatgui.run.FGDesktopRunner;
import flatgui.run.FGRunUtil;

import java.awt.*;

/**
 * @author Denis Lebedev
 */
public class FGTextField2Demo
{
    public static final String CONTAINER_NS = "flatgui.samples.forms.textfield2sample";
    public static final String CONTAINER_VAR_NAME = "root-panel";
    public static final String ICON_RESOURCE = "flatgui/samples/images/icon_FlatGUI_32x32.png";
    public static final String TITLE = "FlatGUI Demo - Rich Text (2)";

    public static void main(String[] args)
    {
        Image logoIcon = FGRunUtil.loadIconFromClasspath(ICON_RESOURCE, e -> e.printStackTrace());
        FGDesktopRunner.runDesktop(CONTAINER_NS, CONTAINER_VAR_NAME, TITLE, logoIcon, e -> e.printStackTrace());
    }
}
