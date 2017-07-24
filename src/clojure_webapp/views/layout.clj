(ns clojure-webapp.views.layout
	(:require [hiccup.page :as h]))

(defn common [title & body]
	(h/html5
		[:head
			[:meta {:charset "utf-8"}]
			[:meta {:name "viewport" :content
				"width=device-width, initial-scale=1, maximum-scale=1"}]
			[:title title]
			(h/include-css "/stylesheets/base.css")]
		[:body
			[:div {:id "header"}
				[:h1 {:class "title"} "Clojure-counter"]]
			[:div {:id "content"} body]]))

(defn four-oh-four []
	(common "Page Not Found"
		[:div {:id "four-oh-four"}
			"The page you requested could not be found"]))