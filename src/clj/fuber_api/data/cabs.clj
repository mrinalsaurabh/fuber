(ns fuber-api.data.cabs
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.walk :as walk]
            [utils.json :as json-utils]))

(def all-cabs 
    (atom
        [{:number "1", :driver-name "A" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}
        {:number "2", :driver-name "B" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}
        {:number "3", :driver-name "C" :color "Green" :location {:latitude 11.5 :longitude 13.5}}
        {:number "4", :driver-name "D" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}
        {:number "5", :driver-name "E" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}]))

(defn update-cab-location [cab-number longitude latitude]
    (swap! all-cabs (fn [data]
                        (map #(if (= (:number %) cab-number) 
                                        (assoc % :location {:latitude latitude :longitude longitude})
                                        %) data))))

(defn get-nearest-free-cabs 
    ([booked-list longitude latitude hippie?]
        (let [cabs-list (remove (fn [cab] (some #(= % (:number cab)) 
                                                    booked-list)) @all-cabs)
             available-cabs (if hippie? 
                                (remove #(not= "Pink" (:color %)) cabs-list) cabs-list)]
        (if-not (empty? available-cabs)
            (letfn [(distance-squared [elem longitude latitude]
                (let [x (- (:longitude (:location elem)) longitude)         
                    y (- (:latitude (:location elem)) latitude)]
                    (+ (* x x) (* y y))))]
                (reduce (fn [current-min remaining]
                            (if (< (distance-squared current-min longitude latitude)
                                (distance-squared remaining longitude latitude))
                            current-min remaining)) available-cabs)))))
    ([booked-list longitude latitude]
        (get-nearest-free-cabs booked-list longitude latitude false)))