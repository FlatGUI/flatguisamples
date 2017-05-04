; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.tablewin2
  (:require [flatgui.util.matrix :as m]
            [flatgui.base :as fg]
            [flatgui.awt :as awt]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.table2.table :as table]
            [flatgui.widgets.table2.cell :as cell]
            [flatgui.widgets.scrollpanel :as scrollpanel]
            [flatgui.widgets.checkbox :as checkbox]
            [flatgui.widgets.abstractbutton :as abstractbutton]
            [flatgui.paint :as fgp]))

(def col-count 30)
(def row-count 60)

(def header-model-pos
  [(mapv identity (range 0 col-count))
   (mapv identity (range 0 row-count))])

(def header-model-size
  [(mapv (fn [_] 1) (range 0 col-count))
   (mapv (fn [_] 1) (range 0 row-count))])

(fgp/deflookfn cell-look1 (:theme :id :text :background :widget-type)
 [;(fgp/call-look flatgui.skins.flat/component-look)
  (awt/setColor background)
  (awt/fillRoundRect (awt/+px 0 2) (awt/+px 0 2) (awt/-px w- 4) (awt/-px h- 4) 0.125)
  (awt/setColor (:prime-1 theme))
  (awt/drawRoundRect (awt/+px 0 2) (awt/+px 0 2) (awt/-px w- 4) (awt/-px h- 4) 0.125)
  (awt/drawString text 0.375 0.375)
  (awt/drawString widget-type 0.0 0.625)])

(fgp/deflookfn cell-look2 (:theme :id :text :background :widget-type)
 [;(fgp/call-look flatgui.skins.flat/component-look)
  (awt/setColor background)
  (awt/fillRoundRect (awt/+px 0 2) (awt/+px 0 2) (awt/-px w- 4) (awt/-px h- 4) 0.125)
  (awt/setColor (:prime-1 theme))
  (awt/drawRoundRect (awt/+px 0 2) (awt/+px 0 2) (awt/-px w- 4) (awt/-px h- 4) 0.125)
  (awt/drawString text 0.375 0.375)
  (awt/drawString widget-type 0.0 0.625)])


(fg/defevolverfn cell-text-evolver :text
  (let [mc (get-property [:this] :model-coord)]
    (str (first mc) "-" (second mc))))

(def cell-exp-h 3)
(def cell-col-h 1)

(fg/defevolverfn checkbox-pressed-evolver :pressed
  (if (= [] (get-reason))
    (let [as (get-property [] :atomic-state)]
      (if (= (first (:screen-coord as)) 0);(not= cell/not-in-use-coord (:model-coord as))
        (> (m/y (:clip-size as)) cell-col-h)
        old-pressed))
    (abstractbutton/check-pressed-evolver component)))

(def cell-expand-checkbox
  (fg/defcomponent checkbox/checkbox :exp
    {:clip-size (m/defpoint 0.75 0.25)
     :position-matrix (m/translation 0.125 0.25)
     :evolvers {:pressed checkbox-pressed-evolver}}))

(fg/defevolverfn :atomic-state
  (let [as (cell/atomic-state-evolver component)]
    (if (and
          (= (first (:screen-coord as)) 0)
          (= [:this :exp] (get-reason)))
      (if (get-property [:this :exp] :pressed)
        (assoc as :clip-size (m/defpoint (m/x (:clip-size as)) cell-exp-h))
        (assoc as :clip-size (m/defpoint (m/x (:clip-size as)) cell-col-h)))
      as)))

(def regular-color (awt/color 8 16 32))
(def selected-color (awt/color 8 64 96))
(fg/defevolverfn :background
  (if (:selected (get-property [:this] :atomic-state))
    selected-color
    regular-color))

(def democell1
  (merge-with fg/properties-merger cell/cell
                {:skin-key false
                 :look cell-look1
                 :text "?"
                 :widget-type "democell1"
                 :children {:exp cell-expand-checkbox}
                 :evolvers {:text cell-text-evolver
                            :atomic-state atomic-state-evolver
                            :background background-evolver
                            ;; Strip some unneeded features
                            :look nil
                            :coord-map nil
                            :has-mouse nil
                            :enabled nil}}))

(def democell2
  (merge-with fg/properties-merger cell/cell
              {:skin-key false
               :look cell-look2
               :text "x"
               :widget-type "democell2"
               :evolvers {:text cell-text-evolver
                          :atomic-state atomic-state-evolver
                          :background background-evolver
                          ;; Strip some unneeded features
                          :look nil
                          :coord-map nil
                          :has-mouse nil
                          :enabled nil}}))

(fg/defevolverfn scroll-cs-evolver :clip-size
  (let [ win-size (get-property [] :clip-size)]
    (m/defpoint (- (m/x win-size) 0.25) (- (m/y win-size) 0.625))))

(def cell-prototypes
  (let [v (mapv (fn [_] nil) (range 0 col-count))]
    (assoc v 0 democell1)))

(fg/defwidget "demotable"
  {:header-model-loc {:positions header-model-pos
                      :sizes header-model-size}
   :selection [nil nil]
   :model-column->cell-prototype cell-prototypes
   :cell-prototype democell2
   :position-matrix m/identity-matrix
   :background (awt/color 32 16 8)
   :evolvers {:header-model-loc table/shift-header-model-loc-evolver
              :clip-size scrollpanel/scrollpanelcontent-clip-size-evolver
              ;:selection table/direct-selection
              :selection table/cbc-selection
              }}
  scrollpanel/scrollpanelcontent table/table)

(def table-window
  (fg/defcomponent window/window :table-window
    {:clip-size (m/defpoint 4 6)
     :background (awt/color 8 16 32)
     :position-matrix (m/translation 2.5 0)
     :text "Table Test"}
    (fg/defcomponent scrollpanel/scrollpanel :scroll
      {:position-matrix (m/translation 0.125 0.5)
       :clip-size (m/defpoint 1 1)
       :evolvers {:clip-size scroll-cs-evolver}
       :children {:content-pane (fg/defcomponent demotable :content-pane {})}})))