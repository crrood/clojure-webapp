(ns clojure-webapp.models.tally-model-test
	(:require	[clojure.test :refer :all]
				[clojure-webapp.models.tally :as model]
				[clojure-webapp.models.migration :as migration]))

(deftest test-tally-model
	(migration/reset-db)
	(migration/migrate)

	(testing "new-tally-group"
		(let [response (model/new-tally-group)]
			(is (not= (type response) java.lang.String))
			(is (some? (response :tally-group)))
			(is (some? (response :tally)))
			(is (= 1 ((response :tally-group) :groupid)))
			(is (= "New Tally Group" (-> response :tally-group :groupname)))))

	(testing "get-tally-group-info"
		(let [response (model/get-tally-group-info 1)]
			(is (not= (type response) java.lang.String))
			(is (= 1 (response :groupid)))
			(is (= "New Tally Group" (response :groupname)))))

	(testing "get-tally"
		(let [response (model/get-tally 1)]
			(is (not= (type response) java.lang.String))
			(is (= 1 (response :id)))))
	
	(model/new-tally-group)

	(testing "get-list-of-tally-groups"
		(let [response (model/get-list-of-tally-groups)]
			(is (not= (type response) java.lang.String))
			(is (= 2 (count response)))))

	(testing "add-tally"
		(let [response (model/add-tally 2)]
			(is (not= (type response) java.lang.String))
			(is (= 3 (response :id)))
			(is (= 0 (response :tallycount)))
			(is (= 2 (response :groupid)))
			(is (= "New Tally" (response :tallyname)))))
	
	(model/add-tally 2)

	(testing "load-tally-group"
		(let [response (model/load-tally-group 2)]
			(is (not= (type response) java.lang.String))
			(is (= 3 (count (response :tallies))))
			(is (= 2 (-> response :tallies first :groupid)))
			(is (= "New Tally Group" (-> response :tally-group :groupname)))))

	(testing "rename-tally-group"
		(let [response (model/rename-tally-group 2 "new group name")]
			(is (not= (type response) java.lang.String))
			(is (= 2 (response :groupid)))
			(is (= "new group name" (response :groupname)))))

	(testing "remove-tally"
		(let [response (model/remove-tally 2)]
			(is (not= (type response) java.lang.String))
			(is (nil? (some #{2} (map :id (model/load-tally-group 2)))))))

	(testing "rename-tally"
		(let [response (model/rename-tally 3 "new tally name")]
			(is (not= (type response) java.lang.String))
			(is (= 3 (response :id)))
			(is (= "new tally name" (response :tallyname)))))

	(testing "increment-tally"
		(let [response (model/increment-tally 3)]
			(is (not= (type response) java.lang.String))
			(is (= 3 (response :id)))
			(is (= 1 (response :tallycount)))))

	(model/decrement-tally 3)
	(testing "decrement-tally"
		(let [response (model/decrement-tally 3)]
			(is (not= (type response) java.lang.String))
			(is (= 3 (response :id)))
			(is (= 0 (response :tallycount)))))

	(model/increment-tally 3)
	(testing "reset-tally"
		(let [response (model/reset-tally 3)]
			(is (not= (type response) java.lang.String))
			(is (= 3 (response :id)))
			(is (= 0 (response :tallycount)))))

	(model/increment-tally 3)
	(model/increment-tally 4)
	(testing "reset-all-in-group"
		(let [response (model/reset-all-in-group 2)]
			(is (not= (type response) java.lang.String))
			(is (= 2 (-> response :tally-group :groupid)))
			(is (= 0 (-> response :tallies first :tallycount)))
			(is (= 0 (-> response :tallies last :tallycount))))))