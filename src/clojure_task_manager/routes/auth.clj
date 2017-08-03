(ns clojure_task_manager.routes.auth
	(:require [hiccup.form :refer :all]
			  [compojure.core :refer :all]
			  [clojure_task_manager.routes.home :refer :all]
			  [clojure_task_manager.views.layout :as layout]
			  [noir.session :as session]
			  [noir.response :as resp]
			  [noir.validation :as vali]))

(defn registration-page [& [id]]
	(layout/common
		(form-to [:post "/register"]
				 (label "user-id" "user id")
				 (text-field "id" id)
				 [:br]
				 (label "pass" "password")
				 (password-field "pass")
				 [:br]
				 (label "pass1" "retype password")
				 (password-field "pass1")
				 [:br]
				 (submit-button "create account"))))

(defn handle-registration [id pass pass1]
	(session/put! :user is)
	(resp/redirect "/"))

(defroutes auth-routes
	(GET "/register" []
		(registration-page))

	(POST "/register" [id pass pass1]
		(handle-registration id pass pass1)))