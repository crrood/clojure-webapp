(ns clojure-webapp.models.migration
	(:require [clojure.java.jdbc :as sql]
		[clojure-webapp.models.tally :as tally]))

(defn migrated? []
	(-> (sql/query tally/spec
		[(str "select count(*) from information_schema.tables "
			"where table_name='tallies'")])
		first :count pos?))

(defn migrate []
	(when (not (migrated?))
		(try
			(print "Creating database structure...") (flush)
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