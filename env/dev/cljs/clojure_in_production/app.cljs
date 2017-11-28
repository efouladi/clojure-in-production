(ns ^:figwheel-no-load clojure-in-production.app
  (:require [clojure-in-production.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
