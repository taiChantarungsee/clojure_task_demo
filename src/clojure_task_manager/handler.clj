(ns clojure_task_manager.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.util.middleware :as noir-middleware]
            [clojure_task_manager.routes.home :refer [home-routes]]))

(defn init []
  (println "clojure_task_manager is starting"))

(defn destroy []
  (println "clojure_task_manager is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (noir-middleware/app-handler [home-routes app-routes])

  ;;(-> (routes home-routes app-routes)
  ;;    (handler/site)
  ;;    (wrap-base-url)))
