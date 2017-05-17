; Copyright (c) 2017 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.forms.hellowindow-test
  (:require [flatgui.samples.forms.helloworld :as hw]
            [flatgui.test :as fgt]
            [clojure.test :as test]))

(fgt/enable-traces-for-failed-tests)

(test/deftest scenario
  (let [container (fgt/create-container #'flatgui.samples.forms.helloworld/root-panel)]
    ;;
    ;; Test initial state
    ;;
    (fgt/wait-for-property container [:main :hello :greeting] :text hw/nogreeting-text)
    ;;
    ;; Simulate left mouse click on [:main :hello :say-hello] checkbox
    ;;
    (fgt/left-click container [:main :hello :say-hello])
    ;;
    ;; Check that [:main :hello :greeting] :text property has changed as a result of the click on checkbox
    ;;
    (fgt/wait-for-property container [:main :hello :greeting] :text hw/greeting-text)
    ;;
    ;; Click and check once again, this time using screen coords instead of specifying a known target
    ;;
    (fgt/left-click container (+ hw/win-x hw/chk-x) (+ hw/win-y hw/chk-y))
    (fgt/wait-for-property container [:main :hello :greeting] :text hw/nogreeting-text)))