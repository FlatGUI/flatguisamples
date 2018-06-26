; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.tablewin
  (:require [flatgui.util.matrix :as m]
            [flatgui.base :as fg]
            [flatgui.awt :as awt]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.table :as table]
            [flatgui.widgets.table.commons :as tcom] ;TODO common table API instead of requiring several namespaces
            [flatgui.widgets.table.header :as th]
            [flatgui.widgets.table.contentpane :as tcpane]
            [flatgui.widgets.table.columnheader :as tcolh]
            [flatgui.widgets.table.cell :as tcell]))

(def row-count 500)

(def header-ids [:cola])

(def header-aliases
  {:cola "A"})

;;; This clip size evolver for blotter table
;;; resizes table together with window resize
(fg/defevolverfn blotter-table-cs-evolver :clip-size
                 (let [ win-size (get-property [] :clip-size)]
                   (m/defpoint (- (m/x win-size) 0.25) (- (m/y win-size) 0.5))))

(def blotter-table
  (fg/defcomponent table/table :table
    {:clip-size (m/defpoint 7.75 5.5 0)
     :position-matrix (m/translation 0.125 0.375)
     :header-ids header-ids
     :header-aliases header-aliases
     :value-provider (fn [model-row _model-col] (str model-row))
     :evolvers {:clip-size blotter-table-cs-evolver}}
  (th/deftableheader
    (tcolh/defcolumn :cola [:sorting] {:clip-size (m/defpoint 3 tcom/default-row-height)})
    )
  (tcpane/deftablecontent row-count)))

(def table-window
  (fg/defcomponent window/window :blotter
    {:clip-size (m/defpoint 4 6 0)
     :position-matrix (m/translation 2.5 0)
     :text "Table Test"}
    blotter-table))