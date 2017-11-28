(ns user
  (:require 
            [mount.core :as mount]
            [clojure-in-production.figwheel :refer [start-fw stop-fw cljs]]
            clojure-in-production.core))

(defn start []
  (mount/start-without #'clojure-in-production.core/repl-server))

(defn stop []
  (mount/stop-except #'clojure-in-production.core/repl-server))

(defn restart []
  (stop)
  (start))


