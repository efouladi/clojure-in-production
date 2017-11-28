(ns clojure-in-production.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[clojure-in-production started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[clojure-in-production has shut down successfully]=-"))
   :middleware identity})
