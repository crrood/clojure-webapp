(ns clojure-webapp.views.tallies
	(:require 	[clojure-webapp.views.layout :as layout]
				[clojure-webapp.controllers.tallies :as controller]
				[reagent.core :as r]))

(defn controls []
	[:div {:id "controls" :class "control-bar"}
		[:input {:type "button" :value "New tally group" :on-click controller/new-tally-group}]
		[:input {:type "button" :value "New tally counter" :on-click controller/add-tally}]
		[:input {:type "button" :value "Load tally group" :on-click show-load-options}]])

(defn tally-group [tallies]
	[:div {:class "tally-group-container"}
		(map tally tallies)])

(defn tally [tally-hash]
	[:div {:class "tally-container"}
		[:div {:class "tally-name"} (tally-hash :name)]
		[:div {:class "tally-count"} (tally-hash :count)]
		[:div {:class "tally-controller-container"}
			[:input {:type "button" :class "tally-controller" :value "Reset" 
				:on-click #(controller/reset-tally (tally-hash :id))}]
			[:input {:type "button" :class "tally-controller" :value "-" 
				:on-click #(controller/decrement-tally (tally-hash :id))}]
			[:input {:type "button" :class "tally-controller" :value "+" 
				:on-click #(controller/increment-tally (tally-hash :id))}]]])

(defn show-created-tally-groups []
	[:div {:class "list-of-tally-groups"}
		[:ul
			(for [tally-group tally-groups]
				^{:key (tally-group :id)}
				[:li {:class "load-tally-group-entry" :on-click #(load-tally-group (tally-group :id))}
				(tally-group :name)])]])

(defn load-tally-group [group-id]
	)