(defproject clojure_task_manager "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [ring-server "0.4.0"]
		             [lib-noir "0.9.9"]
		             [liberator "0.15.1"]
		             [cheshire "5.7.1"]
		             [org.clojure/java.jdbc "0.7.0"]
 	               [postgresql/postgresql "9.1-901.jdbc4"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler clojure_task_manager.handler/app
         :init clojure_task_manager.handler/init
         :destroy clojure_task_manager.handler/destroy}
  :repl-options {:init-ns clojure_task_manager.models.db}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.5.1"]]}})