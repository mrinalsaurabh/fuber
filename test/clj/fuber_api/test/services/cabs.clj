(ns fuber-api.test.services.cabs
  (:require [clojure.test :refer :all] 
            [fuber-api.services.cabs :as cabs-service]
            [fuber-api.data.cabs :as cab-data]
            [fuber-api.data.ledgers :as ledger-data]))

(deftest test-book-cabs
      (testing "Booking should go through when cabs are available"
            (with-redefs [ledger-data/get-cabs-in-trip (fn [] [])
                          cab-data/get-nearest-free-cabs (fn [booked-cabs longitude latitude hippie?]
                                                                  {:number "3", :driver-name "C" :color "Pink" 
                                                                  :location {:latitude 11.5 :longitude 13.5}})
                          ledger-data/add-new-ledgers (fn [customer-id cab-number])]
                  (is (= (cabs-service/book-cabs 1 1 1 true) 
                        "Your cab 3 is on its way."))))

      (testing "Booking should go through when cabs are available"
            (with-redefs [ledger-data/get-cabs-in-trip (fn [] [])
                         cab-data/get-nearest-free-cabs (fn [booked-cabs longitude latitude hippie?])]
                  (is (= (cabs-service/book-cabs 1 1 1 true) 
                        "No cabs found. Please try again later. ")))))

(deftest test-start-trip
      (testing "Shoud start the trip if cab number is available"
            (with-redefs [ledger-data/get-customer-booked-cab-id (fn [id] 
                                                                  "5")
                          cab-data/update-cab-location (fn [number longitude latitude])]
                  (is (= "Your trip with 5 has started" (cabs-service/start-trip "1" 1 1)))))
                          
      (testing "Shoud not start the trip if cab number is not available"
            (with-redefs [ledger-data/get-customer-booked-cab-id (fn [id])]
                  (is (= "Cannot start the trip yet.. Please book a cab first." (cabs-service/start-trip "1" 1 1))))))

(deftest test-end-trip
      (testing "Shoud end the trip if cab number is available"
            (with-redefs [ledger-data/get-customer-booked-cab-id (fn [id] 
                                                                  "5")
                          cab-data/update-cab-location (fn [number longitude latitude])
                          ledger-data/end-trip-for-customer (fn [id])]
                  (is (= "Your trip with 5 has ended. Please pay some amount to him.. Totally upto you." 
                        (cabs-service/end-trip "1" 1 1)))))
                              
      (testing "Shoud not the trip if cab number is not available"
            (with-redefs [ledger-data/get-customer-booked-cab-id (fn [id])]
                  (is (= "Cannot end the trip yet.. Please book a cab first." (cabs-service/end-trip "1" 1 1))))))