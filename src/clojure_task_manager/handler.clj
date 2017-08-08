(ns clojure_task_manager.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [clojure_task_manager.routes.auth :refer [auth-routes]]
            [clojure_task_manager.routes.gallery :refer [gallery-routes]]
            [clojure_task_manager.routes.home :refer [home-routes]]
            [clojure_task_manager.routes.upload :refer [upload-routes]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.util.middleware :as noir-middleware]
            [noir.session :as session]))

(defn init []
  (println "clojure_task_manager is starting"))

(defn destroy []
  (println "clojure_task_manager is shutting down"))

;; The underscore indicates that any arguments will be ignored
(defn user-page [_]
  (session/get :user))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (noir-middleware/app-handler [auth-routes home-routes upload-routes gallery-routes
    app-routes]
    :access-rules [user-page]))

  ;;(-> (routes home-routes app-routes)
  ;;    (handler/site)
  ;;    (wrap-base-url)))
