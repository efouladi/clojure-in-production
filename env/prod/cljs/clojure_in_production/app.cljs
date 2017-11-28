(ns clojure-in-production.app
  (:require [clojure-in-production.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
