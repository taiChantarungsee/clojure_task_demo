(ns clojure_task_manager.models.db
	(:require [clojure.java.jdbc :as sql]))

(def db
	{:subprotocol "postgresql"
	 :subname "//localhost/tasks"
 	 :user "admin"
	 :password "admin"})

(defn create-users-table []
	(sql/with-connection db
	(sql/create-table
		:users
		[:id "varchar(32) PRIMARY KEY"]
		[:pass "varchar(100)"])))

(defn get-user [id]
	(sql/with-connection db
	(sql/with-query-results
	res ["select * from users where id = ?" id] (first res))))