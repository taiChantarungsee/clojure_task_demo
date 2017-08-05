(ns clojure_task_manager.routes.auth
	(:require [hiccup.form :refer :all]
			  [compojure.core :refer :all]
			  [clojure_task_manager.routes.home :refer :all]
			  [clojure_task_manager.views.layout :as layout]
			  [noir.session :as session]
			  [noir.response :as resp]
			  [noir.validation :as vali]))

(defn control [id label field]
	(list
		(vali/on-error id error-item)
		label field
		[:br]))

(defn registration-page [& [id]]
 (layout/common
  (form-to [:post "/register"]
		   (control :id
					(label "user-id" "user id")
					(text-field {:tabindex 1} "id" id))
			(control :pass
					(label "pass" "password")
					(password-field {:tabindex 2} "pass"))
			(control :pass1
					(label "pass1" "retype password")
					(password-field {:tabindex 3} "pass1"))
			(submit-button {:tabindex 4} "create account"))))

(defn handle-registration [id pass pass1]
	(if (valid? id pass pass1)
		(do (session/put! :user id)
		  (resp/redirect "/"))
		(registration-page id)))

(defroutes auth-routes
	(GET "/register" []
		(registration-page))

	(POST "/register" [id pass pass1]
		(handle-registration id pass pass1)))

(defn valid? [id pass pass1]
	(vali/rule (vali/has-value? id)
		[:id "User id is required"])
	(vali/rule (vali/min-length? pass 5)
		[:pass "password must be at least 5 characters"])
	(vali/rule (= pass pass1)
		[:pass "entered passwords do not match"])
	(not (vali/errors? :id :pass :pass1)))

(defn error-item [[error]]
	[:div.error error])

