(ns clojure-in-production.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [clojure-in-production.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(def entries (r/atom []))

(defn get-entries [] (GET "/api/entries" {:handler #(reset! entries %)}))

(defn post-entry [m] (POST "/api/post" {:params {:message m :user (session/get :user)}}))

(defn post-message [m]
    [:div.form-inline 
      [:div.form-group
      [:input.form-control {:type "text" 
        :placeholder "Enter a message"
        :value @m
        :on-change #(reset! m (-> % .-target .-value))}] 
      [:button {:class "btn btn-primary" :on-click #(post-entry @m)} "Post"]]])
 
(defn show-entry [entry]
  [:li (:message entry) " - " (:user entry) " - " (:timestamp entry)])    

(defn home-page []
  (let [m (r/atom "")][:div.container
      [:div.row [:h2 "Welcome " (session/get :user)]]
      [:div.row [post-message m]]
      [:div.row [:button {:class "btn btn-primary" :on-click #(get-entries)} "Refresh"]]
      [:div.row
       [:ul (for [entry @entries]
              ^{:key entry} (show-entry entry))]]]))

(defn about-page []
  [:div.container
    [:div.row
    [:div.col-md-12
      [:img {:src (str js/context "/img/warning_clojure.png")}]]]])
      
(def pages
  {:home #'home-page
   :about #'about-page})

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :page :home))

(secretary/defroute "/about" []
  (session/put! :page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

(defn mount-components []
;  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (GET "/api/uuid" {:handler #(session/put! :user %)})
  (get-entries)
  (hook-browser-navigation!)
  (mount-components))
