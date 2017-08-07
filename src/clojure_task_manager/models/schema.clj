;; This namespace will hold our table definitions and act as documentation for out models.

(ns clojure_task_manager.models.schema
	(:require [clojure_task_manager.models.db :refer :all]
			  [clojure.java.jdbc :as sql]))

(defn create-users-table []
	(sql/with-connection db
		(sql/create-table
			:users
			[:id "varchar(32) PRIMARY KEY"]
			[:pass "varchar(100)"])))

(defn create-images-table []
	(sql/with-connection db
		(sql/create-table
			:images
			[:userid "varchar(32)"]
			[:name "varchar(100)"])))