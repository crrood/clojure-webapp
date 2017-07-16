(ns clojure-webapp.web
	(:require 	[compojure.core :refer [defroutes GET]]
				[ring.adapter.jetty :as ring]
				[hiccup.page :as page]
				[compojure.route :as route]
				[ring.middleware.defaults :refer [wrap-defaults site-defaults]]
				[clojure-webapp.controllers.tallies :as tallies]
				[clojure-webapp.view.layout :as layout]
				[clojure-webapp.models.migration :as schema])
	(:gen-class))

(defn index []
	(page/html5
		[:head
			[:title "A clojure webapp"]]
		[:body
			[:div {:id "content"} "Hello World!"]]))

(defroutes routes
	tallies/routes
	(route/resources "/")
	(route/not-found (layout/four-oh-four)))

(def application (wrap-defaults routes site-defaults))

(defn start [port]
	ring/run-jetty #'routes {:port port :join? false})

(defn -main []
	(schema/migrate)
	(let [port (Integer. (or (System/getenv "PORT") "8080"))]
		(start port)))