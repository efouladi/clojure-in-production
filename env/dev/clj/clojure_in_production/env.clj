(ns clojure-in-production.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [clojure-in-production.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[clojure-in-production started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[clojure-in-production has shut down successfully]=-"))
   :middleware wrap-dev})
