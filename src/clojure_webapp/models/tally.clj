(ns clojure-webapp.models.tally
	(:require 	[clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/clojure-webapp"))

(defn group-exists? [group-id]
	(< 0 (-> (sql/query spec ["select count(*) from tally_groups where groupid = ?" group-id]) first :count)))

(defn tally-exists? [tally-id]
	(< 0 (-> (sql/query spec ["select count(*) from tallies where id = ?" tally-id]) first :count)))

(defn get-tally [tally-id]
	(if (tally-exists? tally-id)
		(first (sql/query spec ["select * from tallies where id = ?" tally-id]))
		"Error: Invalid tally"))

(defn load-tally-group [^Integer group-id]
	(if (group-exists? group-id)
		(hash-map 
			:tallies (into [] (sql/query spec ["select * from tallies where groupid = ?" group-id]))
			:tally-group (first (sql/query spec ["select * from tally_groups where groupid = ?" group-id])))
		"Error: Invalid group"))

(defn add-tally [^Integer group-id]
	(if (and (group-exists? group-id)
	(< (-> (sql/query spec ["select count(*) from tallies where groupid = ?" group-id]) first :count) 5))
		(first (sql/insert! spec :tallies 
			{:groupid group-id :tallycount 0 :tallyname "New Tally"}))
		"Error: Too many tallies"))

(defn remove-tally [^Integer tally-id]
	(if (tally-exists? tally-id)
		(sql/delete! spec :tallies ["id = ?" tally-id])
		"Error: Invalid tally"))

(defn rename-tally [^Integer tally-id ^String new-name]
	(if (tally-exists? tally-id)
		(do (sql/update! spec :tallies {:tallyname new-name} ["id = ?" tally-id])
			(get-tally tally-id))
		"Error: Invalid tally"))

(defn increment-tally [^Integer tally-id]
	(if (tally-exists? tally-id)
		(do (sql/execute! spec ["update tallies set tallycount = (tallycount + 1) where id = ?" tally-id])
			(get-tally tally-id))
		"Error: Invalid tally"))

(defn decrement-tally [^Integer tally-id]
	(if (tally-exists? tally-id)
		(do (sql/execute! spec ["update tallies set tallycount = (tallycount - 1) where id = ? and tallycount > 0" tally-id])
			(get-tally tally-id))
		"Error: Invalid tally"))

(defn reset-tally [^Integer tally-id]
	(if (tally-exists? tally-id)
		(do (sql/update! spec :tallies {:tallycount 0} ["id = ?" tally-id])
			(get-tally tally-id))
		"Error: Invalid tally"))

(defn reset-all-in-group [^Integer group-id]
	(if (group-exists? group-id)
		(do (sql/update! spec :tallies {:tallycount 0} ["groupid = ?" group-id])
			(load-tally-group group-id))
		"Error: Invalid tally"))

(defn new-tally-group [] 
	(let [result-map {
			:tally-group (-> (sql/insert! spec :tally_groups {:groupname "New Tally Group"}) 
				first)}]
		(merge result-map {:tally (add-tally (-> result-map :tally-group :groupid))})))

(defn get-list-of-tally-groups []
	(into [] (sql/query spec 
		["select * from tally_groups order by groupid desc"])))

(defn rename-tally-group [^Integer group-id ^String new-name]
	(if (group-exists? group-id)
		(do (sql/update! spec :tally_groups 
				{:groupname new-name} ["groupid = ?" group-id])
			(first (sql/query spec
				["select * from tally_groups where groupid = ? limit 1" group-id])))
		"Error: Invalid group"))