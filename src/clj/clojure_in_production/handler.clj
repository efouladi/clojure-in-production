(ns clojure-in-production.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [clojure-in-production.layout :refer [error-page]]
            [clojure-in-production.routes.home :refer [home-routes]]
            [clojure-in-production.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [clojure-in-production.env :refer [defaults]]
            [mount.core :as mount]
            [clojure-in-production.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
