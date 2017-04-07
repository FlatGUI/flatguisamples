; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns table2
  (:require [flatgui.skins.flat]
            [flatgui.base :as fg]
            [flatgui.awt :as awt]
            [flatgui.util.matrix :as m]
            [flatgui.widgets.panel :as panel]
            [flatgui.samples.forms.tablewin2 :as tablewin2]
            [flatgui.test]))

(def tablepanel2
  (fg/defcomponent panel/panel :app-panel
    {:clip-size (m/defpoint 16 11.5)
     :position-matrix (m/translation 0 0)
     :background (awt/color 0 38 70)
     :closed-focus-root true
     :focus-state {:mode :has-focus
                   :focused-child nil}}
     tablewin2/table-window))