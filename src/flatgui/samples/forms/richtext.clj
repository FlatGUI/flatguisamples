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
            [flatgui.widgets.panel :as panel]
            [flatgui.widgets.button :as button])
  (:import (flatgui.core IFGEvolveConsumer)))

(def sample-text "If you can fill the unforgiving minute With sixty seconds’ worth of distance run, Yours is the Earth and everything that’s in it, And — which is more — you’ll be a Man, my son!")

(def gap 0.125)

(def tooppanel-h 0.5)

(fg/defaccessorfn header-&-toolpanel-h [compoment]
  (+ (get-property [] :header-h) tooppanel-h))

(fg/defevolverfn text-clip-size :clip-size
  (let [s (get-property [] :clip-size)]
    (m/defpoint (- (m/x s) gap gap) (- (m/y s) gap gap (header-&-toolpanel-h component)))))

(fg/defevolverfn text-position-matrix :position-matrix (m/translation gap (+ (header-&-toolpanel-h component) gap)))

(fg/defevolverfn :rendition
  (if (and (= (get-reason) [:text->]) (get-property [:text->] :pressed))
    (textrich/rendition-input-data-evolver component old-rendition {:type :string :data "TEST"})
    (textrich/rendition-evolver component)))

(def window
  (fg/defcomponent
    window/window
    :win
    {:clip-size (m/defpoint 3 3)
     :position-matrix (m/translation 1 1)
     :text "Rich Text Example"}

    (fg/defcomponent button/button :text->
      {:position-matrix (m/translation 0.125 0.5)
       :clip-size (m/defpoint 0.375 0.375)
       :text "T"})

    (fg/defcomponent
      textrich/textrich
      :text
      {:rendition (assoc textrich/empty-rendition :glyphs (map textrich/char-glyph sample-text))
       :evolvers {:clip-size text-clip-size
                  :position-matrix text-position-matrix
                  :rendition rendition-evolver}})))

(def root-panel
  (fg/defcomponent
    panel/panel
    :main
    {:clip-size  (m/defpoint 6 4)
     :background (awt/color 9 17 26)
     :font "bold 14px sans-serif"
     :evolvers {:clip-size component/clip-size-to-host}}
    window))

;;;
;;; Tool button bindings: simulate input data
;;;

;(defn create-addtopic-consumer [jd-node]
;  (reify IFGEvolveConsumer
;    (getTargetPath [_this] [:mainframe :topiclist-tab :add-topic-button])
;    (getTargetProperties [_this] [:pressed])
;    (acceptEvolveResult [_this sessionId container _reason]
;      (if (:pressed container)
;        (let [topic-count (.getTopicCount jd-node)
;              topic-name (str "topic-" topic-count) ]
;          (do
;            (println "[JD]" sessionId "Adding topic " topic-name)
;            (.addTopic jd-node topic-name)))))))
;
;
;(defn setup-bindings [container]
;  (println "[JD] Initializing control binding consumers...")
;  (.addEvolveConsumer container (addtopic/create-addtopic-consumer *jd-node*)))