(ns clojure-webapp.models.migration
	(:require 	[clojure.java.jdbc :as sql]
				[clojure-webapp.models.tally :as tally]))

(defn migrated? []
	(-> (sql/query tally/spec
		[(str "select count(*) from information_schema.tables "
			"where table_name='tallies' or table_name='tally_groups'")])
		first :count (= 2)))

(defn migrate []
	(when (not (migrated?))
		(try
			(print "Creating database structure...")
			(sql/db-do-commands tally/spec
				(sql/create-table-ddl
					:tallies
					[[:id :serial "PRIMARY KEY"]
					[:groupid :integer]
					[:tallycount :integer "NOT NULL"]
					[:tallyname :varchar]]))
			(sql/db-do-commands tally/spec
				(sql/create-table-ddl
					:tally_groups
					[[:groupid :serial "PRIMARY KEY"]
					[:groupname :varchar]]))
			(println " done")
			(catch Exception e (.getNextException e)))))

(defn seed []
	(when (and 
	(migrated?) 
	(-> (sql/query tally/spec ["select count(*) from tallies"]) first :count zero?)
	(-> (sql/query tally/spec ["select count(*) from tally_groups"]) first :count zero?))
		(print "Seeding starting database values...")
		(tally/new-tally-group)
		(tally/add-tally 1)
		(tally/add-tally 1)
		(println "done")))

(defn reset-db []
	(when (migrated?)
		(print "Dropping tables from database...")
		(sql/db-do-commands tally/spec "drop table tallies")
		(sql/db-do-commands tally/spec "drop table tally_groups")
		(println "done")))

(defn reseed []
	(reset-db)
	(migrate)
	(seed))