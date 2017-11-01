(ns fuber-api.data.ledgers
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clj-time.core :as time]))

(def all-ledgers 
    (atom
        []))

(defn add-new-ledgers [customer-id cab-id]
    (swap! all-ledgers (fn [data]
                        (let [conj-data (conj data 
                                        {:cab-id cab-id :customer-id customer-id :time (time/now)})]
                        (sort #(time/before? (:time %1) (:time %2)) conj-data))))
     (last @all-ledgers))

(defn get-cabs-in-trip []
    (let [ledgers @all-ledgers
          unique-cabs (distinct (map #(:cab-id %) ledgers))
          booked-cabs (map (fn [cab-id]
                                (let [filtered-ledgers (filter #(= (:cab-id %) cab-id) ledgers)
                                      last-ledger (last filtered-ledgers)
                                      before-ledger (last (butlast filtered-ledgers))]
                                      (if-not (= (:customer-id last-ledger) (:customer-id before-ledger)) 
                                        cab-id))) 
                                unique-cabs)]
        (remove nil? booked-cabs)))

(defn get-customer-booked-cab-id [customer-id]
    (let [ledgers @all-ledgers
        customer-cabs (filter #(= (:customer-id %) customer-id) ledgers)
        customer-last-cab (:cab-id (last customer-cabs))
        customer-butlast-cab (:cab-id (last (butlast customer-cabs)))]
        (if-not (= customer-butlast-cab customer-last-cab) 
            customer-last-cab)))

(defn end-trip-for-customer [customer-id]
    (let [last-booked-id (get-customer-booked-cab-id customer-id)]
        (if-not (nil? last-booked-id)
            (add-new-ledgers customer-id last-booked-id))))

