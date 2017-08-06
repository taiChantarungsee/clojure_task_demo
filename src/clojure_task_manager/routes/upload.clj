;; note the .util
(ns clojure_task_manager.routes.upload
 (:require [compojure.core :refer [defroutes GET POST]]
	[hiccup.form :refer :all]
	[hiccup.element :refer [image]]
	[hiccup.util :refer [url-encode]]
	[picture-gallery.views.layout :as layout]
	[noir.io :refer [upload-file resource-path]]
	[noir.session :as session]
	[noir.response :as resp]
	[noir.util.route :refer [restricted]]
	[clojure.java.io :as io]
	[ring.util.response :refer [file-response]]
	[clojure_task_manager.models.db :as db]
	[clojure_task_manager.util :refer [galleries gallery-path]
	[clojure_task_manager.routes.upload :refer [gallery-path]]])

 (:import [java.io File FileInputStream FileOutputStream]
	[java.awt.image AffineTransformOp BufferedImage]
	java.awt.RenderingHints
	java.awt.geom.AffineTransform
	java.io.file
	javax.imageio.ImageIO))

(defn gallery-path []
  (str galleries File/separator (session/get :user)))

(def galleries "galleries")

(defn upload-page [info]
  (layout/common
    [:h2 "Upload an image"]
    [:p info]
    (form-to {:enctype "multipart/form-data"}
             [:post "/upload"]
             (file-upload :file)
             (submit-button "upload"))))

(defn handle-upload [{:keys [filename] :as file}]
  (upload-page
    (if (empty? filename)
	  "please select a file to upload"

	  (try
	    (noir.io/upload-file (gallery-path) file :create-path? true)
	    (image {:height "150px"}
 		  (str "/img/" (url-encode filename)))
		(catch Exception ex
		  (str "error uploading file " (.getMessage ex)))))))

(defn serve-file [user-id file-name]
  (file-response (str galleries File/separator user-id File/separator file-name)))

(defroutes upload-routes
  (GET "/img/:user-id/:file-name" [user-id file-name]
  (serve-file user-id file-name))
  (GET "/upload" [info] (restricted (upload-page info)))
  (POST "/upload" [file] (restricted (handle-upload file))))