(ns clojure-webapp.views.tallies
	(:require 	[clojure-webapp.views.layout :as layout]
				[reagent.core :as r]))

(defn new-tally-group []
	)

(defn get-tally-groups []
	)

(defn add-tally [group-id]
	)

(defn load-tally-group [group-id]
	)

(defn controls []
	[:div {:id "controls" :class "control-bar"}
		[:input {:type "button" :value "New tally group" :on-click new-tally-group}]
		[:input {:type "button" :value "Load tally group" :on-click get-tally-groups}]]
		[:input {:type "button" :value "New tally counter" :on-click #(add-tally group-id)}])

(defn tally-group [group-id tallies]
	[:div {:class "tally-group-container"}
		(into [] (map tally tallies)]))

(defn tally [tally-hash]
	[:div {:class "tally-container"}
		[:div {:class "tally-name"} (tally-hash :tallyname)]
		[:div {:class "tally-count"} (tally-hash :tallycount)]
		[:div {:class "tally-controller-container"}
			[:input {:type "button" :class "tally-controller reset-btn" :value "Reset" 
				:on-click #(controller/reset-tally (tally-hash :id))}]
			[:input {:type "button" :class "tally-controller increment-btn" :value "-" 
				:on-click #(controller/decrement-tally (tally-hash :id))}]
			[:input {:type "button" :class "tally-controller decrement-btn" :value "+" 
				:on-click #(controller/increment-tally (tally-hash :id))}]]])

(defn show-created-tally-groups []
	[:div {:class "list-of-tally-groups"}
		[:ul
			(for [tally-group tally-groups]
				^{:key (tally-group :groupid)}
				[:li {:class "load-tally-group-entry" :on-click #(load-tally-group (tally-group :groupid))}
				(tally-group :groupname)])]])

(defn load-tally-group [group-id]
	)

(defn view-index [group-id]
	(layout/common "Tallies"
					(controls)
					(tally-group group-id (load-tally-group group-id))))