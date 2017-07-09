(ns clojure-webapp.web
	(:require [compojure.core :refer [defroutes GET]]
	[ring.adapter.jetty :as ring]
	[hiccup.page :as page]))

(defn index []
	(page/html5
		[:head
			[:title "A clojure webapp"]]
		[:body
			[:div {:id "content"} "Hello World!"]]))

(defroutes routes
	(GET "/" [] (index)))

(defonce server (ring/run-jetty #'routes {:port 8080 :join? false}))

(defn -main []
	(server))