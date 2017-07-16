(ns clojure-webapp.controllers.tallies
	(:require	[compojure.core :refer [defroutes GET POST]]
				[clojure.string :as str]
				[ring.util.response :as ring]
				[clojure-webapp.views.tallies :as view]
				[clojure-webapp.models.tallies :as model]))

(defn new-tally-group []
	)

(defn get-list-of-tally-groups []
	)

(defn load-tally-group [group-id]
	)

(defn add-tally [group-id]
	)

(defn remove-tally [tally-id]
	)

(defn rename-tally [tally-id new-name]
	)

(defn increment-tally [tally-id]
	)

(defn decrement-tally [tally-id]
	)

(defn reset-tally [tally-id]
	)

(defn reset-all-in-group [group-id]
	)