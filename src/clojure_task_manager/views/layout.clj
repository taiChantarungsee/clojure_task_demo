(ns clojure_task_manager.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer :all]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to clojure_task_manager"]
     (include-css "/css/screen.css")]
    [:body body]))

(defn common [& content]
	(base
		(if-let [user (session/get :user)]
			[:p user] (link-to "/register" "register"))
		content))

(defn common [& content]
 (base
  (if-let [user (session/get :user)]
   [:div (link-to "/logout" (str "logout " user))]
   [:div (link-to "/register" "register")
    (form-to [:post "/login"]
      (text-field {:placeholder "screen name"} "id")
      (password-field {:placeholder "password"} "pass")
      (submit-button "login"))])
  content))