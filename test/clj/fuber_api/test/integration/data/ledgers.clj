(ns fuber-api.test.integration.data.ledgers
    (:require [clojure.test :refer :all] 
              [fuber-api.data.ledgers :as ledgers]
              [clj-time.core :as time]
              [fuber-api.test.utils.exception :refer [is-type?]]))
  
(deftest test-add-new-ledgers
    (let [time-now (time/date-time 2017 1 1)]
        (with-redefs [time/now (fn [] time-now)]
            (testing "should add data to the ledgers"
                (let [expected-data [{:cab-id "1" :customer-id "1" :time time-now}]]
                    (is (= (first expected-data) (ledgers/add-new-ledgers "1" "1"))) 
                    (is (= expected-data @ledgers/all-ledgers))))))
    (testing "should always keep data sorted"
        (let [all-cab-ids ["1" "3"]]
            (ledgers/add-new-ledgers "2" "3")
            (is (= all-cab-ids (map #(:cab-id %) @ledgers/all-ledgers))))))

(deftest test-get-cabs-in-trip
    (testing "should get all cabs currently in trip"
        (is (= ["3"] (ledgers/get-cabs-in-trip)))))

(deftest test-end-trip-for-customer
    (testing "should end trip for the customer, if trip is ongoing"
        (ledgers/end-trip-for-customer "1")
        (is (= (:customer-id (last @ledgers/all-ledgers)) "1"))
        (is (= (count @ledgers/all-ledgers) 3)))
    (testing "should not end trip for the customer multiple times"
        (ledgers/end-trip-for-customer 1)
        (is (= (count @ledgers/all-ledgers) 3))))
    
