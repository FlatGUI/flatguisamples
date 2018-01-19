; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.textfield2sample
  (:require [flatgui.base :as fg]
            [flatgui.util.matrix :as m]
            [flatgui.awt :as awt]
            [flatgui.widgets.component :as component]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.textfield2 :as textfield2]
            [flatgui.widgets.panel :as panel]
            [flatgui.widgets.button :as button])

  (:import (flatgui.util.resourceserver FGLocalResourceServer)))

; Apostrophe 8185, DASH
;(def sample-text "If you can fill the unforgiving minute With sixty seconds’ worth of distance run, Yours is the Earth and everything that’s in it, And — which is more — you’ll be a Man, my son!")
;(def sample-text "If you can fill the unforgiving minute With sixty seconds' worth of distance run, Yours is the Earth and everything that's in it, And - which is more - you'll be a Man, my son!")
(def sample-text "")

(def gap 0.125)

(def tooppanel-h 0.5)

(def media-server (FGLocalResourceServer.))

(fg/defaccessorfn header-&-toolpanel-h [compoment]
  (+ (get-property [] :header-h) tooppanel-h))

(fg/defevolverfn text-clip-size :clip-size
  (let [s (get-property [] :clip-size)]
    (m/defpoint (- (m/x s) gap gap) (- (m/y s) gap gap (header-&-toolpanel-h component)))))

(fg/defevolverfn text-position-matrix :position-matrix (m/translation gap (+ (header-&-toolpanel-h component) gap)))

;(fg/defevolverfn :rendition
;  (cond
;
;    (and (= (get-reason) [:text->]) (get-property [:text->] :pressed))
;    (textrich/rendition-input-data-evolver component old-rendition {:type :string :data "TEST"})
;
;    (and (= (get-reason) [:image->]) (get-property [:image->] :pressed))
;    (textrich/rendition-input-data-evolver component old-rendition {:type :image
;                                                                    :data "imagesrv/text/icon.png";"http://flatgui.org/resources/icon.png"
;                                                                    :size {:w 0.5 :h 0.5}})
;
;    (and (= (get-reason) [:video->]) (get-property [:video->] :pressed))
;    (textrich/rendition-input-data-evolver component old-rendition {:type :video :data "imagesrv/text/SampleVideo_360x240_2mb.mp4" :size {:w 1.0 :h 1.0}})
;
;    :else
;    (textrich/rendition-evolver component)))

(def window
  (fg/defcomponent
    window/window
    :win
    {:clip-size (m/defpoint 3 3)
     :position-matrix (m/translation 1 1)
     :text "Rich Text Example (2)"}

    (fg/defcomponent button/button :text->
      {:position-matrix (m/translation 0.125 0.5)
       :clip-size (m/defpoint 0.375 0.375)
       :text "T"})

    (fg/defcomponent button/button :image->
      {:position-matrix (m/translation 0.625 0.5)
       :clip-size (m/defpoint 0.375 0.375)
       :text "I"})

    (fg/defcomponent button/button :video->
      {:position-matrix (m/translation 1.5 0.5)
       :clip-size (m/defpoint 0.375 0.375)
       :text "V"})

    (fg/defcomponent
      textfield2/textfield
      :text
      {:media-server media-server
       :evolvers {:clip-size text-clip-size
                  :position-matrix text-position-matrix
                  }})))

(def root-panel
  (fg/defcomponent
    panel/panel
    :main
    {:clip-size  (m/defpoint 6 4)
     :background (awt/color 9 17 26)
     :font "bold 14px sans-serif"
     :evolvers {:clip-size component/clip-size-to-host}}
    window))