(ns clojure_task_manager.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer :all]
            [hiccup.element :refer [link-to]]
            [noir.session :as session]))

(defn make-menu [& items]
[:div (for [item items] [:div.menuitem item])])

(defn guest-menu []
  (make-menu
    (link-to "/" "home")
    (link-to "/register" "register")
    (form-to [:post "/login"]
      (text-field {:placeholder "screen name"} "id")
      (password-field {:placeholder "password"} "pass")
      (submit-button "login"))))

(defn user-menu [user]
  (make-menu
    (link-to "/" "home")
    (link-to "/upload" "upload images")
    (link-to "/logout" (str "logout " user))))

(defn base [& content]
  (html5
    [:head
     [:title "Welcome to picture-gallery"]
     (include-css "/css/screen.css")]
    [:body content]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to clojure_task_manager"]
     (include-css "/css/screen.css")]
    [:body body]))

(defn common [& content]
	(base
		(if-let [user (session/get :user)]
      (user-menu user)
      (guest-menu))
		[:div.content content]))

(defn common [& content]
  (base
    (if-let [user (session/get :user)]
      (list
    [:div (link-to "/upload" "upload images")]
    [:div (link-to "/logout" (str "logout " user))])
    [:div (link-to "/register" "register")
      (form-to [:post "/login"]
      (text-field {:placeholder "screen name"} "id")
      (password-field {:placeholder "password"} "pass")
      (submit-button "login"))])
  content))