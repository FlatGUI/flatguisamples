; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.helloworld
  (:require [flatgui.skins.flat]
            [flatgui.base :as fg]
            [flatgui.util.matrix :as m]
            [flatgui.awt :as awt]
            [flatgui.widgets.component :as component]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.checkbox :as checkbox]
            [flatgui.widgets.label :as label]
            [flatgui.widgets.panel :as panel]))

(def nogreeting-text "Not now")
(def greeting-text "Hello, world!")

(def win-x 1)
(def win-y 1)
(def chk-x 0.125)
(def chk-y 0.75)

(fg/defevolverfn greeting-evolver :text
  (if (get-property [:say-hello] :pressed)
    greeting-text
    nogreeting-text))

(def hello-window
  (fg/defcomponent
    window/window
    :hello
    {:clip-size (m/defpoint 3 1.5)
     :position-matrix (m/translation win-x win-y)
     :text "Hello World Example"}

    (fg/defcomponent
      checkbox/checkbox
      :say-hello
      {:clip-size (m/defpoint 1.75 0.25)
       :text "Greeting"
       :position-matrix (m/translation chk-x chk-y)})

    (fg/defcomponent
      label/label
      :greeting
      {:text nogreeting-text
       :clip-size (m/defpoint 2.25 0.25)
       :position-matrix (m/translation 1.0 0.75)
       :evolvers {:text greeting-evolver}})))

(def root-panel
  (fg/defcomponent
    panel/panel
    :main
    {:clip-size  (m/defpoint 6 4)
     :background (awt/color 9 17 26)
     :evolvers {:clip-size component/clip-size-to-host}}
    hello-window))