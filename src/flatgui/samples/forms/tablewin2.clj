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
            [flatgui.paint :as fgp]))

(def col-count 30)
(def row-count 60)

(def header-model-pos
  [(mapv identity (range 0 col-count))
   (mapv identity (range 0 row-count))])

(def header-model-size
  [(mapv (fn [_] 1) (range 0 col-count))
   (mapv (fn [_] 1) (range 0 row-count))])


(fgp/deflookfn cell-look (:theme :id :text)
 [;(fgp/call-look flatgui.skins.flat/component-look)
  (awt/setColor (:prime-1 theme))
  (awt/drawRoundRect (awt/+px 0 2) (awt/+px 0 2) (awt/-px w- 4) (awt/-px h- 4) 0.125)
  (awt/drawString text 0.375 0.375)])

(fg/defevolverfn cell-text-evolver :text
  (let [mc (get-property [:this] :model-coord)]
    (str (first mc) "-" (second mc))))

(def democell
  (merge-with fg/properties-merger  cell/cell
                {:skin-key false
                 :look cell-look
                 :text "?"
                 :evolvers {:text cell-text-evolver}}))

;(fg/defevolverfn :clip-size (get-property [] :clip-size))

(fg/defevolverfn scroll-cs-evolver :clip-size
  (let [ win-size (get-property [] :clip-size)]
    (m/defpoint (- (m/x win-size) 0.25) (- (m/y win-size) 0.625))))

(fg/defwidget "demotable"
  {:header-model-pos header-model-pos
   :header-model-size header-model-size
   :cell-prototype democell
   :position-matrix m/identity-matrix
   ;:clip-size (m/defpoint 1 1)
   :background (awt/color 32 16 8)
   :evolvers {:clip-size scrollpanel/scrollpanelcontent-clip-size-evolver}} ;TODO bug: this evolver is not taken from scrollpanelcontent
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