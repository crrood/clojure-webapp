(ns clojure-webapp.models.tally
	(:require 	[clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/clojure-webapp"))

(defn new-tally-group
	([] 
		(let [result-map {
				:tally-group (-> (sql/insert! spec :tally_groups {:groupname "New Tally Group"}) 
					first)}]
			(merge result-map {:tally (add-tally ((result-map :tally-group) :groupid))})))
	([^String group-name] 
		(let [result-map {
				:tally-group (-> (sql/insert! spec :tally_groups {:groupname group-name}) 
					first)}]
			(merge result-map {:tally (add-tally ((result-map :tally-group) :groupid))}))))

(defn get-tally-groups []
	(into [] (sql/query spec 
		["select * from tally_groups order by groupid desc"])))

(defn load-tally-group [^Integer group-id]
	(into [] (sql/query spec
		["select * from tallies where groupid = ?" group-id])))

(defn rename-tally-group [^Integer group-id ^String new-name]
	(sql/update! spec :tally_groups 
		{:groupname new-name} ["groupid = ?" group-id]))

(defn add-tally
	([^Integer group-id] 
		(sql/insert! spec :tallies 
			{:groupid group-id :tallycount 0 :tallyname "New Tally"}))
	([^Integer group-id ^String tally-name] 
		(sql/insert! spec :tallies 
			{:groupid group-id :tallycount 0 :tallyname tally-name})))

(defn remove-tally [^Integer tally-id]
	(sql/delete! spec :tallies ["id = ?" tally-id]))

(defn rename-tally [^Integer tally-id ^String new-name]
	(sql/update! spec :tallies {:tallyname new-name} ["id = ?" tally-id]))

(defn increment-tally [^Integer tally-id]
	(sql/execute! spec ["update tallies set tallycount = (tallycount + 1) where id = ?" tally-id]))

(defn decrement-tally [^Integer tally-id]
	(sql/execute! spec ["update tallies set tallycount = (tallycount - 1) where id = ? and tallycount > 0" tally-id]))

(defn reset-tally [^Integer tally-id]
	(sql/update! spec :tallies {:tallycount 0} ["id = ?" tally-id]))

(defn reset-all-in-group [^Integer group-id]
	(sql/update! spec :tallies {:tallycount 0} ["groupid = ?" group-id]))