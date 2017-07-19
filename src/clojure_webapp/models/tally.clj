(ns clojure-webapp.models.tally
	(:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/clojure-webapp"))

(defn new-tally-group
	([] 
		(sql/insert! spec :tally_groups 
			{:groupname "New Tally Group"}))
	([^String group-name] 
		(sql/insert! spec :tally_groups 
			{:groupname group-name})))

(defn get-tally-groups []
	(into [] (sql/query spec 
		["select * from tally_groups order by groupid desc"])))

(defn load-tally-group [group-id]
	)

(defn rename-tally-group [^Integer group-id ^String new-name]
	(sql/update! spec :tally_groups 
		{:groupname new-name} ["groupid = ?" group-id]))

(defn add-tally
	([group-id] 
		(sql/insert! spec :tallies 
			{:groupid group-id :tallycount 0 :tallyname "New Tally"}))
	([group-id tally-name] 
		(sql/insert! spec :tallies 
			{:groupid group-id :tallycount 0 :tallyname tally-name})))

(defn remove-tally [tally-id]
	(sql/delete! spec :tallies ["id = ?" tally-id]))

(defn rename-tally [tally-id new-name]
	(sql/update! spec :tallies {:tallyname new-name} ["id = ?" tally-id]))

(defn increment-tally [tally-id]
	)

(defn decrement-tally [tally-id]
	)

(defn reset-tally [tally-id]
	(sql/update! spec :tallies {:tallycount 0} ["id = ?" tally-id]))

(defn reset-all-in-group [group-id]
	)