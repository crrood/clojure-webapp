(ns clojure-webapp.models.migration
	(:require [clojure.java.jdbc :as sql]
		[clojure-webapp.models.tally :as tally]))

(defn migrated? []
	(-> (sql/query clojure-webapp/spec
		[(str "select count(*) from information_schema.tables "
			"where table_name='tallies'")])
		first :count pos?))

(defn migrate []
	(when (not (migrated?))
		(print "Creating database structure...") (flush)
		(sql/db-do-commands tally/spec
			(sql/create-table-ddl
				:tallies
				[[:id :serial "PRIMARY KEY"]
				[:group-id :integer]
				[:tally-count :integer "NOT NULL"]
				[:tally-name :varchar]])
			(sql/create-table-ddl
				:tally_groups
				[[:group-id :serial "PRIMARY KEY"]
				[:group-name :varchar]]))
		(println " done")))