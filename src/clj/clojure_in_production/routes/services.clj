(ns clojure-in-production.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(def entries (atom []))

(defn get-timestamp [] (System/currentTimeMillis))
;(defn get-timestamp [] (str (java.time.LocalDateTime/now)))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["guestbook"]
 
    (GET "/entries" []
      :summary      "all guestbook entries"
      (ok @entries))

    (GET "/uuid" []
      :return String
      (ok (str (java.util.UUID/randomUUID))))

    (POST "/post" []
      :body-params [message :- String, user :- String]
      (ok (swap! entries conj {:message message :user user :timestamp (get-timestamp)})))))
