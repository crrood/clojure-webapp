(ns clojure-webapp.controllers.tallies
	(:require	[compojure.core :refer [defroutes GET POST]]
				[clojure.string :as str]
				[ring.util.response :as ring]
				[clojure-webapp.views.tallies :as view]
				[clojure-webapp.models.tallies :as model]))

(defn index []
	(view/index (model/all)))

(defn create
	)

(defn add-tally
	)

(defn rename-tally
	)

(defn increment
	)

(defn decrement
	)

(defn reset-tally
	)

(defn reset-all
	)