(ns clojure-webapp.controllers.tallies
	(:require	[compojure.core :refer [defroutes GET POST]]
				[ring.util.response :as ring]
				[clojure-webapp.views.tallies :as view]
				[clojure-webapp.models.tallies :as model]))

(defroutes routes
	(GET "/:group-id" [group-id] (view-index group-id))
	(POST "/new-tally-group" [] (new-tally-group))
	(GET "/get-tally-groups" [] (get-tally-groups))
	(GET "/load-tally-group" [] :query-params [group-id :- Long] (load-tally-group group-id))
	(POST "/rename-tally-group" [] :body-params [group-id :- Long new-name :- String] (rename-tally-group group-id new-name))
	(POST "/add-tally" [] :body-params [group-id :- Long])
	(POST "/remove-tally" [] :body-params [tally-id :- Long])
	(POST "/rename-tally" [] :body-params [tally-id :- Long new-name :- String])
	(POST "/increment-tally" [] :body-params [tally-id :- Long])
	(POST "/decrement-tally" [] :body-params [tally-id :- Long])
	(POST "/reset-tally" [] :body-params [tally-id :- Long])
	(POST "/reset-all-in-group" [] :body-params [group-id :- Long]))

(defn view-index [group-id]
	view/view-index)

(defn new-tally-group []
	)

(defn get-tally-groups []
	)

(defn load-tally-group [group-id]
	)

(defn rename-tally-group [group-id new-name]
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