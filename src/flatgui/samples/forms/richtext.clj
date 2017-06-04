; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.richtext
  (:require [flatgui.base :as fg]
            [flatgui.util.matrix :as m]
            [flatgui.awt :as awt]
            [flatgui.widgets.component :as component]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.textrich :as textrich]
            [flatgui.widgets.panel :as panel]))

(def sample-text "If you can fill the unforgiving minute With sixty seconds’ worth of distance run, Yours is the Earth and everything that’s in it, And — which is more — you’ll be a Man, my son!")

(def gap 0.125)

(fg/defevolverfn text-clip-size :clip-size
  (let [s (get-property [] :clip-size)]
    (m/defpoint (- (m/x s) gap gap) (- (m/y s) gap gap (get-property [] :header-h)))))

(fg/defevolverfn text-position-matrix :position-matrix (m/translation gap (+ (get-property [] :header-h) gap)))

(def window
  (fg/defcomponent
    window/window
    :win
    {:clip-size (m/defpoint 3 3)
     :position-matrix (m/translation 1 1)
     :text "Rich Text Example"}

    (fg/defcomponent
      textrich/textrich
      :text
      {:model (assoc textrich/empty-model :glyphs (map textrich/char-glyph sample-text))
       :evolvers {:clip-size text-clip-size
                  :position-matrix text-position-matrix}})))

(def root-panel
  (fg/defcomponent
    panel/panel
    :main
    {:clip-size  (m/defpoint 6 4)
     :background (awt/color 9 17 26)
     :font "bold 14px sans-serif"
     :evolvers {:clip-size component/clip-size-to-host}}
    window))