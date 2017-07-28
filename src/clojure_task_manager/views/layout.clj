(ns clojure_task_manager.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to clojure_task_manager"]
     (include-css "/css/screen.css")]
    [:body body]))
