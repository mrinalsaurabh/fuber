(ns fuber-api.services.cabs
  (:require [clj-time.core :as time]
            [clj-time.periodic :as periodic]
            [fuber-api.data.cabs :as cab-data]
            [fuber-api.data.ledgers :as ledger-data]))

(defn book-cabs [customer-id latitude longitude hippie?]
  (let [booked-cabs (ledger-data/get-cabs-in-trip)
        booking-cab (cab-data/get-nearest-free-cabs booked-cabs longitude latitude)]
        (if-not (nil? booking-cab) 
          (do 
            (ledger-data/add-new-ledgers customer-id (:number booking-cab))
            (str "Your cab " (:number booking-cab) " is on its way."))
          (do
            (str "No cabs found. Please try again later. ")))))

(defn start-trip [customer-id longitude latitude]
  (let [cab-number (ledger-data/get-customer-booked-cab-id customer-id)]
    (if-not (nil? cab-number)
      (do 
        (cab-data/update-cab-location cab-number longitude latitude)
        (str "Your trip with " cab-number " has started"))
      (do
        (str "Cannot start the trip yet.. Please book a cab first.")))))

(defn end-trip [customer-id longitude latitude]
  (let [cab-number (ledger-data/get-customer-booked-cab-id customer-id)]
    (if-not (nil? cab-number)
      (do 
        (cab-data/update-cab-location cab-number longitude latitude)
        (ledger-data/end-trip-for-customer customer-id)
        (str "Your trip with " cab-number 
          " has ended. Please pay some amount to him.. Totally upto you."))
      (do
        (str "Cannot end the trip yet.. Please book a cab first.")))))



