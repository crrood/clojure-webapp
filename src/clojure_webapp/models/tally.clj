(ns clojure-webapp.models.tally
	(:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/clojure-webapp"))

(defn new-tally-group []
	)

(defn get-tally-groups []
	(into [] (sql/query spec ["select * from tally_groups order by groupid desc"])))

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